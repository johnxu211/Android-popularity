/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import com.opm.core.Call_URL;
import com.opm.core.JSONUtils;
import static com.opm.util.Constants.cons.TODAY_DATE;
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
public class ReadReposCommits {

    public static List<List<String>> count(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {

        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> messageList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct >= (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            if (!first_date.equals("")) {
              jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
  
            }else{
               jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]); 
            }
            
            
            //System.out.println("                https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE);
            String dates = "date";
            String name = "name";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                times += jsonArray.size();
                if (times % 500 == 0) {
                    System.out.println("     " + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    modeList.add(type);

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            dates = (String) author_Obj.get("date");
                            dd_list.add(dates);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            dateList.add((String) commiterObj.get("date"));
                        }
                        if ((String) commitsObj.get("message") != null) {
                            messageList.add((String) commitsObj.get("message"));
                            //System.out.println((String) commitsObj.get("message") + "\t" + (String) jsonObject.get("sha"));
                        }
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        modeList.add(ct+"");
        allLists.add(shaList);
        allLists.add(dateList);
        allLists.add(messageList);
        allLists.add(modeList);

        return allLists;
    }
    
    public static List<List<String>> count2222(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {

        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> messageList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            //jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            if (type.equals("mlp")) {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
                
            } else {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }
            String dates = "date";
            String name = "name";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                times += jsonArray.size();
                if (times % 500 == 0) {
                    System.out.println("     " + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    modeList.add(type);

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            dates = (String) author_Obj.get("date");
                            dd_list.add(dates);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            dateList.add((String) commiterObj.get("date"));
                        }
                        if ((String) commitsObj.get("message") != null) {
                            messageList.add((String) commitsObj.get("message"));
                            //System.out.println((String) commitsObj.get("message") + "\t" + (String) jsonObject.get("sha"));
                        }
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        modeList.add(ct+"");
        allLists.add(shaList);
        allLists.add(dateList);
        allLists.add(messageList);
        allLists.add(modeList);

        return allLists;
    }

    public static List<List<String>> count_2(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        List<String> loginList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";//Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            ///System.out.println("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "");
            if (type.equals("mlp")) {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
                
            } else {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }
            String dates = "date";
            String name = "name";
            String email = "email";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    modeList.add(type);

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            dates = (String) author_Obj.get("date");
                            email = (String) author_Obj.get("email");
                            dd_list.add(dates);
                            emailList.add(email);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            dateList.add((String) commiterObj.get("date"));
                        }
                        if ((JSONObject) jsonObject.get("author") != null) {
                            JSONObject author = (JSONObject) jsonObject.get("author");
                            String login = (String) author.get("login");
                            loginList.add(name + "|" + email + "|" + login);
                        } else {
                            loginList.add(name + "|" + email + "|login######");
                        }
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        modeList.add(ct+"");
        allLists.add(shaList);
        allLists.add(dd_list);
        allLists.add(loginList);
        allLists.add(modeList);

        return allLists;
    }

    public static List<List<String>> count_3(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> emailList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> loginList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> modeList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            String dates = "date";
            String name = "name";
            String email = "email";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                //System.out.println( "    "+p+" \t"+jsonArray.size());
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    modeList.add(type);

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            email = (String) author_Obj.get("email");
                            dates = (String) author_Obj.get("date");
                            dd_list.add(dates);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            emailList.add((String) commiterObj.get("email"));
                        }

                    }

                    if ((JSONObject) jsonObject.get("author") != null) {
                        JSONObject author = (JSONObject) jsonObject.get("author");
                        String login = (String) author.get("login");
                        loginList.add(name + "|" + email + "|" + login);
                    } else {
                        loginList.add(name + "|" + email + "|login######");
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        allLists.add(shaList);
        allLists.add(emailList);
        allLists.add(loginList);
        allLists.add(modeList);

        return allLists;
    }

    public static List<List<String>> countDetails(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        List<String> loginList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> detailsList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            if (type.equals("mlp")) {
                // jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            } else {
                // jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }
            //System.out.println("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "");
            String dates = "date";
            String name = "name";
            String email = "email";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

                times += jsonArray.size();
                if (times % 500 == 0) {
                    System.out.println(" \t" + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    String commits_details = Shaa_Details.details1(project, (String) jsonObject.get("sha"), tockens, ct);
                    if (commits_details != null) {
                        ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length - 1]);
                        detailsList.add(commits_details);
                    } else {
                        detailsList.add("");
                    }

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            dates = (String) author_Obj.get("date");
                            email = (String) author_Obj.get("email");
                            dd_list.add(dates);
                            emailList.add(email);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            dateList.add((String) commiterObj.get("date"));
                        }
                        if ((JSONObject) jsonObject.get("author") != null) {
                            JSONObject author = (JSONObject) jsonObject.get("author");
                            String login = (String) author.get("login");
                            loginList.add(name + "|" + email + "|" + login);
                        } else {
                            loginList.add(name + "|" + email + "|login######");
                        }
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        allLists.add(shaList);
        allLists.add(emailList);
        allLists.add(loginList);
        allLists.add(detailsList);

        return allLists;
    }

    public static List<List<String>> countDetails2(String project, String type, String first_date, String last_date, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> c_return = new ArrayList<>();
        List<String> dd_list = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        List<String> loginList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        List<String> detailsList = new ArrayList<>();

        double count = 0;
        int times = 0;
        int flag = 0;
        double tot_changes = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = "";
            //jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            if (type.equals("mlp")) {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
                if (ct == (tockens.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                //System.out.println("https://api.github.com/repos/" + project + "/commits?until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);

            } else {
                jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + last_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            }
            //System.out.println("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "");
            String dates = "date";
            String name = "name";
            String email = "email";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

                times += jsonArray.size();
                //System.out.println(p+"   \t"+times);
                if (times % 500 == 0) {
                    System.out.println(" \t" + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));
                    String commits_details = Shaa_Details.details1(project, (String) jsonObject.get("sha"), tockens, ct);

                    // System.out.println(shaa+"\t"+commits_details);
                    if (commits_details != null) {
                        ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length - 1]);
                        detailsList.add(commits_details);

                        tot_changes += Double.parseDouble(commits_details.split("/")[commits_details.split("/").length - 4]);
                        //System.out.println("        "+tot_changes);
                    } else {
                        detailsList.add("");
                    }

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            name = (String) author_Obj.get("name");
                            dates = (String) author_Obj.get("date");
                            email = (String) author_Obj.get("email");
                            dd_list.add(dates);
                            emailList.add(email);
                            count++;
                        }
                        if ((JSONObject) commitsObj.get("committer") != null) {
                            JSONObject commiterObj = (JSONObject) commitsObj.get("committer");
                            dateList.add((String) commiterObj.get("date"));
                        }
                        if ((JSONObject) jsonObject.get("author") != null) {
                            JSONObject author = (JSONObject) jsonObject.get("author");
                            String login = (String) author.get("login");
                            loginList.add(name + "|" + email + "|" + login);
                        } else {
                            loginList.add(name + "|" + email + "|login######");
                        }
                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        
        detailsList.add(tot_changes+"");
        allLists.add(shaList);
        allLists.add(emailList);
        allLists.add(loginList);
        allLists.add(detailsList);
        allLists.add(dd_list);

        return allLists;
    }

    public static List<List<String>> countALLCOM(String project, String latest_date, String[] tockens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter
        String status = "fine";
        List<String> dd_list = new ArrayList<>();
        List<String> shaList = new ArrayList<>();
        List<List<String>> allLists = new ArrayList<>();
        double count = 0;
        int times = 0;
        int flag = 0;
        double tot_changes = 0;
        while (true) {////loop thru the pagess....
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/commits?until=" + latest_date + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            //System.out.println("https://api.github.com/repos/" + project + "/commits?since=" + first_date + "&until=" + TODAY_DATE + "");
            String dates = "date";
            String name = "name";
            String email = "email";
            if (jsonString.equals("Error")) {
                break;
            }

            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSON(jsonString) == true) {

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

                times += jsonArray.size();
                //System.out.println(p+"   \t"+times);
                if (times % 500 == 0) {
                    System.out.println(" \t" + times);
                }
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {

                    JSONObject jsonObject = (JSONObject) jsonObj;
                    //String shaa = (String) jsonObject.get("sha");
                    String shaa = (String) jsonObject.get("sha");
                    shaList.add((String) jsonObject.get("sha"));

                    if ((JSONObject) jsonObject.get("commit") != null) {
                        JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                        if ((JSONObject) commitsObj.get("author") != null) {
                            JSONObject author_Obj = (JSONObject) commitsObj.get("author");
                            dates = (String) author_Obj.get("date");
                            dd_list.add(dates);
                            count++;
                        }

                    }
                }/// *** End of JSon Object.....  
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        allLists.add(shaList);
        allLists.add(dd_list);

        return allLists;
    }

}
