/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

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
public class Refactor_Commits {

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
        String[] tokens = Constants.getToken();
        String file1 = "repos_gp_refactoring_output_0-20.xlsx";
        String file2 = "repos_gp_refactoring_output_0001.xlsx";
        String file3 = "repos_gp_refactoring_output_80-100.xlsx";
        String file4 = "repos_gp_refactoring_output_0-20.xlsx";
        String fork2 = "repos_gp_refactoring_output_100-end.xlsx";

        String path = "";
        String path2 = "";
        String path_new = "";
        String[] FILES = {file1, file2};
        int ct = 0;
        int sheet = 0;
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            String file_name = "repos_gp_refactoring_commit_0.xlsx";
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
                    String shaa_mlp = Shaa_Details.details_with_refactoring(project, shaaList.get(i),Math.round(refactor.get(i)), tokens, ct);
                    if (!shaa_mlp.equals("")) {
                        ct = Integer.parseInt(shaa_mlp.split("/")[shaa_mlp.split("/").length - 1]);
                        detailL.add(shaa_mlp);
                        dateL.add(dateList.get(i));
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
                ct = Fetch_CommitsDetail.process(project, fork, detailL, dateL, tokens, ct, date_interval, interval, file_name, sheet_name);
                if (fork.equals("")) {
                    sheet++;
                }
                count++;
            }
        }
    }
}
