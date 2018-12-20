/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.core.Coversions;
import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.core.MathsFunctions;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Merge_Developers_BYLogin {
    
    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        DecimalFormat newFormat = new DecimalFormat("#.##");
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";
        String file_google0 = "repos_fdetails_dev_mj_mn2.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        String path_google = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/00commits/00cleaned/";
        //todo:
        String[] files_google = {file_google0};

        for (int a = 0; a < files_google.length; a++) {
            int sheet_index = 0;
            int totalSheets = File_Details.getWorksheets(path_google + files_google[a]);
            //System.out.println("Reading Collection Excel....!");
           
            System.out.println(totalSheets);
            while (sheet_index  < totalSheets) {
                //System.err.println("Number: "+(totalSheets-1));
                ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
                datas = new Object[]{"Project", "forks", "Commits", "Unique", "Changes", "Percentage", "Category", "Major", "Minor", "Total"};
                DataSet.add(datas);

                String project = File_Details.setProjectName(path_google + files_google[a], sheet_index, "B2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 2, 2);
                List<Double> commits = ReadExcelFile_1Column.readColumnAsNumeric(path_google + files_google[a], sheet_index, 3, 1);
                List<Double> unique = ReadExcelFile_1Column.readColumnAsNumeric(path_google + files_google[a], sheet_index, 4, 1);
                //List<String> commits_ = Pick_GeneralNext.pick(path_google + files_google[a], sheet_index, 6, 1);
                List<String> contribution = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 7, 1);
                List<String> percentage = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 8, 1);
                //List<String> category = Pick_GeneralNext.pick(path_google + files_google[a], sheet_index, 7, 1);
                
                double com_mlp = commits.get(0);
                double unique_mlp = unique.get(0);
                commits.remove(0);
                unique.remove(0);

                System.err.println(project);
                List<List<String>> lists_all = new ArrayList<>();
                for (int i = 0; i < contribution.size(); i++) {
                    String[] splits = contribution.get(i).split("/");
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < splits.length; j++) {
                        list.add(splits[j]);
                    }
                    System.out.println(list);
                    lists_all.add(list);

                }
                String name_i = "", name_j = "", email_prefix_i = "", email_prefix_j = "", login_i = "", login_j = "", location_i = "", location_j = "", created_at_i = "", created_at_j = "", date_i = "", date_j = "";
                String contrib_i = "", contrib_j = "";

                List<String> contList = new ArrayList<>();

                for (int i = 0; i < lists_all.size(); i++) {
                    //System.out.println(i+"\t"+contribution.get(i));
                    // contrib_i = contribution.get(i);
                    //String[] split_i = contribution.get(i).split("/");
                    date_i = percentage.get(i);
                    List<String> lists_i = lists_all.get(i);
                    for (int x = 0; x < lists_i.size(); x++) {
                        name_i = lists_i.get(x).split("\\|")[0];
                        //System.out.println(split_i[x]+"\t"+split_i.length);
                        if (lists_i.get(x).split("\\|").length > 1) {
                            if (lists_i.get(x).split("\\|")[1].contains("@")) {
                                email_prefix_i = lists_i.get(x).split("\\|")[1].substring(0, lists_i.get(x).split("\\|")[1].indexOf("@"));
                            }
                            login_i = lists_i.get(x).split("\\|")[2].split(":")[0];
                        } else {
                            email_prefix_i = "em##";
                            login_i = "###";
                        }
                        for (int j = 0; j < lists_all.size(); j++) {
                            //contrib_j = contribution.get(j);
                            List<String> lists_j = lists_all.get(j);
                            //String[] split_j = contribution.get(j).split("/");
                            date_j = percentage.get(j);
                            for (int y = 0; y < lists_j.size(); y++) {
                                name_j = lists_j.get(y).split("\\|")[0];
                                if (lists_j.get(y).split("\\|").length > 1) {

                                    //System.out.println(lists_j.size()+"\t"+y);
                                    //System.out.println(name_i+"\t"+email_prefix_i+"\t"+login_i);
                                    ///System.out.println(name_j+"\t"+email_prefix_j+"\t"+login_j);
                                    if (lists_j.get(y).split("\\|")[1].contains("@")) {
                                        email_prefix_j = lists_j.get(y).split("\\|")[1].substring(0, lists_j.get(y).split("\\|")[1].indexOf("@"));
                                    }
                                    login_j = lists_j.get(y).split("\\|")[2].split(":")[0];

                                } else {
                                    email_prefix_j = "em2###";
                                    login_j = "log2###";
                                }
                                //todo::::: .........
                                String str1 = "", commits1 = "";
                                String str2 = "", commits2 = "";

                                String str_1 = "", commits_1 = "";
                                String str_2 = "", commits_2 = "";

                                String str3 = "", commits3 = "";
                                //

                                if (email_prefix_i.equals(email_prefix_j) && !login_i.equals("login######") && login_j.equals("login######")) {// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));

                                        if (str1.length() > 5 && str2.length() > 5) {
                                            String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                            String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());

                                            if (date_i.equals(date_j)) {
                                                double com = 0;
                                                double com2 = 0;
                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }

                                                //contrib_i = contrib_i.replaceAll(split_i[x], str1 + ":" + com);
                                                /// Delete from the list..
                                                //contrib_i = contrib_i.replaceAll(split_j[y], "");
                                                //System.out.println(lists_i + "\t" + split_i[x]);
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str1 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);
                                                }
                                                //System.out.println("1 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + str1 + ":" + com);
                                                //System.out.println("1... : " + contribution.get(i) + "\t" + split_j[y]);

                                            } else {
                                                //contrib_j = contrib_j.replaceAll(split_j[y], str1 + com_j);
                                                //System.out.println(lists_j+"\t"+split_j[y]);

                                                int index = lists_j.indexOf(lists_j.get(y));
                                                if (index != -1) {
                                                    lists_j.set(index, str1 + ":" + com_j);

                                                    lists_all.set(j, lists_j);

                                                }

                                                //System.err.println("11:  " + contribution.get(i) + "\t" + split_i[x]);
                                            }
                                        }
                                    }
                                    //System.out.println("1: "+login_i+" , "+login_j);
                                } 
                                //TODO::: THERE MUST BE A BUG IN THIS STATEMENT
                                else if (email_prefix_i.equals(email_prefix_j) && !login_j.equals("login######") && login_i.equals("login######")) {
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));
                                        //System.err.println(str1);
                                        //System.err.println(str2);
                                        //System.err.println(lists_all);
                                        if (str1.length() > 5 && str2.length() > 5) {
                                            String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                            String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());
                                            if (date_i.equals(date_j)) {
                                                double com = 0;

                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str2 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);

                                                }
                                                //System.err.println("2 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                            } else {
                                                
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str2 + ":" + com_i);
                                                    lists_all.set(i, lists_i);
                                                }
                                                //System.err.println("21 : " + contribution.get(i) + "\t" + split_i[x]);
                                            }
                                        }
                                    }
                                    //System.out.println("4: "+login_i+" , "+login_j);
                                } else if (name_i.equals(name_j) && !login_i.equals("login######") && login_j.equals("login######")) {
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));

                                        String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                        String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());
                                        if (str1.length() > 5 && str2.length() > 5) {
                                            if (date_i.equals(date_j)) {
                                                double com = 0;
                                                //double com2 = 0;
                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str1 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);
                                                    //lists_all.remove(lists_j);
                                                }
                                            } else {
                                                //contrib_j = contrib_j.replaceAll(split_j[y], str1 + ":" + com_j);
                                                int index = lists_j.indexOf(lists_j.get(y));
                                                if (index != -1) {
                                                    lists_j.set(index, str1 + ":" + com_j);
                                                    lists_all.set(j, lists_j);
                                                }
                                                /// System.err.println("31 : " + contribution.get(j) + "\t" + split_j[y]);
                                            }
                                        }
                                    }
                                    /////System.out.println("5: "+login_i+" , "+login_j);
                                } 
                                //SECOND BUG IS HERE....!
                                else if (name_i.equals(name_j) && !login_j.equals("login######") && login_i.equals("login######")) {
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));

                                        if (str1.length() > 5 && str2.length() > 5) {
                                            String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                            String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());

                                            if (date_i.equals(date_j)) {
                                                double com = 0;
                                                //double com2 = 0;
                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }

                                                //contrib_i = contrib_i.replaceAll(split_i[x], str2 + ":" + com);
                                                /// Delete from the list..
                                                //contrib_i = contrib_i.replaceAll(split_j[y], "");
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str2 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);
                                                }
                                                //System.err.println("4 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                            } else {

                                                //contrib_i = contrib_i.replaceAll(split_i[x], str2 + ":" + com_i);
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str2 + ":" + com_i);
                                                    lists_all.set(i, lists_i);
                                                }
                                                // System.err.println("41 : " + contribution.get(i) + "\t" + split_i[x]);
                                            }
                                        }
                                    }
                                } else if (email_prefix_i.equals(email_prefix_j) && login_i.equals(login_j) && x != y) {
                                    //System.out.println(lists_i.size() + " \t" + x);
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));
                                        if (str1.length() > 5 && str2.length() > 5) {
                                            String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                            String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());

                                            if (date_i.equals(date_j)) {
                                                double com = 0;
                                                double com2 = 0;
                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str1 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);
                                                }

                                            } else {

                                                //contrib_j = contrib_j.replaceAll(split_j[y], str1 + ":" + com_j);
                                                int index = lists_j.indexOf(lists_j.get(y));
                                                if (index != -1) {
                                                    lists_j.set(index, str1 + ":" + com_j);
                                                    lists_all.set(j, lists_j);
                                                }
                                            }
                                        }

                                    }
                                } else if (!login_j.equals("login######") && login_i.equals(login_j) && x != y) {
                                    //System.out.println(lists_i.size() + " \t" + x);
                                    if (x < lists_i.size() && y < lists_j.size()) {
                                        str1 = lists_i.get(x).substring(0, lists_i.get(x).lastIndexOf(":"));
                                        str2 = lists_j.get(y).substring(0, lists_j.get(y).lastIndexOf(":"));
                                        if (str1.length() > 5 && str2.length() > 5) {
                                            String com_i = lists_i.get(x).substring(lists_i.get(x).lastIndexOf(":") + 1, lists_i.get(x).length());
                                            String com_j = lists_j.get(y).substring(lists_j.get(y).lastIndexOf(":") + 1, lists_j.get(y).length());

                                            if (date_i.equals(date_j)) {
                                                double com = 0;
                                                double com2 = 0;
                                                if (Coversions.isDouble(com_i) && Coversions.isDouble(com_j)) {
                                                    com = Double.parseDouble(com_i) + Double.parseDouble(com_j);
                                                }
                                                int index = lists_i.indexOf(lists_i.get(x));
                                                if (index != -1) {
                                                    lists_i.set(index, str1 + ":" + com);
                                                    lists_i.remove(lists_i.indexOf(lists_j.get(y)));
                                                    lists_all.set(i, lists_i);
                                                }

                                            } else {

                                                //contrib_j = contrib_j.replaceAll(split_j[y], str1 + ":" + com_j);
                                                int index = lists_j.indexOf(lists_j.get(y));
                                                if (index != -1) {
                                                    lists_j.set(index, str1 + ":" + com_j);
                                                    lists_all.set(j, lists_j);
                                                }
                                            }
                                        }

                                    }
                                }

                            }
                        }
                    }

                    //contList.add(contrib_i);
                }

                
                for (int i = 0; i < lists_all.size(); i++) {
                    System.out.println("\t"+lists_all.get(i));
                }
                double tot_1 = 0, tot_2 = 0;
                double tot1 = 0;
                List<String> rList1 = new ArrayList<>();

                List<String> lists1 = lists_all.get(0);
                String combined1 = "";
                List<Double> percL_1 = new ArrayList<>();
                List<Double> tot_list_1 = new ArrayList<>();
                for (int j = 0; j < lists1.size(); j++) {
                    combined1 = combined1.concat(lists1.get(j) + "/");
                    if (lists1.get(j).contains(":")) {
                        tot_list_1.add(Double.parseDouble(lists1.get(j).substring(lists1.get(j).lastIndexOf(":") + 1, lists1.get(j).length())));
                        tot1 += Double.parseDouble(lists1.get(j).substring(lists1.get(j).lastIndexOf(":") + 1, lists1.get(j).length()));

                    } else {
                        rList1.add(lists1.get(j));
                    }
                }
                lists1.removeAll(rList1);

                for (int i = 0; i < tot_list_1.size(); i++) {
                    double perc0 = (tot_list_1.get(i) / tot1) * 100;
                    percL_1.add(Double.valueOf(newFormat.format(perc0)));
                }
                String login_mlp = "", log_percentage_1 = "", log_category_1 = "";
                MathsFunctions.InsertionSort(lists1, percL_1, tot_list_1);
                double max1 = 0;
                double c_mj1 = 0, c_mn1 = 0;
                if (tot_list_1.size() > 0) {
                    String cat;
                    for (int i = 0; i < tot_list_1.size(); i++) {
                        if (max1 <= 80) {
                            cat = "Major";
                            c_mj1++;
                        } else {
                            c_mn1++;
                            cat = "Minor";

                        }

                        if (lists1.get(i).contains(":")) {
                            // System.out.println(i+":"+log_list_1.get(i)+"\t"+max1+"\t"+tot_list_1.get(i)+"\t"+cat);
                            login_mlp = login_mlp.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + tot_list_1.get(i) + "/");
                            log_percentage_1 = log_percentage_1.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + percL_1.get(i) + "/");
                            log_category_1 = log_category_1.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + cat + "/");

                        }
                        max1 += percL_1.get(i);
                    }
                }

                
                datas = new Object[]{project, project, com_mlp, unique_mlp, login_mlp, log_percentage_1, log_category_1, c_mj1, c_mn1, c_mj1 + c_mn1};
                DataSet.add(datas);
                lists_all.remove(0);

                
                for (int i1 = 0; i1 < lists_all.size(); i1++) {
                    double tot2 = 0;
                    List<String> lists = lists_all.get(i1);
                    //System.out.println(i1+"\t"+lists);
                    String combined = "";
                    List<Double> percL_2 = new ArrayList<>();
                    List<Double> tot_list_2 = new ArrayList<>();
                    List<String> rList2 = new ArrayList<>();

                    for (int j = 0; j < lists.size(); j++) {
                        combined = combined.concat(lists.get(j) + "/");
                        //System.err.println(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length()));
                        if (lists.get(j).contains(":")) {
                            tot_list_2.add(Double.parseDouble(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length())));
                            tot2 += Double.parseDouble(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length()));

                            
                        } else {
                            rList2.add(lists.get(j));
                        }
                    }
                    lists.removeAll(rList2);

                    for (int i = 0; i < tot_list_2.size(); i++) {
                        double perc0 = (tot_list_2.get(i) / tot2) * 100;
                        percL_2.add(Double.valueOf(newFormat.format(perc0)));
                        //System.out.println(tot2);
                        
                    }
                    MathsFunctions.InsertionSort(lists, percL_2, tot_list_2);
                    double max2 = 0;
                    double c_mj2 = 0, c_mn2 = 0;
                    String email_collections = "", log_percentage_2 = "", log_category_2 = "";
                    if (lists.size() > 0) {
                        String cat;
                        for (int i = 0; i < tot_list_2.size(); i++) {
                            if (max2 <= 80) {
                                cat = "Major";
                                c_mj2++;
                            } else {
                                c_mn2++;
                                cat = "Minor";
                            }
                            if (lists.get(i).contains(":")) {
                                email_collections = email_collections.concat(lists.get(i).substring(0, lists.get(i).lastIndexOf(":")) + ":" + tot_list_2.get(i) + "/");
                                log_percentage_2 = log_percentage_2.concat(lists.get(i).substring(0, lists.get(i).lastIndexOf(":")) + ":" + percL_2.get(i) + "/");
                                log_category_2 = log_category_2.concat(lists.get(i).substring(0, lists.get(i).lastIndexOf(":")) + ":" + cat + "/");

                            }

                            max2 += percL_2.get(i);
                            
                        }

                    }

                    //System.out.println("***************************");
                    datas = new Object[]{"", nameList.get(i1), commits.get(i1), unique.get(i1), email_collections, log_percentage_2, log_category_2, c_mj2, c_mn2, c_mj2+ c_mn2};
                    DataSet.add(datas);
                    //datas = new Object[]{"", nameList.get(i1), commits.get(i1), unique.get(i1), email_collections, log_percentage_2, log_category_2, c_mj2, c_mn2, c_mj2 + c_mn2};
                    // DataSet.add(datas);
                }

                String file_name = files_google[a].replaceAll("repos_fdetails_dev_mj_mn2", "repos_merged_dev_mj_mn");
                Create_ExcelFile.createExcel(DataSet, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_index);

                sheet_index++;
            }
        }

    }
}
