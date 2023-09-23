import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Receptionist extends Person{
    String recID;

    public Receptionist(String name, String address, String gender, String phoneNumber, String email, String password, String recID){
        super(name, address, gender, phoneNumber, email, password);
        this.recID = recID;
    }

    public String getRecID() {
        return this.recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }
    
    public static Receptionist getReceptionist(ArrayList<Receptionist> receptionistList, String recID){

        int index = -1;

        for(int i = 0; i < receptionistList.size(); i++){
            if(receptionistList.get(i).getRecID().equals(recID)){
                index = i;
                break;
            }
        }

        return receptionistList.get(index);
    }

    public static int searchReceptionist(ArrayList<Receptionist> receptionistList, String inputRecID){

        int index = -1;

        for(int i = 0; i < receptionistList.size(); i++){
            if(receptionistList.get(i).getRecID().equals(inputRecID)){
                index = i;
                break;
            }
        }

        return index;
    }

    public static void loadReceptionist(ArrayList<Receptionist> receptionistList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/ReceptionistRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/ReceptionistRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                receptionistList.add(new Receptionist(detail[0], detail[1], detail[2], detail[3], detail[4], detail[5], detail[6]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ReceptionistRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }
    
    public static void updateReceptionistDatabase(ArrayList<Receptionist> receptionistList){

        try {
            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/ReceptionistRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/ReceptionistRecords.csv",false));
            }

            bw.write("");

            for(int i = 0; i < receptionistList.size(); i++){

                String name = receptionistList.get(i).getName();
                String address = receptionistList.get(i).getAddress();
                String gender = receptionistList.get(i).getGender();
                String phoneNumber = receptionistList.get(i).getPhoneNumber();
                String email = receptionistList.get(i).getEmail();
                String password = receptionistList.get(i).getPassword();

                String recID = receptionistList.get(i).getRecID();

                String writeString = String.format("%s,%s,%s,%s,%s,%s,%s", name, address, gender, phoneNumber, email, password, recID);
                
                try {
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/ReceptionistRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/ReceptionistRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("ReceptionistRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ReceptionistRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void receptionMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");

        String currentDateTime = dateTime.format(dateTimeFormat);

        System.out.println("Reception Menu");
        System.out.println(currentDateTime);

        System.out.println("1. Manage Patient");
        System.out.println("2. Manage Appointment");
        System.out.println("3. Create Payment");
        System.out.println("4. Logout");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        switch(choice){
            case 1:
                managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 2:
                manageAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 3:
                createPayment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 4:
                HospitalManagement.logout();
                break;
            default:
                receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
        }

        scanner.close();
    }

    public static void managePatients(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Show All Patients");
        System.out.println("2. Register Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Back");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        switch(choice){
            case 1:
                if(patientList.size() == 0){
                    System.out.println("NO PATIENT DATA");
                    scanner.nextLine();
                    managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                } else {
                    showPatients(patientList);
                }

                System.out.println("Press any key to continue...");
                scanner.nextLine();
                managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);

                break;
            case 2:
                registerPatient(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 3:
                if(patientList.size() == 0){
                    System.out.println("NO PATIENT DATA");
                    scanner.nextLine();
                    managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                } else {
                    deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);
                }

                break;
            case 4:
                if(patientList.size() == 0){
                    System.out.println("NO PATIENT DATA");
                    scanner.nextLine();
                    managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                } else {
                    deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);
                }

                break;
            case 5:
                receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            default:
                managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
        }

        scanner.close();
    }

    public static void showPatients(ArrayList<Patient> patientList){
        
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("=============================================PATIENTS=====================================================================");
        System.out.println("|PatientID|BloodType|Name                  |Address                  |Gender|Phone Number |Email                         |");
        System.out.println("==========================================================================================================================");

        for(int i = 0; i < patientList.size(); i++){
            
            if(!patientList.get(i).getPatientID().equals("PA00X")){
                System.out.printf("|%-9s|", patientList.get(i).getPatientID());
                System.out.printf("%-9s|", patientList.get(i).getBloodType());
                System.out.printf("%-22s|", patientList.get(i).getName());
                System.out.printf("%-25s|", patientList.get(i).getAddress());
                System.out.printf("%-6s|", patientList.get(i).getGender());
                System.out.printf("%-13s|", patientList.get(i).getPhoneNumber());
                System.out.printf("%-30s|", patientList.get(i).getEmail());
                System.out.println();
            }
            
        }
    }


    public static void registerPatient(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        String name = "", bloodType = "", address = "", gender = "", phoneNumber = "", email = "";
        Boolean valid = true;

        do{
            valid = true;

            System.out.print("Enter patient name: ");
            name = scanner.nextLine();
            

            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                System.out.println("Invalid name! Try again!");
                valid = false;
            } 

        } while(!valid);

        do{
            valid = true;

            System.out.print("Enter patient blood type: ");
            bloodType = scanner.nextLine();

            if(!bloodType.equals("A") && !bloodType.equals("B") && !bloodType.equals("AB") && !bloodType.equals("O")){
                System.out.println("Invalid blood type! Try again!");
                valid = false;
            } 

        } while(!valid);



        do{
            valid = true;

            System.out.print("Enter patient address: ");
            address = scanner.nextLine();
            
            if((address.length() < 10)){
                System.out.println("Invalid address! Try again!");
                valid = false;
            } 

        } while(!valid);

        do{

            valid = true;

            System.out.print("Input gender [Male || Female]: ");
            gender = scanner.nextLine();

            if(!gender.equals("Male") && !gender.equals("Female")){
                System.out.println("Invalid gender! Try again!");
                valid = false;
            } 

        }while(!valid);

        do{

            valid = true;

            System.out.print("Input Phone Number: ");
            phoneNumber = scanner.nextLine();

            for(int i = 0; i < patientList.size(); i++){
                if(patientList.get(i).getPhoneNumber().equals(phoneNumber)){
                    System.out.println("Duplicate phone number prohibited!");
                    valid = false;
                    break;
                }
            }

            if(phoneNumber.length() < 10 || phoneNumber.length() > 13 || hasLetter(phoneNumber)){
                System.out.println("Invalid number! Try again!");
                valid = false;
            } 

        }while(!valid);

        do{ 

            valid = true;

            System.out.print("Input Email: ");
            email = scanner.nextLine();

            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";

            if(!Pattern.matches(regex, email)){
                System.out.println("Invalid email! Try again!");
                valid = false;
            }else {
                for(int i = 0; i < patientList.size(); i++){
                    if(patientList.get(i).getEmail().equals(email)){
                        System.out.println("Duplicate email prohibited!");
                        valid = false;
                        break;
                    }
                }
            }
        }while(!valid);
        
        
        System.out.println("Name: "  + name);
        System.out.println("Address: " + address);
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);

        System.out.print("Reenter details? [Y/N]: ");

        String input = "";

        do{
            valid = true;

            input = scanner.nextLine();

            if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                System.out.println("Invalid input! Try again!");
                valid = false;
            }

        } while(!valid);

        String patientID = "";

        String currentID = patientList.get(patientList.size() - 1).getPatientID().substring(2, 5);
        int currentIDNumber = Integer.parseInt(currentID);

        if(currentIDNumber + 1 < 10){
            patientID = String.format("PA00%d", currentIDNumber + 1);
        } else if (patientList.size() < 100){
            patientID = String.format("PA0%d", currentIDNumber + 1);
        } else {
            patientID = String.format("PA%d", currentIDNumber + 1);
        }

        if(input.toLowerCase().equals("y")){
            registerPatient(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        } else {

            patientList.add(new Patient(name, address, gender, phoneNumber, email, patientID, bloodType));

            Patient.updatePatientDatabase(patientList);

            System.out.println("Added patient to database!");
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        scanner.close();
    }

    public static Boolean hasNumber(String str){
        
        Boolean numberExist = false;

        for(int i = 0; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i))){
                numberExist = true;
                break;
            }
        }

        return numberExist;
    }

    public static Boolean validSpace(String str){
        
        int count = 0;

        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == ' ') count++;
        }

        if(count < 1){
            return true;
        } else {
            return false;
        }
    }

    public static Boolean hasLetter(String str){
        
        Boolean letterExist = false;

        for(int i = 0; i < str.length(); i++){
            if(Character.isLetter(str.charAt(i))){
                letterExist = true;
                break;
            }
        }

        return letterExist;
    }

    public static void deleteUpdatePatientMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, int operation, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        showPatients(patientList);

        if(operation == 1){
            System.out.println("1. Delete Patient");
        } else {
            System.out.println("1. Update Patient");
        }

        System.out.println("2. Back");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            scanner.nextLine(); scanner.nextLine();
            if(operation == 1){
                deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);
            } else {
                deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);
            }
        }
        
        switch(choice){
            case 1:
                if(operation == 1){
                    deletePatient(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                } else {
                    updatePatient(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                }

                break;
            case 2:
                managePatients(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            default:
                if(operation == 1){
                    deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);
                } else {
                    deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);
                }

                break;
            }

        scanner.close();
    }

    public static void deletePatient(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputPatientID = "";

        System.out.print("Enter patientID: ");
        inputPatientID = scanner.nextLine();

        int index = Patient.searchPatient(patientList, inputPatientID);

        if(index != -1){

            System.out.print("Delete patient? [Y/N]: ");

            String input = "";

            do{
                valid = true;

                input = scanner.nextLine();

                if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                    System.out.println("Invalid input! Try again!");
                    valid = false;
                }

            } while(!valid);

            if(input.toLowerCase().equals("y")){

                patientList.remove(index);

                Patient.updatePatientDatabase(patientList);

                System.out.println("Patient data deleted successfully!");

                System.out.println("Press any key to continue...");
                scanner.nextLine();
                deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);            
            } else {
                deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);
            }

        } else {
            System.out.println("Patient not found!");
            scanner.nextLine();
            deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, diseaseList);
        }

        scanner.close();

    }

    

    public static void updatePatient(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputPatientID = "";

        System.out.print("Enter patientID: ");
        inputPatientID = scanner.nextLine();

        int index = Patient.searchPatient(patientList, inputPatientID);

        if(index != -1){

            System.out.print("Update patient? [Y/N]: ");

            String input = "";

            do{
                valid = true;

                input = scanner.nextLine();

                if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                    System.out.println("Invalid input! Try again!");
                    valid = false;
                }

            } while(!valid);

            if(input.toLowerCase().equals("y")){
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("|============================================PATIENT DETAIL==============================================================|");
                System.out.println("|PatientID|BloodType|Name                  |Address                  |Gender|Phone Number |Email                         |");                    
                System.out.printf("|%-9s|", patientList.get(index).getPatientID());
                System.out.printf("%-9s|", patientList.get(index).getBloodType());
                System.out.printf("%-22s|", patientList.get(index).getName());
                System.out.printf("%-25s|", patientList.get(index).getAddress());
                System.out.printf("%-6s|", patientList.get(index).getGender());
                System.out.printf("%-13s|", patientList.get(index).getPhoneNumber());
                System.out.printf("%-30s|", patientList.get(index).getEmail());
                System.out.println();

                System.out.println("Choose field to update");
                System.out.println("1. BloodType");
                System.out.println("2. Name");
                System.out.println("3. Address");
                System.out.println("4. Gender");
                System.out.println("5. Phone Number");
                System.out.println("6. Email");
                System.out.print(">> ");

                int choice = 0;

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    updatePatient(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                }

                String name = "", bloodType = "", address = "", gender = "", phoneNumber = "", email = "";
                valid = true;

                switch(choice){
                    case 1:
                        do{
                            valid = true;
                
                            System.out.print("Enter patient blood type: ");
                            bloodType = scanner.nextLine();
                
                            if(!bloodType.equals("A") && !bloodType.equals("B") && !bloodType.equals("AB") && !bloodType.equals("O")){
                                System.out.println("Invalid blood type! Try again!");
                                valid = false;
                            } 
                        } while(!valid);

                        patientList.get(index).setBloodType(bloodType);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                    case 2:
                        do{
                            valid = true;
                
                            System.out.print("Enter patient name: ");
                            name = scanner.nextLine();
                            
                            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                                System.out.println("Invalid name! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        patientList.get(index).setName(name);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                    case 3:

                        do{
                            valid = true;
                
                            System.out.print("Enter patient address: ");
                            address = scanner.nextLine();
                            
                            if((address.length() < 10)){
                                System.out.println("Invalid address! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        patientList.get(index).setAddress(address);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                    case 4:
                        do{
                            valid = true;
                
                            System.out.print("Input gender [Male || Female]: ");
                            gender = scanner.nextLine();
                
                            if(!gender.equals("Male") && !gender.equals("Female")){
                                System.out.println("Invalid gender! Try again!");
                                valid = false;
                            } 
                
                        }while(!valid);

                        patientList.get(index).setGender(gender);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;

                    case 5:

                        do{
                            valid = true;
                
                            System.out.print("Input Phone Number: ");
                            phoneNumber = scanner.nextLine();
                
                            for(int i = 0; i < patientList.size(); i++){
                                if(patientList.get(i).getPhoneNumber().equals(phoneNumber)){
                                    System.out.println("Duplicate phone number prohibited!");
                                    valid = false;
                                    break;
                                }
                            }
                
                            if(phoneNumber.length() < 10 || phoneNumber.length() > 13 || hasLetter(phoneNumber)){
                                System.out.println("Invalid number! Try again!");
                                valid = false;
                            } 
                
                        }while(!valid);

                        patientList.get(index).setPhoneNumber(phoneNumber);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                    case 6:

                        do{ 

                            valid = true;
                
                            System.out.print("Input Email: ");
                            email = scanner.nextLine();
                
                            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";
                
                            if(!Pattern.matches(regex, email)){
                                System.out.println("Invalid email! Try again!");
                                valid = false;
                            }else {
                                for(int i = 0; i < patientList.size(); i++){
                                    if(patientList.get(i).getEmail().equals(email)){
                                        System.out.println("Duplicate email prohibited!");
                                        valid = false;
                                        break;
                                    }
                                }
                            }
                        }while(!valid);

                        patientList.get(index).setEmail(email);
                        Patient.updatePatientDatabase(patientList);
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                    default:
                        deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);

                        break;
                }

            } else {
                deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);
            }

        } else {
            System.out.println("Patient not found!");
            deleteUpdatePatientMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, diseaseList);
        }

        scanner.close();
    }

    public static void manageAppointment(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        //CREATE APPOINTMENT
        //SHOW APPOINTMENTS -> ACTIVE/PAST

        System.out.println("1. Show Appointments");
        System.out.println("2. Create Appointment");
        System.out.println("3. Back");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            manageAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        switch(choice){
            case 1:
                showAppointmentsMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 2:
                createAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 3:
                receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            default:
                manageAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
        }

        scanner.close();

    }

    public static void showAppointmentsMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Show Active Appointments");
        System.out.println("2. Show Past Appointments");
        System.out.println("3. Back");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            showAppointmentsMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        switch(choice){
            case 1:
                if(appointmentList.size() == 0){
                    System.out.println("NO ACTIVE APPOINTMENTS");
                    scanner.nextLine();
                } else {
                    showAppointments(appointmentList, false, false);

                    System.out.println("Press any key to continue...");
                    scanner.nextLine();
                    showAppointmentsMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                }

                break;
            case 2:
                if(appointmentList.size() == 0){
                    System.out.println("NO PAST APPOINTMENTS");
                    scanner.nextLine();
                } else {
                    showAppointments(appointmentList, true, false);

                    System.out.println("Press any key to continue...");
                    scanner.nextLine();
                    showAppointmentsMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                }

                break;
            case 3:
                manageAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            default:
                showAppointmentsMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
        }

        scanner.close();
    }

    public static void showAppointments(ArrayList<Appointment> appointmentList, boolean isDone, boolean checkPayment){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        DateTimeFormatter stringFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

        System.out.println("============================================================APPOINTMENT LIST===========================================================================");
        System.out.println("|AppointmentID|Patient Name             |Date & Time              |Doctor Name              |Emergency|Consulted|PrescriptionID|Given Medicine|Done   |");
        System.out.println("=======================================================================================================================================================");
            
        for(int i = 0; i < appointmentList.size(); i++){
            if((appointmentList.get(i).getIsDone() == isDone && checkPayment == false && !appointmentList.get(i).getAppointmentID().equals("AP00X")) || (checkPayment == true && appointmentList.get(i).getIsConsulted() == true && appointmentList.get(i).getGivenMedicine() == true && appointmentList.get(i).getIsDone() == false && !appointmentList.get(i).getAppointmentID().equals("AP00X"))){                    
                System.out.printf("|%-13s|", appointmentList.get(i).getAppointmentID());
                System.out.printf("%-25s|", appointmentList.get(i).getPatient().getName());

                String dateTime = appointmentList.get(i).getDateTime().format(stringFormat);

                System.out.printf("%-25s|", dateTime);
                System.out.printf("%-25s|", appointmentList.get(i).getDoctor().getName());
                System.out.printf("%-9s|", appointmentList.get(i).getEmergency());
                System.out.printf("%-9s|", appointmentList.get(i).getIsConsulted());
                System.out.printf("%-14s|", appointmentList.get(i).getPrescription().getPrescriptionID());
                System.out.printf("%-14s|", appointmentList.get(i).getGivenMedicine());
                System.out.printf("%-7s|", appointmentList.get(i).getIsDone());
                System.out.println();
            }
            
        }
    }

    public static void showDoctors(ArrayList<Doctor> doctorList){

        System.out.println("===============================DOCTOR LIST===============================");
        System.out.println("|DoctorID|Name                  |Specialization          |Patients Slots|");
        System.out.println("=========================================================================");

        for(int i = 0; i < doctorList.size(); i++){

            int appointmentSize = doctorList.get(i).getAppointmentList().size();

            if(doctorList.get(i).getAppointmentList().get(0).getAppointmentID().equals("AP00X")){
                appointmentSize -= 1;
            }

                
            System.out.printf("|%-9s|", doctorList.get(i).getDoctorID());
            System.out.printf("%-21s|", doctorList.get(i).getName());
            System.out.printf("%-24s|", doctorList.get(i).getSpecialization());
            System.out.printf("%d/%-12d|", appointmentSize, 5);
            System.out.println();
        }
    }

    public static void createAppointment(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\033[H\033[2J");
        System.out.flush();

        showPatients(patientList);
        String inputPatientID = "";
        
        System.out.print("Enter patientID: ");
        inputPatientID = scanner.nextLine();

        int indexPatient = Patient.searchPatient(patientList, inputPatientID);

        if(indexPatient != -1){

            String dateTimeInput = "";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
            Boolean valid = false;
            
            do {

                valid = true;

                System.out.print("Enter appointment date & time [dd MMMM yyyy HH:mm | 12 December 2020 14:25 ]: ");
                dateTimeInput = scanner.nextLine();

                try {
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, format);

                    LocalDateTime currentDateTime = LocalDateTime.now();

                    if(dateTime.compareTo(currentDateTime) < 0){
                        System.out.println("Invalid Date!");
                        valid = false;
                    } 

                } catch (Exception e) {
                    System.err.println("Invalid Format!");
                    valid = false;
                }

            } while(!valid);

            String input = "";
            Boolean emergency = false;

            do{
                valid = true;

                System.out.print("Is it an emergency? [Y/N]: ");

                input = scanner.nextLine();

                if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                    System.out.println("Invalid input! Try again!");
                    valid = false;
                }

                if(input.toLowerCase().equals("y")){
                    emergency = true;
                } 

            } while(!valid);


            showDoctors(doctorList);
            String inputDoctorID = "";

            int indexDoctor = -1;

            do{

                valid = true;

                System.out.print("Enter DoctorID: ");
                inputDoctorID = scanner.nextLine();

                indexDoctor = Doctor.searchDoctor(doctorList, inputDoctorID);

                if(indexDoctor == -1 || doctorList.get(indexDoctor).getAppointmentList().size() == 5){
                    valid = false;
                } else {

                    if(doctorList.get(indexDoctor).getAppointmentList().get(0).getAppointmentID().equals("AP00X")){
                        doctorList.get(indexDoctor).getAppointmentList().remove(0);
                    }
                }

            }while(!valid);

            //UPDATE APPOINTMENT ARRAYLIST

            String appointmentID = "";

            String currentID = appointmentList.get(appointmentList.size() - 1).getAppointmentID().substring(2, 5);
            int currentIDNumber = Integer.parseInt(currentID);

            if(currentIDNumber + 1 < 10){
                appointmentID = String.format("AP00%d", currentIDNumber + 1);
            } else if (patientList.size() < 100){
                appointmentID = String.format("AP0%d", currentIDNumber + 1);
            } else {
                appointmentID = String.format("AP%d", currentIDNumber + 1);
            }

            Disease disease = Disease.getDisease(diseaseList, "DS00X");
            Prescription prescription = Prescription.getPrescription(prescriptionList, "PR00X");
                
            appointmentList.add(new Appointment(appointmentID, LocalDateTime.parse(dateTimeInput, format), emergency, false, false, false, patientList.get(indexPatient), doctorList.get(indexDoctor), prescription, disease, "Empty"));

            //UPDATE APPOINTMENT DATABASE
            Appointment.updateAppointmentDatabase(appointmentList);

            //UPDATE DOCTOR DATABASE
            doctorList.get(indexDoctor).getAppointmentList().add(Appointment.getAppointment(appointmentList, appointmentID));
            Doctor.updateDoctorDatabase(doctorList);
                

        } else {
            System.out.print("PATIENT NOT FOUND!");
        }

        System.out.println("Press any key to continue...");
        scanner.nextLine();
        manageAppointment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);

        scanner.close();
    }

    public static void createPayment(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);

        showAppointments(appointmentList, false, true);

        System.out.println("1. Generate Payment");
        System.out.println("2. Back");
        System.out.print(">> ");

        int choice = 0;

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            createPayment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        switch(choice){
            case 1:
                generatePayment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            case 2:
                receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
            default:
                createPayment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
                break;
        }
        scanner.close();
    }

    public static void generatePayment(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){

        Scanner scanner = new Scanner(System.in);
        String inputAppointmentID = "";

        System.out.print("Enter appointmentID: ");
        inputAppointmentID = scanner.nextLine();

        int indexAppointment = Appointment.searchAppointment(appointmentList, inputAppointmentID);

        if(indexAppointment != -1){

            //UPDATE APPOINTMENT
            appointmentList.get(indexAppointment).setIsDone(true);
            Appointment.updateAppointmentDatabase(appointmentList);

            //GENERATE BILL
            String billingID = "";

            String currentID = billingList.get(billingList.size() - 1).getBillingID().substring(2, 5);
            int currentIDNumber = Integer.parseInt(currentID);

            if(currentIDNumber + 1 < 10){
                billingID = String.format("BL00%d", currentIDNumber + 1);
            } else if (patientList.size() < 100){
                billingID = String.format("BL0%d", currentIDNumber + 1);
            } else {
                billingID = String.format("BL%d", currentIDNumber + 1);
            }

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            String formattedDate = dateTime.format(format);
            
            Appointment appointment = appointmentList.get(indexAppointment);

            int doctorFee = appointment.getDoctor().getDoctorFee();
            
            ArrayList<Medicine> medicineList = appointment.getPrescription().getMedicineList();

            int medicineFee = 0;

            for(int i = 0; i < medicineList.size(); i++){
                medicineFee += medicineList.get(i).getMedicinePrice();
            }

            int totalBill = doctorFee + medicineFee;

            Billing billing = new Billing(billingID, dateTime, appointment, totalBill);

            billingList.add(billing);

            //UPDATE BILLING DATABASE
            Billing.updateBillingDatabase(billingList);
            
            //PRINT BILL
            System.out.println("================================================Billing Info===========================================");
            System.out.println("|BillingID|Patient Name             |Date & Time Generated    |Doctor Name              |AppointmentID|");
            System.out.println("=======================================================================================================");
            System.out.printf("|%-9s|", billingID);
            System.out.printf("%-25s|", appointment.getPatient().getName());
            System.out.printf("%-25s|", formattedDate);
            System.out.printf("%-25s|", appointment.getDoctor().getName());
            System.out.printf("%-13s|\n", appointment.getAppointmentID());
            System.out.println("=======================================================================================================\n");

            System.out.println("==========================Prescription Info=====================");
            System.out.println("|MedicineID|Medicine Name          |Quantity    |Medicine Price|");
            System.out.println("================================================================");
            
            for(int i = 0; i < medicineList.size(); i++){
                System.out.printf("|%-10s|", medicineList.get(i).getMedicineID());
                System.out.printf("%-23s|", medicineList.get(i).getMedicineName());
                System.out.printf("%-12d|", medicineList.get(i).getMedicineQuantity());
                System.out.printf("%-14d|", medicineList.get(i).getMedicinePrice());
            }

            System.out.println("\n================================================================\n");
            System.out.printf("Doctor Fee: Rp.%d\n", doctorFee);
            System.out.printf("Medicine Fee: Rp.%d\n", medicineFee);
            System.out.printf("Total Bill: Rp.%d\n", totalBill);


            System.out.println("\nPress any key to continue...");
            scanner.nextLine();
            receptionMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);

        } else {
            System.out.println("PATIENT NOT FOUND");
            scanner.nextLine();
            createPayment(patientList, doctorList, appointmentList, billingList, prescriptionList, diseaseList);
        }

        scanner.close();
    }


}
