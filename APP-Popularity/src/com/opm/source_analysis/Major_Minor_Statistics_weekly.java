/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.excel_.Create_Excel;
import com.opm.core.DateOperations;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Major_Minor_Statistics_weekly {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        String file1 = "RepoPercentage_MSR.xlsx";
        String merge1 = "RepoMerged_MSR.xlsx";

        String path_percent = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00//";

        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";
        String[] files_percent = {file1};
        String[] files = {merge1};

        List<String> pList = new ArrayList<>();
        List<Double> weekL = new ArrayList<>();
        for (int a = 0; a < files.length; a++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path_percent + files[a]);
            System.out.println("Reading Merged Excel file....!");
            while (count < numbers) {
                String project = File_Details.setProjectName(path_percent + files[a], count, "H2");
                List<String> dateList = ReadExcelFile_1Column.readColumnAsString(path_percent + files[a], count, 0, 2);
                List<String> commitList = ReadExcelFile_1Column.readColumnAsString(path_percent + files[a], count, 8, 2);

                System.out.println(count+" : "+project);
                for (int i = commitList.size() - 1; i >= 0; i--) {
                    if (commitList.get(i).equals("-")) {
                        commitList.remove(i);
                    } else {
                        break;
                    }
                }
                Set<String> comSet = new LinkedHashSet<>();
                for (int i = 0; i < commitList.size(); i++) {
                    comSet.add(dateList.get(i));
                }
                List<String> dList = new ArrayList<>();
                dList.addAll(comSet);
                String fDate = "", lDate = "";
                double diff = 0;
                if (dList.size() > 0) {
                    fDate = dList.get(0).split(" - ")[0];
                    lDate = dList.get(dList.size() - 1).split(" - ")[1];
                    diff = Double.parseDouble(DateOperations.diff(fDate, lDate).split("/")[0]);
                }
                pList.add(project);
                weekL.add(diff);
                count++;
            }
        }
        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Weeks", "Tot_Com", "Tot_CLOC", "MVA_com", "MVA_CLOC", "MJ", "MN", "Total"};
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
                if (pList.contains(project)) {
                    int index = pList.indexOf(project);
                    datas = new Object[]{project, weekL.get(index) / 7, com, cloc, mva1, mva2, mj, mn, mj + mn};
                    allobj.add(datas);

                    String f_name = file1.replaceAll("RepoPercentage_MSR", "RepoMJ_MN_MSR");
                    //Create_Excel.createExcel(allobj, 0, path_new + f_name, File_Details.getWorksheetName(path + files[aa], count));
                    Create_Excel.createExcel2(allobj, 0, path_new + f_name, "stats");
                }

                count++;
            }
        }

    }
}
