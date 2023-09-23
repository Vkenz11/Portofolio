import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Prescription {
	private String prescriptionID;
	ArrayList <Medicine> medicineList = new ArrayList <>();
	
	public Prescription(String prescriptionID, ArrayList<Medicine> medicineList) {
		this.prescriptionID = prescriptionID;
		this.medicineList = medicineList;
	}

	public String getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(String prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public ArrayList<Medicine> getMedicineList() {
		return medicineList;
	}

	public void setMedicineList(ArrayList<Medicine> medicineList) {
		this.medicineList = medicineList;
	}

	public static Prescription getPrescription(ArrayList<Prescription> prescriptionList, String prescriptionID){

        int index = -1;

        for(int i = 0; i < prescriptionList.size(); i++){
            if(prescriptionList.get(i).getPrescriptionID().equals(prescriptionID)){
                index = i;
                break;
            }
        }

        return prescriptionList.get(index);
    }

	public static void loadPrescription(ArrayList<Prescription> prescriptionList, ArrayList<Doctor> doctorList, ArrayList<Medicine> medicineList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/PrescriptionRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/PrescriptionRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                ArrayList<Medicine> prescriptionMedicineList = new ArrayList<Medicine>();
                String[] medicineArr = detail[1].split("#");

                for(String medicineID : medicineArr){   
                    prescriptionMedicineList.add(Medicine.getMedicine(medicineList, medicineID));
                }

                prescriptionList.add(new Prescription(detail[0], prescriptionMedicineList));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("PrescriptionRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void updatePrescriptionDatabase(ArrayList<Prescription> prescriptionList){

        try {
            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/PrescriptionRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/PrescriptionRecords.csv",false));
            }

            bw.write("");

            for(int i = 0; i < prescriptionList.size(); i++){

                String prescriptionID = prescriptionList.get(i).getPrescriptionID();
                String medicineString = "";

                ArrayList<Medicine> medicineList = prescriptionList.get(i).getMedicineList();

                for(int j = 0; j < medicineList.size(); j++){
                    medicineString = medicineString + medicineList.get(j).getMedicineID();

                    if(j < medicineList.size() - 1){
                        medicineString = medicineString + "#";
                    }
                }

                String writeString = String.format("%s,%s", prescriptionID, medicineString);
                
                try {
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/PrescriptionRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/PrescriptionRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("PrescriptionRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("PrescriptionRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

	
}
