import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pharmacist extends Person{
    String pharID;

    public Pharmacist(String name, String address, String gender, String phoneNumber, String email, String password, String pharID){
        super(name, address, gender, phoneNumber, email, password);
        this.pharID = pharID;
    }

    public String getPharID() {
        return this.pharID;
    }

    public void setPharID(String pharID) {
        this.pharID = pharID;
    }

    public static Pharmacist getPharmacist(ArrayList<Pharmacist> pharmacistList, String pharmacistID){

        int index = -1;

        for(int i = 0; i < pharmacistList.size(); i++){
            if(pharmacistList.get(i).getPharID().equals(pharmacistID)){
                index = i;
                break;
            }
        }

        return pharmacistList.get(index);
    }

    public static void loadPharmacist(ArrayList<Pharmacist> pharmacistList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/PharmacistRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/PharmacistRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                pharmacistList.add(new Pharmacist(detail[0], detail[1], detail[2], detail[3], detail[4], detail[5], detail[6]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("PharmacistRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }
    
    public static void pharmacistMenu(ArrayList<Appointment> appointmentList) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
		
		System.out.println("Pharmacist Menu");

        Appointment.showAppointmentNotGivenMedicine(appointmentList);

        int choice = 0;

		System.out.println("1. Select Appointment ");
        System.out.println("2. Logout");
        System.out.print(">> ");

		try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            pharmacistMenu(appointmentList);
        }

        switch(choice){
            case 1:
                System.out.print("Enter AppointmentID: ");
                String inputAppointmentID = "";

                inputAppointmentID = scanner.nextLine();
                int indexAppointment = Appointment.searchAppointmentNotGivenMedicine(appointmentList, inputAppointmentID);

                if(indexAppointment != -1){
                    Prescription prescription = appointmentList.get(indexAppointment).getPrescription();
                    ArrayList<Medicine> medicineList = prescription.getMedicineList();

                    Medicine.showMedicineList(medicineList);

                    choice = 0;

                    System.out.println("1. Prepare Medicine ");
                    System.out.println("2. Back");
                    System.out.print(">> ");

                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (Exception e) {
                        pharmacistMenu(appointmentList);
                    }

                    switch (choice) {
                        case 1:
                        appointmentList.get(indexAppointment).setGivenMedicine(true);
                        System.out.println("Medicine Prepared.");
                        scanner.nextLine();

                        //update to database
                        Appointment.updateAppointmentDatabase(appointmentList);

                            break;
                        case 2:
                        pharmacistMenu(appointmentList);
                            break;
                        default:
                        pharmacistMenu(appointmentList);
                            break;
                    }

                    pharmacistMenu(appointmentList);

                } else {
                    System.out.println("APPOINTMENT NOT FOUND");
                    scanner.nextLine();
                    pharmacistMenu(appointmentList);
                }

                break;
            case 2:
                HospitalManagement.logout();
                break;
            default:
                pharmacistMenu(appointmentList);
                break;
        }
        scanner.close();
		
	}

    
   
}
