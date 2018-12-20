/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.JSONUtilsConversion;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author john
 */
public class New_CommitsInterval_2 {

    /**
     * **
     *
     * @param projectName
     * @param date1
     * @param date2
     * @param i
     * @param toks
     * @return
     * @throws org.json.simple.parser.ParseException
     */
    public static ArrayList<Object[]> getCommitsNow(String projectName, String created_at, String proj_major, String date1, String date2, int i, String[] toks, String lastDate, double pl, double pc, double is, double ic, double ff, int ct, int com_index, String shaaStrings) throws org.json.simple.parser.ParseException, ParseException {
        List<String> shaaaList = new ArrayList<>();
        String[] splits_sh = shaaStrings.split("/");
        for (int j = 0; j < splits_sh.length; j++) {
            shaaaList.add(splits_sh[j].split(":")[0]);
        }
        
        Object[] datas = null;
        //System.out.println(i+"\t\t\t : "+date1+"\t\t - "+date2);
        String sub_date = date1.substring(date1.lastIndexOf(":") + 1, date1.length() - 1);
        String sub_d2 = String.valueOf(Integer.parseInt(sub_date) + 1) + "Z";
        if (com_index > 0) {
            date1 = date1.substring(0, date1.lastIndexOf(":")) + ":" + sub_d2;
        }
        ///..........................................
        String location = "location";
        long public_repos = 0;
        long public_gists = 0;
        long followers = 0;
        long following = 0;
        String createdAt = "#######";
        String updatedAt = "#######";
        String login = "login######";
        String loginURL = "";
        JSONObject loginObj = null;
        long changed = 0, added = 0, deleted = 0;

        ArrayList<String> shaLists = new ArrayList<>();
        ArrayList<String> logList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();

        Set<String> loginSet = new LinkedHashSet<String>();
        Set<String> locationSet = new LinkedHashSet<String>();
        Set<String> nameSet = new LinkedHashSet<String>();
        Set<String> emailSet = new LinkedHashSet<String>();
        Set<String> createdSet = new LinkedHashSet<String>();
        Set<String> updatedSet = new LinkedHashSet<String>();

        List<Long> cList = new ArrayList<>();
        List<Long> aList = new ArrayList<>();
        List<Long> dList = new ArrayList<>();

        ArrayList< List<String>> list = new ArrayList< List<String>>();
        JSONParser parser = new JSONParser();

        int p = 1; // Page number parameter
        //int i = 0; // Commit Counter

        int count = 0;
        int flag = 0;
        while (true) {////loop thru the pagess....
            if (ct == (toks.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = Call_URL.callURL("https://api.github.com/repos/" + projectName + "/commits?page=" + p + "&per_page=100&since=" + date1 + "&until=" + date2 + "&access_token=" + toks[ct++]);

            
            //System.out.println("https://api.github.com/repos/" + projectName + "/commits?");
            //System.out.println(jsonString);
            if (JSONUtilsConversion.isValidJSON(jsonString) == false) {///
                flag = 1;
                //System.out.println("Invalid json returned!");
                break;
            }
            JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
            if (jsonArray.toString().equals("[]")) {
                /// Break out of the loop, when empty array is found!
                break;
            }
            List<Long> cL = new ArrayList<>();
            List<Long> aL = new ArrayList<>();
            List<Long> dL = new ArrayList<>();

            for (Object jsonObj : jsonArray) {
                count++;

                JSONObject jsonObject = (JSONObject) jsonObj;
                String shaa = (String) jsonObject.get("sha");

                if (shaaaList.contains(shaa)) {
                    shaLists.add(shaa);/// Add to the List
                    JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                    JSONObject authorObj = (JSONObject) commitsObj.get("author");
                    ///..........................................
                    String name = (String) authorObj.get("name");
                    String email = (String) authorObj.get("email");
                    String date = (String) authorObj.get("date");
                    if (!name.equals("")) {
                        nameList.add(name);
                    } else {
                        nameList.add("name####");
                    }
                    if (!email.equals("")) {
                        emailList.add(email);
                    } else {
                        emailList.add("email####");
                    }

                    //......................................
                    if (ct == (toks.length)) {/// the the index for the tokens array...
                        ct = 0; //// go back to the first index......
                    }
                    String commitsShaURL = new Call_URL().callURL("https://api.github.com/repos/" + projectName + "/commits/" + shaa + "?access_token=" + toks[ct++]);
                    if (JSONUtilsConversion.isValidJSONObject(commitsShaURL) == false) {///

                        System.out.println(shaa + " :Invalid Sha found!");
                        break;
                    }
                    if (JSONUtilsConversion.isValidJSONObject(commitsShaURL) == true) {///

                        JSONObject allObj = (JSONObject) parser.parse(commitsShaURL);
                        ////##########################################################################
                        //if((JSONObject) allObj.get("author") != null){ ////  if statement for testing null author....
                        JSONObject logObj = (JSONObject) allObj.get("author");

                        JSONArray fileArray = (JSONArray) allObj.get("files");
                        long chh = 0, add = 0, del = 0;
                        //if(!fileArray.toString().equals("[]")){
                        /// Now we also need to get the Login Details,,the corresponding followes and following

                        List<String> comList = new ArrayList<>();
                        // String lgg = "###########";

                        // for(int a=0; a<fileArray.size(); a++){
                        // if((JSONObject)allObj.get("stats"))
                        JSONObject fileObj = (JSONObject) allObj.get("stats");
                        chh = (long) fileObj.get("total");
                        add = (long) fileObj.get("additions");
                        del = (long) fileObj.get("deletions");

                        //  }
                        // }
                        cList.add(chh);
                        aList.add(add);
                        dList.add(del);
                        //long ch1 = 0,add1 = 0, del1 = 0;
                        long ch1 = 0;
                        if (com_index == 0 && count == jsonArray.size()) {
                            //System.out.println(name+"\t"+shaa+" \t"+chh+"\t"+add+"\t"+del);
                            cList.set(cList.size() - 1, ch1);
                            aList.set(aList.size() - 1, ch1);
                            dList.set(dList.size() - 1, ch1);
                        }

                        nameSet.add(name);
                        emailSet.add(email);

                        if (logObj != null) {
                            if ((JSONObject) jsonObject.get("author") != null) {
                                JSONObject loginAuthorObj = (JSONObject) jsonObject.get("author");
                                login = (String) loginAuthorObj.get("login");
                                loginSet.add(login);
                                logList.add(login);
                            } else {
                                loginSet.add("login######");
                                logList.add("login######");
                            }

                            /// Preventing dublicates using see............
                            //**********************************************
                        }//......................................

                        if (logObj == null) {
                            loginSet.add("login######");
                            logList.add("login######");
                        }
                    }//// Check for invalid shaas stops here....

                    shaaStrings.replace(shaa, "");
                }

            }/// *** End of for loop for JSon Object.....
            p++;//// Goto the next Page.......

        }// ******* End of while loop....

        // create an iterator
        Iterator iterator = loginSet.iterator();
        Iterator name = nameSet.iterator();
        Iterator email = emailSet.iterator();  ///

        List<String> lList = new ArrayList<>(); //// We 
        ArrayList<String> nList = new ArrayList<>();
        ArrayList<String> eList = new ArrayList<>();
        ArrayList<String> locList = new ArrayList<>();

        ArrayList<String> creatList = new ArrayList<>();
        ArrayList<String> updateList = new ArrayList<>();
        ArrayList<String> locaList = new ArrayList<>();

        while (iterator.hasNext()) {
            String l = (String) iterator.next();
            //if(!l.equals("")){
            lList.add(l);
            //}

        }

        /// This is where we shall store the list of all the comits with other details
        ArrayList<Long> commList = new ArrayList<>();
        ArrayList<Long> chaList = new ArrayList<>();
        ArrayList<Long> addList = new ArrayList<>();
        ArrayList<Long> delList = new ArrayList<>();
        ArrayList<Long> pubList = new ArrayList<>();
        ArrayList<Long> gisList = new ArrayList<>();
        ArrayList<Long> folList = new ArrayList<>();
        ArrayList<Long> fowList = new ArrayList<>();

        ArrayList<String> locateList = new ArrayList<>();
        ArrayList<String> createdList = new ArrayList<>();
        ArrayList<String> updatedList = new ArrayList<>();

        for (int a = 0; a < lList.size(); a++) {
            if (lList.get(a) == null) {
                lList.remove(a);
            }
        }
        for (int a = 0; a < logList.size(); a++) {
            if (logList.get(a) == null) {
                logList.remove(a);
                shaLists.remove(a);
                nameList.remove(a);
                emailList.remove(a);
            }
        }

        ///First initials all with 0 depending on number of users...
        for (int a = 0; a < lList.size(); a++) {
            long com = 0;
            String names = "name";
            String emails = "emails";
            commList.add(com);
            chaList.add(com);
            addList.add(com);
            delList.add(com);
            pubList.add(com);
            gisList.add(com);
            folList.add(com);
            fowList.add(com);

            nList.add(names);
            eList.add(emails);
            creatList.add(names);
            updateList.add(names);
            locaList.add(names);

        }

        for (int ii = 0; ii < logList.size(); ii++) {

            String login_i = logList.get(ii);
            if (logList.get(ii) != null) {
                for (int jj = 0; jj < lList.size(); jj++) {
                    if (lList.get(jj) != null && logList.get(ii) != null) {
                        if (lList.get(jj).equals(login_i)) {
                            nList.set(jj, nameList.get(ii));
                            eList.set(jj, emailList.get(ii));
                        }
                    }
                }
            }

        }

        for (int cc = 0; cc < shaLists.size(); cc++) {

            for (int b = 0; b < lList.size(); b++) {

                if (lList != null) {
                    try {
                        if (logList.get(cc).equals(lList.get(b))) {
                            long get_com = commList.get(b) + 1;
                            long get_cha = chaList.get(b) + cList.get(cc);
                            long get_add = addList.get(b) + aList.get(cc);
                            long get_del = delList.get(b) + dList.get(cc);
                            /////
                            if (com_index == 0 && b == 0 && cc == 0) {
                                get_com = commList.get(b);
                            }
                            /// Now we have to put it back to there position
                            commList.set(b, get_com);
                            chaList.set(b, get_cha);
                            addList.set(b, get_add);
                            delList.set(b, get_del);
                        }
                    } catch (Exception e) {

                        long get_com = commList.get(b);
                        long get_cha = chaList.get(b);
                        long get_add = addList.get(b);
                        long get_del = delList.get(b);
                        /////
                        if (com_index == 0 && b == 0 && cc == 0) {
                            get_com = commList.get(b);
                        }
                        /// Now we have to put it back to there position
                        commList.set(b, get_com);
                        chaList.set(b, get_cha);
                        addList.set(b, get_add);
                        delList.set(b, get_del);
                    }

                }

            }
        }

        for (int b = 0; b < lList.size(); b++) {/// Looping thru all the Shaa ......
            //System.out.println(lList.get(b));
            long get_up = 0;
            long get_gis = 0;
            long get_fol = 0;
            long get_fow = 0;
            /**
             * if(com_index == 0 && b == 0){
             *
             * System.out.println("DELETED\t"+commList.get(b)+"\t"+chaList.get(b)+"\t"+addList.get(b)+"\t"+delList.get(b));
             * /// long ccc = 0; commList.set(0, ccc); chaList.set(0, ccc);
             * addList.set(0, ccc); delList.set(0, ccc);
             *
             * }
             **
             */

            if (!lList.get(b).equals("login######")) {
                if (ct == (toks.length)) {/// the the index for the tokens array...
                    ct = 0; //// go back to the first index......
                }
                loginURL = new Call_URL().callURL("https://api.github.com/users/" + lList.get(b) + "?access_token=" + toks[ct++]);
                loginObj = (JSONObject) parser.parse(loginURL);

                get_up = pubList.get(b) + (long) loginObj.get("public_repos");
                get_gis = gisList.get(b) + (long) loginObj.get("public_gists");
                get_fol = folList.get(b) + (long) loginObj.get("followers");
                get_fow = fowList.get(b) + (long) loginObj.get("following");
                if (loginObj.get("location") != null) {
                    location = (String) loginObj.get("location");

                } else {
                    locateList.add("location");
                }
                createdAt = (String) loginObj.get("created_at");
                updatedAt = (String) loginObj.get("updated_at");

                pubList.set(b, get_up);
                gisList.set(b, get_gis);
                folList.set(b, get_fol);
                fowList.set(b, get_fow);

                locationSet.add(location);
                createdSet.add(createdAt);
                updatedSet.add(updatedAt);

                locateList.add(location);
                createdList.add(createdAt);
                updatedList.add(updatedAt);

            } else {
                pubList.set(b, pubList.get(b) + get_up);
                gisList.set(b, gisList.get(b) + get_gis);
                folList.set(b, folList.get(b) + get_fol);
                fowList.set(b, fowList.get(b) + get_fow);

                locationSet.add("location");
                createdSet.add("created");
                updatedSet.add("updated");

                locateList.add("location");
                createdList.add("created");
                updatedList.add("updated");
            }

        }

        /// Split them here..
        Iterator locat = locationSet.iterator();
        Iterator create = createdSet.iterator();
        Iterator update = updatedSet.iterator();

        List<String> allList = new ArrayList<>();
        allList.add(date1 + " - " + date2);

        if (i == 0) {
            allList.add(String.valueOf(pl));
            allList.add(String.valueOf(pc));
            allList.add(String.valueOf(is));
            allList.add(String.valueOf(ic));
            allList.add(String.valueOf(ff));

            allList.add(projectName);
            allList.add(created_at);
        } else if (i == 1) {
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add(proj_major);
        } else {
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");
            allList.add("-");

        }
        //System.out.println(i+" : "+projectName+"\t"+pl+" : "+pc+" : "+is+" : "+ic+" : "+ff);

        if (shaLists.size() > 0) {
            //Finally add all the datas in the excel rows 
            for (int z = 0; z < lList.size(); z++) {
                //// first stored in array list ..  
                allList.add(nList.get(z) + "/" + eList.get(z) + "/" + lList.get(z) + "/" + locateList.get(z) + "/" + createdList.get(z) + "/" + updatedList.get(z) + "/"
                        + pubList.get(z) + "/" + gisList.get(z) + "/" + folList.get(z) + "/" + fowList.get(z) + "/" + commList.get(z) + "_" + chaList.get(z) + "_"
                        + addList.get(z) + "_" + delList.get(z));

            }

            /// Converting array list to the normal array of strings......
            datas = new Object[allList.size()];
            datas = allList.toArray(datas);
            /// ##########################################################

        }

        datas = new Object[allList.size()];

        datas = allList.toArray(datas);

        ArrayList< Object[]> allobj2 = new ArrayList<Object[]>();
        allobj2.add(datas);
        datas = new Object[]{shaaStrings};

        //// Can as well print something to the console.......
        // System.out.print(shaLists+"\t\t pr_O: "+pl+", Closed: "+pc+" IS_OP:"+is+", IS_CL: "+ic+", FORK: "+ff+", \t Login: "+loginSet+"\t name: "+nList+"\t email: "+eList+"\t Location: "+locList+" , Updated: "+pubList+",Gis: "+gisList+",Followers: "+folList+",Following: "+fowList+", Created_At: "+createdSet+", Updated: "+updatedSet+" \t Commits: "+commList+" ,  Changes:"+commList+", Added: "+chaList+", Deleted: "+addList);
        //System.out.println("**************************");
        //// Return the lists of object to CommitsInterval.java class to be written to excelll sheet......
        return allobj2;

    }
}
