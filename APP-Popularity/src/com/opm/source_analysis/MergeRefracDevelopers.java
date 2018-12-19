/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.core.Coversions;
import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.core.MathsFunctions;
import com.opm.excel_.ReadExcelFile_1Column;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class MergeRefracDevelopers {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        DecimalFormat newFormat = new DecimalFormat("#.##");
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";
        String file_google0 = "refrac_merged_dev.xlsx";
        String file_google1 = "repos_gp_combshaa1_500.xlsx";
        String file_google2 = "repos_gp_combshaa11.xlsx";
        String file_google3 = "repos_gp_combshaa2.xlsx";
        String file_google4 = "repos_gp_combshaa3.xlsx";
        String file_google5 = "repos_gp_combshaa1_500.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        String path_google = "/Users/john/Desktop/Dev_Commits/00New_Repos/merged/00refract_stats/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        //todo:
        String[] files_google = {file_google0};

        for (int a = 0; a < files_google.length; a++) {
            int sheet_index = 0;
            int numbers = File_Details.getWorksheets(path_google + files_google[a]);
            //System.out.println("Reading Collection Excel....!");
            while (sheet_index < numbers) {
                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"N.S", "Project", "forks", "Refactoring", "Refac_Perc", "Refac_Cat", "Refac_Maj", "Refac_min", "", "Changes", "Chang_Perc", "Change_Cat", "Change_Maj", "Change_Min", "", "Commits", "Comit_Perc", "", "Tot_Refac", "Tot_Changes", "Tot_Comits", "Tot_Dev"};
                allobj.add(datas);

                String project = File_Details.setProjectName(path_google + files_google[a], sheet_index, "B2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 2, 2);
                List<String> contribRefrac = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 3, 1);
                List<String> contribChang = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 4, 1);
                List<String> contribComm = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 5, 1);
                List<String> percentage = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 6, 1);
                List<String> category = ReadExcelFile_1Column.readColumnAsString(path_google + files_google[a], sheet_index, 7, 1);

                List<List<String>> lists_refrac = new ArrayList<>();
                List<List<String>> lists_chang = new ArrayList<>();
                List<List<String>> lists_comm = new ArrayList<>();
                for (int i = 0; i < contribRefrac.size(); i++) {
                    String[] splits1 = contribRefrac.get(i).split("/");
                    List<String> list1 = new ArrayList<>();
                    String[] splits2 = contribChang.get(i).split("/");
                    List<String> list2 = new ArrayList<>();
                    String[] splits3 = contribComm.get(i).split("/");
                    List<String> list3 = new ArrayList<>();
                    for (int j = 0; j < splits1.length; j++) {
                        list1.add(splits1[j]);
                        list2.add(splits2[j]);
                        list3.add(splits3[j]);
                    }
                    lists_refrac.add(list1);
                    lists_chang.add(list2);
                    lists_comm.add(list3);
                }
                String name_i = "", name_j = "", email_prefix_i = "", email_prefix_j = "", login_i = "", login_j = "", location_i = "", location_j = "", created_at_i = "", created_at_j = "", date_i = "", date_j = "";
                String contrib_i = "", contrib_j = "";

                List<String> contList = new ArrayList<>();

                for (int i = 0; i < lists_refrac.size(); i++) {
                    //System.out.println(i+"\t"+contribution.get(i));
                    // contrib_i = contribution.get(i);
                    //String[] split_i = contribution.get(i).split("/");
                    date_i = percentage.get(i);
                    List<String> lists1_i = lists_refrac.get(i);
                    List<String> lists2_i = lists_chang.get(i);
                    List<String> lists3_i = lists_comm.get(i);
                    // name_i = 
                    for (int x = 0; x < lists1_i.size(); x++) {
                        name_i = lists1_i.get(x).split("\\|")[0];
                        //System.out.println(split_i[x]+"\t"+split_i.length);
                        if (lists1_i.get(x).split("\\|").length > 1) {

                            if (lists1_i.get(x).split("\\|")[1].contains("@")) {
                                email_prefix_i = lists1_i.get(x).split("\\|")[1].substring(0, lists1_i.get(x).split("\\|")[1].indexOf("@"));
                            }

                            login_i = lists1_i.get(x).split("\\|")[2].split(":")[0];
                        } else {
                            email_prefix_i = "em##";
                            login_i = "###";
                        }
                        for (int j = 0; j < lists_refrac.size(); j++) {
                            //contrib_j = contribution.get(j);
                            List<String> lists1_j = lists_refrac.get(j);
                            List<String> lists2_j = lists_chang.get(j);
                            List<String> lists3_j = lists_comm.get(j);
                            //String[] split_j = contribution.get(j).split("/");
                            date_j = percentage.get(j);
                            for (int y = 0; y < lists1_j.size(); y++) {
                                name_j = lists1_j.get(y).split("\\|")[0];
                                if (lists1_j.get(y).split("\\|").length > 1) {

                                    //System.out.println(lists_j.size()+"\t"+y);
                                    //System.out.println(name_i+"\t"+email_prefix_i+"\t"+login_i);
                                    ///System.out.println(name_j+"\t"+email_prefix_j+"\t"+login_j);
                                    if (lists1_j.get(y).split("\\|")[1].contains("@")) {
                                        email_prefix_j = lists1_j.get(y).split("\\|")[1].substring(0, lists1_j.get(y).split("\\|")[1].indexOf("@"));
                                    }
                                    login_j = lists1_j.get(y).split("\\|")[2].split(":")[0];

                                } else {
                                    email_prefix_j = "em2###";
                                    login_j = "log2###";
                                }
                                //todo::::: .........
                                String str1_1 = "", commits1_1 = "";
                                String str1_2 = "", commits1_2 = "";
                                String str1_3 = "", commits1_3 = "";

                                String str2_1 = "", commits2_1 = "";
                                String str2_2 = "", commits2_2 = "";
                                String str2_3 = "", commits2_3 = "";

                                String str3_1 = "", commits3_1 = "";
                                String str3_2 = "", commits3_2 = "";
                                String str3_3 = "", commits3_3 = "";
                                //

                                if (email_prefix_i.equals(email_prefix_j) && !login_i.equals("login######") && login_j.equals("login######")) {// || name_i.equals(name_j) || email_prefix_i.equals(email_prefix_j)){
                                    str1_1 = lists1_i.get(x).substring(0, lists1_i.get(x).lastIndexOf(":"));
                                    str1_2 = lists1_j.get(y).substring(0, lists1_j.get(y).lastIndexOf(":"));

                                    str2_1 = lists2_i.get(x).substring(0, lists2_i.get(x).lastIndexOf(":"));
                                    str2_2 = lists2_j.get(y).substring(0, lists2_j.get(y).lastIndexOf(":"));

                                    str3_1 = lists3_i.get(x).substring(0, lists3_i.get(x).lastIndexOf(":"));
                                    str3_2 = lists3_j.get(y).substring(0, lists3_j.get(y).lastIndexOf(":"));
                                    if (str1_1.length() > 5 && str1_2.length() > 5) {
                                        String com1_i = lists1_i.get(x).substring(lists1_i.get(x).lastIndexOf(":") + 1, lists1_i.get(x).length());
                                        String com1_j = lists1_j.get(y).substring(lists1_j.get(y).lastIndexOf(":") + 1, lists1_j.get(y).length());

                                        String com2_i = lists2_i.get(x).substring(lists2_i.get(x).lastIndexOf(":") + 1, lists2_i.get(x).length());
                                        String com2_j = lists2_j.get(y).substring(lists2_j.get(y).lastIndexOf(":") + 1, lists2_j.get(y).length());

                                        String com3_i = lists3_i.get(x).substring(lists3_i.get(x).lastIndexOf(":") + 1, lists3_i.get(x).length());
                                        String com3_j = lists3_j.get(y).substring(lists3_j.get(y).lastIndexOf(":") + 1, lists3_j.get(y).length());

                                        if (date_i.equals(date_j)) {
                                            double com1 = 0;
                                            double com2 = 0;
                                            double com3 = 0;
                                            if (Coversions.isDouble(com1_i) && Coversions.isDouble(com1_j)) {
                                                com1 = Double.parseDouble(com1_i) + Double.parseDouble(com1_j);
                                            }
                                            if (Coversions.isDouble(com2_i) && Coversions.isDouble(com2_j)) {
                                                com2 = Double.parseDouble(com2_i) + Double.parseDouble(com2_i);
                                            }
                                            if (Coversions.isDouble(com3_i) && Coversions.isDouble(com3_j)) {
                                                com3 = Double.parseDouble(com3_i) + Double.parseDouble(com3_j);
                                            }
                                            //contrib_i = contrib_i.replaceAll(split_i[x], str1 + ":" + com);
                                            /// Delete from the list..
                                            //contrib_i = contrib_i.replaceAll(split_j[y], "");
                                            //System.out.println(lists_i + "\t" + split_i[x]);
                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_1 + ":" + com1);
                                                lists1_i.remove(lists1_i.indexOf(lists1_j.get(y)));
                                                lists_refrac.set(i, lists1_i);

                                                lists2_i.set(index, str2_1 + ":" + com2);
                                                lists2_i.remove(lists2_i.indexOf(lists2_j.get(y)));
                                                lists_chang.set(i, lists2_i);

                                                lists3_i.set(index, str3_1 + ":" + com3);
                                                lists3_i.remove(lists3_i.indexOf(lists3_j.get(y)));
                                                lists_comm.set(i, lists3_i);
                                                //lists_all.remove(lists_j);
                                            }
                                            //System.out.println("1 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + str1 + ":" + com);
                                            //System.out.println("1... : " + contribution.get(i) + "\t" + split_j[y]);

                                        } else {
                                            //contrib_j = contrib_j.replaceAll(split_j[y], str1 + com_j);
                                            //System.out.println(lists_j+"\t"+split_j[y]);

                                            int index = lists1_j.indexOf(lists1_j.get(y));
                                            if (index != -1) {
                                                lists1_j.set(index, str1_1 + ":" + com1_j);
                                                lists_refrac.set(j, lists1_j);

                                                lists2_j.set(index, str2_1 + ":" + com2_j);
                                                lists_chang.set(j, lists2_j);

                                                lists3_j.set(index, str3_1 + ":" + com3_j);
                                                lists_comm.set(j, lists3_j);
                                            }

                                            //System.err.println("11:  " + contribution.get(i) + "\t" + split_i[x]);
                                        }
                                    }
                                    //System.out.println("1: "+login_i+" , "+login_j);
                                } else if (email_prefix_i.equals(email_prefix_j) && !login_j.equals("login######") && login_i.equals("login######")) {
                                    str1_1 = lists1_i.get(x).substring(0, lists1_i.get(x).lastIndexOf(":"));
                                    str1_2 = lists1_j.get(y).substring(0, lists1_j.get(y).lastIndexOf(":"));

                                    str2_1 = lists2_i.get(x).substring(0, lists2_i.get(x).lastIndexOf(":"));
                                    str2_2 = lists2_j.get(y).substring(0, lists2_j.get(y).lastIndexOf(":"));

                                    str3_1 = lists3_i.get(x).substring(0, lists3_i.get(x).lastIndexOf(":"));
                                    str3_2 = lists3_j.get(y).substring(0, lists3_j.get(y).lastIndexOf(":"));
                                    if (str1_1.length() > 5 && str1_2.length() > 5) {
                                        String com1_i = lists1_i.get(x).substring(lists1_i.get(x).lastIndexOf(":") + 1, lists1_i.get(x).length());
                                        String com1_j = lists1_j.get(y).substring(lists1_j.get(y).lastIndexOf(":") + 1, lists1_j.get(y).length());

                                        String com2_i = lists2_i.get(x).substring(lists2_i.get(x).lastIndexOf(":") + 1, lists2_i.get(x).length());
                                        String com2_j = lists2_j.get(y).substring(lists2_j.get(y).lastIndexOf(":") + 1, lists2_j.get(y).length());

                                        String com3_i = lists3_i.get(x).substring(lists3_i.get(x).lastIndexOf(":") + 1, lists3_i.get(x).length());
                                        String com3_j = lists3_j.get(y).substring(lists3_j.get(y).lastIndexOf(":") + 1, lists3_j.get(y).length());

                                        if (date_i.equals(date_j)) {
                                            double com1 = 0;
                                            double com2 = 0;
                                            double com3 = 0;
                                            if (Coversions.isDouble(com1_i) && Coversions.isDouble(com1_j)) {
                                                com1 = Double.parseDouble(com1_i) + Double.parseDouble(com1_j);
                                            }
                                            if (Coversions.isDouble(com2_i) && Coversions.isDouble(com2_j)) {
                                                com2 = Double.parseDouble(com2_i) + Double.parseDouble(com2_i);
                                            }
                                            if (Coversions.isDouble(com3_i) && Coversions.isDouble(com3_j)) {
                                                com3 = Double.parseDouble(com3_i) + Double.parseDouble(com3_j);
                                            }

                                            //contrib_i = contrib_i.replaceAll(lists_i.get(x), str2 + ":" + com);
                                            /// Delete from the list..
                                            //contrib_i = contrib_i.replaceAll(lists_j.get(y), "");
                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_2 + ":" + com1);
                                                lists1_i.remove(lists1_i.indexOf(lists1_j.get(y)));
                                                lists_refrac.set(i, lists1_i);

                                                lists2_i.set(index, str2_2 + ":" + com2);
                                                lists2_i.remove(lists2_i.indexOf(lists2_j.get(y)));
                                                lists_chang.set(i, lists2_i);

                                                lists3_i.set(index, str3_2 + ":" + com3);
                                                lists3_i.remove(lists3_i.indexOf(lists3_j.get(y)));
                                                lists_comm.set(i, lists3_i);

                                                //lists_all.remove(lists_j);
                                            }

                                            //System.err.println("2 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                        } else {
                                            //contrib_i = contrib_i.replaceAll(split_i[x], str2 + ":" + com_i);

                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_2 + ":" + com1_j);
                                                lists_refrac.set(j, lists1_i);

                                                lists2_i.set(index, str2_2 + ":" + com2_j);
                                                lists_chang.set(j, lists2_i);

                                                lists3_i.set(index, str3_2 + ":" + com3_j);
                                                lists_comm.set(j, lists3_i);

                                            }
                                            //System.err.println("21 : " + contribution.get(i) + "\t" + split_i[x]);
                                        }
                                    }
                                    //System.out.println("4: "+login_i+" , "+login_j);

                                } else if (name_i.equals(name_j) && !login_i.equals("login######") && login_j.equals("login######")) {
                                    str1_1 = lists1_i.get(x).substring(0, lists1_i.get(x).lastIndexOf(":"));
                                    str1_2 = lists1_j.get(y).substring(0, lists1_j.get(y).lastIndexOf(":"));

                                    str2_1 = lists2_i.get(x).substring(0, lists2_i.get(x).lastIndexOf(":"));
                                    str2_2 = lists2_j.get(y).substring(0, lists2_j.get(y).lastIndexOf(":"));

                                    str3_1 = lists3_i.get(x).substring(0, lists3_i.get(x).lastIndexOf(":"));
                                    str3_2 = lists3_j.get(y).substring(0, lists3_j.get(y).lastIndexOf(":"));
                                    String com1_i = lists1_i.get(x).substring(lists1_i.get(x).lastIndexOf(":") + 1, lists1_i.get(x).length());
                                    String com1_j = lists1_j.get(y).substring(lists1_j.get(y).lastIndexOf(":") + 1, lists1_j.get(y).length());

                                    String com2_i = lists2_i.get(x).substring(lists2_i.get(x).lastIndexOf(":") + 1, lists2_i.get(x).length());
                                    String com2_j = lists2_j.get(y).substring(lists2_j.get(y).lastIndexOf(":") + 1, lists2_j.get(y).length());

                                    String com3_i = lists3_i.get(x).substring(lists3_i.get(x).lastIndexOf(":") + 1, lists3_i.get(x).length());
                                    String com3_j = lists3_j.get(y).substring(lists3_j.get(y).lastIndexOf(":") + 1, lists3_j.get(y).length());
                                    if (str1_1.length() > 5 && str1_2.length() > 5) {
                                        if (date_i.equals(date_j)) {
                                            double com1 = 0;
                                            double com2 = 0;
                                            double com3 = 0;
                                            if (Coversions.isDouble(com1_i) && Coversions.isDouble(com1_j)) {
                                                com1 = Double.parseDouble(com1_i) + Double.parseDouble(com1_j);
                                            }
                                            if (Coversions.isDouble(com2_i) && Coversions.isDouble(com2_j)) {
                                                com2 = Double.parseDouble(com2_i) + Double.parseDouble(com2_i);
                                            }
                                            if (Coversions.isDouble(com3_i) && Coversions.isDouble(com3_j)) {
                                                com3 = Double.parseDouble(com3_i) + Double.parseDouble(com3_j);
                                            }

                                            //contrib_i = contrib_i.replaceAll(split_i[x], str1 + ":" + com);
                                            /// Delete from the list..
                                            //contrib_i = contrib_i.replaceAll(split_j[y], "");
                                            //System.err.println("3 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_1 + ":" + com1);
                                                lists1_i.remove(lists1_i.indexOf(lists1_j.get(y)));
                                                lists_refrac.set(i, lists1_i);

                                                lists2_i.set(index, str2_1 + ":" + com2);
                                                lists2_i.remove(lists2_i.indexOf(lists2_j.get(y)));
                                                lists_chang.set(i, lists2_i);

                                                lists3_i.set(index, str3_1 + ":" + com3);
                                                lists3_i.remove(lists3_i.indexOf(lists3_j.get(y)));
                                                lists_comm.set(i, lists3_i);
                                                //lists_all.remove(lists_j);
                                            }
                                        } else {

                                            //contrib_j = contrib_j.replaceAll(split_j[y], str1 + ":" + com_j);
                                            int index = lists1_j.indexOf(lists1_j.get(y));
                                            if (index != -1) {
                                                lists1_j.set(index, str1_1 + ":" + com1_j);
                                                lists_refrac.set(j, lists1_j);

                                                lists2_j.set(index, str2_1 + ":" + com2_j);
                                                lists_chang.set(j, lists2_j);

                                                lists3_j.set(index, str3_1 + ":" + com3_j);
                                                lists_comm.set(j, lists3_j);
                                            }
                                            /// System.err.println("31 : " + contribution.get(j) + "\t" + split_j[y]);
                                        }
                                    }

                                    /////System.out.println("5: "+login_i+" , "+login_j);
                                } else if (name_i.equals(name_j) && !login_j.equals("login######") && login_i.equals("login######")) {
                                    str1_1 = lists1_i.get(x).substring(0, lists1_i.get(x).lastIndexOf(":"));
                                    str1_2 = lists1_j.get(y).substring(0, lists1_j.get(y).lastIndexOf(":"));

                                    str2_1 = lists2_i.get(x).substring(0, lists2_i.get(x).lastIndexOf(":"));
                                    str2_2 = lists2_j.get(y).substring(0, lists2_j.get(y).lastIndexOf(":"));

                                    str3_1 = lists3_i.get(x).substring(0, lists3_i.get(x).lastIndexOf(":"));
                                    str3_2 = lists3_j.get(y).substring(0, lists3_j.get(y).lastIndexOf(":"));
                                    if (str1_1.length() > 5 && str1_2.length() > 5) {
                                        String com1_i = lists1_i.get(x).substring(lists1_i.get(x).lastIndexOf(":") + 1, lists1_i.get(x).length());
                                        String com1_j = lists1_j.get(y).substring(lists1_j.get(y).lastIndexOf(":") + 1, lists1_j.get(y).length());

                                        String com2_i = lists2_i.get(x).substring(lists2_i.get(x).lastIndexOf(":") + 1, lists2_i.get(x).length());
                                        String com2_j = lists2_j.get(y).substring(lists2_j.get(y).lastIndexOf(":") + 1, lists2_j.get(y).length());

                                        String com3_i = lists3_i.get(x).substring(lists3_i.get(x).lastIndexOf(":") + 1, lists3_i.get(x).length());
                                        String com3_j = lists3_j.get(y).substring(lists3_j.get(y).lastIndexOf(":") + 1, lists3_j.get(y).length());

                                        if (date_i.equals(date_j)) {
                                            double com1 = 0;
                                            double com2 = 0;
                                            double com3 = 0;
                                            if (Coversions.isDouble(com1_i) && Coversions.isDouble(com1_j)) {
                                                com1 = Double.parseDouble(com1_i) + Double.parseDouble(com1_j);
                                            }
                                            if (Coversions.isDouble(com2_i) && Coversions.isDouble(com2_j)) {
                                                com2 = Double.parseDouble(com2_i) + Double.parseDouble(com2_i);
                                            }
                                            if (Coversions.isDouble(com3_i) && Coversions.isDouble(com3_j)) {
                                                com3 = Double.parseDouble(com3_i) + Double.parseDouble(com3_j);
                                            }

                                            //contrib_i = contrib_i.replaceAll(split_i[x], str2 + ":" + com);
                                            /// Delete from the list..
                                            //contrib_i = contrib_i.replaceAll(split_j[y], "");
                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_2 + ":" + com1);
                                                lists1_i.remove(lists1_i.indexOf(lists1_j.get(y)));
                                                lists_refrac.set(i, lists1_i);

                                                lists2_i.set(index, str2_2 + ":" + com2);
                                                lists2_i.remove(lists2_i.indexOf(lists2_j.get(y)));
                                                lists_chang.set(i, lists2_i);

                                                lists3_i.set(index, str3_2 + ":" + com3);
                                                lists3_i.remove(lists3_i.indexOf(lists3_j.get(y)));
                                                lists_comm.set(i, lists3_i);

                                                //lists_all.remove(lists_j);
                                            }
                                            //System.err.println("4 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                        } else {

                                            //contrib_i = contrib_i.replaceAll(split_i[x], str2 + ":" + com_i);
                                            int index = lists1_i.indexOf(lists1_i.get(x));
                                            if (index != -1) {
                                                lists1_i.set(index, str1_2 + ":" + com1_j);
                                                lists_refrac.set(j, lists1_i);

                                                lists2_i.set(index, str2_2 + ":" + com2_j);
                                                lists_chang.set(j, lists2_i);

                                                lists3_i.set(index, str3_2 + ":" + com3_j);
                                                lists_comm.set(j, lists3_i);

                                            }
                                            // System.err.println("41 : " + contribution.get(i) + "\t" + split_i[x]);
                                        }
                                    }
                                    //		
                                } else if (email_prefix_i.equals(email_prefix_j) && login_i.equals(login_j) && x != y) {
                                    //System.out.println(lists_i.size() + " \t" + x);
                                    if (x < lists1_i.size() && y < lists1_j.size()) {
                                        str1_1 = lists1_i.get(x).substring(0, lists1_i.get(x).lastIndexOf(":"));
                                        str1_2 = lists1_j.get(y).substring(0, lists1_j.get(y).lastIndexOf(":"));

                                        str2_1 = lists2_i.get(x).substring(0, lists2_i.get(x).lastIndexOf(":"));
                                        str2_2 = lists2_j.get(y).substring(0, lists2_j.get(y).lastIndexOf(":"));

                                        str3_1 = lists3_i.get(x).substring(0, lists3_i.get(x).lastIndexOf(":"));
                                        str3_2 = lists3_j.get(y).substring(0, lists3_j.get(y).lastIndexOf(":"));

                                        //System.out.println(lists_i);
                                        //System.out.println(lists_j);
                                        if (str1_1.length() > 5 && str1_2.length() > 5) {
                                            String com1_i = lists1_i.get(x).substring(lists1_i.get(x).lastIndexOf(":") + 1, lists1_i.get(x).length());
                                            String com1_j = lists1_j.get(y).substring(lists1_j.get(y).lastIndexOf(":") + 1, lists1_j.get(y).length());

                                            String com2_i = lists2_i.get(x).substring(lists2_i.get(x).lastIndexOf(":") + 1, lists2_i.get(x).length());
                                            String com2_j = lists2_j.get(y).substring(lists2_j.get(y).lastIndexOf(":") + 1, lists2_j.get(y).length());

                                            String com3_i = lists3_i.get(x).substring(lists3_i.get(x).lastIndexOf(":") + 1, lists3_i.get(x).length());
                                            String com3_j = lists3_j.get(y).substring(lists3_j.get(y).lastIndexOf(":") + 1, lists3_j.get(y).length());

                                            if (date_i.equals(date_j)) {
                                                double com1 = 0;
                                                double com2 = 0;
                                                double com3 = 0;
                                                if (Coversions.isDouble(com1_i) && Coversions.isDouble(com1_j)) {
                                                    com1 = Double.parseDouble(com1_i) + Double.parseDouble(com1_j);
                                                }
                                                if (Coversions.isDouble(com2_i) && Coversions.isDouble(com2_j)) {
                                                    com2 = Double.parseDouble(com2_i) + Double.parseDouble(com2_i);
                                                }
                                                if (Coversions.isDouble(com3_i) && Coversions.isDouble(com3_j)) {
                                                    com3 = Double.parseDouble(com3_i) + Double.parseDouble(com3_j);
                                                }
                                                //contribution.set(i, str1 + ":" + com);
                                                //contrib_i = contrib_i.replaceAll(split_i[x], str1 + ":" + com);
                                                //System.err.println("5 : " + contribution.get(i) + "\t" + split_i[x] + "\t" + com);
                                                /// Delete from the list..
                                                //contrib_i = contrib_i.replaceAll(split_i[x], "");
                                                int index = lists1_i.indexOf(lists1_i.get(x));
                                                if (index != -1) {
                                                    lists1_i.set(index, str1_1 + ":" + com1);

                                                    lists1_i.remove(lists1_i.indexOf(lists1_j.get(y)));

                                                    lists_refrac.set(i, lists1_i);

                                                    lists2_i.set(index, str2_1 + ":" + com2);
                                                    lists2_i.remove(lists2_i.indexOf(lists2_j.get(y)));

                                                    lists_chang.set(i, lists2_i);

                                                    lists3_i.set(index, str3_1 + ":" + com3);
                                                    lists3_i.remove(lists3_i.indexOf(lists3_j.get(y)));

                                                    lists_comm.set(i, lists3_i);
                                                }

                                            } else {

                                                //contrib_j = contrib_j.replaceAll(split_j[y], str1 + ":" + com_j);
                                                int index = lists1_j.indexOf(lists1_j.get(y));
                                                if (index != -1) {
                                                    lists1_j.set(index, str1_1 + ":" + com1_j);
                                                    lists_refrac.set(j, lists1_j);
                                                }

                                                index = lists2_j.indexOf(lists2_j.get(y));
                                                if (index != -1) {
                                                    lists2_j.set(index, str2_1 + ":" + com2_j);
                                                    lists_chang.set(j, lists2_j);
                                                }

                                                index = lists3_j.indexOf(lists3_j.get(y));
                                                if (index != -1) {
                                                    lists3_j.set(index, str3_1 + ":" + com3_j);
                                                    lists_comm.set(j, lists3_j);
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

                String combined0 = "";
                //List<Double> percL1_0 = new ArrayList<>();
                //List<Double> tot_list1_0 = new ArrayList<>();

                //List<Double> percL2_0 = new ArrayList<>();
                //List<Double> tot_list2_0 = new ArrayList<>();
                //List<Double> percL3_0 = new ArrayList<>();
                //List<Double> tot_list3_0 = new ArrayList<>();
                double tot1_1 = 0, tot2_1 = 0, tot3_1 = 0;

                List<String> rList1_1 = new ArrayList<>();
                List<String> rList1_2 = new ArrayList<>();

                List<String> rList2_1 = new ArrayList<>();
                List<String> rList2_2 = new ArrayList<>();

                List<String> rList3_1 = new ArrayList<>();
                List<String> rList3_2 = new ArrayList<>();

                List<String> lists1_1 = lists_refrac.get(0);
                List<String> lists2_1 = lists_chang.get(0);
                List<String> lists3_1 = lists_comm.get(0);
                String combined1 = "";
                List<Double> percL1_1 = new ArrayList<>();
                List<Double> tot_list1_1 = new ArrayList<>();

                List<Double> percL2_1 = new ArrayList<>();
                List<Double> tot_list2_1 = new ArrayList<>();

                List<Double> percL3_1 = new ArrayList<>();
                List<Double> tot_list3_1 = new ArrayList<>();
                //DOTO:: Refrac
                for (int j = 0; j < lists1_1.size(); j++) {
                    if (lists1_1.get(j).contains(":")) {
                        tot_list1_1.add(Double.parseDouble(lists1_1.get(j).substring(lists1_1.get(j).lastIndexOf(":") + 1, lists1_1.get(j).length())));
                        tot1_1 += Double.parseDouble(lists1_1.get(j).substring(lists1_1.get(j).lastIndexOf(":") + 1, lists1_1.get(j).length()));

                    } else {
                        rList1_1.add(lists1_1.get(j));
                    }
                }
                lists1_1.removeAll(rList1_1);

                for (int i = 0; i < tot_list1_1.size(); i++) {
                    double perc0 = (tot_list1_1.get(i) / tot1_1) * 100;
                    percL1_1.add(Double.valueOf(newFormat.format(perc0)));
                }
                //TODO::::::  Change...!!!!
                for (int j = 0; j < lists2_1.size(); j++) {
                    if (lists2_1.get(j).contains(":")) {
                        tot_list2_1.add(Double.parseDouble(lists2_1.get(j).substring(lists2_1.get(j).lastIndexOf(":") + 1, lists2_1.get(j).length())));
                        tot2_1 += Double.parseDouble(lists2_1.get(j).substring(lists2_1.get(j).lastIndexOf(":") + 1, lists2_1.get(j).length()));

                    } else {
                        rList2_1.add(lists2_1.get(j));
                    }
                }
                lists2_1.removeAll(rList2_1);

                for (int i = 0; i < tot_list2_1.size(); i++) {
                    double perc0 = (tot_list2_1.get(i) / tot2_1) * 100;
                    percL2_1.add(Double.valueOf(newFormat.format(perc0)));
                }

                //TODO::::::    Commits.....!
                for (int j = 0; j < lists3_1.size(); j++) {
                    if (lists3_1.get(j).contains(":")) {
                        tot_list3_1.add(Double.parseDouble(lists3_1.get(j).substring(lists3_1.get(j).lastIndexOf(":") + 1, lists3_1.get(j).length())));
                        tot3_1 += Double.parseDouble(lists3_1.get(j).substring(lists3_1.get(j).lastIndexOf(":") + 1, lists3_1.get(j).length()));

                    } else {
                        rList3_1.add(lists3_1.get(j));
                    }
                }
                lists3_1.removeAll(rList3_1);

                for (int i = 0; i < tot_list3_1.size(); i++) {
                    double perc0 = (tot_list3_1.get(i) / tot3_1) * 100;
                    percL3_1.add(Double.valueOf(newFormat.format(perc0)));
                }

                String login_mlp_1 = "", log_percentage1_1 = "", log_category1_1 = "";
                String login_mlp_2 = "", log_percentage1_2 = "", log_category1_2 = "";
                String login_mlp_3 = "", log_percentage1_3 = "";
                MathsFunctions.InsertionSort(lists1_1, percL1_1, tot_list1_1);
                MathsFunctions.InsertionSort(lists2_1, percL2_1, tot_list2_1);
                MathsFunctions.InsertionSort(lists3_1, percL3_1, tot_list3_1);
                int max1_1 = 0;
                double c_mj1_1 = 0, c_mn1_1 = 0;

                int max2_1 = 0;
                double c_mj2_1 = 0, c_mn2_1 = 0;
                if (tot_list1_1.size() > 0) {
                    String cat;
                    for (int i = 0; i < tot_list1_1.size(); i++) {
                        if (max1_1 <= 80) {
                            cat = "Major";
                            c_mj1_1++;
                        } else {
                            c_mn1_1++;
                            cat = "Minor";

                        }

                        if (lists1_1.get(i).contains(":")) {
                            // System.out.println(i+":"+log_list_1.get(i)+"\t"+max1+"\t"+tot_list_1.get(i)+"\t"+cat);
                            login_mlp_1 = login_mlp_1.concat(lists1_1.get(i).substring(0, lists1_1.get(i).lastIndexOf(":")) + ":" + tot_list1_1.get(i) + "/");
                            log_percentage1_1 = log_percentage1_1.concat(lists1_1.get(i).substring(0, lists1_1.get(i).lastIndexOf(":")) + ":" + percL1_1.get(i) + "/");
                            log_category1_1 = log_category1_1.concat(lists1_1.get(i).substring(0, lists1_1.get(i).lastIndexOf(":")) + ":" + cat + "/");

                            log_percentage1_3 = log_percentage1_3.concat(lists3_1.get(i).substring(0, lists3_1.get(i).lastIndexOf(":")) + ":" + percL3_1.get(i) + "/");
                            login_mlp_3 = login_mlp_3.concat(lists3_1.get(i).substring(0, lists3_1.get(i).lastIndexOf(":")) + ":" + tot_list3_1.get(i) + "/");
                        }
                        max1_1 += percL1_1.get(i);
                    }
                }

                if (tot_list2_1.size() > 0) {
                    String cat;
                    for (int i = 0; i < tot_list2_1.size(); i++) {
                        if (max2_1 <= 80) {
                            cat = "Major";
                            c_mj2_1++;
                        } else {
                            c_mn2_1++;
                            cat = "Minor";

                        }

                        if (lists2_1.get(i).contains(":")) {
                            // System.out.println(i+":"+log_list_1.get(i)+"\t"+max1+"\t"+tot_list_1.get(i)+"\t"+cat);
                            login_mlp_2 = login_mlp_2.concat(lists2_1.get(i).substring(0, lists2_1.get(i).lastIndexOf(":")) + ":" + tot_list2_1.get(i) + "/");
                            log_percentage1_2 = log_percentage1_2.concat(lists2_1.get(i).substring(0, lists2_1.get(i).lastIndexOf(":")) + ":" + percL2_1.get(i) + "/");
                            log_category1_2 = log_category1_2.concat(lists2_1.get(i).substring(0, lists2_1.get(i).lastIndexOf(":")) + ":" + cat + "/");

                        }
                        max2_1 += percL2_1.get(i);
                    }
                }

                datas = new Object[]{"", project, Double.parseDouble(nameList.size() + ""), login_mlp_1, log_percentage1_1, log_category1_1, c_mj1_1, c_mn1_1, "", login_mlp_2, log_percentage1_2, log_category1_2, c_mj2_1, c_mn2_1, "", login_mlp_3, log_percentage1_3, "", tot1_1, tot2_1, tot3_1, c_mj2_1 + c_mn2_1};
                allobj.add(datas);
                lists_refrac.remove(0);
                lists_chang.remove(0);
                lists_comm.remove(0);
                //lists_all.remove(0);

                List<String> refracDevList = new ArrayList<>();
                List<String> refracPercList = new ArrayList<>();
                List<String> refracCatList = new ArrayList<>();
                List<Double> refracTot = new ArrayList<>();
                List<Double> refracMaj = new ArrayList<>();
                List<Double> refracMin = new ArrayList<>();

                List<String> changDevList = new ArrayList<>();
                List<String> changPercList = new ArrayList<>();
                List<String> changCatList = new ArrayList<>();
                List<Double> changMaj = new ArrayList<>();
                List<Double> changMin = new ArrayList<>();
                List<Double> changTot = new ArrayList<>();

                List<String> comDevList = new ArrayList<>();
                List<String> comPercList = new ArrayList<>();

                List<Double> comTot = new ArrayList<>();

                for (int i1 = 0; i1 < lists_refrac.size(); i1++) {
                    double tot1_2 = 0;
                    double max1_2 = 0;
                    double c_mj1_2 = 0, c_mn1_2 = 0;
                    String collections1 = "", log_perc1_2 = "", log_cat1_2 = "";

                    List<String> lists1 = lists_refrac.get(i1);
                    List<Double> percL1_2 = new ArrayList<>();
                    List<Double> tot_list1_2 = new ArrayList<>();

                    for (int j = 0; j < lists1.size(); j++) {
                        //System.err.println(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length()));
                        if (lists1.get(j).contains(":")) {
                            tot_list1_2.add(Double.parseDouble(lists1.get(j).substring(lists1.get(j).lastIndexOf(":") + 1, lists1.get(j).length())));
                            tot1_2 += Double.parseDouble(lists1.get(j).substring(lists1.get(j).lastIndexOf(":") + 1, lists1.get(j).length()));

                        } else {
                            rList1_2.add(lists1.get(j));
                        }
                    }
                    lists1.removeAll(rList1_2);

                    for (int i = 0; i < tot_list1_2.size(); i++) {
                        double perc0 = (tot_list1_2.get(i) / tot1_2) * 100;
                        percL1_2.add(Double.valueOf(newFormat.format(perc0)));
                    }
                    MathsFunctions.InsertionSort(lists1, percL1_2, tot_list1_2);
                    if (lists1.size() > 0) {
                        String cat;
                        for (int i = 0; i < tot_list1_2.size(); i++) {
                            if (max1_2 <= 80) {
                                cat = "Major";
                                c_mj1_2++;
                            } else {
                                c_mn1_2++;
                                cat = "Minor";
                            }
                            if (lists1.get(i).contains(":")) {
                                collections1 = collections1.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + tot_list1_2.get(i) + "/");
                                log_perc1_2 = log_perc1_2.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + percL1_2.get(i) + "/");
                                log_cat1_2 = log_cat1_2.concat(lists1.get(i).substring(0, lists1.get(i).lastIndexOf(":")) + ":" + cat + "/");

                            }

                            max1_2 += percL1_2.get(i);
                        }

                    }

                    refracDevList.add(collections1);
                    refracPercList.add(log_perc1_2);
                    refracCatList.add(log_cat1_2);
                    refracMaj.add(c_mj1_2);
                    refracMin.add(c_mn1_2);
                    refracTot.add(tot1_2);
                }
                for (int i1 = 0; i1 < lists_chang.size(); i1++) {
                    double tot2_2 = 0;
                    double max2_2 = 0;
                    double c_mj2_2 = 0, c_mn2_2 = 0;
                    String collections2 = "", log_perc2_2 = "", log_cat2_2 = "";

                    List<String> lists2 = lists_chang.get(i1);
                    List<Double> tot_list2_2 = new ArrayList<>();
                    List<Double> percL2_2 = new ArrayList<>();

                    //TODO::: Changess.....!!!
                    for (int j = 0; j < lists2.size(); j++) {
                        //System.err.println(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length()));
                        if (lists2.get(j).contains(":")) {
                            tot_list2_2.add(Double.parseDouble(lists2.get(j).substring(lists2.get(j).lastIndexOf(":") + 1, lists2.get(j).length())));
                            tot2_2 += Double.parseDouble(lists2.get(j).substring(lists2.get(j).lastIndexOf(":") + 1, lists2.get(j).length()));

                        } else {
                            rList2_2.add(lists2.get(j));
                        }
                    }
                    lists2.removeAll(rList2_2);

                    for (int i = 0; i < tot_list2_2.size(); i++) {
                        double perc0 = (tot_list2_2.get(i) / tot2_2) * 100;
                        percL2_2.add(Double.valueOf(newFormat.format(perc0)));
                    }
                    MathsFunctions.InsertionSort(lists2, percL2_2, tot_list2_2);
                    //Change...
                    if (lists2.size() > 0) {
                        String cat;
                        for (int i = 0; i < tot_list2_2.size(); i++) {
                            if (max2_2 <= 80) {
                                cat = "Major";
                                c_mj2_2++;
                            } else {
                                c_mn2_2++;
                                cat = "Minor";
                            }
                            if (lists2.get(i).contains(":")) {
                                collections2 = collections2.concat(lists2.get(i).substring(0, lists2.get(i).lastIndexOf(":")) + ":" + tot_list2_2.get(i) + "/");
                                log_perc2_2 = log_perc2_2.concat(lists2.get(i).substring(0, lists2.get(i).lastIndexOf(":")) + ":" + percL2_2.get(i) + "/");
                                log_cat2_2 = log_cat2_2.concat(lists2.get(i).substring(0, lists2.get(i).lastIndexOf(":")) + ":" + cat + "/");

                            }

                            max2_2 += percL2_2.get(i);
                        }

                    }

                    changDevList.add(collections2);
                    changPercList.add(log_perc2_2);
                    changCatList.add(log_cat2_2);
                    changMaj.add(c_mj2_2);
                    changMin.add(c_mn2_2);
                    changTot.add(tot2_2);
                }
                for (int i1 = 0; i1 < lists_comm.size(); i1++) {
                    double tot3_2 = 0;
                    String com_collect = "", com_perc = "";
                    List<String> lists3 = lists_comm.get(i1);

                    List<Double> percL3_2 = new ArrayList<>();
                    List<Double> tot_list3_2 = new ArrayList<>();

                    //TODO:::::: Commits.....!!!!!
                    for (int j = 0; j < lists3.size(); j++) {
                        //System.err.println(lists.get(j).substring(lists.get(j).lastIndexOf(":") + 1, lists.get(j).length()));
                        if (lists3.get(j).contains(":")) {
                            tot_list3_2.add(Double.parseDouble(lists3.get(j).substring(lists3.get(j).lastIndexOf(":") + 1, lists3.get(j).length())));
                            tot3_2 += Double.parseDouble(lists3.get(j).substring(lists3.get(j).lastIndexOf(":") + 1, lists3.get(j).length()));

                        } else {
                            rList3_2.add(lists3.get(j));
                        }
                    }
                    lists3.removeAll(rList3_2);

                    for (int i = 0; i < tot_list3_2.size(); i++) {
                        double perc0 = (tot_list3_2.get(i) / tot3_2) * 100;
                        percL3_2.add(Double.valueOf(newFormat.format(perc0)));
                    }

                    MathsFunctions.InsertionSort(lists3, percL3_2, tot_list3_2);

                    for (int i = 0; i < tot_list3_2.size(); i++) {

                        if (lists3.get(i).contains(":")) {
                            com_collect = com_collect.concat(lists3.get(i).substring(0, lists3.get(i).lastIndexOf(":")) + ":" + tot_list3_2.get(i) + "/");
                            com_perc = com_perc.concat(lists3.get(i).substring(0, lists3.get(i).lastIndexOf(":")) + ":" + percL3_2.get(i) + "/");

                        }

                    }

                    comDevList.add(com_collect);
                    comPercList.add(com_perc);
                    comTot.add(tot3_2);

                }

                for (int i = 0; i < nameList.size(); i++) {
                    datas = new Object[]{Double.parseDouble((i + 1) + ""), "", nameList.get(i),
                        refracDevList.get(i), refracPercList.get(i), refracCatList.get(i), refracMaj.get(i), refracMin.get(i), "",
                        changDevList.get(i), changPercList.get(i), changCatList.get(i), changMaj.get(i), changMin.get(i), "",
                        comDevList.get(i), comPercList.get(i), "", refracTot.get(i), changTot.get(i), comTot.get(i), refracMaj.get(i) + refracMin.get(i)};
                    allobj.add(datas);
                }

                ///Refrac....
                String file_name = files_google[a].replaceAll("refrac_merged_dev", "refrac_merged_final");
                Create_Excel.createExcel(allobj, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_index);

                sheet_index++;
            }

        }
    }
}
