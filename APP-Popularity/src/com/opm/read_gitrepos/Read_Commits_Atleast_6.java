/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;
import com.opm.core.Call_URL;
import com.opm.core.JSONUtils;
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
public class Read_Commits_Atleast_6 {
    /**
     * 
     * @param project
     * @param today
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException 
     */
    public static String countCommits(String project, String today, String[] tockens, int ct) throws ParseException {
        int flag = 0;
        if (ct == (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + today + "&page=1&per_page=100&access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSON(jsonString) == true) {
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
            if (jsonArray.size() >= 6) {
                flag = 1;
            }
        }
        return flag + "/" + ct;
    }
}
