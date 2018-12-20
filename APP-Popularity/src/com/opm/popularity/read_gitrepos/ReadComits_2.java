/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.JSONUtilsConversion;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class ReadComits_2 {

    ArrayList< Object[]> allobj = new ArrayList<Object[]>();
    XSSFRow rows;
    int rowid = 0;
    XSSFWorkbook workbook2;
    XSSFSheet[] sheet;
    String projectName, sheetName;

    /**
     *
     * @param projectName
     * @throws ParseException
     * @throws Exception
     */
    public ArrayList<Object[]> readCommitsNow(String projectName, String created_at, String[] toks, String sheetName, int number, XSSFSheet[] sheet, String toDAY, String file, String path, String proj_mastor,String shaaString) throws ParseException, Exception {
        int ct = 0;

        String jsonString = null;
        ////Excell Header goes here....
        Object[] datas = null;
        this.projectName = projectName;
        this.sheetName = sheetName;
        this.sheet = sheet;
        /// Writing the Headers of the excell documents..
        datas = new Object[]{"Tag Date", "Months", "PR Open",
            "PR Closed", "Stars", "Forks", "Project",
            "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"

        };// end of assigning the header to the object..
        /// putting the header in to the arraylist..
        allobj.add(datas);
        ///#########################################################################
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter

        int oop = 0;
        int count = 0;
        int flag = 0;
        List<String> check_List = new ArrayList<>();
        while (true) {////loop thru the pagess....
            if (ct == (toks.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            jsonString = Call_URL.callURL("https://api.github.com/repos/" + projectName + "/commits?page=" + p + "&per_page=100&since=" + created_at + "&until=" + toDAY + "&access_token=" + toks[ct++]);
            String sha = null;
            JSONParser parser = new JSONParser();
            // System.out.print("Data received!");

            if (JSONUtilsConversion.isValidJSON(jsonString) == false) {///
                flag = 1;
                System.out.println("Invalid json returned!");
                break;
            }
            if (jsonString.equals("[]")) {
                //flag = 1;
                break;
            }

            if (JSONUtilsConversion.isValidJSON(jsonString) == true) {///
                check_List.add("added");

                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                if (jsonArray.toString().equals("[]")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                for (Object jsonObj : jsonArray) {
                    count++;

                    /**
                     * Please remove this code, it was only used for testing...*
                     */
                    //if(count == 200){
                    //  break;
                    // }
                    /**
                     * Remove Up to here *
                     */
                    JSONObject jsonObject = (JSONObject) jsonObj;
                    String shaa = (String) jsonObject.get("sha");
                    JSONObject commitsObj = (JSONObject) jsonObject.get("commit");
                    JSONObject authorObj = (JSONObject) commitsObj.get("author");
                    ///..........................................
                    String name = (String) authorObj.get("name");
                    String email = (String) authorObj.get("email");
                    String date = (String) authorObj.get("date");

                    datas = new Object[]{date, "", "", "",
                        "", "", name + "/" + email + "/" + "" + "/" + "" + "/" + "" + "/" + "" + "/" + "" + "/" + "" + "/" + "" + "/0_" + "" + "_" + "" + "_" + ""
                    };
                    /// Now add the datas to the array list....
                    allobj.add(datas);// putting the object in to list...
                    //System.out.println(count+"\tShaa: "+shaa+", Date: "+date);

                    //// We can print breaking line now . for the next commits..
                    //System.out.println();
                }/// *** End of JSon Object.....
            }

            p++;//// Goto the next Page.......

        } /// ******** End of while loop ......
        if (check_List.size() > 0) {
            /// Send the ArrayObject containing all data to the next class for further manipulation....
            ReadCommitsByDateInterval cInterval = new ReadCommitsByDateInterval();
            ArrayList< Object[]> allobj2 = ReadCommits_BYInterval.useTagDatesInterval(projectName, created_at, allobj, toks, ct, Constants.cons.TODAY_DATE, proj_mastor, shaaString);
            return allobj2;
        } else {
            return null;
        }

    }
}
