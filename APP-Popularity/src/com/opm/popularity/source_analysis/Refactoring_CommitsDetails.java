/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.read_gitrepos.Fetch_CommitsDetail;
import com.opm.popularity.read_gitrepos.Collections;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import com.opm.popularity.read_gitrepos.Shaa_Details;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Refactoring_CommitsDetails {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        collect();
    }

    public static void collect() throws Exception {
        Object[] datas = null;
        int date_interval = 14;
        int interval = 14;

        String merged_file1 = "repos_commits_mlp_fp_merged_fileed1_50.xlsx";
        String merged_file2 = "repos_commits_mlp_fp_merged_fileed50_100.xlsx";
        String merged_file3 = "repos_commits_mlp_fp_merged_fileed101_150.xlsx";
        String merged_file4 = "repos_commits_mlp_fp_merged_fileed151_241.xlsx";
        //String merged_file5 = "repos_gp_refactoring_output_100-end.xlsx";

        String file1 = "repos_gp_refactoring_output_0-20.xlsx";
        String file2 = "repos_gp_refactoring_output_0001.xlsx";
        String file3 = "repos_gp_refactoring_output_80-100.xlsx";
        String file4 = "repos_gp_refactoring_output_0-20.xlsx";
        String file5 = "repos_gp_refactoring_output_100-end.xlsx";

        String path = "";
        //String path2 = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00merged_fileed/";
        String path2 = "";
        String path_new = "";
        String[] FILES = {file3, file4, file5};

        String[] FILES2 = {merged_file2, merged_file3, merged_file4};
        List<String> pList = new ArrayList<>();
        List<String> firstCommitDateList = new ArrayList<>();
        List<String> lastCommitDateList = new ArrayList<>();
        for (int a = 0; a < FILES2.length; a++) {
            int total_index = File_Details.getWorksheets(path2 + FILES2[a]);
            int sheet_index = 0;

            while (sheet_index < total_index) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path2 + FILES2[a], sheet_index, "H2");
                List<List<String>> allLists = Collections.readCommits(path2 + FILES2[a], sheet_index);
                List<String> daList = allLists.get(1);
                List<String> comList = allLists.get(0);

                for (int i = comList.size() - 1; i >= 0; i--) {
                    if (comList.get(i).equals("-")) {
                        comList.remove(i);
                    } else {
                        break;
                    }
                }
                String firstCommitDate = daList.get(0).split(" - ")[0];
                String lastCommitDate = daList.get(comList.size() - 1).split(" - ")[1];
                pList.add(project.split("\\|")[0]);
                firstCommitDateList.add(firstCommitDate);
                lastCommitDateList.add(lastCommitDate);
                System.out.println(sheet_index + "" + project.split("\\|")[0] + "" + daList.size() + " : " + comList.size() + "\t" + firstCommitDate + " : " + lastCommitDate);
                sheet_index++;
            }
        }
        int ct = 0;
        int sheet = 0;
        for (int a = 0; a < FILES.length; a++) {
            int total_index = File_Details.getWorksheets(path + FILES[a]);
            String file_name = "repos_gp_refactoring_commit_002.xlsx";
            int sheet_index = 0;

            int f_sheet_index = 0;
            while (sheet_index < total_index) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path + FILES[a], sheet_index, "A2");
                String fork = File_Details.setProjectName(path + FILES[a], sheet_index, "B2");
                List<String> shaaList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 2, 2);
                List<String> dateList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 6, 2);
                List<Double> refactor = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 3, 2);
                List<String> detailL = new ArrayList<>();
                List<String> dateL = new ArrayList<>();
                List<Long> refac = new ArrayList<>();

                for (int i = 0; i < shaaList.size(); i++) {
                    String shaa_mlp = Shaa_Details.details_with_refactoring(project, shaaList.get(i), Math.round(refactor.get(i)), Constants.getToken(), ct);
                    if (!shaa_mlp.equals("")) {
                        ct = Integer.parseInt(shaa_mlp.split("/")[shaa_mlp.split("/").length - 1]);
                        detailL.add(shaa_mlp);
                        dateL.add(shaa_mlp.split("/")[2]);
                    }
                }

                String sheet_name = project.split("/")[0] + "_" + sheet;
                if (fork.equals("")) {
                    sheet++;
                    f_sheet_index = 0;
                }
                if (!fork.equals("")) {
                    sheet_name = project.split("/")[0] + "_" + sheet + "_FP_" + f_sheet_index;
                    f_sheet_index++;
                }
                if (pList.contains(project)) {
                    int index = pList.indexOf(project);
                    ct = Fetch_CommitsDetail.process2(project, fork, detailL, dateL,firstCommitDateList.get(index), lastCommitDateList.get(index), Constants.getToken(), ct, date_interval, interval, file_name, sheet_name);
                    if (fork.equals("")) {
                        sheet++;
                    }
                }else{
                    System.out.println(project+" \tsorry Not found in the list..!");
                }

                sheet_index++;
            }
        }

    }
}
