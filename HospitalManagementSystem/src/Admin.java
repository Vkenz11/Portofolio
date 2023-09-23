import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin extends Person{
    String adminID;

    public Admin(String name, String address, String gender, String phoneNumber, String email, String password, String adminID){
        super(name, address, gender, phoneNumber, email, password);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return this.adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public static void loadAdmin(ArrayList<Admin> adminList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/AdminRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/AdminRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                adminList.add(new Admin(detail[0], detail[1], detail[2], detail[3], detail[4], detail[5], detail[6]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("AdminRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }
    
    public static void printManage(){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");

        String currentDateTime = dateTime.format(dateTimeFormat);

        System.out.println("Admin Menu");
        System.out.println(currentDateTime);

        System.out.println("1. Manage Doctor");
        System.out.println("2. Manage Receptionist");
        System.out.println("3. Logout");
        System.out.print(">> ");
    }

    public static void printManageDoctor(){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("1. Add Doctor");
        System.out.println("2. Remove Doctor");
        System.out.println("3. Update Doctor");
        System.out.println("4. Show Doctors");
        System.out.println("5. Back");
        System.out.print(">> ");
    }

    public static void printManageRec(){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("1. Add Receptionist");
        System.out.println("2. Remove Receptionist");
        System.out.println("3. Update Receptionist");
        System.out.println("4. Show Receptionists");
        System.out.println("5. Back");
        System.out.print(">> ");
    }
    
    public static void adminMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList)
    {

        printManage();
        Scanner s = new Scanner(System.in);
        int option = 0;

        try{
            option = s.nextInt();
            s.nextLine();
        } catch(Exception e){
            adminMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        }

        switch(option){
            // Option 1 Manage Doctor
            case 1:
                manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            case 2:
                manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            case 3:
                HospitalManagement.logout();
                break;

            default:
                adminMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;
        }

        s.close();
    }
    
    public static void manageDoctor(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        printManageDoctor();

        Scanner s = new Scanner(System.in);

        int optionDoc = 0;
        
        try{
            optionDoc = s.nextInt();
            s.nextLine();
        } catch(Exception e){
            manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        }

        switch(optionDoc){
          // Option 1 Add Doctor
            case 1:
                addDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            // Option 2 Remove Doctor
            case 2:
                if(doctorList.size() == 0){
                    System.out.println("NO DOCTOR DATA");
                    s.nextLine();
                    manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
                }
                
                break;

            // Option 3 Update Doctor
            case 3:
                if(doctorList.size() == 0){
                    System.out.println("NO DOCTOR DATA");
                    s.nextLine();
                    manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
                }

                break;

            // Option 4 Show Doctors
            case 4:
                if(doctorList.size() == 0){
                    System.out.println("NO DOCTOR DATA");
                    s.nextLine();
                    manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    showDoctors(doctorList);
                }

                System.out.println("Press any key to continue...");
                s.nextLine();
                manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            case 5:
                adminMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            default:
                manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;
            }

        s.close();

    }
    
    public static void addDoctor(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){
        String name ="", address = "", gender = "", phoneNumber = "", email = "", specialization = "", password = "";
        int docFee = 0;
        // name, address, gender, phonenumber, email, specialization, doctorfee
        Boolean valid = true;

        Scanner s = new Scanner(System.in);
        
        
        // doctor name
        do{
            valid = true;

            System.out.print("Enter doctor name: ");
            name = s.nextLine();
            

            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                System.out.println("Invalid name! Try again!");
                valid = false;
            } 

        } while(!valid);

        
        // doctor specialization
        do{
            valid = true;
            
            System.out.print("Input doctor specialization: ");
            specialization = s.nextLine();
            
            if(hasNumber(name)){
                System.out.println("Invalid name! Try again!");
                valid = false;
            }
            
        } while(!valid);
        
        
        
        // doctor address
        do{
            valid = true;

            System.out.print("Enter doctor address: ");
            address = s.nextLine();
            
            if((address.length() < 10)){
                System.out.println("Invalid address! Try again!");
                valid = false;
            } 

        } while(!valid);

        
        // doctor gender
        do{

            valid = true;

            System.out.print("Input gender [Male || Female]: ");
            gender = s.nextLine();

            if(!gender.equals("Male") && !gender.equals("Female")){
                System.out.println("Invalid gender! Try again!");
                valid = false;
            } 

        }while(!valid);

        
        // doctor phonenumber
        do{

            valid = true;

            System.out.print("Input Phone Number: ");
            phoneNumber = s.nextLine();

            for(int i = 0; i < doctorList.size(); i++){
                if(doctorList.get(i).getPhoneNumber().equals(phoneNumber)){
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

        
        // doctor password
        do{
            valid = true;

            System.out.print("Input password: ");
            password = s.nextLine();

            if(password.length() < 8){
                System.out.println("Password length must be more than 7 characters");
                valid = false;
            }


        } while(!valid);



        // doctor email
        do{ 

            valid = true;

            System.out.print("Input Email: ");
            email = s.nextLine();

            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";

            if(!Pattern.matches(regex, email)){
                System.out.println("Invalid email! Try again!");
                valid = false;
            }else {
                for(int i = 0; i < doctorList.size(); i++){
                    if(doctorList.get(i).getEmail().equals(email)){
                        System.out.println("Duplicate email prohibited!");
                        valid = false;
                        break;
                    }
                }
            }
        }while(!valid);
        
        
        
        // doctor fee
        do{
            valid = true;

            System.out.print("Input Doctor Fee: ");
            docFee = s.nextInt();
            s.nextLine();
            
            if(docFee < 50000) {
                System.out.println("Doctor Fee must be above Rp.50000");
                valid = false;
            }
            
            
        } while (!valid);
        
        System.out.println("Name: "  + name);
        System.out.println("Specialization: " + specialization);
        System.out.println("Address: " + address);
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("Fee per Month: " + docFee);

        System.out.print("Reenter details? [Y/N]: ");

        String input = "";

        do{
            valid = true;

            input = s.nextLine();

            if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                System.out.println("Invalid input! Try again!");
                valid = false;
            }

        } while(!valid);

        String doctorID = "";

        String currentID = doctorList.get(doctorList.size() - 1).getDoctorID().substring(2, 5);
        int currentIDNumber = Integer.parseInt(currentID);

        if(currentIDNumber + 1 < 10){
            doctorID = String.format("DC00%d", currentIDNumber + 1);
        } else if (doctorList.size() < 100){
            doctorID = String.format("DC0%d", currentIDNumber + 1);
        } else {
            doctorID = String.format("DC%d", currentIDNumber + 1);
        }

        if(input.toLowerCase().equals("y")){
            addDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        } else {

            ArrayList<Appointment> doctorAppointmentList = new ArrayList<Appointment>();

            doctorAppointmentList.add(Appointment.getAppointment(appointmentList, "AP00X"));

            doctorList.add(new Doctor(name, address, gender, phoneNumber, email, password, doctorID, specialization, doctorAppointmentList, docFee));

            Doctor.updateDoctorDatabase(doctorList);

            System.out.println("Added doctor to database!");
            System.out.println("Press any key to continue...");
            s.nextLine();
            manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        }

        s.close();
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

    public static void deleteUpdateDoctorMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, int operation, 
    ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        showDoctors(doctorList);

        if(operation == 1){
            System.out.println("1. Delete Doctor");
        } else {
            System.out.println("1. Update Doctor");
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
                deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
            } else {
                deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
            }
        }
        
        switch(choice){
            case 1:
                if(operation == 1){
                    deleteDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    updateDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                }

                break;
            case 2:
                manageDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;
            default:
                if(operation == 1){
                    deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
                }

                break;
            }

        scanner.close();
    }


    public static void deleteDoctor(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, 
    ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputDoctorID = "";

        System.out.print("Enter doctorID: ");
        inputDoctorID = scanner.nextLine();

        int index = Doctor.searchDoctor(doctorList, inputDoctorID);

        if(index != -1){

            System.out.print("Delete doctor? [Y/N]: ");

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

                doctorList.remove(index);

                Doctor.updateDoctorDatabase(doctorList);

                System.out.println("Doctor data deleted successfully!");

                System.out.println("Press any key to continue...");
                scanner.nextLine();
                deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);            
            } else {
                deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
            }

        } else {
            System.out.println("Doctor not found!");
            scanner.nextLine();
            deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
        }

        scanner.close();
    }


    public static void updateDoctor(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, 
    ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputDoctorID = "";

        System.out.print("Enter doctorID: ");
        inputDoctorID = scanner.nextLine();

        int index = Doctor.searchDoctor(doctorList, inputDoctorID);

        if(index != -1){

            System.out.print("Update doctor? [Y/N]: ");

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

                System.out.println("|==============================================================================DOCTOR DETAIL========================================================================|");
                System.out.println("|DoctorID|Doctor Name              |Specialization      |Address                  |Gender|Phone Number |Password          |Email                         |Doctor Fee|");    
                System.out.println("|===================================================================================================================================================================|");

                
                System.out.printf("|%-8s|", doctorList.get(index).getDoctorID());
                System.out.printf("%-25s|", doctorList.get(index).getName());
                System.out.printf("%-20s|", doctorList.get(index).getSpecialization());
                System.out.printf("%-25s|", doctorList.get(index).getAddress());
                System.out.printf("%-6s|", doctorList.get(index).getGender());
                System.out.printf("%-13s|", doctorList.get(index).getPhoneNumber());
                System.out.printf("%-18s|", doctorList.get(index).getPassword());
                System.out.printf("%-30s|", doctorList.get(index).getEmail());
                System.out.printf("%-10d|", doctorList.get(index).getDoctorFee());
                System.out.println();
                

                System.out.println("Choose field to update");
                System.out.println("1. Name");
                System.out.println("2. Specialization");
                System.out.println("3. Address");
                System.out.println("4. Gender");
                System.out.println("5. Phone Number");
                System.out.println("6. Password");
                System.out.println("7. Email");
                System.out.println("8. Fee per month");
                System.out.print(">> ");

                int choice = 0;

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    updateDoctor(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                }

                String name ="", address = "", gender = "", phoneNumber = "", email = "", specialization = "", password = "";
                int docFee = 0;
                valid = true;

                switch(choice){
                    // Option 1 Update Name
                    case 1:
                        do{
                            valid = true;
                
                            System.out.print("Enter doctor name: ");
                            name = scanner.nextLine();
                            
                            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                                System.out.println("Invalid name! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        doctorList.get(index).setName(name);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 2 Update Specialization
                    case 2:
                        do{
                            valid = true;
                            
                            System.out.print("Input doctor specialization: ");
                            specialization = scanner.nextLine();
                            
                            if(hasNumber(name)){
                                System.out.println("Invalid name! Try again!");
                                valid = false;
                            }
                            
                        } while(!valid);

                        doctorList.get(index).setSpecialization(specialization);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                    // Option 3 Update Address
                    case 3:

                        do{
                            valid = true;
                
                            System.out.print("Enter doctor address: ");
                            address = scanner.nextLine();
                            
                            if((address.length() < 10)){
                                System.out.println("Invalid address! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        doctorList.get(index).setAddress(address);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 4 Update Gender
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

                        doctorList.get(index).setGender(gender);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 5 Update Phone Number
                    case 5:

                        do{
                            valid = true;
                
                            System.out.print("Input Phone Number: ");
                            phoneNumber = scanner.nextLine();
                
                            for(int i = 0; i < doctorList.size(); i++){
                                if(doctorList.get(i).getPhoneNumber().equals(phoneNumber)){
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

                        doctorList.get(index).setPhoneNumber(phoneNumber);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 6 Update Password
                    case 6:
                        do{
                            valid = true;
                
                            System.out.print("Input password: ");
                            password = scanner.nextLine();
                
                            if(password.length() < 8){
                                System.out.println("Password length must be more than 7 characters");
                                valid = false;
                            }
                
                
                        } while(!valid);

                        doctorList.get(index).setPassword(password);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                    // Option 7 Update Email
                    case 7:

                        do{ 

                            valid = true;
                
                            System.out.print("Input Email: ");
                            email = scanner.nextLine();
                
                            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";
                
                            if(!Pattern.matches(regex, email)){
                                System.out.println("Invalid email! Try again!");
                                valid = false;
                            }else {
                                for(int i = 0; i < doctorList.size(); i++){
                                    if(doctorList.get(i).getEmail().equals(email)){
                                        System.out.println("Duplicate email prohibited!");
                                        valid = false;
                                        break;
                                    }
                                }
                            }
                        }while(!valid);

                        doctorList.get(index).setEmail(email);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 8 Update Doctor Fee
                    case 8:
                        do{
                            valid = true;
                            
                            System.out.print("Input Doctor Fee: ");
                            try{
                                docFee = scanner.nextInt();
                            } catch (Exception e){
                                System.out.println("Enter only integer value..." + e);
                                valid = false;
                            }

                            if(docFee < 50000) {
                                System.out.println("Doctor Fee must be above Rp.50000");
                                valid = false;
                            }
                        
                            
                        } while (!valid);

                        doctorList.get(index).setDoctorFee(docFee);
                        Doctor.updateDoctorDatabase(doctorList);
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    default:
                        deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;
                }

            } else {
                deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
            }

        } else {
            System.out.println("Doctor not found!");
            deleteUpdateDoctorMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
        }

        scanner.close();
    }


    public static void showDoctors(ArrayList<Doctor> doctorList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("|==============================================================================DOCTOR DETAIL========================================================================|");
        System.out.println("|DoctorID|Doctor Name              |Specialization      |Address                  |Gender|Phone Number |Password          |Email                         |Doctor Fee|");    
        System.out.println("|===================================================================================================================================================================|");

        for(int i = 0; i < doctorList.size(); i++){
            System.out.printf("|%-8s|", doctorList.get(i).getDoctorID());
            System.out.printf("%-25s|", doctorList.get(i).getName());
            System.out.printf("%-20s|", doctorList.get(i).getSpecialization());
            System.out.printf("%-25s|", doctorList.get(i).getAddress());
            System.out.printf("%-6s|", doctorList.get(i).getGender());
            System.out.printf("%-13s|", doctorList.get(i).getPhoneNumber());
            System.out.printf("%-18s|", doctorList.get(i).getPassword());
            System.out.printf("%-30s|", doctorList.get(i).getEmail());
            System.out.printf("%-10d|", doctorList.get(i).getDoctorFee());
            System.out.println();
        }
    
    }


    public static void manageReceptionist(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, 
    ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){
        printManageRec();

        Scanner s = new Scanner(System.in);

        int optionRec = 0;
        
        try{
            optionRec = s.nextInt();
            s.nextLine();
        } catch(Exception e){
            manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        }

        switch(optionRec){
          // Option 1 Add Receptionist
            case 1:
                addReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            // Option 2 Remove Receptionist
            case 2:
                if(receptionistList.size() == 0){
                    System.out.println("NO RECEPTIONIST DATA");
                    s.nextLine();
                    manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
                }
                
                break;

            // Option 3 Update Receptionist
            case 3:
                if(receptionistList.size() == 0){
                    System.out.println("NO RECEPTIONIST DATA");
                    s.nextLine();
                    manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
                }

                break;

            // Option 4 Show Receptionists
            case 4:
                if(receptionistList.size() == 0){
                    System.out.println("NO RECEPTIONIST DATA");
                    s.nextLine();
                    manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    showReceptionists(receptionistList);
                }

                System.out.println("Press any key to continue...");
                s.nextLine();
                manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            case 5:
                adminMenu(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;

            default:
                manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;
            }

        s.close();
    }

    public static void addReceptionist(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){
        String name ="", address = "", gender = "", phoneNumber = "", email = "", password = "";
        // name, address, gender, phonenumber, email
        Boolean valid = true;

        Scanner s = new Scanner(System.in);
        
        
        // receptionist name
        do{
            valid = true;

            System.out.print("Enter receptionist name: ");
            name = s.nextLine();
            

            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                System.out.println("Invalid name! Try again!");
                valid = false;
            } 

        } while(!valid);

        
        // receptionist address
        do{
            valid = true;

            System.out.print("Enter receptionist address: ");
            address = s.nextLine();
            
            if((address.length() < 10)){
                System.out.println("Invalid address! Try again!");
                valid = false;
            } 

        } while(!valid);

        
        // receptionist gender
        do{

            valid = true;

            System.out.print("Input gender [Male || Female]: ");
            gender = s.nextLine();

            if(!gender.equals("Male") && !gender.equals("Female")){
                System.out.println("Invalid gender! Try again!");
                valid = false;
            } 

        }while(!valid);

        
        // receptionist phonenumber
        do{

            valid = true;

            System.out.print("Input Phone Number: ");
            phoneNumber = s.nextLine();

            for(int i = 0; i < receptionistList.size(); i++){
                if(receptionistList.get(i).getPhoneNumber().equals(phoneNumber)){
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


        // receptionist email
        do{ 

            valid = true;

            System.out.print("Input Email: ");
            email = s.nextLine();

            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";

            if(!Pattern.matches(regex, email)){
                System.out.println("Invalid email! Try again!");
                valid = false;
            }else {
                for(int i = 0; i < receptionistList.size(); i++){
                    if(receptionistList.get(i).getEmail().equals(email)){
                        System.out.println("Duplicate email prohibited!");
                        valid = false;
                        break;
                    }
                }
            }
        }while(!valid);
        
        
        // receptionist password
        do{
            valid = true;

            System.out.print("Input password: ");
            password = s.nextLine();

            if(password.length() < 8){
                System.out.println("Password length must be more than 7 characters");
                valid = false;
            }


        } while(!valid);

        
        System.out.println("Name: "  + name);
        System.out.println("Address: " + address);
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        System.out.print("Reenter details? [Y/N]: ");

        String input = "";

        do{
            valid = true;

            input = s.nextLine();

            if(!input.toLowerCase().equals("y") && !input.toLowerCase().equals("n")){
                System.out.println("Invalid input! Try again!");
                valid = false;
            }

        } while(!valid);

        String recID = "";

        String currentID = receptionistList.get(receptionistList.size() - 1).getRecID().substring(2, 5);
        int currentIDNumber = Integer.parseInt(currentID);

        if(currentIDNumber + 1 < 10){
            recID = String.format("RC00%d", currentIDNumber + 1);
        } else if (receptionistList.size() < 100){
            recID = String.format("RC0%d", currentIDNumber + 1);
        } else {
            recID = String.format("RC%d", currentIDNumber + 1);
        }

        if(input.toLowerCase().equals("y")){
            addReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        } else {

            receptionistList.add(new Receptionist(name, address, gender, phoneNumber, email, password, recID));

            Receptionist.updateReceptionistDatabase(receptionistList);

            System.out.println("Added receptionist to database!");
            System.out.println("Press any key to continue...");
            s.nextLine();
            manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
        }

        s.close();
    }

    public static void deleteUpdateRecMenu(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, int operation, 
    ArrayList<Billing> billingList, ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        showReceptionists(receptionistList);

        if(operation == 1){
            System.out.println("1. Delete Receptionist");
        } else {
            System.out.println("1. Update Receptionist");
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
                deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
            } else {
                deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
            }
        }
        
        switch(choice){
            case 1:
                if(operation == 1){
                    deleteReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                } else {
                    updateReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                }

                break;
            case 2:
                manageReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                break;
            default:
                if(operation == 1){
                    deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
                } else {
                    deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
                }

                break;
            }

        scanner.close();
    }


    public static void deleteReceptionist(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, 
    ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputRecID = "";

        System.out.print("Enter recID: ");
        inputRecID = scanner.nextLine();

        int index = Receptionist.searchReceptionist(receptionistList, inputRecID);

        if(index != -1){

            System.out.print("Delete receptionist? [Y/N]: ");

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

                receptionistList.remove(index);

                Receptionist.updateReceptionistDatabase(receptionistList);

                System.out.println("Receptionist data deleted successfully!");

                System.out.println("Press any key to continue...");
                scanner.nextLine();
                deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);            
            } else {
                deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
            }

        } else {
            System.out.println("Receptionist not found!");
            scanner.nextLine();
            deleteUpdateRecMenu(patientList, doctorList, appointmentList, 1, billingList, prescriptionList, receptionistList);
        }

        scanner.close();
    }


    public static void updateReceptionist(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, 
    ArrayList<Appointment> appointmentList, ArrayList<Billing> billingList, 
    ArrayList<Prescription> prescriptionList, ArrayList<Receptionist> receptionistList){

        Scanner scanner = new Scanner(System.in);

        Boolean valid = false;
        String inputRecID = "";

        System.out.print("Enter recID: ");
        inputRecID = scanner.nextLine();

        int index = Receptionist.searchReceptionist(receptionistList, inputRecID);

        if(index != -1){

            System.out.print("Update receptionist? [Y/N]: ");

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


                System.out.println("|============================================RECEPTIONIST DETAIL==================================================================|");
                System.out.println("|RecID    |Name                  |Address                  |Gender|Phone Number |Email                         |Password          |");    
                
                System.out.printf("|%-9s|", receptionistList.get(index).getRecID());
                System.out.printf("%-22s|", receptionistList.get(index).getName());
                System.out.printf("%-25s|", receptionistList.get(index).getAddress());
                System.out.printf("%-6s|", receptionistList.get(index).getGender());
                System.out.printf("%-13s|", receptionistList.get(index).getPhoneNumber());
                System.out.printf("%-30s|", receptionistList.get(index).getEmail());
                System.out.printf("%-18s|", receptionistList.get(index).getPassword());
                System.out.println();

                System.out.println("Choose field to update");
                System.out.println("1. Name");
                System.out.println("2. Address");
                System.out.println("3. Gender");
                System.out.println("4. Phone Number");
                System.out.println("5. Email");
                System.out.println("6. Password");
                System.out.print(">> ");

                int choice = 0;

                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    updateReceptionist(patientList, doctorList, appointmentList, billingList, prescriptionList, receptionistList);
                }

                String name ="", address = "", gender = "", phoneNumber = "", email = "", password = "";
                // name, address, gender, phonenumber, email
                valid = true;

                switch(choice){
                    // Option 1 Update Name
                    case 1:
                        do{
                            valid = true;
                
                            System.out.print("Enter receptionist name: ");
                            name = scanner.nextLine();
                            
                            if((name.length() < 5) || hasNumber(name) || validSpace(name)){
                                System.out.println("Invalid name! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        receptionistList.get(index).setName(name);
                        Receptionist.updateReceptionistDatabase(receptionistList);

                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 2 Update Address
                    case 2:

                        do{
                            valid = true;
                
                            System.out.print("Enter receptionist address: ");
                            address = scanner.nextLine();
                            
                            if((address.length() < 10)){
                                System.out.println("Invalid address! Try again!");
                                valid = false;
                            } 
                
                        } while(!valid);

                        receptionistList.get(index).setAddress(address);
                        Receptionist.updateReceptionistDatabase(receptionistList);
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 3 Update Gender
                    case 3:
                        do{
                            valid = true;
                
                            System.out.print("Input gender [Male || Female]: ");
                            gender = scanner.nextLine();
                
                            if(!gender.equals("Male") && !gender.equals("Female")){
                                System.out.println("Invalid gender! Try again!");
                                valid = false;
                            } 
                
                        }while(!valid);

                        receptionistList.get(index).setGender(gender);
                        Receptionist.updateReceptionistDatabase(receptionistList);
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 4 Update Phone Number
                    case 4:

                        do{
                            valid = true;
                
                            System.out.print("Input Phone Number: ");
                            phoneNumber = scanner.nextLine();
                
                            for(int i = 0; i < receptionistList.size(); i++){
                                if(receptionistList.get(i).getPhoneNumber().equals(phoneNumber)){
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

                        receptionistList.get(index).setPhoneNumber(phoneNumber);
                        Receptionist.updateReceptionistDatabase(receptionistList);
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;
                    
                    
                    // Option 5 Update Email
                    case 5:

                        do{ 

                            valid = true;
                
                            System.out.print("Input Email: ");
                            email = scanner.nextLine();
                
                            String regex = "^([\\w\\.-]+)@([\\w-]+)\\.([\\w-]{2,6})(\\.[\\w]{2,6})?$";
                
                            if(!Pattern.matches(regex, email)){
                                System.out.println("Invalid email! Try again!");
                                valid = false;
                            }else {
                                for(int i = 0; i < receptionistList.size(); i++){
                                    if(receptionistList.get(i).getEmail().equals(email)){
                                        System.out.println("Duplicate email prohibited!");
                                        valid = false;
                                        break;
                                    }
                                }
                            }
                        }while(!valid);

                        receptionistList.get(index).setEmail(email);
                        Receptionist.updateReceptionistDatabase(receptionistList);
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;

                    // Option 6 Update Password
                    case 6:
                        do{
                            valid = true;
                
                            System.out.print("Input password: ");
                            password = scanner.nextLine();
                
                            if(password.length() < 8){
                                System.out.println("Password length must be more than 7 characters");
                                valid = false;
                            }
                
                
                        } while(!valid);

                        receptionistList.get(index).setPassword(password);
                        Receptionist.updateReceptionistDatabase(receptionistList);
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                    default:
                        deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);

                        break;
                }

            } else {
                deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
            }

        } else {
            System.out.println("Receptionist not found!");
            deleteUpdateRecMenu(patientList, doctorList, appointmentList, 0, billingList, prescriptionList, receptionistList);
        }

        scanner.close();
    }


    public static void showReceptionists(ArrayList<Receptionist> receptionistList){
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("|============================================RECEPTIONIST DETAIL==================================================================|");
        System.out.println("|RecID    |Name                  |Address                  |Gender|Phone Number |Email                         |Password          |");    
        
        for(int i = 0; i < receptionistList.size(); i++){
            System.out.printf("|%-9s|", receptionistList.get(i).getRecID());
            System.out.printf("%-22s|", receptionistList.get(i).getName());
            System.out.printf("%-25s|", receptionistList.get(i).getAddress());
            System.out.printf("%-6s|", receptionistList.get(i).getGender());
            System.out.printf("%-13s|", receptionistList.get(i).getPhoneNumber());
            System.out.printf("%-30s|", receptionistList.get(i).getEmail());
            System.out.printf("%-18s|", receptionistList.get(i).getPassword());
            System.out.println();
        }
    
    }
}
