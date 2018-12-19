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
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class Read_DeveloperLogin_Details {

    /**
     *
     * @param login
     * @param tokens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static String logins(String login, String[] tokens, int ct) throws ParseException {
        String all_value = "";
        if (ct == (tokens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/users/" + login + "?access_token=" + tokens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {

            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            String created_at = (String) jSONObject.get("created_at");
            String updated_at = (String) jSONObject.get("updated_at");

            long get_up = (long) jSONObject.get("public_repos");
            long get_gis = (long) jSONObject.get("public_gists");
            long get_fol = (long) jSONObject.get("followers");
            long get_fow = (long) jSONObject.get("following");
            String location = "location";
            if ((String) jSONObject.get("location") != null) {
                location = (String) jSONObject.get("location");
            }
            all_value = created_at + "/" + updated_at +"/"+location+ "/" + get_up + "/" + get_gis + "/" + get_fol + "/" + get_fow + "/" + ct;
        }
        return all_value;
    }
}
