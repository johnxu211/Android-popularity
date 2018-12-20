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
import com.opm.popularity.read_gitrepos.ReadReposCommits;
import java.util.ArrayList;
import java.util.List;
import com.opm.popularity.read_gitrepos.Read_ReposCreation_Date;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Combine_Com_Refactor_Stats {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        int ct = 0;
        String sfile = "repos_gp_refac_com_statistics2.xlsx";
        String file = "repos_gp_refac_stats.xlsx";
        String file2 = "repos_gp_refac_com_statistics.xlsx";
        String filess = "repos_first_lastcom_final.xlsx";
        //String path_file = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/";
        //String path_cfile = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";

        //String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";
        String path_cfile = "";
        String path_new = "";

        String[] FILES3 = {filess};

        List<String> projectList = ReadExcelFile_1Column.readColumnAsString(path_cfile + file2, 1, 0, 1);
        List<String> projList = new ArrayList<>();
        List<Double> pComList = new ArrayList<>();
        //List<Double> pChangList = new ArrayList<>();
        //List<Double> pDaysList = new ArrayList<>();

        for (int i = 0; i < projectList.size(); i++) {
            projList.add(projectList.get(i).split("\\|")[0]);
        }

        List<Double> m1List = new ArrayList<>();
        List<String> p1List = new ArrayList<>();
        for (int a = 0; a < FILES3.length; a++) {
            int numbers = File_Details.getWorksheets(path_cfile + FILES3[a]);
            int sheet_index = 0;
            while (sheet_index < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path_cfile + FILES3[a], sheet_index, "A2");
                String first = File_Details.setProjectName(path_cfile + FILES3[a], sheet_index, "C2");
                String last = File_Details.setProjectName(path_cfile + FILES3[a], sheet_index, "D2");
                List<Double> com = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 6, 1);
                String dateDiff = DateOperations.diff(first, last);
                System.out.println(sheet_index + " \t " + project.split("\\|")[0]);
                if (projList.contains(project)) {
                    double days = Double.parseDouble(dateDiff.split("/")[0]);

                    double months = days / 30;
                    m1List.add(months);

                    String created_file = Read_ReposCreation_Date.creation(project, Constants.getToken(), ct);
                    String created_at = created_file.split("/")[0];
                    ct = Integer.parseInt(created_file.split("/")[1]);
                    List<List<String>> allList_1 = ReadReposCommits.count(project, "mlp", created_at, Constants.cons.TODAY_DATE, Constants.getToken(), ct);
                    List<String> shaList_1 = allList_1.get(0);
                    List<String> dateList_1 = allList_1.get(1);
                    List<String> messageList_1 = allList_1.get(2);
                    List<String> modelList_1 = allList_1.get(allList_1.size() - 1);
                    ct = Integer.parseInt(modelList_1.get(modelList_1.size() - 1));

                    pComList.add(Double.parseDouble(shaList_1.size() + ""));

                    p1List.add(project.split("\\|")[0]);
                }

                sheet_index++;
            }
        }

        List<String> proj2List = ReadExcelFile_1Column.readColumnAsString(path_cfile + sfile, 0, 0, 1);
        List<Double> variants2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 1, 1);
        List<Double> commits2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 2, 1);
        List<Double> changes = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 3, 1);
        List<Double> MVA_com2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 4, 1);
        List<Double> MVA_Chang = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 5, 1);
        List<Double> MJ2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 6, 1);
        List<Double> MN2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 7, 1);

        List<Double> commits1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 10, 1);
        List<Double> refactors = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 11, 1);
        List<Double> MVA_com1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 12, 1);
        List<Double> MVA_Refac = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 13, 1);
        List<Double> MJ1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 14, 1);
        List<Double> MN1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 15, 1);

        List<Double> Month1 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 17, 1);
        List<Double> Month2 = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + sfile, 0, 18, 1);

        List<String> pList = ReadExcelFile_1Column.readColumnAsString(path_cfile + file, 0, 0, 1);
        List<Double> variants = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + file, 0, 1, 1);
        List<Double> mlp_com = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + file, 0, 2, 1);
        List<Double> mlp_cloc = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + file, 0, 3, 1);
        List<Double> mlp_ref = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + file, 0, 4, 1);
        List<Double> mlp_weeks = ReadExcelFile_1Column.readColumnAsNumeric(path_cfile + file, 0, 5, 1);

        System.out.println(proj2List.size() + " : " + commits2.size() + " : " + changes.size() + " : " + MVA_com2.size() + " : " + MVA_Chang.size() + " : " + MJ2.size() + ": " + MJ2.size());
        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Variants", "Tot_Months", "MLP_Months", "Tot_Com", "Tot_CLOC", "MVA_com", "MVA_CLOC", "MJ", "MN", "Total", "MLP_Com", "", "Tot_Com", "Tot_Refac", "MVA_com", "MVA_Refac", "MJ", "MN", "Total", "Months_refac", "MLP_Ref_Month", "MLP_Ref_Com", "MLP_Refac", "Ref_Variants"};
        DataSet.add(datas);
        for (int i = 0; i < projList.size(); i++) {
            double com1 = 0, ref1 = 0, var1 = 0, m1 = 0, com2 = 0, chang2 = 0, mva_com2 = 0, mva_chag2 = 0, mj2 = 0, mn2 = 0, mva_com1 = 0, mva_ref1 = 0, mj1 = 0, mn1 = 0, m2 = 0;
            if (proj2List.contains(projList.get(i))) {
                int index = proj2List.indexOf(projList.get(i));
                com1 = commits1.get(index);
                ref1 = refactors.get(index);
                mva_com1 = MVA_com1.get(index);
                mva_ref1 = MVA_Refac.get(index);
                mj1 = MJ1.get(index);
                mn1 = MN1.get(index);
                m2 = Month2.get(index);
                var1 = variants2.get(index);
                m1 = Month1.get(index);
                com2 = commits2.get(index);
                chang2 = changes.get(index);
                mva_com2 = MVA_com2.get(index);
                mva_chag2 = MVA_Chang.get(index);
                mj2 = MJ2.get(index);
                mn2 = MN2.get(index);
            }
            double ml_ref = 0, ml_weeks = 0, ml_com = 0, var = 0, ml_cloc = 0;
            if (pList.contains(projList.get(i))) {
                int index2 = pList.indexOf(projList.get(i));
                ml_ref = mlp_ref.get(index2);
                ml_weeks = mlp_weeks.get(index2);
                ml_com = mlp_com.get(index2);
                ml_cloc = mlp_cloc.get(index2);
                var = variants.get(index2);
            }
            if (p1List.contains(projList.get(i))) {
                int index3 = p1List.indexOf(projList.get(i));
                datas = new Object[]{projList.get(i), var1, m1, m1List.get(index3), com2, chang2, mva_com2, mva_chag2, mj2, mn2, mj2 + mn2, pComList.get(index3),
                    "", com1, ref1, mva_com1, mva_ref1, mj1, mn1, mj1 + mn1, m2, ml_weeks / 30, ml_com, ml_ref, var};
                DataSet.add(datas);
            }

        }
        String file_name = "repos_gp_refac_com_comnbined_statistics3.xlsx";
        Create_ExcelFile.createExcel2(DataSet, 0, path_new + file_name, "statistics");
    }
}
