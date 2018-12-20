/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_datacollection;

import com.opm.popularity.read_gitrepos.Collections;
import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
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
public class Merge_MainForks_CommitsDetails {

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

        String file_commits1 = "repos_gp_refactoring_commit_001.xlsx";
        String file_commits2 = "repos_gp_refactoring_commit_002.xlsx";
        String file_commits3 = "repos_commit_lg_mainlinep_forkproject101_150.xlsx";
        String file_commits4 = "repos_commit_lg_mainlinep_forkproject151_241.xlsx";
        String file_commits5 = "repos_gp_commits5.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        //String path_commits = "/Users/john/Desktop/Dev_Commits/00New_Repos/00Combined/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        //String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/00Combined/merged_commits/";

        String path_commits = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00refactors/";

        //todo:
        String[] files_commits = {file_commits1};

        for (int a = 0; a < files_commits.length; a++) {
            
            int numbers = File_Details.getWorksheets(path_commits + files_commits[a]);
            //System.out.println("Reading Collection Excel....!");
            List< List<String>> dateList = new ArrayList<>();
            List< List<String>> commitList = new ArrayList<>();
            List<String> sheetL = new ArrayList<>();

            int sheet_index = 0;
            int s_c = 0;
            int sheet_c = 0;
            double pr_open = 0, pr_closed = 0, is_open = 0, is_closed = 0, forks = 0;
            String projNames = "";
            String mainlinep = "";
            String mainlinep2 = "";
            while (sheet_index < numbers) {
                String sheet = File_Details.getWorksheetName(path_commits + files_commits[a], sheet_index);
                String project = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "G2");
                String mainline = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "G2");
                String forkproject = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "H2");
                
                List<String> date = ReadExcelFile_1Column.readColumnAsString(path_commits + files_commits[a], sheet_index, 0, 1);
                List<List<String>> lists = Collections.readCommits_2(path_commits + files_commits[a], sheet_index);
                List<String> commits = lists.get(0);

                String pr_o = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "B2");
                String pr_c = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "C2");

                String is_o = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "D2");
                String is_c = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "E2");
                String f = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "F2");

                
                if (sheet.split("_").length > 2) {
                    project = File_Details.setProjectName(path_commits + files_commits[a], sheet_index, "H2");
                    //projNames = projNames.concat(project);
                }
                String subSheet = sheet.split("_")[0] + "_" + sheet.split("_")[1];
                System.out.println(sheet_index + " : " + s_c + " \t" + project + "\t" + date.size() + "\t" + commits.size());
                if (dateList.size() > 0) {
                    if (sheetL.contains(subSheet)) {
                        dateList.add(date);
                        commitList.add(commits);
                        sheetL.add(sheet);
                        try {
                            pr_open += Double.parseDouble(pr_o);
                            pr_closed += Double.parseDouble(pr_c);
                            is_open += Double.parseDouble(is_o);
                            is_closed += Double.parseDouble(is_c);
                            forks += Double.parseDouble(f);
                        } catch (Exception e) {
                            pr_open += 0;
                            pr_closed += 0;
                            is_open += 0;
                            is_closed += 0;
                            forks += 0;
                        }

                        projNames = projNames.concat(project + "|");

                        //TODO::: LAST SHEET>>>>
                        if (sheet_index == numbers - 1) {

                            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                            Set<Object[]> allOBJSet = new LinkedHashSet<Object[]>();
                            List<String> listOBJ = new ArrayList<>();
                            datas = new Object[]{"Tag Date", "PR Open",
                                "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                                "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                            };
                            allOBJSet.add(datas);

                            List<String> DeveloperList1 = new ArrayList<>();
                            List<String> DeveloperList2 = new ArrayList<>();
                            List<String> commits1 = new ArrayList<>();

                            for (int i = 0; i < dateList.size(); i++) {
                                List<String> date_ = dateList.get(i);
                                List<String> commits_ = commitList.get(i);
                                //System.out.println(i+"\t"+date_.size()+"\t"+commits_.size());
                                for (int j = 0; j < date_.size(); j++) {
                                    DeveloperList1.add(date_.get(j).split(" - ")[0]);
                                    DeveloperList2.add(date_.get(j).split(" - ")[1]);
                                    commits1.add(commits_.get(j));
                                }
                            }
                            String min_date = DateOperations.sorts(DeveloperList1, DeveloperList2).split("/")[0];
                            String max_date = DateOperations.sorts(DeveloperList1, DeveloperList2).split("/")[1];
                            int diff = Integer.parseInt(DateOperations.diff(min_date, max_date).split("/")[0]);
                            int new_diff = (int) Math.ceil((diff + 14) / 14);
                            int next = 0;
                            while (next < new_diff) {
                                String next_date = DateOperations.addDates(min_date, 14);
                                List<String> new_commits = new ArrayList<>();
                                Set<String> emailSet = new LinkedHashSet<>();
                                for (int i = 0; i < DeveloperList1.size(); i++) {
                                    if (Integer.parseInt(DateOperations.diff(min_date, DeveloperList1.get(i)).split("/")[0]) >= 0
                                            && Integer.parseInt(DateOperations.diff(DeveloperList1.get(i), next_date).split("/")[0]) >= 0) {

                                        if (!commits1.get(i).equals("-")) {
                                            String[] splits = commits1.get(i).split(":-");
                                            for (int j = 0; j < splits.length; j++) {
                                                new_commits.add(splits[j]);
                                                emailSet.add(splits[j].split("/")[1]);
                                            }
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
                                objList.add(min_date + " - " + next_date);

                                if (next == 0) {
                                    objList.add("" + pr_open);
                                    objList.add("" + pr_closed);
                                    objList.add("" + is_open);
                                    objList.add("" + is_closed);
                                    objList.add("" + forks);
                                    objList.add(projNames.split("\\|")[0]);
                                    objList.add(projNames);
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
                                allOBJSet.add(datas);
                                min_date = DateOperations.addMinutes(next_date, 1);
                                projNames = project+"|";

                                next++;
                            }
                            String sheetN = "";
                            String sheetNN = "";
                            if (sheetL.size() > 0) {
                                sheetN = sheetL.get(0);
                                sheetNN = sheetN;
                                if (sheetN.contains("_")) {
                                    sheetNN = sheetN.substring(0, sheetN.lastIndexOf("_"));
                                }
                                sheet_c++;
                            }
                            Iterator iterate_OBJ = allOBJSet.iterator();
                            while (iterate_OBJ.hasNext()) {
                                allobj.add((Object[]) iterate_OBJ.next());
                            }
                            String file_name = files_commits[a].replaceAll("repos_gp_commits", "final_merged_gp_commits_");
                            Create_ExcelFile.createExcel(allobj, 0, path_new + file_name, sheetN);

                            /// DOTO::::::::: ###################################
                            dateList = new ArrayList<>();
                            commitList = new ArrayList<>();
                            sheetL = new ArrayList<>();
                            dateList.add(date);
                            commitList.add(commits);
                            sheetL.add(subSheet);

                            try {
                                pr_closed = Double.parseDouble(pr_c);
                                is_open = Double.parseDouble(is_o);
                                is_closed = Double.parseDouble(is_c);
                                forks = Double.parseDouble(f);
                            } catch (Exception e) {
                                pr_open = 0;
                            }

                        }

                        /// END>>>>>>>>
                        s_c++;
                    }  if (!sheetL.contains(subSheet)) {

                        mainlinep2 = project;
                        s_c = 0;
                        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                        Set<Object[]> allOBJSet = new LinkedHashSet<Object[]>();
                        List<String> listOBJ = new ArrayList<>();
                        datas = new Object[]{"Tag Date", "PR Open",
                            "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                            "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                        };
                        allOBJSet.add(datas);

                        List<String> DeveloperList1 = new ArrayList<>();
                        List<String> DeveloperList2 = new ArrayList<>();
                        List<String> commits1 = new ArrayList<>();

                        for (int i = 0; i < dateList.size(); i++) {
                            List<String> date_ = dateList.get(i);
                            List<String> commits_ = commitList.get(i);
                            //System.out.println(i+"\t"+date_.size()+"\t"+commits_.size());
                            for (int j = 0; j < date_.size(); j++) {
                                DeveloperList1.add(date_.get(j).split(" - ")[0]);
                                DeveloperList2.add(date_.get(j).split(" - ")[1]);
                                commits1.add(commits_.get(j));
                            }
                        }
                        String min_date = DateOperations.sorts(DeveloperList1, DeveloperList2).split("/")[0];
                        String max_date = DateOperations.sorts(DeveloperList1, DeveloperList2).split("/")[1];
                        int diff = Integer.parseInt(DateOperations.diff(min_date, max_date).split("/")[0]);
                        int new_diff = (int) Math.ceil((diff + 14) / 14);
                        int next = 0;
                        while (next < new_diff) {
                            String next_date = DateOperations.addDates(min_date, 14);
                            List<String> new_commits = new ArrayList<>();
                            Set<String> emailSet = new LinkedHashSet<>();
                            for (int i = 0; i < DeveloperList1.size(); i++) {
                                if (Integer.parseInt(DateOperations.diff(min_date, DeveloperList1.get(i)).split("/")[0]) >= 0
                                        && Integer.parseInt(DateOperations.diff(DeveloperList1.get(i), next_date).split("/")[0]) >= 0) {

                                    if (!commits1.get(i).equals("-")) {
                                        String[] splits = commits1.get(i).split(":-");
                                        for (int j = 0; j < splits.length; j++) {
                                            new_commits.add(splits[j]);
                                            emailSet.add(splits[j].split("/")[1]);
                                        }
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
                            objList.add(min_date + " - " + next_date);

                            if (next == 0) {
                                objList.add("" + pr_open);
                                objList.add("" + pr_closed);
                                objList.add("" + is_open);
                                objList.add("" + is_closed);
                                objList.add("" + forks);
                                objList.add(projNames.split("\\|")[0]);
                                objList.add(projNames);
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
                            allOBJSet.add(datas);
                            min_date = DateOperations.addMinutes(next_date, 1);
                            projNames = project+"|";

                            next++;
                        }
                        String sheetN = "";
                        String sheetNN = "";
                        if (sheetL.size() > 0) {
                            sheetN = sheetL.get(0);
                            sheetNN = sheetN;
                            if (sheetN.contains("_")) {
                                sheetNN = sheetN.substring(0, sheetN.lastIndexOf("_"));
                            }
                            sheet_c++;
                        }
                        Iterator iterate_OBJ = allOBJSet.iterator();
                        while (iterate_OBJ.hasNext()) {
                            allobj.add((Object[]) iterate_OBJ.next());
                        }
                        String file_name = files_commits[a].replaceAll("repos_gp_refactoring_commit_", "repos_gp_refactoring_merged1_");
                        Create_ExcelFile.createExcel2(allobj, 0, path_new + file_name, sheetNN + "_" + sheet_c);
                        /// DOTO::::::::: ###################################
                        dateList = new ArrayList<>();
                        commitList = new ArrayList<>();
                        sheetL = new ArrayList<>();
                        dateList.add(date);
                        commitList.add(commits);
                        sheetL.add(subSheet);

                        try {
                            pr_closed = Double.parseDouble(pr_c);
                            is_open = Double.parseDouble(is_o);
                            is_closed = Double.parseDouble(is_c);
                            forks = Double.parseDouble(f);
                        } catch (Exception e) {
                            pr_open = 0;
                        }

                    }
                } else {
                    dateList.add(date);
                    commitList.add(commits);
                    sheetL.add(subSheet);

                    try {
                        pr_open += Double.parseDouble(pr_o);
                        pr_closed += Double.parseDouble(pr_c);
                        is_open += Double.parseDouble(is_o);
                        is_closed += Double.parseDouble(is_c);
                        forks += Double.parseDouble(f);
                    } catch (Exception e) {
                        pr_open += 0;
                        pr_closed += 0;
                        is_open += 0;
                        is_closed += 0;
                        forks += 0;
                    }

                    projNames = projNames.concat(project + "|");

                    s_c++;
                    
                    
                }

                sheet_index++;
            }
        }
    }
}
