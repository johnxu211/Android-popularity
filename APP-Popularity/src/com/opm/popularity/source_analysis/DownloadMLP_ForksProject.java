/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import java.util.ArrayList;

/**
 *
 * @author john
 */
public class DownloadMLP_ForksProject {

    public static void main(String[] args) throws Exception {
        check();
    }

    private static void check() throws Exception {
        Object[] datas = null;
        String file1 = "repos_gp_final_cleaned_merged1.xlsx";
        String file2 = "repos_gp_final_cleaned_merged2.xlsx";
        String file3 = "repos_gp_final_cleaned_merged3.xlsx";
        String file4 = "repos_gp_final_cleaned_merged4.xlsx";
        String file5 = "repos_gp_final_cleaned_merged5.xlsx";

        String path = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/00cleaned/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/00cleaned/";
        //todo:
        String[] files = {file1, file2, file3, file4, file5};
        ArrayList< Object[]> DatSet = new ArrayList<Object[]>();
        datas = new Object[]{"MLV Reponame", "Forks","Variants"};
        for (int a = 0; a < files.length; a++) {

            if (a == 0) {
                DatSet.add(datas);
            }
            int totalsheet = File_Details.getWorksheets(path + files[a]);
            int sheet_index = 0;

            System.out.println(a + " File: " + files[a] + "\t" + totalsheet);
            while (sheet_index < totalsheet-1) {
                String project = File_Details.setProjectName(path + files[a], sheet_index, "H2");

                String mlp = project.split("\\|")[0];
                String fp = project.replaceAll(mlp + "\\|", "");
                datas = new Object[]{mlp, fp, Double.parseDouble(project.split("\\|").length+"")};
                DatSet.add(datas);
                System.out.println("       " + sheet_index + " : " + project);

                String f_name = file1.replaceAll("repos_gp_final_cleaned_merged1", "repos_forks");
                //Create_Excel.createExcel(DatSet, 0, path_new + f_name, File_Details.getWorksheetName(path + files[aa], sheet_index));
                Create_ExcelFile.createExcel2(DatSet, 0, path_new + f_name, "collections");

                sheet_index++;
            }
        }
    }
}
