/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.read_gitrepos.Collections;
import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class RefactorCommits_Statistics {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        String merg1 = "repos_commits_mlp_fp_merged1_50.xlsx";
        String merg2 = "repos_commits_mlp_fp_merged50_100.xlsx";
        String merg3 = "repos_commits_mlp_fp_merged101_150.xlsx";
        String merg4 = "repos_commits_mlp_fp_merged151_241.xlsx";

        String rmerg1 = "repos_gp_refactoring_commit_001.xlsx";
        String rmerg2 = "repos_gp_refactoring_commit_002.xlsx";

        //String file4 = "repos_commits_mlp_fp_merged151_241.xlsx";
        String rmerged = "repos_gp_refactoring_merged.xlsx";

        String rfile = "repos_gp_refactoring_mj_mn.xlsx";
        String cfile = "repos_commits_mlp_fp_mj_mn.xlsx";

        String path_file = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/";
        String path_cfile = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";

        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";

        String[] FILES3 = {rmerged};

        List<String> p1List = new ArrayList<>();
        List<Double> m1List = new ArrayList<>();

        for (int a = 0; a < FILES3.length; a++) {
            int numbers = File_Details.getWorksheets(path_cfile + FILES3[a]);
            int count = 0;

            while (count < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path_cfile + FILES3[a], count, "H2");
                List<String> daList = ReadExcelFile_1Column.readColumnAsString(path_cfile + FILES3[a], count, 0, 2);;
                List<String> comList = ReadExcelFile_1Column.readColumnAsString(path_cfile + FILES3[a], count, 8, 2);;

                for (int i = comList.size() - 1; i >= 0; i--) {
                    if (comList.get(i).equals("-")) {
                        comList.remove(i);
                    } else {
                        break;
                    }
                }
                int f_index = 0;
                for (int i = 0; i < comList.size(); i++) {
                    if (!comList.get(i).equals("-")) {
                        f_index = i;
                        break;
                    }
                }

                String fDate = "", lDate = "";
                double days = 0;
                if (comList.size() > 0) {
                    fDate = daList.get(f_index).split(" - ")[0];
                    lDate = daList.get(comList.size() - 1).split(" - ")[1];
                    String dateDiff = DateOperations.diff(fDate, lDate);
                    days = Double.parseDouble(dateDiff.split("/")[0]);
                }

                double months = days / 30;
                p1List.add(project.split("\\|")[0]);
                m1List.add(months);
                System.out.println(count + " \t " + project.split("\\|")[0] + "" + daList.size() + " : " + comList.size() + "\t" + fDate + " : " + lDate);
                count++;
            }
        }

        String[] FILES2 = {merg1, merg2, merg3, merg4};

        List<String> pList = new ArrayList<>();
        List<Double> monthList = new ArrayList<>();

        for (int a = 0; a < FILES2.length; a++) {
            int numbers = File_Details.getWorksheets(path_file + FILES2[a]);
            int count = 0;

            while (count < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path_file + FILES2[a], count, "H2");
                List<List<String>> allLists = Collections.readCommits(path_file + FILES2[a], count);
                List<String> daList = allLists.get(1);
                List<String> comList = allLists.get(0);

                for (int i = comList.size() - 1; i >= 0; i--) {
                    if (comList.get(i).equals("-")) {
                        comList.remove(i);
                    } else {
                        break;
                    }
                }
                String fDate = daList.get(0).split(" - ")[0];
                String lDate = daList.get(comList.size() - 1).split(" - ")[1];

                String dateDiff = DateOperations.diff(fDate, lDate);
                double days = Double.parseDouble(dateDiff.split("/")[0]);
                double months = days / 30;
                pList.add(project.split("\\|")[0]);
                monthList.add(months);
                System.out.println(count + " \t " + project.split("\\|")[0] + "" + daList.size() + " : " + comList.size() + "\t" + fDate + " : " + lDate);
                count++;
            }
        }
        List<String> proj1List = ReadExcelFile_1Column.readColumnAsString(path_cfile + rfile, 0, 0, 1);
        List<Double> commits1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 1, 1);
        List<Double> refactors = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 2, 1);
        List<Double> MVA_com1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 3, 1);
        List<Double> MVA_Refac = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 4, 1);
        List<Double> MJ1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 5, 1);
        List<Double> MN1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + rfile, 0, 6, 1);

        List<String> proj2List = ReadExcelFile_1Column.readColumnAsString(path_cfile + cfile, 0, 0, 1);
        List<Double> commits2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 1, 1);
        List<Double> changes = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 2, 1);
        List<Double> MVA_com2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 3, 1);
        List<Double> MVA_Chang = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 4, 1);
        List<Double> MJ2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 5, 1);
        List<Double> MN2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + cfile, 0, 6, 1);

        System.out.println(proj2List.size() + " : " + commits2.size() + " : " + changes.size() + " : " + MVA_com2.size() + " : " + MVA_Chang.size() + " : " + MJ2.size() + ": " + MJ2.size());
        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Variants", "Tot_Com", "Tot_CLOC", "MVA_com", "MVA_CLOC", "MJ", "MN", "Total", "", "Tot_Com", "Tot_Refac", "MVA_com", "MVA_Refac", "MJ", "MN", "Total", "Months_com", "Months_refac"};
        allobj.add(datas);
        for (int i = 0; i < proj2List.size(); i++) {
            double com = 0;
            double ref = 0;
            double mva1 = 0;
            double mva2 = 0;
            double mj = 0;
            double mn = 0;
            double month_refac = 0;
            if (proj1List.contains(proj2List.get(i).split("\\|")[0])) {
                int index = proj1List.indexOf(proj2List.get(i).split("\\|")[0]);
                com = commits1.get(index);
                ref = refactors.get(index);
                mva1 = MVA_com1.get(index);
                mva2 = MVA_Refac.get(index);
                mj = MJ1.get(index);
                mn = MN1.get(index);
            }

            if (p1List.contains(proj2List.get(i).split("\\|")[0])) {
                int index = proj1List.indexOf(proj2List.get(i).split("\\|")[0]);
                month_refac = m1List.get(index);
            }
            if (pList.contains(proj2List.get(i).split("\\|")[0])) {
                int index2 = pList.indexOf(proj2List.get(i).split("\\|")[0]);
                datas = new Object[]{proj2List.get(i).split("\\|")[0],
                    Double.parseDouble(proj2List.get(i).split("\\|").length + ""),
                    commits2.get(i),
                    changes.get(i),
                    MVA_com2.get(i),
                    MVA_Chang.get(i),
                    MJ2.get(i),
                    MN2.get(i),
                    MJ2.get(i) + MN2.get(i),
                    "", com, ref, mva1, mva2, mj, mn, mj + mn,
                    monthList.get(index2),
                    month_refac
                };
                allobj.add(datas);
            }
        }
        String file_name = "repos_gp_refac_com_statistics2.xlsx";
        Create_ExcelFile.createExcel2(allobj, 0, path_new + file_name, "statistics");
    }
}
