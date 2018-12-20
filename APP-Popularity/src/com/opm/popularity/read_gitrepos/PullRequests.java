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
public class PullRequests {

    /**
     *
     * @param project
     * @param token
     * @param ct
     * @return
     * @throws ParseException
     */
    public ArrayList<String> getDatas(String project, String[] token, int ct) throws ParseException {
        int y_m_d = 20170717;
        int h_m_s = 0;
        long marchDate = 20170717;
        marchDate = (long) 20170717;
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
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls?page=" + p + "&per_page=100&state=all&access_token=" + token[ct++]);
                if (JSONUtilsConversion.isValidJSON(forks_url) == false) {///

                    System.out.println(" :Invalid Pull found!");
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
                    details.add(state + ":" + created_at);
                    String updated_at = (String) jsonObject.get("updated_at");

                    String closed_at = "empty";
                    if (state.equals("closed")) {
                        closed_at = (String) jsonObject.get("closed_at");
                    }

                    String merged_at = "empty";
                    if (jsonObject.get("merged_at") != null) {
                        merged_at = (String) jsonObject.get("merged_at");
                    }
                }
                p++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(details);
        String str = ct + "";
        details.add(str);
        return details;

    }
}
