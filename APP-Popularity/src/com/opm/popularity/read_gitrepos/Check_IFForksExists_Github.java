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
public class Check_IFForksExists_Github {

    /**
     *
     * @param project
     * @param token
     * @param ct
     * @return
     */
    public static String isExists(String project, String[] token, int ct) {
        JSONParser parser = new JSONParser();
        int rr = 1;
        try {
            if (ct == (token.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "?access_token=" + token[ct++]);
            if (JSONUtilsConversion.isValidJSONObject(forks_url) == false) {///

                System.out.println("    " + project + "\t Doesn't exist..!");
                rr = 0;
                return rr + "/" + ct;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rr + "/" + ct;

    }
}
