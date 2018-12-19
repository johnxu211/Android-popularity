/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.core.Call_URL;
import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.core.JSONUtils;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class EliminateRepos_Less6Commits {
    
    public static void main(String[] args) throws Exception {
        check();
    }
    private static void check() throws Exception {
        Object[] datas = null;
        String repos = "google_play_2.xlsx";
        //String repos_created = "google_play_.xlsx";

        String path = "";
        //String path_created = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "";
        //todo:
        String[] files = {repos};
        for (int a = 0; a < files.length; a++) {

            int total_sheets = File_Details.getWorksheets(path + files[a]);
            int sheet_index = 0;
            int ct = 0;
            while (sheet_index < total_sheets) {
                ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
                ArrayList< Object[]> DataSet2 = new ArrayList<Object[]>();
                datas = new Object[]{"Repos", "Package"};
                DataSet.add(datas);
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + files[a], sheet_index, 0, 1);
                List<String> package_List = ReadExcelFile_1Column.readColumnAsString(path + files[a], sheet_index, 1, 1);
                for (int i = 0; i < nameList.size(); i++) {

                    String[] tokens = Constants.getToken();
                    int flag = 0;
                    if (ct == (tokens.length)) {/// the the index for the tokens array...
                        ct = 0; //// go back to the first index......
                    }
                    String jsonString = Call_URL.callURL("https://api.github.com/repos/" + nameList.get(i) + "/commits?until=" + Constants.cons.TODAY_DATE + "&page=1&per_page=100&access_token=" + tokens[ct++]);
                    JSONParser parser = new JSONParser();
                    if (JSONUtils.isValidJSON(jsonString) == true) {
                        JSONArray jsonArray = (JSONArray) parser.parse(jsonString);
                        if (jsonArray.size() >= 6) {
                            flag = 1;
                        }
                    }
                    System.out.println(i + " : " + nameList.get(i) + " \t " + flag);                  
                    if (flag == 1) {
                        datas = new Object[]{nameList.get(i), package_List.get(i)};
                        DataSet.add(datas);
                    } else {
                        datas = new Object[]{nameList.get(i), package_List.get(i)};
                        DataSet2.add(datas);
                    }
                }
                String output_file_name = files[a].replaceAll("google_play_2", "google_play_6commits2");
                Create_Excel.createExcel(DataSet, 0, path_new + output_file_name, "atleast_6");
                Create_Excel.createExcel(DataSet2, 0, path_new + output_file_name, "lessthan_6");
                sheet_index++;
            }
        }
    }
}
