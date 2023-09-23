import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Billing {
    private String billingID;
    private LocalDateTime billingDate;
    private Appointment appointment;
    private int totalBill;

    public Billing(){} 

    public Billing(String billingID, LocalDateTime billingDate, Appointment appointment, int totalBill) {
        this.billingID = billingID;
        this.billingDate = billingDate;
        this.appointment = appointment;
        this.totalBill = totalBill;
    }

    public String getBillingID() {
        return this.billingID;
    }

    public void setBillingID(String billingID) {
        this.billingID = billingID;
    }

    public LocalDateTime getBillingDate() {
        return this.billingDate;
    }

    public void setBillingDate(LocalDateTime billingDate) {
        this.billingDate = billingDate;
    }

    public Appointment getAppointment(){
        return this.appointment;
    }

    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }

    public int getTotalBill() {
        return this.totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public static void loadBilling(ArrayList<Billing> billingList, ArrayList<Appointment> appointmentList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/BillingRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/BillingRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(detail[1], format);

                System.out.println("");
                
                billingList.add(new Billing(detail[0], dateTime, Appointment.getAppointment(appointmentList, detail[2]), Integer.parseInt(detail[3])));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("BillingRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void updateBillingDatabase(ArrayList<Billing> billingList){

        try {

            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/BillingRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/BillingRecords.csv",false));
            }

            bw.write("");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

            for(int i = 0; i < billingList.size(); i++){

                String billingID = billingList.get(i).getBillingID();
                String formattedDate = billingList.get(i).getBillingDate().format(format);
                String appointmentID = billingList.get(i).getAppointment().getAppointmentID();
                String totalBill = String.valueOf(billingList.get(i).getTotalBill());
                

                String writeString = String.format("%s,%s,%s,%s", billingID, formattedDate, appointmentID, totalBill);
                
                try {
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/BillingRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/BillingRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("BillingRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("BillingRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }
}
