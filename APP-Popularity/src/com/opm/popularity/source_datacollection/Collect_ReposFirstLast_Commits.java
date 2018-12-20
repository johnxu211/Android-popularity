/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_datacollection;


import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Collect_ReposFirstLast_Commits {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;
        String file_repos = "repos_gp_statistics3.xlsx";
        String file_shaa1 = "repos_gp_final_3.xlsx";
        //String file_com2 = "Commits_Cleared_500-1000.xlsx";

        String path_repos = "/Users/john/Documents/Destope_data_2018-05_18/00commits/server/OUTPUT/";
        String path_com = "/Users/john/Documents/Destope_data_2018-05_18/00commits/server/";
        //String path_repos = "";
        //String path_com = "";

        String path_new = "";
        //todo:

        String[] files = {file_shaa1};

        List<String> projList = new ArrayList<>();
        List<List<String>> namesList = new ArrayList<>();

        //for (int a = 0; a < files.length; a++) {
        int total_sheets = File_Details.getWorksheets(path_com + files[0]);
        int sheet_total = total_sheets - 1;
        List<String> projectL = ReadExcelFile_1Column.readColumnAsString(path_com + files[0], sheet_total, 0, 1);
        List<String> namesL = ReadExcelFile_1Column.readColumnAsString(path_com + files[0], sheet_total, 1, 1);

        List<String> repos = ReadExcelFile_1Column.readColumnAsString(path_repos + file_repos, 1, 0, 1);
        List<String> names = ReadExcelFile_1Column.readColumnAsString(path_repos + file_repos, 1, 10, 1);
        
        for (int i = 0; i < repos.size(); i++) {
            if (projectL.contains(repos.get(i).split("\\|")[0])) {
                System.out.println(i+" : "+repos.get(i).split("\\|")[0]+"\t Found..!"); 
            }else{
                System.out.println("       "+i+" : "+repos.get(i).split("\\|")[0]+"\t Not Found..!"); 
            }
        }
        
        ///Create excel here....
    }
}
