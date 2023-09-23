import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Medicine {
    public String medicineID, medicineName, medicineDescription, medicineInstruction;
	public int medicineQuantity, medicinePrice;
	
	Medicine(String medicineID, String medicineName, int medicineQuantity, String medicineDescription, String medicineInstruction, int medicinePrice) {
		this.medicineID = medicineID;
		this.medicineName = medicineName;
		this.medicineQuantity = medicineQuantity;
		this.medicineDescription = medicineDescription;
		this.medicineInstruction = medicineInstruction;
		this.medicinePrice = medicinePrice;
	}
	
	public String getMedicineID() {
		return medicineID;
	}
	
	public String getMedicineName() {
		return medicineName;
	}
	
	public int getMedicineQuantity() {
		return medicineQuantity;
	}
	
	public String getMedicineDescription() {
		return medicineDescription;
	}
	
	public String getMedicineInstruction() {
		return medicineInstruction;
	}
	
	public int getMedicinePrice() {
		return medicinePrice;
	}

	public static Medicine getMedicine(ArrayList<Medicine> medicineList, String medicineID){

        int index = -1;

        for(int i = 0; i < medicineList.size(); i++){
            if(medicineList.get(i).getMedicineID().equals(medicineID)){
                index = i;
                break;
            }
        }

        return medicineList.get(index);
    }

    public static int searchMedicine(ArrayList<Medicine> medicineList, String medicineID){

        int index = -1;

        for(int i = 0; i < medicineList.size(); i++){
            if(medicineList.get(i).getMedicineID().equals(medicineID)){
                index = i;
                break;
            }
        }

        return index;
    }

	public static void loadMedicine(ArrayList<Medicine> medicineList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/MedicineRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/MedicineRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                medicineList.add(new Medicine(detail[0], detail[1], Integer.parseInt(detail[2]), detail[3], detail[4], Integer.parseInt(detail[5])));

            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("MedicineRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void showMedicineList(ArrayList<Medicine> medicineList){
        System.out.println("=====================MEDICINE LIST=====================");
        System.out.println("|MedicineID|Medicine Name            |Quantity|Price  |");
        System.out.println("=======================================================");

        for(int i = 0; i < medicineList.size(); i++) {
            
            System.out.printf("|%-10s|", medicineList.get(i).getMedicineID());
            System.out.printf("%-25s|", medicineList.get(i).getMedicineName());
            System.out.printf("%-8s|", medicineList.get(i).getMedicineQuantity());
            System.out.printf("%-7s|", medicineList.get(i).getMedicinePrice());
            System.out.println();
            
        }

        if(medicineList.size() == 0){
            System.out.println("No Data");
        }

    }
}
