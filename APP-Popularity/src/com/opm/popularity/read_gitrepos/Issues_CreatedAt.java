/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.JSONUtilsConversion;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class Issues_CreatedAt {

    public static ArrayList<String> getData(String project, String[] token, int ct) throws ParseException {
        int y_m_d = 20160306;
        int h_m_s = 0;
        long marchDate = 20160306;
        marchDate = (long) 20160306;
        ArrayList<String> details = new ArrayList<String>();

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/issues?page=" + p + "&state=all&per_page=100&access_token=" + token[ct++]);
                if (JSONUtilsConversion.isValidJSON(forks_url) == false) {///

                    System.out.println(" :Invalid Issues found!");
                    break;
                }
                JSONArray a = (JSONArray) parser.parse(forks_url);
                if (a.toString().equals("[]")) {
                    x = 1;//System.out.println("Empty JSON Object Returned: "+a.toString());
                    break;
                }
                for (Object o : a) {

                    JSONObject jsonObject = (JSONObject) o;
                    String state = (String) jsonObject.get("state");
                    String created_at = (String) jsonObject.get("created_at");
                    String updated_at = (String) jsonObject.get("updated_at");
                    
                    JSONObject userObject = (JSONObject) jsonObject.get("user");

                    String title = (String) jsonObject.get("title");;
                    String login = "login";
                    if (userObject != null) {
                        login = (String) userObject.get("login");
                        
                    }

                    String closed_at = "empty";
                    if (jsonObject.get("closed_at") != null) {
                        closed_at = (String) jsonObject.get("closed_at");
                    }

                    details.add(state + "/" + created_at+"/"+updated_at+"/"+closed_at+"/"+title+"/"+login);
                }
                p++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = ct + "";
        details.add(str);
        return details;

    }
    
    public static ArrayList<String> getDatas(String project, String[] token, int ct) throws ParseException {
        int y_m_d = 20160306;
        int h_m_s = 0;
        long marchDate = 20160306;
        marchDate = (long) 20160306;
        ArrayList<String> details = new ArrayList<String>();

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/issues?page=" + p + "&state=closed&per_page=100&access_token=" + token[ct++]);
                if (JSONUtilsConversion.isValidJSON(forks_url) == false) {///

                    System.out.println(" :Invalid Issues found!");
                    break;
                }
                JSONArray a = (JSONArray) parser.parse(forks_url);
                if (a.toString().equals("[]")) {
                    x = 1;//System.out.println("Empty JSON Object Returned: "+a.toString());
                    break;
                }
                for (Object o : a) {

                    JSONObject jsonObject = (JSONObject) o;
                    String state = (String) jsonObject.get("state");
                    String created_at = (String) jsonObject.get("created_at");
                    String updated_at = (String) jsonObject.get("updated_at");
                    long number = (long) jsonObject.get("number");
                    
                    JSONObject userObject = (JSONObject) jsonObject.get("user");

                    String title = (String) jsonObject.get("title");;
                    String login = "login";
                    if (userObject != null) {
                        login = (String) userObject.get("login");
                        
                    }

                    String closed_at = "empty";
                    if (jsonObject.get("closed_at") != null) {
                        closed_at = (String) jsonObject.get("closed_at");
                    }
                    details.add(state + "/" +closed_at+"/"+title+"/"+number+"/"+login);
                }
                p++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = ct + "";
        details.add(str);
        return details;

    }
    
    public static ArrayList<String> getDetailsIS(String project, String[] token, int ct) throws ParseException {
        int y_m_d = 20160306;
        int h_m_s = 0;
        long marchDate = 20160306;
        marchDate = (long) 20160306;
        ArrayList<String> details = new ArrayList<String>();

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/issues?page=" + p + "&state=closed&per_page=100&access_token=" + token[ct++]);
                if (JSONUtilsConversion.isValidJSON(forks_url) == false) {///

                    System.out.println(" :Invalid Issues found!");
                    break;
                }
                JSONArray a = (JSONArray) parser.parse(forks_url);
                if (a.toString().equals("[]")) {
                    x = 1;//System.out.println("Empty JSON Object Returned: "+a.toString());
                    break;
                }
                for (Object o : a) {
                    JSONObject jsonObject = (JSONObject) o;
                    String state = (String) jsonObject.get("state");
                    String created_at = (String) jsonObject.get("created_at");
                    String updated_at = (String) jsonObject.get("updated_at");
                    
                    JSONObject userObject = (JSONObject) jsonObject.get("user");

                    String title = (String) jsonObject.get("title");;
                    String login = "login";
                    if (userObject != null) {
                        login = (String) userObject.get("login");
                        
                    }

                    String closed_at = "empty";
                    if (jsonObject.get("closed_at") != null) {
                        closed_at = (String) jsonObject.get("closed_at");
                    }

                    details.add(created_at+"/"+closed_at);
                }
                p++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = ct + "";
        details.add(str);
        return details;
    }
    
}
