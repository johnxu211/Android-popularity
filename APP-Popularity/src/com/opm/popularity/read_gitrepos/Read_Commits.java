/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.JSONUtilsConversion;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static com.opm.popularity.util.Constants.cons.TODAY_DATE;

/**
 *
 * @author john
 */
public class Read_Commits {
    public static String count(String project,String pr,String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        double counts = 0;
        double changes = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls/"+pr+"/commits?until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            if (jsonString.equals("Error")) {
                break;
            }
            JSONParser parser = new JSONParser();
            if (JSONUtilsConversion.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]") || jsonArray.size() == 0) {
                    /// Break out of the loop, when empty array is found!
                    break;
                } 
                counts += jsonArray.size();
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    String shaa = (String) jsonObject.get("sha");
                    String commits_details = Shaa_Details.details1(project, (String) jsonObject.get("sha"), tockens, ct);
                    
                   // System.out.println(shaa+"\t"+commits_details);
                    if (commits_details != null) {
                        ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length - 1]);
                        
                        try{
                            if (commits_details.contains("/")) {
                               changes += Double.parseDouble(commits_details.split("/")[commits_details.split("/").length - 4]);
                            }
                            }catch (Exception e){
                            e.printStackTrace();
                        }
                        
                        
                    }
                }
                
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
       
        return counts+"/"+changes;
    }
    
    public static String count2(String project,String pr,String[] tockens, int ct) throws ParseException {
        //System.out.println("   "+project+"\t"+pr);
        int p = 1; // Page number parameter
        double counts = 0;
        double changes = 0;
        String shaaString = "-";
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls/"+pr+"/commits?until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            if (jsonString.equals("Error")) {
                break;
            }
            JSONParser parser = new JSONParser();
            if (JSONUtilsConversion.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]") || jsonArray.size() == 0) {
                    /// Break out of the loop, when empty array is found!
                    break;
                } 
                counts += jsonArray.size();
                for (Object jsonObj : jsonArray) {

                    shaaString = shaaString.replace("-", "");
                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    String shaa = (String) jsonObject.get("sha");
                    shaaString = shaaString.concat(shaa+"/");
                    String commits_details = Shaa_Details.details1(project, (String) jsonObject.get("sha"), tockens, ct);
                    
                   // System.out.println(shaa+"\t"+commits_details);
                    if (commits_details != null) {
                        ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length - 1]);
                        
                        try{
                            if (commits_details.contains("/")) {
                               changes += Double.parseDouble(commits_details.split("/")[commits_details.split("/").length - 4]);
                            }
                            }catch (Exception e){
                            e.printStackTrace();
                        }
                        
                        
                    }
                }
                
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
       
        return counts+"|"+changes+"|"+shaaString;
    }
    
}
