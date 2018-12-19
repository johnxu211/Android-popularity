/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.core.MathsFunctions;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Stats_UniqueMerged {
    public static void main(String[] args) throws Exception {
        stats();
    }
    private static void stats() throws Exception {
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";
        String file_google0 = "file_googleplay_com-shaa.xlsx";
        String file_google1 = "repos_gp_combshaa1_500.xlsx";
        String file_google2 = "repos_gp_combshaa11.xlsx";
        String file_google3 = "repos_gp_combshaa2.xlsx";
        String file_google4 = "repos_gp_combshaa3.xlsx";
        String file_google5 = "repos_gp_combshaa1_500.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        String path_google = "/Users/john/Desktop/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/00Combined/00counts/";
        //todo:
        String[] files_google = {file_google0};

        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "Commits", "Mean", "Median", "", "Unique", "Mean", "Median", "", "Merged", "Mean", "Median"};

        for (int a = 0; a < files_google.length; a++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path_google + files_google[a]);
            //System.out.println("Reading Collection Excel....!");
            while (count < numbers) {
                if (count == 0) {
                    allobj.add(datas);
                }
                String project = File_Details.setProjectName(path_google + files_google[a], count, "B2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], count, 2, 2);
                List<Double> commits = ReadExcelFile_1Column.readColumnAsNumeric(path_google + files_google[a], count, 4, 2);
                List<Double> unique = ReadExcelFile_1Column.readColumnAsNumeric(path_google + files_google[a], count, 5, 2);
                List<Double> merged = new ArrayList<>();
                double tot_c = 0, tot_u = 0, tot_m = 0;
                for (int i = 0; i < nameList.size(); i++) {
                    merged.add(commits.get(i) - unique.get(i));
                    tot_c += commits.get(i);
                    tot_u += unique.get(i);
                    tot_m += (commits.get(i) - unique.get(i));
                }
                double mean_c = MathsFunctions.getMean(commits);
                double mean_u = MathsFunctions.getMean(unique);
                double mean_m = MathsFunctions.getMean(merged);
                double median_c = MathsFunctions.getMedian(commits);
                double median_u = MathsFunctions.getMedian(unique);
                double median_m = MathsFunctions.getMedian(merged);
                System.out.println(count + " : " + project);
                datas = new Object[]{project, tot_c, mean_c, median_c, "", tot_u, mean_u, median_u, "", tot_m, mean_m, median_m};
                allobj.add(datas);
                String file_name = files_google[a].replaceAll("file_googleplay_com-shaa", "repos_gp_mergedstats_0");
                Create_Excel.createExcel2(allobj, 0, path_new + file_name, "statistics");
                count++;
            }
        }

    }
}
