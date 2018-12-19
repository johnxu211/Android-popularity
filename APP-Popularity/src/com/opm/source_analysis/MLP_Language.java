/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.core.Call_URL;
import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.core.JSONUtils;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class MLP_Language {

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
        String[] tokens = Constants.getToken();
        String fork1 = "repos_merged_dev_mj_mn.xlsx";
       
        String path = "";
        String path_new = "";
        String[] FILES = {fork1};
        
        int ct = 0;
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            int count = 0;
            int s = 0;

            while (count < numbers) {
                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"MLP", "FP", "Lang","Com","Uniq","Chang","Perc","Cat", "MJ","MN","Tot"};// end of assigning the header to the object..
                allobj.add(datas);

                String project = File_Details.setProjectName(path + FILES[a], count, "A2");
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 1, 2);
                List<Double> commits_ = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 2, 1);
                List<Double> unique_ = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 3, 1);

                List<String> changeL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 4, 1);
                List<String> percentL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 5, 1);
                List<String> catL = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 6, 1);
                List<Double> mj = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 7, 1);
                List<Double> mn = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 8, 1);
                
               
                
                
                if (ct == tokens.length) {
                    ct = 0;
                }
                String jsonString = Call_URL.callURL("https://api.github.com/repos/" + project + "?access_token=" + tokens[ct++]);
                int flag = 0;
                JSONParser parser = new JSONParser();
                if (JSONUtils.isValidJSONObject(jsonString) == true) {
                    JSONObject jSONObject = (JSONObject) parser.parse(jsonString);

                    String language = (String) jSONObject.get("language");
                    if (language.equals("Java")) {
                        datas = new Object[]{project, "", language,commits_.get(0),unique_.get(0),changeL.get(0),percentL.get(0),catL.get(0),mj.get(0),mn.get(0),mj.get(0)+mn.get(0)};// end of assigning the header to the object..
                        allobj.add(datas);
                        flag++;

                        s++;
                    }

                }
                if (flag > 0) {
                    for (int i = 0; i < name.size(); i++) {

                        if (ct == tokens.length) {
                            ct = 0;
                        }
                        jsonString = Call_URL.callURL("https://api.github.com/repos/" + name.get(i) + "?access_token=" + tokens[ct++]);

                        if (JSONUtils.isValidJSONObject(jsonString) == true) {
                            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
                            String language = (String) jSONObject.get("language");
                            datas = new Object[]{"", name.get(i), language,commits_.get(i+1),unique_.get(i+1),changeL.get(i+1),percentL.get(i+1),catL.get(i+1),mj.get(i+1),mn.get(i+1),mj.get(i+1)+mn.get(i+1)};// end of assigning the header to the object..
                            allobj.add(datas);
                        }

                    }
                }
                String sheet = File_Details.getWorksheetName(path + FILES[a], count);

                if (flag > 0) {
                    String f_name = FILES[a].replaceAll("repos_merged_dev_mj_mn", "repos_language_dev_mj_mn");
                    Create_Excel.createExcel2(allobj, 0, path_new + f_name, project.split("/")[0] + "_" + s);
                }

                count++;
            }
        }
    }
}
