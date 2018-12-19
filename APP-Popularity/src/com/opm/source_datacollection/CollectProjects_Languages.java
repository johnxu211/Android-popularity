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
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class CollectProjects_Languages {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        collect();
    }

    private static void collect() throws Exception {
        Object[] datas = null;
        int date_interval = 14;
        double interval = 14.0;
        String[] tokens = Constants.getToken();
        String fork2 = "repos_first_lastcom_final.xlsx";

        //String path2 = "/Users/john/Documents/Destope_data_2018-05_18/00commits/server/OUTPUT/";
        //String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/";
        String path2 = "";
        String path_new = "";

        String[] FILE_ARRAY = {fork2};
        //List<String> pList = new ArrayList<>();

        for (int a = 0; a < FILE_ARRAY.length; a++) {
            int total_sheets = File_Details.getWorksheets(path2 + FILE_ARRAY[a]);
            int sheet_index = 0;
            int ct = 0;
            ArrayList< Object[]> DataSets = new ArrayList<Object[]>();
            datas = new Object[]{"Project", "Language"};
            DataSets.add(datas);

            while (sheet_index < total_sheets) {
                String project = File_Details.setProjectName(path2 + FILE_ARRAY[a], sheet_index, "A2");
                System.out.println(sheet_index + "  :   " + project);

                if (ct == tokens.length) {
                    ct = 0;
                }
                String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "?access_token=" + tokens[ct++]);

                JSONParser parser = new JSONParser();
                if (JSONUtils.isValidJSONObject(jsonString) == true) {
                    JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
                    String language = (String) jSONObject.get("language");
                    datas = new Object[]{project, language};
                    DataSets.add(datas);

                }

                //pList.add(project);
                String file_name = FILE_ARRAY[a].replaceAll("repos_first_lastcom_final", "repos_total2");
                Create_Excel.createExcel2(DataSets, 0, path_new + file_name, "repos_");

                sheet_index++;
            }
        }

    }
}
