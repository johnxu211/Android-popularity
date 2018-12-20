/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;


import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class RefactoringCommits_Statistics {
    
    public static void main(String[] args) throws Exception {
        stats();
    }
    
    private static void stats() throws Exception {
        Object[] datas = null;
        
        String file1 = "repos_gp_refactoring_output_0-20.xlsx";
        String file2 = "repos_gp_refactoring_output_0001.xlsx";
        String file3 = "repos_gp_refactoring_output_80-100.xlsx";
        String file4 = "repos_gp_refactoring_output_100-end.xlsx";
        
        String path = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";
        
        String[] FILES = {file1, file2, file3, file4};
        
        List<String> mainLine_projectList = new ArrayList<>();
        List<Double> daysList = new ArrayList<>();
        List<Double> refacList = new ArrayList<>();
        List<Double> comList = new ArrayList<>();
        List<Double> changeList = new ArrayList<>();
        
        List<String> pforkList = new ArrayList<>();
        List<String> forkList = new ArrayList<>();
        
        for (int a = 0; a < FILES.length; a++) {
            int total_sheets = File_Details.getWorksheets(path + FILES[a]);
            int sheet_index = 0;
            while (sheet_index < total_sheets) {
                String project = File_Details.setProjectName(path + FILES[a], sheet_index, "A2");
                String fork = File_Details.setProjectName(path + FILES[a], sheet_index, "B2");
                List<String> shaaList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 2, 2);
                List<String> dateList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 6, 2);
                List<Double> refactor = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 3, 2);
                List<Double> changes = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 5, 2);
                
                pforkList.add(project);
                forkList.add(fork);
                if (fork.contains("")) {
                    mainLine_projectList.add(project);
                    double ref = 0, chang = 0;
                    for (int i = 0; i < refactor.size(); i++) {
                        ref += refactor.get(i);
                        chang += changes.get(i);
                    }
                    
                    String minDate = DateOperations.sorts(dateList, dateList).split("/")[0];
                    String maxDate = DateOperations.sorts(dateList, dateList).split("/")[1];
                    daysList.add(Double.parseDouble(DateOperations.diff(minDate, maxDate).split("/")[0]));
                    refacList.add(ref);
                    comList.add(Double.parseDouble(dateList.size() + ""));
                    changeList.add(chang);
                    System.out.println(sheet_index + " : " + project);
                    
                }
                sheet_index++;
            }
            
        }
        
        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Variants", "MLP_Com", "MLP_CLOC", "MLP_Refac", "MLP_Weeks"};
        DataSet.add(datas);
        
        Set<String> pfSet = new LinkedHashSet<>();
        pfSet.addAll(pforkList);
        List<String> pforkList2 = new ArrayList<>();
        pforkList2.addAll(pfSet);
        for (int i = 0; i < pforkList2.size(); i++) {
            double var = 0;
            for (int j = 0; j < pforkList.size(); j++) {
                if (pforkList2.get(i).equals(pforkList.get(j))) {
                    var++;
                }
            }
            int index = mainLine_projectList.indexOf(pforkList2.get(i));
            datas = new Object[]{pforkList2.get(i), var, comList.get(index), changeList.get(index), refacList.get(index), daysList.get(index)};
            DataSet.add(datas);
            
        }
        
        String file_name = "repos_gp_refac_stats.xlsx";
        Create_ExcelFile.createExcel2(DataSet, 0, path_new + file_name, "statistics");
    }
}
