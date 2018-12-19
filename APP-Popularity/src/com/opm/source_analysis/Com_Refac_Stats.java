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
public class Com_Refac_Stats {
    
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
        
        List<String> pList = new ArrayList<>();
        List<Double> daysList = new ArrayList<>();
        List<Double> refacList = new ArrayList<>();
        List<Double> comList = new ArrayList<>();
        List<Double> changeList = new ArrayList<>();
        
        List<String> pfList = new ArrayList<>();
        List<String> fList = new ArrayList<>();
        
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            int count = 0;
            while (count < numbers) {
                String project = File_Details.setProjectName(path + FILES[a], count, "A2");
                String fork = File_Details.setProjectName(path + FILES[a], count, "B2");
                List<String> shaaList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 2, 2);
                List<String> dateList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 6, 2);
                List<Double> refactor = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 3, 2);
                List<Double> changes = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 5, 2);
                
                pfList.add(project);
                fList.add(fork);
                if (fork.contains("")) {
                    pList.add(project);
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
                    System.out.println(count + " : " + project);
                    
                }
                count++;
            }
            
        }
        
        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Variants", "MLP_Com", "MLP_CLOC", "MLP_Refac", "MLP_Weeks"};
        allobj.add(datas);
        
        Set<String> pfSet = new LinkedHashSet<>();
        pfSet.addAll(pfList);
        List<String> pfList2 = new ArrayList<>();
        pfList2.addAll(pfSet);
        for (int i = 0; i < pfList2.size(); i++) {
            double var = 0;
            for (int j = 0; j < pfList.size(); j++) {
                if (pfList2.get(i).equals(pfList.get(j))) {
                    var++;
                }
            }
            int index = pList.indexOf(pfList2.get(i));
            datas = new Object[]{pfList2.get(i), var, comList.get(index), changeList.get(index), refacList.get(index), daysList.get(index)};
            allobj.add(datas);
            
        }
        
        String file_name = "repos_gp_refac_stats.xlsx";
        Create_Excel.createExcel2(allobj, 0, path_new + file_name, "statistics");
    }
}
