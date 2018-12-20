/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.core.JSONUtilsConversion;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class CollectionMLP_Language {

    public static void main(String[] args) throws Exception {
        indentify();
    }

    /**
     *
     * @throws Exception
     */
    private static void indentify() throws Exception {
        ////String toDay = "2017-07-06T00:00:00Z";
        Object[] datas = null;
        String fork1 = "repos_merged_dev_major_developers_minor_developers.xlsx";
       
        String path = "";
        String path_new = "";
        String[] FILES = {fork1};
        
        int ct = 0;
        for (int a = 0; a < FILES.length; a++) {
            int total_sheets = File_Details.getWorksheets(path + FILES[a]);
            int sheet_index = 0;
            int s = 0;

            while (sheet_index < total_sheets) {
                ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
                datas = new Object[]{"MLP", "FP", "Lang","Com","Uniq","Chang","Perc","Cat", "MJ","MN","Tot"};// end of assigning the header to the object..
                DataSet.add(datas);

                String project = File_Details.setProjectName(path + FILES[a], sheet_index, "A2");
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 1, 2);
                List<Double> commits_ = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 2, 1);
                List<Double> unique_ = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 3, 1);

                List<String> changeL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 4, 1);
                List<String> percentL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 5, 1);
                List<String> catL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 6, 1);
                List<Double> major_developers = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 7, 1);
                List<Double> minor_developers = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 8, 1);
                
               
                
                
                if (ct == Constants.getToken().length) {
                    ct = 0;
                }
                String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "?access_token=" + Constants.getToken()[ct++]);
                int flag = 0;
                JSONParser parser = new JSONParser();
                if (JSONUtilsConversion.isValidJSONObject(jsonString) == true) {
                    JSONObject jSONObject = (JSONObject) parser.parse(jsonString);

                    String language = (String) jSONObject.get("language");
                    if (language.equals("Java")) {
                        datas = new Object[]{project, "", language,commits_.get(0),unique_.get(0),changeL.get(0),percentL.get(0),catL.get(0),major_developers.get(0),minor_developers.get(0),major_developers.get(0)+minor_developers.get(0)};// end of assigning the header to the object..
                        DataSet.add(datas);
                        flag++;

                        s++;
                    }

                }
                if (flag > 0) {
                    for (int i = 0; i < name.size(); i++) {

                        if (ct == Constants.getToken().length) {
                            ct = 0;
                        }
                        jsonString = Call_URL.callURL("https://api.github.com/repos/" + name.get(i) + "?access_token=" + Constants.getToken()[ct++]);

                        if (JSONUtilsConversion.isValidJSONObject(jsonString) == true) {
                            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
                            String language = (String) jSONObject.get("language");
                            datas = new Object[]{"", name.get(i), language,commits_.get(i+1),unique_.get(i+1),changeL.get(i+1),percentL.get(i+1),catL.get(i+1),major_developers.get(i+1),minor_developers.get(i+1),major_developers.get(i+1)+minor_developers.get(i+1)};// end of assigning the header to the object..
                            DataSet.add(datas);
                        }

                    }
                }
                String sheet = File_Details.getWorksheetName(path + FILES[a], sheet_index);

                if (flag > 0) {
                    String f_name = FILES[a].replaceAll("repos_merged_dev_major_developers_minor_developers", "repos_language_dev_major_developers_minor_developers");
                    Create_ExcelFile.createExcel2(DataSet, 0, path_new + f_name, project.split("/")[0] + "_" + s);
                }

                sheet_index++;
            }
        }
    }
}
