import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Patient extends Person{
    String patientID;
    String bloodType;

    public Patient(){};

    public Patient(String name, String address, String gender, String phoneNumber, String email, String patientID, String bloodType){
        super(name, address, gender, phoneNumber, email, patientID);
        this.patientID = patientID;
        this.bloodType = bloodType;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public static Patient getPatient(ArrayList<Patient> patientList, String patientID){

        int index = -1;

        for(int i = 0; i < patientList.size(); i++){
            if(patientList.get(i).getPatientID().equals(patientID)){
                index = i;
                break;
            }
        }

        return patientList.get(index);
    }

    public static int searchPatient(ArrayList<Patient> patientList, String inputPatientID){

        int index = -1;

        for(int i = 0; i < patientList.size(); i++){
            if(patientList.get(i).getPatientID().equals(inputPatientID)){
                index = i;
                break;
            }
        }

        return index;
    }

    public static void loadPatient(ArrayList<Patient> patientList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/PatientRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/PatientRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");
                patientList.add(new Patient(detail[2], detail[3], detail[4], detail[5], detail[6], detail[0], detail[1]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("PatientRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }
    
    public static void updatePatientDatabase(ArrayList<Patient> patientList){

        try {
            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/PatientRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/PatientRecords.csv",false));
            }

            bw.write("");

            for(int i = 0; i < patientList.size(); i++){

                String patientID = patientList.get(i).getPatientID();
                String bloodType = patientList.get(i).getBloodType();
                String name = patientList.get(i).getName();
                String address = patientList.get(i).getAddress();
                String gender = patientList.get(i).getGender();
                String phoneNumber = patientList.get(i).getPhoneNumber();
                String email = patientList.get(i).getEmail();

                String writeString = String.format("%s,%s,%s,%s,%s,%s,%s", patientID, bloodType, name, address, gender, phoneNumber, email);
                
                try {
        
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/PatientRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/PatientRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("PatientRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("PatientRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }

        
    }
    
}