import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Disease {
    String diseaseID;
    String diseaseName;
    String category;
    String dangerLevel;

    public Disease(String diseaseID, String diseaseName, String category, String dangerLevel){
        this.diseaseID = diseaseID;
        this.diseaseName = diseaseName;
        this.category = category;
        this.dangerLevel = dangerLevel;
    }

    public String getDiseaseID() {
        return diseaseID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getCategory() {
        return category;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDiseaseID(String diseaseID) {
        this.diseaseID = diseaseID;
    }
    
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public static Disease getDisease(ArrayList<Disease> diseaseList, String diseaseID){

        int index = -1;

        for(int i = 0; i < diseaseList.size(); i++){
            if(diseaseList.get(i).getDiseaseID().equals(diseaseID)){
                index = i;
                break;
            }
        }

        return diseaseList.get(index);
    }

    public static int searchDisease(ArrayList<Disease> diseaseList, String diseaseID){

        int index = -1;

        for(int i = 0; i < diseaseList.size(); i++){
            if(diseaseList.get(i).getDiseaseID().equals(diseaseID)){
                index = i;
                break;
            }
        }

        return index;
    }

    public static void loadDisease(ArrayList<Disease> diseaseList){
        try{

            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader("./Database/DiseaseRecords.csv"));
            } catch (Exception e) {
                br = new BufferedReader(new FileReader("src/Database/DiseaseRecords.csv"));
            }
            
            String line;


            while((line = br.readLine()) != null){
                String[] detail = line.split(",");
                diseaseList.add(new Disease(detail[0], detail[1], detail[2], detail[3]));
            }

            br.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("DiseaseRecords.csv not found, closing application...");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred, closing application...");
            System.exit(0);
        }
    }

    public static void showDisease(ArrayList<Disease> diseaseList){

        System.out.println("===================================APPOINTMENT LIST======================");
        System.out.println("|DiseaseID|Disease Name             |Category            |Danger Level  |");
        System.out.println("=========================================================================");

        for(int i = 0; i < diseaseList.size(); i++){

            if(!diseaseList.get(i).getDiseaseID().equals("DS00X")){
                System.out.printf("|%-9s|", diseaseList.get(i).getDiseaseID());
                System.out.printf("%-25s|", diseaseList.get(i).getDiseaseName());

                System.out.printf("%-20s|", diseaseList.get(i).getCategory());
                System.out.printf("%-14s|", diseaseList.get(i).getDangerLevel());
                System.out.println();
            }
        }
    }
}



