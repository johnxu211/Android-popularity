/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.core;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author john
 */
public class Latest_Commits {
    /**
     *
     * @param project
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static String getLatest(String project, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> alllist = new ArrayList<>();
        double count = 0;
        int times = 0;
        int flag = 0;
        //while (true) {////loop thru the pagess....
        if (ct == (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = new Call_URL().callURL("https://api.github.com/repos/" + project + "/commits?page=1&per_page=1&access_token=" + tockens[ct++]);
        String dates = "date";
        String name = "name";
        if (jsonString.equals("Error")) {
            //break;
        }
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSON(jsonString) == true) {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
            for (Object jsonObj : jsonArray) {
                JSONObject jsonObject = (JSONObject) jsonObj;
                //String shaa = (String) jsonObject.get("sha");
                if ((JSONObject) jsonObject.get("commit") != null) {
                    JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                    if ((JSONObject) commitsObj.get("author") != null) {
                        JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                        name = (String) author_Obj.get("name");
                        dates = (String) author_Obj.get("date");
                    }
                }
            }/// *** End of JSon Object.....  

        }
        return dates + "/" + ct;
    }
}
