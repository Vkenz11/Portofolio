import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Appointment {
	private String appointmentID;
	private LocalDateTime dateTime;
    private Boolean emergency;
	private Boolean isConsulted;
	private Boolean givenMedicine;
	private Boolean isDone;
	private Patient patient;
	private Doctor doctor;
	private Prescription prescription;
    private Disease disease;
    private String symptoms;

	public Appointment(String appointmentID, LocalDateTime dateTime, Boolean emergency, Boolean isConsulted, Boolean givenMedicine, Boolean isDone, Patient patient, Doctor doctor, Prescription prescription, Disease disease, String symptoms) {
		this.appointmentID = appointmentID;
		this.dateTime = dateTime;
		this.emergency = emergency;
		this.isConsulted = isConsulted;
		this.givenMedicine = givenMedicine;
		this.isDone = isDone;
		this.patient = patient;
		this.doctor = doctor;
		this.prescription = prescription;
        this.disease = disease;
        this.symptoms = symptoms;
	}

	public String getAppointmentID() {
		return this.appointmentID;
	}

	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}

	public LocalDateTime getDateTime(){
		return this.dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Boolean getEmergency() {
		return this.emergency;
	}

	public void setEmergency(Boolean emergency) {
		this.emergency = emergency;
	}

	public Boolean getIsConsulted() {
		return this.isConsulted;
	}

	public void setIsConsulted(Boolean isConsulted) {
		this.isConsulted = isConsulted;
	}

	public Boolean getGivenMedicine() {
		return this.givenMedicine;
	}

	public void setGivenMedicine(Boolean givenMedicine) {
		this.givenMedicine = givenMedicine;
	}

	public Boolean getIsDone(){
		return this.isDone;
	}

	public void setIsDone(Boolean isDone){
		this.isDone = isDone;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Prescription getPrescription() {
        return this.prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

    public Disease getDisease() {
        return this.disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public String getSymptoms() {
        return this.symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }    


	public static Appointment getAppointment(ArrayList<Appointment> appointmentList, String appointmentID){

        int index = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getAppointmentID().equals(appointmentID)){
                index = i;
                break;
            }
        }

        return appointmentList.get(index);
    }

	public static int searchAppointment(ArrayList<Appointment> appointmentList, String inputAppointmentID){

        int index = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getAppointmentID().equals(inputAppointmentID) && appointmentList.get(i).getIsConsulted() == true && appointmentList.get(i).getGivenMedicine() == true && appointmentList.get(i).getIsDone() == false){
                index = i;
                break;
            }
        }

        return index;
    }

    public static int searchAppointmentNotGivenMedicine(ArrayList<Appointment> appointmentList, String inputAppointmentID){

        int index = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getAppointmentID().equals(inputAppointmentID) && appointmentList.get(i).getIsConsulted() == true && appointmentList.get(i).getGivenMedicine() == false && appointmentList.get(i).getIsDone() == false){
                index = i;
                break;
            }
        }

        return index;
    }

    public static int searchAppointmentDoctor(ArrayList<Appointment> appointmentList, String inputAppointmentID){

        int index = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getAppointmentID().equals(inputAppointmentID)){
                index = i;
                break;
            }
        }

        return index;
    }

	public static void loadAppointment(ArrayList<Patient> patientList, ArrayList<Doctor> doctorList, ArrayList<Appointment> appointmentList, ArrayList<Prescription> prescriptionList, ArrayList<Disease> diseaseList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/AppointmentRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/AppointmentRecords.csv"));
            }
            
            String line;

            while((line = br.readLine()) != null){
                String[] detail = line.split(",");
                
                Patient patient = Patient.getPatient(patientList, detail[6]);
                Doctor doctor = Doctor.getDoctor(doctorList, detail[7]);
                Prescription prescription = Prescription.getPrescription(prescriptionList, detail[8]);

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(detail[1], format);

                Disease disease = Disease.getDisease(diseaseList, detail[9]);
                
                appointmentList.add(new Appointment(detail[0], dateTime, Boolean.parseBoolean(detail[2]), Boolean.parseBoolean(detail[3]), Boolean.parseBoolean(detail[4]), Boolean.parseBoolean(detail[5]), patient, doctor, prescription, disease, detail[10]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("AppointmentRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

	public static void updateAppointmentDatabase(ArrayList<Appointment> appointmentList){

        try {

            BufferedWriter bw;
        
            try {
                bw = new BufferedWriter(new FileWriter("./Database/AppointmentRecords.csv",false));
            } catch (Exception e) {
                bw = new BufferedWriter(new FileWriter("src/Database/AppointmentRecords.csv",false));
            }

            bw.write("");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

            for(int i = 0; i < appointmentList.size(); i++){

                String appointmentID = appointmentList.get(i).getAppointmentID();
                String formattedDate = appointmentList.get(i).getDateTime().format(format);
                String emergency = String.valueOf(appointmentList.get(i).getEmergency());
                String isConsulted = String.valueOf(appointmentList.get(i).getIsConsulted());
                String givenMedicine = String.valueOf(appointmentList.get(i).getGivenMedicine());
                String isDone = String.valueOf(appointmentList.get(i).getIsDone());
                String patientID = appointmentList.get(i).getPatient().getPatientID();
                String doctorID = appointmentList.get(i).getDoctor().getDoctorID();
                String prescriptionID = appointmentList.get(i).getPrescription().getPrescriptionID();
                String diseaseID = appointmentList.get(i).getDisease().getDiseaseID();
                String symptoms = appointmentList.get(i).getSymptoms();
                

                String writeString = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", appointmentID, formattedDate, emergency, isConsulted, givenMedicine, isDone, patientID, doctorID, prescriptionID, diseaseID, symptoms);
                
                try {
                    try {
                        bw = new BufferedWriter(new FileWriter("./Database/AppointmentRecords.csv",true));
                    } catch (Exception e) {
                        bw = new BufferedWriter(new FileWriter("src/Database/AppointmentRecords.csv",true));
                    }

                    bw.write(writeString);
                    bw.newLine();
                    bw.close();
    
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    System.out.println("AppointmentRecords.csv not found, closing application...");
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException occurred, closing application...");
                    System.exit(0);
                }            
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("AppointmentRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void showAppointmentNotGivenMedicine(ArrayList<Appointment> appointmentList){
        System.out.println("============================================================APPOINTMENT LIST=====================================");
        System.out.println("|AppointmentID|Patient Name             |Doctor Name              |Consulted|PrescriptionID|Given Medicine|Done |");
        System.out.println("=================================================================================================================");

        for(int i = 0; i < appointmentList.size(); i++) {
            if(appointmentList.get(i).getIsConsulted() == true && appointmentList.get(i).getGivenMedicine() == false && appointmentList.get(i).getIsDone() == false){
                System.out.printf("|%-13s|", appointmentList.get(i).getAppointmentID());
                System.out.printf("%-25s|", appointmentList.get(i).getPatient().getName());

                System.out.printf("%-25s|", appointmentList.get(i).getDoctor().getName());
                System.out.printf("%-9s|", appointmentList.get(i).getIsConsulted());
                System.out.printf("%-14s|", appointmentList.get(i).getPrescription().getPrescriptionID());
                System.out.printf("%-14s|", appointmentList.get(i).getGivenMedicine());
                System.out.printf("%-5s|", appointmentList.get(i).getIsDone());
                System.out.println();
            }
        }

        if(appointmentList.size() == 0){
            System.out.println("No Data");
        }
    }
}
