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
import static com.opm.util.Constants.cons.TODAY_DATE;

/**
 *
 * @author john
 */
public class Shaa_Details {

    /**
     *
     * @param project
     * @param shaa
     * @param tockens
     * @param ct
     * @return
     * @throws ParseException
     */
    public static String details1(String project, String shaa, String[] tockens, int ct) throws ParseException {
        List<String> dateList = new ArrayList<>();
        List<String> emalList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();

        List<String> loginList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();
        List<String> createdList = new ArrayList<>();

        List<Long> upList = new ArrayList<>();
        List<Long> gisList = new ArrayList<>();
        List<Long> folList = new ArrayList<>();
        List<Long> fowList = new ArrayList<>();

        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        String shaa_details = "";

        if (ct == (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            long chh = 0, add = 0, del = 0;
            String login = "login######";
            if ((JSONObject) jSONObject.get("author") != null) {
                JSONObject authorOBJ = (JSONObject) jSONObject.get("author");
                login = (String) authorOBJ.get("login");
            }
            if ((JSONObject) jSONObject.get("stats") != null) {
                JSONObject fileObj = (JSONObject) jSONObject.get("stats");
                chh = (long) fileObj.get("total");
                add = (long) fileObj.get("additions");
                del = (long) fileObj.get("deletions");
            }

            shaa_details = name + "/" + email + "/" + date + "/" + login + "/" + chh + "/" + add + "/" + del + "/" + ct;

            return shaa_details;
        }
        return null;
    }

    public static String details(String project, String shaa, String[] tockens, int ct) throws ParseException {
        List<String> dateList = new ArrayList<>();
        List<String> emalList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();

        List<String> loginList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();
        List<String> createdList = new ArrayList<>();

        List<Long> upList = new ArrayList<>();
        List<Long> gisList = new ArrayList<>();
        List<Long> folList = new ArrayList<>();
        List<Long> fowList = new ArrayList<>();

        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        String shaa_details = "";

        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            long chh = 0, add = 0, del = 0, get_up = 0, get_gis = 0, get_fol = 0, get_fow = 0;
            String created_at = "created####", updated_at = "updated###", location = "location###", login = "login######";
            if ((JSONObject) jSONObject.get("author") != null) {
                JSONObject authorOBJ = (JSONObject) jSONObject.get("author");
                login = (String) authorOBJ.get("login");
                String login_details = Read_DeveloperLogin_Details.logins(login, tockens, ct);
                if (!login_details.equals("")) {
                    created_at = login_details.split("/")[0];
                    updated_at = login_details.split("/")[1];
                    location = login_details.split("/")[2];
                    
                    //System.out.println("shaa:::   "+login_details);
                    try {
                        get_up = Long.parseLong(login_details.split("/")[login_details.split("/").length-5]);
                        get_gis = Long.parseLong(login_details.split("/")[login_details.split("/").length-4]);
                        get_fol = Long.parseLong(login_details.split("/")[login_details.split("/").length-3]);
                        get_fow = Long.parseLong(login_details.split("/")[login_details.split("/").length-2]);
                        ct = Integer.parseInt(login_details.split("/")[7]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            if ((JSONObject) jSONObject.get("stats") != null) {
                JSONObject fileObj = (JSONObject) jSONObject.get("stats");
                chh = (long) fileObj.get("total");
                add = (long) fileObj.get("additions");
                del = (long) fileObj.get("deletions");
            }

            shaa_details = name + "/" + email + "/" + date + "/" + created_at + "/" + updated_at + "/" + location + "/" + login + "/" + get_up + "/" + get_gis + "/" + get_fol + "/" + get_fow + "/" + chh + "/" + add + "/" + del + "/" + ct;
        }
        return shaa_details;
    }
    
    public static String details_with_refactoring(String project, String shaa,long refactor, String[] tockens, int ct) throws ParseException {
        List<String> dateList = new ArrayList<>();
        List<String> emalList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();

        List<String> loginList = new ArrayList<>();
        List<String> locationList = new ArrayList<>();
        List<String> createdList = new ArrayList<>();

        List<Long> upList = new ArrayList<>();
        List<Long> gisList = new ArrayList<>();
        List<Long> folList = new ArrayList<>();
        List<Long> fowList = new ArrayList<>();

        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        String shaa_details = "";

        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            long chh = 0, add = 0, del = 0, get_up = 0, get_gis = 0, get_fol = 0, get_fow = 0;
            String created_at = "created####", updated_at = "updated###", location = "location###", login = "login######";
            if ((JSONObject) jSONObject.get("author") != null) {
                JSONObject authorOBJ = (JSONObject) jSONObject.get("author");
                login = (String) authorOBJ.get("login");
                String login_details = Read_DeveloperLogin_Details.logins(login, tockens, ct);
                if (!login_details.equals("")) {
                    created_at = login_details.split("/")[0];
                    updated_at = login_details.split("/")[1];
                    location = login_details.split("/")[2];
                    
                    //System.out.println("shaa:::   "+login_details);
                    try {
                        get_up = Long.parseLong(login_details.split("/")[login_details.split("/").length-5]);
                        get_gis = Long.parseLong(login_details.split("/")[login_details.split("/").length-4]);
                        get_fol = Long.parseLong(login_details.split("/")[login_details.split("/").length-3]);
                        get_fow = Long.parseLong(login_details.split("/")[login_details.split("/").length-2]);
                        ct = Integer.parseInt(login_details.split("/")[7]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            if ((JSONObject) jSONObject.get("stats") != null) {
                JSONObject fileObj = (JSONObject) jSONObject.get("stats");
                chh = (long) fileObj.get("total");
                add = (long) fileObj.get("additions");
                del = (long) fileObj.get("deletions");
            }
            shaa_details = name + "/" + email + "/" + date + "/" + created_at + "/" + updated_at + "/" + location + "/" + login + "/" + get_up + "/" + get_gis + "/" + get_fol + "/" + get_fow + "/" + refactor + "/" + add + "/" + del + "/" + ct;
        }
        return shaa_details;
    }
    
    
    public static String details_files(String project, String shaa, String[] tockens, int ct) throws ParseException {
      
        String shaa_details = "";

        if (ct >= (tockens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tockens[ct++]);
        JSONParser parser = new JSONParser();
        if (JSONUtils.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONObject comOBJ = (JSONObject) jSONObject.get("commit");
            JSONObject authOBJ = (JSONObject) comOBJ.get("author");
            String name = (String) authOBJ.get("name");
            String email = (String) authOBJ.get("email");
            String date = (String) authOBJ.get("date");
            
            String file_names = "";
            String del_string = "";
            String add_string = "";
            String change_string = "";
            String status_string = "";
            String patch_string = "";
            int count = 0;
            
            
            if ((JSONArray) jSONObject.get("files") != null) {
                JSONArray fileObj = (JSONArray) jSONObject.get("files");
                count = fileObj.size();
                for (int i = 0; i < fileObj.size(); i++) {
                    JSONObject fileOBJ = (JSONObject) fileObj.get(i);
                    String fileName = (String) fileOBJ.get("filename");
                    String status = (String) fileOBJ.get("status");
                    String patch = (String) fileOBJ.get("patch");
                    long additions = (long) fileOBJ.get("additions");
                    long deletions = (long) fileOBJ.get("deletions");
                    long changes = (long) fileOBJ.get("changes");
                    
                    file_names = file_names.concat(fileName+" ! ");
                    del_string = del_string.concat(deletions+" ! ");
                    add_string = add_string.concat(additions+" ! ");
                    change_string = change_string.concat(changes+" ! ");
                    status_string = status_string.concat(status+" ! ");
                    patch_string = patch_string.concat(patch+" ! ");
                }
                
            }

            shaa_details = name + "XXX/XXX" + email + "XXX/XXX" + file_names + "XXX/XXX" + patch_string + "XXX/XXX" + status_string  +"XXX/XXX"+add_string+"XXX/XXX"+del_string+"XXX/XXX"+change_string+"XXX/XXX"+ count+"XXX/XXX"+ct;
        }
        return shaa_details;
    }
    
}
