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
public class ReadCLOC {

    public static String getVal(String url, String[] token, int ct) throws ParseException {
        long total_val = 0;
        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            if (ct == (token.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
            String jString = Call_URL.callURL(url+"?access_token=" + token[ct++]);

            JSONObject jSONObject = (JSONObject) parser.parse(jString);
            JSONObject statsOBJ = (JSONObject) jSONObject.get("stats");

            long total = (long) statsOBJ.get("total");
            total_val = total;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total_val+"/"+ct;

    }
}
