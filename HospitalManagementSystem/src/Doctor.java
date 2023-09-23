import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Doctor extends Person{
    private String doctorID;
    private String specialization;
    private ArrayList<Appointment> appointmentList = new ArrayList<>();
    private Integer doctorFee;

    public Doctor(){};

    public Doctor(String name, String address, String gender, String phoneNumber, String email, String password, String doctorID, String specialization, ArrayList<Appointment> appointmentList, Integer doctorFee){
        super(name, address, gender, phoneNumber, email, password);
        this.doctorID = doctorID;
        this.specialization = specialization;
        this.appointmentList = appointmentList;
        this.doctorFee = doctorFee;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Appointment> getAppointmentList() {
        return this.appointmentList;
    }

    public void setAppointmentList(ArrayList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public Integer getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(Integer doctorFee) {
        this.doctorFee = doctorFee;
    }
    
    public static Doctor getDoctor(ArrayList<Doctor> doctorList, String doctorID){

        int index = -1;

        for(int i = 0; i < doctorList.size(); i++){
            if(doctorList.get(i).getDoctorID().equals(doctorID)){
                index = i;
                break;
            }
        }

        return doctorList.get(index);
    }

    public static int searchDoctor(ArrayList<Doctor> doctorList, String inputDoctorID){

        int index = -1;

        for(int i = 0; i < doctorList.size(); i++){
            if(doctorList.get(i).getDoctorID().equals(inputDoctorID)){
                index = i;
                break;
            }
        }

        return index;
    }

    public static void loadDoctors(ArrayList<Doctor> doctorList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/DoctorRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/DoctorRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                ArrayList<Appointment> doctorAppointmentList = new ArrayList<Appointment>();

                doctorList.add(new Doctor(detail[0], detail[1], detail[2], detail[3], detail[4], detail[5], detail[6], detail[7], doctorAppointmentList, Integer.parseInt(detail[9])));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("DoctorRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void updateDoctorDatabase(ArrayList<Doctor> doctorList){

        try {
            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/DoctorRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/DoctorRecords.csv",false));
            }

            bw.write("");

            for(int i = 0; i < doctorList.size(); i++){

                String name = doctorList.get(i).getName();
                String address = doctorList.get(i).getAddress();
                String gender = doctorList.get(i).getGender();
                String phoneNumber = doctorList.get(i).getPhoneNumber();
                String email = doctorList.get(i).getEmail();
                String password = doctorList.get(i).getPassword();

                String doctorID = doctorList.get(i).getDoctorID();
                String specialization = doctorList.get(i).getSpecialization();

                ArrayList<Appointment> doctorAppointmentList = doctorList.get(i).getAppointmentList();
                String doctorAppointmentString = "";
                
                for(int j = 0; j < doctorAppointmentList.size(); j++){
                    doctorAppointmentString = doctorAppointmentString + doctorAppointmentList.get(j).getAppointmentID();

                    if(j < doctorAppointmentList.size() - 1){
                        doctorAppointmentString = doctorAppointmentString + "#";
                    }
                }                

                String doctorFee = String.valueOf(doctorList.get(i).getDoctorFee());

                String writeString = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", name, address, gender, phoneNumber, email, password, doctorID, specialization, doctorAppointmentString, doctorFee);
                
                try {
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/DoctorRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/DoctorRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("DoctorRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("DoctorRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }  

    public static void loadAppointmentListDoctor(ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList){

        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/DoctorRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/DoctorRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                Doctor doctor = Doctor.getDoctor(doctorList, detail[6]);
                
                String[] appointmentArr = detail[8].split("#");

                for(String appointmentID : appointmentArr){   
                    doctor.getAppointmentList().add(Appointment.getAppointment(appointmentList, appointmentID));
                }
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("DoctorRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void showDoctorAppointments(ArrayList<Appointment> doctorAppointmentList){

        System.out.println("===================================APPOINTMENT LIST====================================");
        System.out.println("|AppointmentID|Patient Name             |Consulted|PrescriptionID|Given Medicine|Done |");
        System.out.println("=======================================================================================");

        for(int i = 0; i < doctorAppointmentList.size(); i++) {

            if(!doctorAppointmentList.get(i).getAppointmentID().equals("AP00X")){
                System.out.printf("|%-13s|", doctorAppointmentList.get(i).getAppointmentID());
                System.out.printf("%-25s|", doctorAppointmentList.get(i).getPatient().getName());

                System.out.printf("%-9s|", doctorAppointmentList.get(i).getIsConsulted());
                System.out.printf("%-14s|", doctorAppointmentList.get(i).getPrescription().getPrescriptionID());
                System.out.printf("%-14s|", doctorAppointmentList.get(i).getGivenMedicine());
                System.out.printf("%-5s|", doctorAppointmentList.get(i).getIsDone());
                System.out.println();
            }

            
            
        }

        if(doctorAppointmentList.size() == 0){
            System.out.println("No Data");
        }
    }

    public static void doctorMenu(String doctorID, ArrayList<Doctor> doctorList, ArrayList<Disease> diseaseList, ArrayList<Medicine> medicineList, ArrayList<Prescription> prescriptionList, ArrayList<Appointment> appointmentList){

        ArrayList<Appointment> doctorAppointmentList = getDoctor(doctorList, doctorID).getAppointmentList();

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        showDoctorAppointments(doctorAppointmentList);

        System.out.println("1. Manage Appointment");
        System.out.println("2. Logout");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            doctorMenu(doctorID, doctorList, diseaseList, medicineList, prescriptionList, appointmentList);
        }

        switch (choice) { 
            case 1:

            String inputAppointmentID = "";
        
            System.out.print("Enter appointmentID: ");
            inputAppointmentID = scanner.nextLine();

            int indexAppointment = Appointment.searchAppointmentDoctor(doctorAppointmentList, inputAppointmentID);

            if(indexAppointment != -1){

                String symptoms = "";
                String diseaseID = "";
                ArrayList<Medicine> patientMedicineList = new ArrayList<>();
                
                do{
                    System.out.print("Enter patient symptoms [WITHOUT , & length > 10]: ");
                    symptoms = scanner.nextLine();
                }while(symptoms.length() <= 10);

                //DISEASE SELECTION
                Disease.showDisease(diseaseList);

                int indexDisease = -1;

                do{
                    System.out.print("Select disease from database: ");
                    diseaseID = scanner.nextLine();

                    indexDisease = Disease.searchDisease(diseaseList, diseaseID);
                }while(indexDisease == -1);


                //MEDICINE SELECTION
                doctorMedicineSelection(medicineList, patientMedicineList);

                //CREATE PRESCRIPTION
                String prescriptionID = "";

                String currentID = prescriptionList.get(prescriptionList.size() - 1).getPrescriptionID().substring(2, 5);
                int currentIDNumber = Integer.parseInt(currentID);

                if(currentIDNumber + 1 < 10){
                    prescriptionID = String.format("PA00%d", currentIDNumber + 1);
                } else if (prescriptionList.size() < 100){
                    prescriptionID = String.format("PA0%d", currentIDNumber + 1);
                } else {
                    prescriptionID = String.format("PA%d", currentIDNumber + 1);
                }

                Prescription prescription = new Prescription(prescriptionID, patientMedicineList);
                prescriptionList.add(prescription);

                //UPDATE APPOINTMENT
                Appointment currentAppointment = Appointment.getAppointment(doctorAppointmentList, inputAppointmentID);
                currentAppointment.setDisease(Disease.getDisease(diseaseList, diseaseID));
                currentAppointment.setSymptoms(symptoms);
                currentAppointment.setPrescription(prescription);
                currentAppointment.setIsConsulted(true);
                Appointment.updateAppointmentDatabase(appointmentList);

                //UPDATE DOCTOR
                doctorAppointmentList.remove(indexAppointment);
                doctorAppointmentList.add(Appointment.getAppointment(appointmentList, "AP00X"));
                Doctor.updateDoctorDatabase(doctorList);

                //UPDATE PRESCRIPTION
                Prescription.updatePrescriptionDatabase(prescriptionList);

                System.out.println("Patient consulted.");
                scanner.nextLine();
                
            } else {
                System.out.println("APPOINTMENT NOT FOUND!");
                scanner.nextLine();
            }

                break;
            case 2:
            HospitalManagement.logout();
                break;
            default: 
            doctorMenu(doctorID, doctorList, diseaseList, medicineList, prescriptionList, appointmentList);
                break;
        }
        doctorMenu(doctorID, doctorList, diseaseList, medicineList, prescriptionList, appointmentList);

        scanner.close();
        
    }

    public static void doctorMedicineSelection(ArrayList<Medicine> medicineList, ArrayList<Medicine> patientMedicineList){

        Scanner scanner = new Scanner(System.in);

        Boolean done = false;

        int choice;

        do{
            done = false;
            System.out.print("\033[H\033[2J");
            System.out.flush();

            Medicine.showMedicineList(medicineList);
            System.out.print("Current Prescription: ");
        
            for(int i = 0; i < patientMedicineList.size(); i++){
                System.out.print(patientMedicineList.get(i).getMedicineID() + " ");
            }
            System.out.println("\n1. Manage Prescription");
            System.out.println("2. Done");
            System.out.print(">> ");

            choice = 0;

            while(choice == 0){
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.print(">> ");
                    scanner.nextLine();
                }
            }

            switch(choice){

                case 1:

                String medicineID = "";

                System.out.print("Enter medicineID: ");
                medicineID = scanner.nextLine();

                int indexMedicine = Medicine.searchMedicine(medicineList, medicineID);

                if(indexMedicine != -1){

                    if(!patientMedicineList.contains(medicineList.get(indexMedicine))){
                        patientMedicineList.add(medicineList.get(indexMedicine));
                    } else {
                        System.out.println("Medicine already added");
                        scanner.nextLine();
                    }
                    
                } else {
                    System.out.println("Medicine does not exist");
                    scanner.nextLine();
                }
                    break;
                case 2:

                if(patientMedicineList.size() != 0){
                    done = true;
                } else {
                    System.out.println("Medicine list must not be empty");
                    scanner.nextLine();
                }
                
                    break;
                default:
                doctorMedicineSelection(medicineList, patientMedicineList);
                    break;
            }

        }while(done == false);
    }
}
