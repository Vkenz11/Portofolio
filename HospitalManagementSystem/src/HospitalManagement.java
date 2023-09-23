import java.util.ArrayList;
import java.util.Scanner;

public class HospitalManagement {

    public HospitalManagement(){};

    public static void main(String[] args) throws Exception {

        //LOAD DISEASE DATA
        ArrayList<Disease> diseaseList = new ArrayList<>();
        Disease.loadDisease(diseaseList);
        
        //LOAD PATIENT DATA
        ArrayList<Patient> patientList = new ArrayList<>();
        Patient.loadPatient(patientList);

        //LOAD ADMIN DATA
        ArrayList<Admin> adminList = new ArrayList<>();
        Admin.loadAdmin(adminList);

        //LOAD RECEPTIONIST DATA
        ArrayList<Receptionist> receptionistList = new ArrayList<>();
        Receptionist.loadReceptionist(receptionistList);

        //LOAD DOCTOR DATA
        ArrayList<Doctor> doctorList = new ArrayList<>();
        Doctor.loadDoctors(doctorList);

        //LOAD PHARMACIST DATA
        ArrayList<Pharmacist> pharmacistList = new ArrayList<>();
        Pharmacist.loadPharmacist(pharmacistList);

        //LOAD MEDICINE DATA
        ArrayList<Medicine> medicineList = new ArrayList<>();
        Medicine.loadMedicine(medicineList);

        //LOAD PRESCRIPTION DATA
        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        Prescription.loadPrescription(prescriptionList, doctorList, medicineList);

        //LOAD APPOINTMENT DATA
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        Appointment.loadAppointment(patientList, doctorList, appointmentList, prescriptionList, diseaseList);

        //LOAD DOCTOR APPOINTMENTS
        Doctor.loadAppointmentListDoctor(doctorList, appointmentList);

        //LOAD BILLING DATA
        ArrayList<Billing> billingList = new ArrayList<>();
        Billing.loadBilling(billingList, appointmentList);
        
        mainMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
    }

    public static void mainMenu(ArrayList<Patient> patientList, ArrayList<Receptionist> receptionistList, ArrayList<Admin> adminList, ArrayList<Doctor> doctorList, ArrayList<Pharmacist> pharmacistList, ArrayList<Medicine> medicineList, ArrayList<Prescription> prescriptionList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Disease> diseaseList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        int menu = 0;

        System.out.println("+==============================+");
        System.out.println("|             MENU             |");
        System.out.println("+==============================+");
        System.out.println("| 1. Login                     |");
        System.out.println("| 2. Exit                      |");
        System.out.println("+==============================+");
        System.out.print(">> ");

        try {
            menu = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid Input");
            mainMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
        }        

        switch (menu) {
            case 1:
                roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                break;
            case 2:
                System.out.println("Thank You!");
                System.exit(0);
                break;
            default:
                mainMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                break;
        }

        
        scanner.close();
    }

    public static void roleMenu(ArrayList<Patient> patientList, ArrayList<Receptionist> receptionistList, ArrayList<Admin> adminList, ArrayList<Doctor> doctorList, ArrayList<Pharmacist> pharmacistList, ArrayList<Medicine> medicineList, ArrayList<Prescription> prescriptionList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Disease> diseaseList){
        
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        int menu = 0;

        System.out.println("+==============================+");
        System.out.println("|             MENU             |");
        System.out.println("+==============================+");
        System.out.println("| 1. Doctor                    |");
        System.out.println("| 2. Receptionist              |");
        System.out.println("| 3. Admin                     |");
        System.out.println("| 4. Pharmacist                |");
        System.out.println("| 5. Exit                      |");
        System.out.println("+==============================+");
        System.out.print(">> ");

        try {
            menu = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid Input");
            mainMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
        }        

        switch (menu) {
            case 1:
                menuRedirect(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList, 0);
                break;
            case 2:
                menuRedirect(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList, 1);
                break;
            case 3:
                menuRedirect(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList, 2);
                break;
            case 4:
                menuRedirect(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList, 3);
                break;
            case 5:
                System.out.println("Thank You!");
                System.exit(0);
            default:
                roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                break;
        }

        
        scanner.close();

    }

    public static void menuRedirect(ArrayList<Patient> patientList, ArrayList<Receptionist> receptionistList, ArrayList<Admin> adminList, ArrayList<Doctor> doctorList, ArrayList<Pharmacist> pharmacistList, ArrayList<Medicine> medicineList, ArrayList<Prescription> prescriptionList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Disease> diseaseList, int operation){
        
        Scanner scanner = new Scanner(System.in);

        String pass;
    	String user;
    		
    	do {
            System.out.print("userID:  ");
            user = scanner.nextLine();
        }while(user.length() != 5);
    		
    	do {
    		System.out.print("Password: ");
    		pass = scanner.nextLine();
        }while(pass.length() < 5);

        boolean valid = false;

        switch (operation) {
            case 0: //doctor menu
                for(int i = 0; i < doctorList.size(); i++) {
                    if(doctorList.get(i).getPassword().equals(pass) && doctorList.get(i).getDoctorID().equals(user)){
                        System.out.println("Login Successful");
                        scanner.nextLine();
                        valid = true;
                        //REDIRECT TO MENU
                        Doctor.doctorMenu(doctorList.get(i).getDoctorID(), doctorList, diseaseList, medicineList, prescriptionList, appointmentList);
                    } 
                }

                if(valid == false){
                    System.out.println("User/Password Wrong");
                    scanner.nextLine();
                    roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                }

                break;
            case 1: //receptionist menu
                for(int i = 0; i < receptionistList.size(); i++) {
                    if(receptionistList.get(i).getPassword().equals(pass) && receptionistList.get(i).getRecID().equals(user)){
                        System.out.println("Login Successful");
                        scanner.nextLine();
                        valid = true;
                        //REDIRECT TO MENU
                        Receptionist.receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                    } 
                }

                if(valid == false){
                    System.out.println("User/Password Wrong");
                    scanner.nextLine();
                    roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                }

                break;
            case 2: //admin menu
                for(int i = 0; i < adminList.size(); i++) {
                    if(adminList.get(i).getPassword().equals(pass) && adminList.get(i).getAdminID().equals(user)){
                        System.out.println("Login Successful");
                        scanner.nextLine();
                        valid = true;
                        //REDIRECT TO MENU
                        Admin.adminMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                    }
                }

                if(valid == false){
                    System.out.println("User/Password Wrong");
                    scanner.nextLine();
                    roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                }

                break;
            case 3: //pharmacist menu
                for(int i = 0; i < pharmacistList.size(); i++) {
                    if(pharmacistList.get(i).getPassword().equals(pass) && pharmacistList.get(i).getPharID().equals(user)){
                        System.out.println("Login Successful");
                        scanner.nextLine();
                        valid = true;
                        //REDIRECT TO MENU
                        Pharmacist.pharmacistMenu(appointmentList);
                    } 
                }

                if(valid == false){
                    System.out.println("User/Password Wrong");
                    scanner.nextLine();
                    roleMenu(patientList, receptionistList, adminList, doctorList, pharmacistList, medicineList, prescriptionList, appointmentList, billingList, diseaseList);
                }

                break;
            default:
                break;
        }


        scanner.close();
    }

    public static void logout(){

        String message = "Closing...";

        for(int i = 0; i < message.length(); i++){

            try{
                Thread.sleep(100);
                System.out.print(message.charAt(i));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        try {
            HospitalManagement.main(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
