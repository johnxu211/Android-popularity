/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import com.opm.core.Call_URL;
import com.opm.core.Call_URL_1;
import com.opm.core.DateOperations;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Starts_CreatedAt {

    public static String repoStars(String projectName, String[] tokens, int ct) {

        int count = 0;
        int size = 0;
        JSONParser parser = new JSONParser();
        try {
            //URL oracle = new URL("https://api.github.com/repos/atom/atom/commits?page=1&per_page=100&since=2016-04-05T00:18:59Z&until=2016-04-18T22:20:18Z"); // URL to Parse
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            while (true) {
                if (ct == tokens.length) {
                    ct = 0;
                }
                String jsonString = Call_URL_1.callURL2("https://api.github.com/repos/" + projectName + "/stargazers?page=" + p + "&per_page=100&access_token=" + tokens[ct++], "v3.star");
                //System.out.println(jsonString);
                if (jsonString.equals("[]")) {
                    break;
                }
                JSONArray jSONArray = (JSONArray) parser.parse(jsonString);
                size += jSONArray.size();
                System.out.println(size);
                for (int j = 0; j < jSONArray.size(); j++) {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(j);
                    String stared_at = (String) jSONObject.get("starred_at");
                    //System.out.println(stared_at);
                    if (DateOperations.compareDates(stared_at, Constants.cons.TODAY_DATE) == false) {
                        count++;
                    }
                }
                p++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count + "/" + ct;
    }
}
