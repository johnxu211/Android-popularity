/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Joint_DeveloperStatistics {
    
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        appDetails();
    }

    private static void appDetails() throws Exception {

        String[] token = Constants.getToken();

        DecimalFormat newFormat = new DecimalFormat("#.##");

        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;

        
        String file1 = "common_percentage_cd.xlsx";
        String file2 = "fp_com_chang_cd.xlsx";
        String file_google1 = "file_googleplay_com.xlsx";

        String[] fork_package = {file_google1};
        String[] files_change = {file1};

        //todo:
        String path_google = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/";
        String path_changes = "/Users/john/Desktop/00commits/00from_server/";
        
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/variability_index/";
        //String path_package = "";
        //String path_new = "";

        List<String> proj_list = new ArrayList<>();
        List<List<String>> nameList = new ArrayList<>();
        List<List<String>> GFDList = new ArrayList<>();
        List<List<String>> GLDList = new ArrayList<>();
        List<List<String>> GUList = new ArrayList<>();
        List<List<String>> downList = new ArrayList<>();

        List<List<Double>> star1 = new ArrayList<>();
        List<List<Double>> star2 = new ArrayList<>();
        List<List<Double>> star3 = new ArrayList<>();
        List<List<Double>> star4 = new ArrayList<>();
        List<List<Double>> star5 = new ArrayList<>();
        List<List<Double>> avgstars = new ArrayList<>();

        for (int a = 0; a < fork_package.length; a++) {
            int numbers = File_Details.getWorksheets(path_google + fork_package[a]);
            int count = 0;
            while (count < numbers) {
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path_google + fork_package[a], count, 2, 2);

                List<String> pack = ReadExcelFile_1Column.readColumnAsString(path_google + fork_package[a], count, 3, 1);
                List<String> GFD = ReadExcelFile_1Column.readColumnAsString(path_google + fork_package[a], count, 6, 1);
                List<String> GLD = ReadExcelFile_1Column.readColumnAsString(path_google + fork_package[a], count, 7, 1);
                
                String project = File_Details.setProjectName(path_google + fork_package[a], count, "B2");

                proj_list.add(project);
                nameList.add(name);
                GFDList.add(GFD);
                GLDList.add(GLD);
                
                System.out.println(count+" : "+project);

                count++;
            }
        }

        System.out.println("**************************************************************");
        for (int a = 0; a < files_change.length; a++) {
            int numbers = File_Details.getWorksheets(path_changes + files_change[a]);
            int count = 0;
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"ProjectName", "F-Com-Date-ML", "Star1", "Star2", "Star3", "Star4", "Star5", "avgstars", "DownLoad", "FP", "F-Com-Date-FP", "ComCommits-B", "MergedCom", "Unique Commits", "ComCdLOC-B", "Merged-CdLOC", "UniqueCdLOC", "Star1", "Star2", "Star3", "Star4", "Star5", "avgstars", "DownLoad"};
            while (count < numbers) {
                if (count == 0) {
                    allobj.add(datas);
                }
                List<String> nameL = ReadExcelFile_1Column.readColumnAsString(path_changes + files_change[a], count, 1, 2);
                List<Double> commitsB = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 2, 2);
                List<Double> commitsF = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 3, 2);
                List<Double> unique = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 4, 2);
                List<Double> changeB = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 5, 2);
                List<Double> changeF = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 6, 2);
                List<Double> uniqueF = ReadExcelFile_1Column.readColumnAsNumeric(path_changes + files_change[a], count, 7, 2);
                String project = File_Details.setProjectName(path_changes + files_change[a], count, "B2");

                System.out.println(count+" :::::::::"+project);
                if (proj_list.contains(project)) {
                    int index = proj_list.indexOf(project);

                    //List<String> pack = packList.get(index);
                    List<String> names = nameList.get(index);
                    List<String> GFD = GFDList.get(index);
                    List<String> GLD = GLDList.get(index);
                    List<String> GU = GUList.get(index);
                    List<String> down = downList.get(index);
                    //String pCreated = File_Details.setProjectName(path_package + fork_package[a], count, "F2");
                    List<Double> str1 = star1.get(index);
                    List<Double> str2 = star2.get(index);
                    List<Double> str3 = star3.get(index);
                    List<Double> str4 = star4.get(index);
                    List<Double> str5 = star5.get(index);
                    List<Double> avgstrs = avgstars.get(index);

                    String pFirst = GFD.get(0);
                    String pLast = GLD.get(0);
                    String pUpdate = GU.get(0);
                    String pDownloads = down.get(0);

                    double pstar1 = str1.get(0);
                    double pstar2 = str2.get(0);
                    double pstar3 = str3.get(0);
                    double pstar4 = str4.get(0);
                    double pstar5 = str5.get(0);
                    double pavgstars = avgstrs.get(0);

                    GFD.remove(0);
                    GLD.remove(0);
                    GU.remove(0);
                    down.remove(0);
                    str1.remove(0);
                    str2.remove(0);
                    str3.remove(0);
                    str4.remove(0);
                    str5.remove(0);
                    avgstrs.remove(0);

                    for (int i = 0; i < nameL.size(); i++) {
                        if (names.contains(nameL.get(i))) {
                            int index2 = names.indexOf(nameL.get(i));
                            datas = new Object[]{project, pFirst, pstar1, pstar2, pstar3, pstar4, pstar5, pavgstars, pDownloads, nameL.get(i), GFD.get(index2), commitsB.get(i), commitsF.get(i), unique.get(i), changeB.get(i), changeF.get(i), uniqueF.get(i), str1.get(index2), str2.get(index2), str3.get(index2), str4.get(index2), str5.get(index2), avgstrs.get(index2), down.get(index2)};
                            allobj.add(datas);
                        }
                    }
                }
                //TODO:::: 
                String f_name = files_change[a].replaceAll("fp_com_chang_cd", "variability_indices_final");
                Create_ExcelFile.createExcel2(allobj, 0, path_new + f_name, "variability");

                count++;
            }
        }
    }
}
