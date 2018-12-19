/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import com.opm.core.Call_URL;
import com.opm.core.JSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author john
 */
public class Read_ReposCreation_Date {

    /**
     * 
     * @param project
     * @param tokens
     * @param ct
     * @return 
     */
    public static String creation(String project, String[] tokens, int ct) {
        String created = "";
        try {
            if (ct >= (tokens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String url = "https://api.github.com/repos/" + project + "?access_token=" + tokens[ct++];
            String forks_url = Call_URL.callURL(url);
            if (JSONUtils.isValidJSONObject(forks_url) == false) {///                             
                System.out.println(":Invalid fork found!   - " + url);
                //break;
            }

            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse(forks_url);
            created = (String) jSONObject.get("created_at");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return created+"/"+ct;

    }
}
