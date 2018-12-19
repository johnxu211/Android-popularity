/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import com.opm.read_gitrepos.Read_Commits;
import com.opm.read_gitrepos.ReadReposCommits;
import java.util.ArrayList;
import java.util.List;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Collect_ForksFirstLastCommits {

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

        //String path_repos = "/Users/john/Documents/Destope_data_2018-05_18/00commits/server/OUTPUT/";
        //String path_com = "/Users/john/Documents/Destope_data_2018-05_18/00commits/server/";
        String path_repos = "";
        String path_com = "";

        String path_new = "";
        //todo:

        String[] files = {file_shaa1};

        List<String> projList = new ArrayList<>();
        List<List<String>> namesList = new ArrayList<>();
        List<List<String>> shaaList = new ArrayList<>();
        List<List<String>> firstList = new ArrayList<>();
        List<List<String>> lastList = new ArrayList<>();
        List<List<Double>> uniqueList = new ArrayList<>();

        for (int a = 0; a < files.length; a++) {
            int total_sheets = File_Details.getWorksheets(path_com + files[a]);
            int sheet_index = 0;
            while (sheet_index < total_sheets) {
                String project = File_Details.setProjectName(path_com + files[a], sheet_index, "A2");
                List<String> names = ReadExcelFile_1Column.readColumnAsString(path_com + files[a], sheet_index, 1, 2);
                List<String> shaa = ReadExcelFile_1Column.readColumnAsString(path_com + files[a], sheet_index, 5, 2);
                List<String> first = ReadExcelFile_1Column.readColumnAsString(path_com + files[a], sheet_index, 3, 2);
                List<String> last = ReadExcelFile_1Column.readColumnAsString(path_com + files[a], sheet_index, 4, 2);
                List<Double> unque = ReadExcelFile_1Column.readColumnAsNumeric(path_com + files[a], sheet_index, 7, 2);

                projList.add(project);
                namesList.add(names);
                firstList.add(first);
                lastList.add(last);
                shaaList.add(shaa);
                uniqueList.add(unque);

                System.out.println(sheet_index + " : " + project + "\t" + names.size());
                sheet_index++;
            }

        }

        List<String> repos = ReadExcelFile_1Column.readColumnAsString(path_repos + file_repos, 1, 0, 1);
        List<String> names = ReadExcelFile_1Column.readColumnAsString(path_repos + file_repos, 1, 10, 1);
        List<Double> variants = ReadExcelFile_1Column.readColumnAsNumeric(path_repos + file_repos, 1, 1, 1);
        for (int i = 0; i < repos.size(); i++) {
            ArrayList< Object[]> DataSets = new ArrayList<Object[]>();
            datas = new Object[]{"Project", "Forks", "FirstCom", "LastCom", "FirstShaa", "LastShaa", "UniqueShaa", "Tot_Com"};// end of assigning the header to the object..
            DataSets.add(datas);

            int index = -1;
            String subRepos = repos.get(i).split("\\|")[0];
            String project = subRepos;
            if (projList.contains(subRepos)) {
                index = projList.indexOf(subRepos);
            } else {

                for (int j = 0; j < projList.size(); j++) {
                    if (subRepos.split("/")[0].equals(projList.get(j).split("/")[0])) {
                        List<String> nL = namesList.get(j);
                        if (nL.size() + 1 == variants.get(i)) {
                            project = projList.get(j);
                            index = j;
                        }
                    }
                }

            }
            List<List<String>> allList_1 = ReadReposCommits.countALLCOM(project, Constants.cons.TODAY_DATE, tokens, ct);
            List<String> shaList_1 = allList_1.get(0);
            List<String> dateList_1 = allList_1.get(1);

            //System.out.println(repos.get(i) + "\t" + shaList_1.size() + "\t" + shaList_1);
            String fShaa = "", lShaa = "", fDate = "", lDate = "";
            if (shaList_1.size() > 1) {
                fShaa = shaList_1.get(shaList_1.size() - 2);
                lShaa = shaList_1.get(0);
                fDate = dateList_1.get(shaList_1.size() - 2);
                lDate = dateList_1.get(0);
            }

            //System.out.println("    " + fDate + "\t" + lDate + "\t" + fShaa + "\t" + lShaa);
            datas = new Object[]{project, "", fDate, lDate, fShaa, lShaa, "", Double.parseDouble(shaList_1.size() + "")};// end of assigning the header to the object..
            DataSets.add(datas);

            if (index != -1) {
                List<String> namesL = namesList.get(index);
                List<String> shaaL = shaaList.get(index);
                List<String> firstL = firstList.get(index);
                List<String> lastL = lastList.get(index);
                List<Double> uniqueL = uniqueList.get(index);
                for (int j = 0; j < namesL.size(); j++) {
                    datas = new Object[]{"", namesL.get(j), firstL.get(j), lastL.get(j), "", shaaL.get(j), uniqueL.get(j)};// end of assigning the header to the object..
                    DataSets.add(datas);
                }

            } else {
                System.out.println("    " + i + "\t" + repos.get(i) + "\t Unknown repos..!");
            }

            String file_name = file_repos.replaceAll("repos_gp_statistics3", "repos_first_lastcom_final");
            Create_Excel.createExcel2(DataSets, 0, path_new + file_name, repos.get(i).split("/")[0] + "_" + i);
        }
        ///Create excel here....
    }
}
