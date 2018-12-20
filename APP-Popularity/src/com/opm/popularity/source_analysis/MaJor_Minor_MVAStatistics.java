/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class MaJor_Minor_MVAStatistics {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        String file1 = "repos_gp_refactoring_percentage.xlsx";

        String path_percent = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00Refactoring/merged/";

        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00NewDatasets/00Refactoring/merged/percentage/";
        String[] files_percent = {file1};

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project","Tot_Com","Tot_Refac","MVA_com","MVA_Refac", "MJ", "MN", "Total"};
        allobj.add(datas);
        for (int a = 0; a < files_percent.length; a++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path_percent + files_percent[a]);
            System.out.println("Reading Percentage Excel file....!");
            while (count < numbers) {
                List<String> comList = ReadExcelFile_1Column.readColumnAsString(path_percent + files_percent[a], count, 8, 2);
                String project = File_Details.setProjectName(path_percent + files_percent[a], count, "A2");
                List<Double> commits = ReadExcelFile_1Column.readColumnAsNumeric(path_percent + files_percent[a], count, 9, 2);
                List<Double> changes_ = ReadExcelFile_1Column.readColumnAsNumeric(path_percent + files_percent[a], count, 10, 2);
                List<Double> percentage_ = ReadExcelFile_1Column.readColumnAsNumeric(path_percent + files_percent[a], count, 11, 2);
                List<Double> percentage2_ = ReadExcelFile_1Column.readColumnAsNumeric(path_percent + files_percent[a], count, 12, 2);
                
                List<String> catList = ReadExcelFile_1Column.readColumnAsString(path_percent + files_percent[a], count, 15, 2);
                List<String> logList = ReadExcelFile_1Column.readColumnAsString(path_percent + files_percent[a], count, 3, 2);

                System.out.println("  " + count + " : " + project);

                double mj = 0, mn = 0, mva1 = 0, mva2 = 0, com = 0, cloc = 0;
                if (percentage_.size() > 0) {
                    mva1 = percentage_.get(0);
                    mva2 = percentage2_.get(0);
                }
                for (int i = 0; i < catList.size(); i++) {
                    if (catList.get(i).equals("Major")) {
                        mj++;
                    } else {
                        mn++;
                    }
                    com += commits.get(i);
                    cloc += changes_.get(i);
                }
                datas = new Object[]{project,com,cloc,mva1,mva2, mj, mn, mj + mn};
                allobj.add(datas);

                String f_name = file1.replaceAll("repos_gp_refactoring_percentage", "repos_gp_refactoring_ml_mp_stats");
                //Create_Excel.createExcel(allobj, 0, path_new + f_name, File_Details.getWorksheetName(path + files[aa], count));
                Create_ExcelFile.createExcel2(allobj, 0, path_new + f_name, "stats");
                count++;
            }
        }

    }
}
