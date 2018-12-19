/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.core.Collections;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;
import com.opm.read_gitrepos.Shaa_Details;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Refactor_Commits2 {

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

        String merg1 = "repos_commits_mlp_fp_merged1_50.xlsx";
        String merg2 = "repos_commits_mlp_fp_merged50_100.xlsx";
        String merg3 = "repos_commits_mlp_fp_merged101_150.xlsx";
        String merg4 = "repos_commits_mlp_fp_merged151_241.xlsx";
        //String merg5 = "repos_gp_refactoring_output_100-end.xlsx";

        String file1 = "repos_gp_refactoring_output_0-20.xlsx";
        String file2 = "repos_gp_refactoring_output_0001.xlsx";
        String file3 = "repos_gp_refactoring_output_80-100.xlsx";
        String file4 = "repos_gp_refactoring_output_0-20.xlsx";
        String file5 = "repos_gp_refactoring_output_100-end.xlsx";

        String path = "";
        //String path2 = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00merged/";
        String path2 = "";
        String path_new = "";
        String[] FILES = {file3, file4, file5};

        String[] FILES2 = {merg2, merg3, merg4};
        List<String> pList = new ArrayList<>();
        List<String> fDateList = new ArrayList<>();
        List<String> lDateList = new ArrayList<>();
        for (int a = 0; a < FILES2.length; a++) {
            int numbers = File_Details.getWorksheets(path2 + FILES2[a]);
            int count = 0;

            while (count < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path2 + FILES2[a], count, "H2");
                List<List<String>> allLists = Collections.readCommits(path2 + FILES2[a], count);
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
                pList.add(project.split("\\|")[0]);
                fDateList.add(fDate);
                lDateList.add(lDate);
                System.out.println(count + "" + project.split("\\|")[0] + "" + daList.size() + " : " + comList.size() + "\t" + fDate + " : " + lDate);
                count++;
            }
        }
        int ct = 0;
        int sheet = 0;
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            String file_name = "repos_gp_refactoring_commit_002.xlsx";
            int count = 0;

            int f_count = 0;
            while (count < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path + FILES[a], count, "A2");
                String fork = File_Details.setProjectName(path + FILES[a], count, "B2");
                List<String> shaaList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 2, 2);
                List<String> dateList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 6, 2);
                List<Double> refactor = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], count, 3, 2);
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
                    f_count = 0;
                }
                if (!fork.equals("")) {
                    sheet_name = project.split("/")[0] + "_" + sheet + "_FP_" + f_count;
                    f_count++;
                }
                if (pList.contains(project)) {
                    int index = pList.indexOf(project);
                    ct = Fetch_CommitsDetail.process2(project, fork, detailL, dateL,fDateList.get(index), lDateList.get(index), Constants.getToken(), ct, date_interval, interval, file_name, sheet_name);
                    if (fork.equals("")) {
                        sheet++;
                    }
                }else{
                    System.out.println(project+" \tsorry Not found in the list..!");
                }

                count++;
            }
        }

    }
}
