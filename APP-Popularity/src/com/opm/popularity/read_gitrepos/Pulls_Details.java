/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.DateOperations;
import com.opm.popularity.core.JSONUtilsConversion;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opm.popularity.util.Constants;
import static com.opm.popularity.util.Constants.cons.TODAY_DATE;

/**
 *
 * @author john
 */
public class Pulls_Details {

     public static List<List<String>> getDetails1(String project, List<String> pulls, String[] token, int ct) throws ParseException {
        List< List<String>> allList = new ArrayList<>();
        List<Long> totalList = new ArrayList<>();
        List<String> nList = new ArrayList<>();
        List<String> pList = new ArrayList<>();
        long t = 0;
        for (int i = 0; i < pulls.size(); i++) {
            totalList.add(t);
            nList.add("");
            pList.add(pulls.get(i));
        }

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int count = 0;

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls?page=" + p + "&per_page=100&until=" + Constants.cons.TODAY_DATE + "&state=closed&direction=asc&access_token=" + token[ct++]);
                if (JSONUtilsConversion.isValidJSON(forks_url) == false) {///

                    System.out.println(" :Invalid Pull found!");
                    break;
                }
                JSONArray a = (JSONArray) parser.parse(forks_url);
                System.out.println(""+a.size());
                if (a.toString().equals("[]")) {
                    x = 1;//System.out.println("Empty JSON Object Returned: "+a.toString());
                    break;
                }
                for (Object o : a) {
                    count++;
                    JSONObject jsonObject = (JSONObject) o;
                   // if ((String) jsonObject.get("merged_at") != null) {
                        String closed_at = (String) jsonObject.get("closed_at");
                        if (DateOperations.compareDates(closed_at, Constants.cons.TODAY_DATE) == true) {
                            long number = (long) jsonObject.get("number");
                            JSONObject headOBJ = (JSONObject) jsonObject.get("head");
                            if (headOBJ != null) {
                                JSONObject repoOBJ = (JSONObject) headOBJ.get("repo");
                                if (repoOBJ != null) {
                                    if ((String) repoOBJ.get("full_name") != null) {
                                        String full_name = (String) repoOBJ.get("full_name");
                                        // System.out.println(count + " : " + number + "\t" + full_name);
                                        if (pulls.contains(full_name)) {
                                            totalList.set(pulls.indexOf(full_name), totalList.get(pulls.indexOf(full_name)) + 1);
                                            nList.set(pulls.indexOf(full_name), nList.get(pulls.indexOf(full_name)).concat(number + "-"));
                                            pList.set(pulls.indexOf(full_name), full_name);
                                        }
                                    } else {
                                        //System.out.println(count + " :  No fork names found...!!!" );
                                    }

                                }

                            }
                        }

                   // }

                }
                p++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> tList = new ArrayList<>();
        for (int i = 0; i < totalList.size(); i++) {
            tList.add(String.valueOf(totalList.get(i)));
        }
        //tList.add(String.valueOf(ct));
        allList.add(tList);
        allList.add(nList);
        allList.add(pList);

        return allList;

    }
     
    public static List<List<String>> getDetails(String project, List<String> pulls, String[] token, int ct) throws ParseException {
        List< List<String>> allList = new ArrayList<>();
        List<Long> totalList = new ArrayList<>();
        List<String> nList = new ArrayList<>();
        List<String> pList = new ArrayList<>();
        long t = 0;
        for (int i = 0; i < pulls.size(); i++) {
            totalList.add(t);
            nList.add("");
            pList.add(pulls.get(i));
        }

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int count = 0;

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls?page=" + p + "&per_page=100&until=" + Constants.cons.TODAY_DATE + "&state=closed&direction=asc&access_token=" + token[ct++]);
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
                    count++;
                    JSONObject jsonObject = (JSONObject) o;
                    if ((String) jsonObject.get("merged_at") != null) {
                        String merged_at = (String) jsonObject.get("merged_at");
                        if (DateOperations.compareDates(merged_at, Constants.cons.TODAY_DATE) == true) {
                            long number = (long) jsonObject.get("number");
                            
                            //get the commits here...!
                            
                            JSONObject headOBJ = (JSONObject) jsonObject.get("head");
                            if (headOBJ != null) {
                                JSONObject repoOBJ = (JSONObject) headOBJ.get("repo");
                                if (repoOBJ != null) {
                                    if ((String) repoOBJ.get("full_name") != null) {
                                        String full_name = (String) repoOBJ.get("full_name");
                                        // System.out.println(count + " : " + number + "\t" + full_name);
                                        if (pulls.contains(full_name)) {
                                            totalList.set(pulls.indexOf(full_name), totalList.get(pulls.indexOf(full_name)) + 1);
                                            nList.set(pulls.indexOf(full_name), nList.get(pulls.indexOf(full_name)).concat(number + "-"));
                                            pList.set(pulls.indexOf(full_name), full_name);

                                        }
                                    } else {
                                        //System.out.println(count + " :  No fork names found...!!!" );
                                    }

                                }

                            }
                        }

                    }

                }
                p++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> tList = new ArrayList<>();
        for (int i = 0; i < totalList.size(); i++) {
            tList.add(String.valueOf(totalList.get(i)));
        }
        tList.add(String.valueOf(ct));
        allList.add(tList);
        allList.add(nList);
        allList.add(pList);

        return allList;

    }
    
    
    public static List<List<String>> getMergedPulls(String project, List<String> pulls, String[] token, int ct) throws ParseException {
        List< List<String>> allList = new ArrayList<>();
        List<Long> totalList = new ArrayList<>();
        List<String> nList = new ArrayList<>();
        List<String> pList = new ArrayList<>();
        List<String> cList = new ArrayList<>();
        long t = 0;
        for (int i = 0; i < pulls.size(); i++) {
            totalList.add(t);
            nList.add("");
            cList.add(t+"");
            pList.add(pulls.get(i));
        }

        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int count = 0;

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls?page=" + p + "&per_page=100&until=" + Constants.cons.TODAY_DATE + "&state=closed&direction=asc&access_token=" + token[ct++]);
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
                    count++;
                    JSONObject jsonObject = (JSONObject) o;
                    if ((String) jsonObject.get("merged_at") != null) {
                        String merged_at = (String) jsonObject.get("merged_at");
                        if (DateOperations.compareDates(merged_at, Constants.cons.TODAY_DATE) == true) {
                            long number = (long) jsonObject.get("number");
                            
                            //get the commits here...!
                            double mlp_commits = countCom(project, number, token, ct);
                            
                            JSONObject headOBJ = (JSONObject) jsonObject.get("head");
                            if (headOBJ != null) {
                                JSONObject repoOBJ = (JSONObject) headOBJ.get("repo");
                                if (repoOBJ != null) {
                                    if ((String) repoOBJ.get("full_name") != null) {
                                        String full_name = (String) repoOBJ.get("full_name");
                                        // System.out.println(count + " : " + number + "\t" + full_name);
                                        if (pulls.contains(full_name)) {
                                            totalList.set(pulls.indexOf(full_name), totalList.get(pulls.indexOf(full_name)) + 1);
                                            nList.set(pulls.indexOf(full_name), nList.get(pulls.indexOf(full_name)).concat(number + "-"));
                                            cList.set(pulls.indexOf(full_name), (Double.parseDouble(cList.get(pulls.indexOf(full_name))) + mlp_commits)+"");
                                            pList.set(pulls.indexOf(full_name), full_name);

                                        }
                                    } else {
                                        //System.out.println(count + " :  No fork names found...!!!" );
                                    }

                                }

                            }
                        }

                    }

                }
                p++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> tList = new ArrayList<>();
        for (int i = 0; i < totalList.size(); i++) {
            tList.add(String.valueOf(totalList.get(i)));
        }
        tList.add(String.valueOf(ct));
        allList.add(tList);
        allList.add(nList);
        allList.add(pList);
        allList.add(cList);

        return allList;

    }

    public static List<List<String>> getClosedPR(String project, String[] token, int ct) throws ParseException {
        List< List<String>> allList = new ArrayList<>();
        List<String> createList = new ArrayList<>();
        List<String> closedList = new ArrayList<>();
        List<String> pullList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter
            int i = 0; // Commit Counter
            int count = 0;

            while (true) {
                if (ct == (token.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                // xxxxxx Need to include the Since and Until parameters so as to return only the commits between two given tags
                String forks_url = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls?page=" + p + "&per_page=100&state=closed&until=" + Constants.cons.TODAY_DATE + "&state=closed&direction=asc&access_token=" + token[ct++]);
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
                    count++;
                    JSONObject jsonObject = (JSONObject) o;

                    long number = (long) jsonObject.get("number");
                    String created_at = (String) jsonObject.get("created_at");
                    String closed_at = (String) jsonObject.get("closed_at");
                    JSONObject headOBJ = (JSONObject) jsonObject.get("head");
                    if (headOBJ != null) {
                        JSONObject repoOBJ = (JSONObject) headOBJ.get("repo");
                        if (repoOBJ != null) {
                            if ((String) repoOBJ.get("full_name") != null) {
                                String full_name = (String) repoOBJ.get("full_name");
                                pullList.add(full_name);
                                createList.add(created_at);
                                closedList.add(closed_at);
                            }

                        }

                    }

                }
                p++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        pullList.add(ct + "");
        allList.add(createList);
        allList.add(closedList);
        allList.add(pullList);
        return allList;

    }
    
    public static double countCom(String project,long pr,String[] tockens, int ct) throws ParseException {
        //System.out.println("   "+project+"\t"+pr);
        int p = 1; // Page number parameter
        double counts = 0;
        double changes = 0;
        String shaaString = "-";
        while (true) {////loop thru the pagess....
            
            if (ct == (tockens.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
            String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "/pulls/"+pr+"/commits?until=" + TODAY_DATE + "&page=" + p + "&per_page=100&access_token=" + tockens[ct++]);
            if (jsonString.equals("Error")) {
                break;
            }
            JSONParser parser = new JSONParser();
            if (JSONUtilsConversion.isValidJSON(jsonString) == true) {
                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]") || jsonArray.size() == 0) {
                    /// Break out of the loop, when empty array is found!
                    break;
                } 
                counts += jsonArray.size();
             
                
            }
            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
       
        return counts;
    }
}
