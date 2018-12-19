/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.core.Collections;
import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author john
 */
public class Delete_Repos_NoLogin {

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

        String file_commits1 = "repos_gp_final_merged1.xlsx";
        String file_commits2 = "repos_gp_final_merged2.xlsx";
        String file_commits3 = "repos_gp_final_merged3.xlsx";
        String file_commits4 = "repos_gp_final_merged4.xlsx";
        String file_commits5 = "repos_gp_final_merged5.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        //String path_commits = "/Users/john/Desktop/Dev_Commits/00New_Repos/00Combined/";
        //String path_commits = "/Users/john/Desktop/00commits/merged_files/";
        //String path_new = "/Users/john/Desktop/00commits/merged_files/cleaned_repos/";

        //String path_commits = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/";
        //String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/00cleaned/";
        //todo:
        String path_commits = "";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "";

        String[] files_commits = {file_commits1, file_commits2, file_commits3, file_commits3, file_commits4, file_commits5};

        for (int a = 0; a < files_commits.length; a++) {
            int count = 0;
            int numbers = File_Details.getWorksheets(path_commits + files_commits[a]);
            List<String> pList = new ArrayList<>();
            List<String> sList = new ArrayList<>();
            List<Double> tList = new ArrayList<>();

            int sheet_c = 0;
            while (count < numbers) {
                String sheet = File_Details.getWorksheetName(path_commits + files_commits[a], count);
                String project = File_Details.setProjectName(path_commits + files_commits[a], count, "H2");
                List<String> dateL = ReadExcelFile_1Column.readColumnAsString(path_commits + files_commits[a], count, 0, 1);
                List<List<String>> lists = Collections.readCommits_2(path_commits + files_commits[a], count);
                List<String> commitsL = lists.get(0);

                String pr_o = File_Details.setProjectName(path_commits + files_commits[a], count, "B2");
                String pr_c = File_Details.setProjectName(path_commits + files_commits[a], count, "C2");

                String is_o = File_Details.setProjectName(path_commits + files_commits[a], count, "D2");
                String is_c = File_Details.setProjectName(path_commits + files_commits[a], count, "E2");
                String f = File_Details.setProjectName(path_commits + files_commits[a], count, "F2");

                String subSheet = sheet.split("_")[0] + "_" + sheet.split("_")[1];

                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                List<String> listOBJ = new ArrayList<>();
                datas = new Object[]{"Tag Date", "PR Open",
                    "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                    "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                };
                allobj.add(datas);
                double flag = 0;
                for (int i = 0; i < commitsL.size(); i++) {

                    String[] splits = commitsL.get(i).split(":-");
                    for (int j = 0; j < splits.length; j++) {
                        //System.out.println("         " + i + "  : " + splits[j]);
                        if (splits[j].contains("/")) {
                            if (splits[j].split("/")[2].equals("login######")) {
                                flag++;
                            }
                        }

                    }
                }
                System.out.println(count + " : " + flag);
                //System.out.println(count + " : " + count + " \t" + project + "\t" + " \t flag: " + flag);

                if (flag == 0) {
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

                        List<String> objList = new ArrayList<>();
                        objList.add(dateL.get(b));

                        if (b == 0) {
                            objList.add(pr_o);
                            objList.add(pr_c);
                            objList.add(is_o);
                            objList.add(is_c);
                            objList.add(f);
                            objList.add("");
                            objList.add(project);
                        } else {
                            objList.add("-");
                            objList.add("-");
                            objList.add("-");
                            objList.add("-");
                            objList.add("-");
                            objList.add("-");
                            objList.add("-");
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
                                objList.add(final_lists.get(k));
                                listOBJ.add(final_lists.get(k));
                            }

                            //System.out.println(k+" : "+final_lists.get(k));
                        }

                        datas = new Object[objList.size()];
                        datas = objList.toArray(datas);
                        allobj.add(datas);
                    }

                    sheet_c++;

                    String file_name = files_commits[a].replaceAll("repos_gp_final_merged", "repos_gp_final_cleaned_merged");
                    Create_Excel.createExcel(allobj, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_c);

                } else {
                    pList.add(project);
                    sList.add(sheet);
                    tList.add(flag);

                }
                count++;
            }
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"Project", "Sheet", "No.login"};
            allobj.add(datas);
            for (int i = 0; i < pList.size(); i++) {
                datas = new Object[]{pList.get(i), sList.get(i), tList.get(i)};
                allobj.add(datas);
            }

            String file_name = files_commits[a].replaceAll("repos_gp_final_merged", "repos_gp_final_cleaned_merged");
            Create_Excel.createExcel(allobj, 0, path_new + file_name, "delete_summery");

        }
    }
}
