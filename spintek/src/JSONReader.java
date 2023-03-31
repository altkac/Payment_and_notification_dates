import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JSONReader {

    public static String getJSONFromFile(String filename) {
        StringBuilder jsonText = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                jsonText.append(line).append("\n");
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonText.toString();
    }

    public static void createDatesArrayList(ArrayList <String> dates){
        String strJson = getJSONFromFile("src\\unwantedDates.json");

        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(strJson);
            JSONArray jsonArray = (JSONArray) object;

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String kind_id = (String) jsonObject.get("kind_id");
                String date = (String) jsonObject.get("date");

                //"3" and "4" are not holidays
                // The ".json" file contains year 2022, which is no longer necessary
                if(!(kind_id.equals("3") || kind_id.equals("4") || date.equals("2022"))){
                    dates.add(date);
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
