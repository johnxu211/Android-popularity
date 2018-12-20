/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.read_gitrepos.Collections;
import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Merge_MLP_FPCommits {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        stats();
    }

    /**
     *
     * @throws Exception
     */
    private static void stats() throws Exception {
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";

        String file_commits1 = "RepoCommits_MSR01.xlsx";
        String file_commits2 = "RepoCommits_MSR02.xlsx";
        String file_commits3 = "RepoCommits_MSR03.xlsx";
        String file_commits4 = "RepoCommits_MSR04.xlsx";
        String file_commits5 = "merged_gp_commits5.xlsx";

        String file_commits_1 = "final_merged_gp_commits1.xlsx";
        String file_commits_2 = "final_merged_gp_commits2.xlsx";
        String file_commits_3 = "final_merged_gp_commits3.xlsx";
        String file_commits_4 = "final_merged_gp_commits4.xlsx";
        String file_commits_5 = "final_merged_gp_commits5.xlsx";

        String path_commits = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/";
         String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/output00/";
        //todo:
        String[] files_commits = {file_commits1,file_commits2,file_commits3,file_commits4};
        String[] FILES = {file_commits_5};

        for (int a = 0; a < files_commits.length; a++) {
            String file_name = files_commits[a].replaceAll("repos_gp_cmerged2", "repos_gp_fmerged2");

            int sheet_index = 0;
            int numbers = File_Details.getWorksheets(path_commits + files_commits[a]);
            List<String> pList = new ArrayList<>();
            List<String> sList = new ArrayList<>();
            List<Double> tList = new ArrayList<>();

            int sheet_c = 0;
            int c_sheet_index = 0;
            int bk = 0;
            while (sheet_index < numbers) {  
                  
                String sheet = File_Details.getWorksheetName(path_commits + files_commits[a], sheet_index);
                String project = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "H2");
                List<String> dateL = ReadExcelFile_1Column.readColumnAsString(path_commits + files_commits[a], sheet_index, 0, 1);
                List<List<String>> lists = Collections.readCommits_2(path_commits + files_commits[a], sheet_index);
                List<String> commitsL = lists.get(0);

                String pr_o = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "B2");
                String pr_c = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "C2");

                String is_o = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "D2");
                String is_c = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "E2");
                String f = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "F2");

                String subSheet = sheet.split("_")[0] + "_" + sheet.split("_")[1];

                ArrayList< Object[]> allDataCollection = new ArrayList<Object[]>();
                List<String> listOBJ = new ArrayList<>();
                datas = new Object[]{"Tag Date", "PR Open",
                    "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                    "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                };
                allDataCollection.add(datas);

                for (int b = 0; b < dateL.size(); b++) {
                    //System.out.println(i+"\t"+date_.size()+"\t"+commits_.size());
                    List<String> new_commits = new ArrayList<>();
                    Set<String> emailSet = new LinkedHashSet<>();
                    if (!commitsL.equals("")) {
                        String[] splits = commitsL.get(b).split(":-");
                        for (int j = 0; j < splits.length; j++) {
                            new_commits.add(splits[j]);
                            if (splits[j].contains("/")) {
                                emailSet.add(splits[j].split("/")[1]);
                            }
                        }
                    }

                    Iterator iterator = emailSet.iterator();
                    List<String> email_List = new ArrayList<>();
                    List<String> final_lists = new ArrayList<>();
                    while (iterator.hasNext()) {
                        email_List.add((String) iterator.next());
                        final_lists.add("Name/email/login/Location/Created_at/Updated_at/0/0/0/0/0_0_0_0");
                    }

                    List<String> DataCollectionList = new ArrayList<>();
                    DataCollectionList.add(dateL.get(b));

                    if (b == 0) {
                        DataCollectionList.add(pr_o);
                        DataCollectionList.add(pr_c);
                        DataCollectionList.add(is_o);
                        DataCollectionList.add(is_c);
                        DataCollectionList.add(f);
                        DataCollectionList.add("");
                        DataCollectionList.add(project);
                    } else {
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                    }

                    for (int i = 0; i < new_commits.size(); i++) {
                        for (int j = 0; j < final_lists.size(); j++) {
                            if (new_commits.get(i).split("/")[1].equals(email_List.get(j))) {
                                long up = 0;
                                long gis = 0;
                                long fol = 0;
                                long fow = 0;

                                try {
                                    up = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 5]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 5]);
                                    gis = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 4]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 4]);
                                    fol = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 3]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 3]);
                                    fow = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 2]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 2]);

                                } catch (Exception e) {
                                    try {
                                        up = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 5]) + 0;
                                        gis = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 4]) + 0;
                                        fol = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 3]) + 0;
                                        fow = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 2]) + 0;
                                    } catch (Exception ee) {
                                        // e.printStackTrace();
                                    }

                                }
                                String comm_string_j = final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 1];
                                String comm_string_i = new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 1];

                                long com = Long.parseLong(comm_string_j.split("_")[0]) + Long.parseLong(comm_string_i.split("_")[0]);
                                long ch = 0;
                                long add = 0;
                                long del = 0;

                                try {
                                    ch = Long.parseLong(comm_string_j.split("_")[1]) + Long.parseLong(comm_string_i.split("_")[1]);
                                    add = Long.parseLong(comm_string_j.split("_")[2]) + Long.parseLong(comm_string_i.split("_")[2]);
                                    del = Long.parseLong(comm_string_j.split("_")[3]) + Long.parseLong(comm_string_i.split("_")[3]);

                                } catch (Exception e) {
                                    System.out.println(comm_string_j + "\t" + final_lists.get(j));
                                    ch = Long.parseLong(comm_string_j.split("_")[1]) + 0;
                                    add = Long.parseLong(comm_string_j.split("_")[2]) + 0;
                                    del = Long.parseLong(comm_string_j.split("_")[3]) + 0;

                                }
                                String login = "login######";
                                if (!new_commits.get(i).split("/")[2].equals("")) {
                                    login = new_commits.get(i).split("/")[2];
                                }

                                final_lists.set(j, new_commits.get(i).split("/")[0] + "/" + new_commits.get(i).split("/")[1] + "/" + login + "/" + new_commits.get(i).split("/")[3] + "/" + new_commits.get(i).split("/")[4] + "/" + new_commits.get(i).split("/")[5] + "/" + new_commits.get(i).split("/")[6] + "/" + up + "/" + gis + "/" + fol + "/" + fow + "/" + com + "_" + ch + "_" + add + "_" + del);
                            }
                        }
                    }

                    for (int k = 0; k < final_lists.size(); k++) {
                        if (!listOBJ.contains(final_lists.get(k))) {
                            DataCollectionList.add(final_lists.get(k));
                            listOBJ.add(final_lists.get(k));
                        }

                        //System.out.println(k+" : "+final_lists.get(k));
                    }

                    datas = new Object[DataCollectionList.size()];
                    datas = DataCollectionList.toArray(datas);
                    allDataCollection.add(datas);
                }

                sheet_c++;

                Create_ExcelFile.createExcel(allDataCollection, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_c);
                if (sheet_index == bk) {
                    break;
                }
                sheet_index++;
            }
            merge2(FILES[a], path_commits, path_new, file_name, sheet_c);
        }
    }
    private static void merge2(String files_commits, String path_commits, String path_new, String file_name, int sheet_c) throws Exception {
        Object[] datas = null;
        //String file_stats = "Repos.xlsx"; 
       // for (int a = 0; a < files_commits.length; a++) {
            int sheet_index = 0;
            int numbers = File_Details.getWorksheets(path_commits + files_commits);
            List<String> pList = new ArrayList<>();
            List<String> sList = new ArrayList<>();
            List<Double> tList = new ArrayList<>();
            int c_sheet_index = 0;
            while (sheet_index < numbers) {
               
                String sheet = File_Details.getWorksheetName(path_commits + files_commits, sheet_index);
                String project = File_Details.setProjectName(path_commits + files_commits, sheet_index, "H2");
                List<String> dateL = ReadExcelFile_1Column.readColumnAsString(path_commits + files_commits, sheet_index, 0, 1);
                List<List<String>> lists = Collections.readCommits_2(path_commits + files_commits, sheet_index);
                List<String> commitsL = lists.get(0);

                String pr_o = File_Details.setProjectName(path_commits + files_commits, sheet_index, "B2");
                String pr_c = File_Details.setProjectName(path_commits + files_commits, sheet_index, "C2");

                String is_o = File_Details.setProjectName(path_commits + files_commits, sheet_index, "D2");
                String is_c = File_Details.setProjectName(path_commits + files_commits, sheet_index, "E2");
                String f = File_Details.setProjectName(path_commits + files_commits, sheet_index, "F2");

                //String subSheet = sheet.split("_")[0] + "_" + sheet.split("_")[1];

                ArrayList< Object[]> allDataCollection = new ArrayList<Object[]>();
                List<String> listOBJ = new ArrayList<>();
                datas = new Object[]{"Tag Date", "PR Open",
                    "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                    "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                };
                allDataCollection.add(datas);

                //if (flag == 0) {
                for (int b = 0; b < dateL.size(); b++) {
                    //System.out.println(i+"\t"+date_.size()+"\t"+commits_.size());
                    List<String> new_commits = new ArrayList<>();
                    Set<String> emailSet = new LinkedHashSet<>();
                    if (!commitsL.equals("")) {
                        String[] splits = commitsL.get(b).split(":-");
                        for (int j = 0; j < splits.length; j++) {
                            new_commits.add(splits[j]);
                            if (splits[j].contains("/")) {
                                emailSet.add(splits[j].split("/")[1]);
                            }
                        }
                    }

                    Iterator iterator = emailSet.iterator();
                    List<String> email_List = new ArrayList<>();
                    List<String> final_lists = new ArrayList<>();
                    while (iterator.hasNext()) {
                        email_List.add((String) iterator.next());
                        final_lists.add("Name/email/login/Location/Created_at/Updated_at/0/0/0/0/0_0_0_0");
                    }

                    List<String> DataCollectionList = new ArrayList<>();
                    DataCollectionList.add(dateL.get(b));

                    if (b == 0) {
                        DataCollectionList.add(pr_o);
                        DataCollectionList.add(pr_c);
                        DataCollectionList.add(is_o);
                        DataCollectionList.add(is_c);
                        DataCollectionList.add(f);
                        DataCollectionList.add("");
                        DataCollectionList.add(project);
                    } else {
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                        DataCollectionList.add("-");
                    }

                    for (int i = 0; i < new_commits.size(); i++) {
                        for (int j = 0; j < final_lists.size(); j++) {
                            if (new_commits.get(i).split("/")[1].equals(email_List.get(j))) {
                                long up = 0;
                                long gis = 0;
                                long fol = 0;
                                long fow = 0;

                                try {
                                    up = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 5]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 5]);
                                    gis = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 4]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 4]);
                                    fol = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 3]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 3]);
                                    fow = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 2]) + Long.parseLong(new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 2]);

                                } catch (Exception e) {
                                    try {
                                        up = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 5]) + 0;
                                        gis = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 4]) + 0;
                                        fol = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 3]) + 0;
                                        fow = Long.parseLong(final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 2]) + 0;
                                    } catch (Exception ee) {
                                        // e.printStackTrace();
                                    }

                                }
                                String comm_string_j = final_lists.get(j).split("/")[final_lists.get(j).split("/").length - 1];
                                String comm_string_i = new_commits.get(i).split("/")[new_commits.get(i).split("/").length - 1];

                                long com = Long.parseLong(comm_string_j.split("_")[0]) + Long.parseLong(comm_string_i.split("_")[0]);
                                long ch = 0;
                                long add = 0;
                                long del = 0;

                                try {
                                    ch = Long.parseLong(comm_string_j.split("_")[1]) + Long.parseLong(comm_string_i.split("_")[1]);
                                    add = Long.parseLong(comm_string_j.split("_")[2]) + Long.parseLong(comm_string_i.split("_")[2]);
                                    del = Long.parseLong(comm_string_j.split("_")[3]) + Long.parseLong(comm_string_i.split("_")[3]);

                                } catch (Exception e) {
                                    System.out.println(comm_string_j + "\t" + final_lists.get(j));
                                    ch = Long.parseLong(comm_string_j.split("_")[1]) + 0;
                                    add = Long.parseLong(comm_string_j.split("_")[2]) + 0;
                                    del = Long.parseLong(comm_string_j.split("_")[3]) + 0;

                                }
                                String login = "login######";
                                if (!new_commits.get(i).split("/")[2].equals("")) {
                                    login = new_commits.get(i).split("/")[2];
                                }

                                final_lists.set(j, new_commits.get(i).split("/")[0] + "/" + new_commits.get(i).split("/")[1] + "/" + login + "/" + new_commits.get(i).split("/")[3] + "/" + new_commits.get(i).split("/")[4] + "/" + new_commits.get(i).split("/")[5] + "/" + new_commits.get(i).split("/")[6] + "/" + up + "/" + gis + "/" + fol + "/" + fow + "/" + com + "_" + ch + "_" + add + "_" + del);
                            }
                        }
                    }

                    for (int k = 0; k < final_lists.size(); k++) {
                        if (!listOBJ.contains(final_lists.get(k))) {
                            DataCollectionList.add(final_lists.get(k));
                            listOBJ.add(final_lists.get(k));
                        }

                        //System.out.println(k+" : "+final_lists.get(k));
                    }

                    datas = new Object[DataCollectionList.size()];
                    datas = DataCollectionList.toArray(datas);
                    allDataCollection.add(datas);
                }
                sheet_c++;
                Create_ExcelFile.createExcel(allDataCollection, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_c);
                sheet_index++;
            }

        //}
    }
}
