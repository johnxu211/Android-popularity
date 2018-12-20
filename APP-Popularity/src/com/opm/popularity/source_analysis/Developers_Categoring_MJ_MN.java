/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.core.MathsFunctions;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import com.opm.popularity.read_gitrepos.ReadReposCommits;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Developers_Categoring_MJ_MN {
    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        appDetails();
    }

    private static void appDetails() throws Exception {

        DecimalFormat newFormat = new DecimalFormat("#.##");

        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;

        String file_google1 = "repos_fdetails_unique_2.xlsx";

        String[] fork_package = {file_google1};

        //todo:
        //String path_package = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/";
        //String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/tests2/";
        String path_package = "";
        String path_new = "";

        //todo:
        Connection.Response res = null;
        Document doc = null;
        Boolean OK = true;
        int start = 0;

        int s_count = 0;
        for (int aa = 0; aa < fork_package.length; aa++) {

            try {   //first connection with GET request

                int numbers = File_Details.getWorksheets(path_package + fork_package[aa]);
                int count = 195;

                while (count < numbers) {
                    ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                    datas = new Object[]{"N.S", "MLP", "FP", "Com", "Unique", "Shaa", "Commits", "Contribution", "Category", "Percentage", "Major", "Minor", "Total"};
                    allobj.add(datas);

                    List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path_package + fork_package[aa], count, 2, 2);

                    List<String> firstList = ReadExcelFile_1Column.readColumnAsString(path_package + fork_package[aa], count, 4, 2);
                    List<String> lastList = ReadExcelFile_1Column.readColumnAsString(path_package + fork_package[aa], count, 5, 2);

                    List<Double> commits = ReadExcelFile_1Column.readColumnAsNumeric(path_package + fork_package[aa], count, 6, 1);
                    List<Double> unique = ReadExcelFile_1Column.readColumnAsNumeric(path_package + fork_package[aa], count, 7, 2);
                    String project = File_Details.setProjectName(path_package + fork_package[aa], count, "B2");
                    String pFirst = File_Details.setProjectName(path_package + fork_package[aa], count, "E2");
                    String pCreate = File_Details.setProjectName(path_package + fork_package[aa], count, "D2");
                    String pLast = File_Details.setProjectName(path_package + fork_package[aa], count, "F2");
                    double pCom = commits.get(0);
                    commits.remove(0);

                    String sheet = File_Details.getWorksheetName(path_package + fork_package[aa], count);

                    String min_date = DateOperations.sorts(firstList, lastList).split("/")[0];
                    String max_date = DateOperations.sorts(firstList, lastList).split("/")[1];
                    String maxF_date = DateOperations.sorts(firstList, firstList).split("/")[1];

                  
                    
                    List<List<List<String>>> lists = new ArrayList<>();
                    // System.out.println(count+" : "+min_date);

                    List<List<String>> allList_1 = ReadReposCommits.countDetails2(project, "mlp", pFirst, max_date, tokens, ct);
                    List<String> shaList_1 = allList_1.get(0);
                    List<String> emailList_1 = allList_1.get(1);
                    List<String> loginList_1 = allList_1.get(2);
                    List<String> detailList_1 = allList_1.get(3);
                    List<String> dateList_1 = allList_1.get(4);
                    System.out.println("MLP:  " + project + "\t" + shaList_1.size());

                    Set<String> loginSet_1 = new LinkedHashSet<String>();
                    List<Double> tot_list_1 = new ArrayList<>();
                    List<Double> com_list_1 = new ArrayList<>();
                    for (int i = 0; i < loginList_1.size(); i++) {
                        loginSet_1.add(loginList_1.get(i));
                    }
                    Iterator iterator_1 = loginSet_1.iterator();
                    List<String> log_list_1 = new ArrayList<>();
                    while (iterator_1.hasNext()) {
                        double cc = 0;
                        log_list_1.add((String) iterator_1.next());
                        tot_list_1.add(cc);
                        com_list_1.add(cc);
                    }

                    String login_mlp = "", log_percentage_1 = "", log_category_1 = "", log_com = "";
                    for (int i = 0; i < shaList_1.size(); i++) {

                        if (log_list_1.contains(loginList_1.get(i))) {
                            int index = log_list_1.indexOf(loginList_1.get(i));
                            if (!detailList_1.get(i).equals("")) {
                                tot_list_1.set(index, tot_list_1.get(index) + Double.parseDouble(detailList_1.get(i).split("/")[4]));
                            }
                            com_list_1.set(index, com_list_1.get(index) + 1);
                        }
                    }

                    int tot1 = 0;
                    for (int i = 0; i < tot_list_1.size(); i++) {
                        tot1 += tot_list_1.get(i);
                    }
                    List<Double> percL_1 = new ArrayList<>();
                    for (int i = 0; i < tot_list_1.size(); i++) {
                        double perc0 = (tot_list_1.get(i) / tot1) * 100;
                        percL_1.add(Double.valueOf(newFormat.format(perc0)));
                    }
                    MathsFunctions.InsertionSort2(log_list_1, percL_1, tot_list_1, com_list_1);
                    int max1 = 0;
                    double c_mj1 = 0, c_mn1 = 0;

                    if (tot_list_1.size() > 0) {
                        String cat;
                        for (int i = 0; i < tot_list_1.size(); i++) {
                            if (i == 0) {
                                cat = "Major";
                                c_mj1++;
                            } else {
                                if (max1 <= 80) {
                                    cat = "Major";
                                    c_mj1++;
                                } else {
                                    c_mn1++;
                                    cat = "Minor";

                                }
                            }

                            // System.out.println(i+":"+log_list_1.get(i)+"\t"+max1+"\t"+tot_list_1.get(i)+"\t"+cat);
                            login_mlp = login_mlp.concat(log_list_1.get(i) + ":" + tot_list_1.get(i) + "/");
                            log_percentage_1 = log_percentage_1.concat(log_list_1.get(i) + ":" + percL_1.get(i) + "/");
                            log_category_1 = log_category_1.concat(log_list_1.get(i) + ":" + cat + "/");
                            log_com = log_com.concat(log_list_1.get(i) + ":" + com_list_1.get(i) + "/");

                            max1 += percL_1.get(i);
                        }

                    }
                    //String shaa_mlp = "";
                    //shaa1.removeAll(shaa2);
                    //System.out.println(maxF_date+"\tCOUNTS: "+shaa2.size()+"\t"+dateList_1.get(0));

                    //for (int i = 0; i < shaa1.size(); i++) {
                      //  shaa_mlp = shaa_mlp.concat(shaa1.get(i) + ":Unique/");
                   // }

                    
                    List<String> shaas = new ArrayList<>();
                    for (int i = 0; i < nameList.size(); i++) {
                        List<List<String>> allList = ReadReposCommits.countDetails2(nameList.get(i), "fp", firstList.get(i), lastList.get(i), tokens, ct);
                        List<String> shaList_3 = allList.get(0);

                        for (int j = 0; j < shaList_3.size(); j++) {
                            shaas.add(shaList_3.get(j));
                        }
                        lists.add(allList);
                    }

                    double unique_mlp = 0, merged = 0;
                    String unique_shaa = "";
                    for (int i = 0; i < shaList_1.size(); i++) {
                        int ccc_s = 0;
                        for (int j = 0; j < shaas.size(); j++) {
                            if (shaList_1.get(i).equals(shaas.get(j))) {
                                ccc_s ++;
                            }
                        }
                        if (ccc_s == 0) {
                            unique_mlp ++;
                            unique_shaa = unique_shaa.concat(shaList_1.get(i)+":Unique/");
                        }
                        
                    }
                    datas = new Object[]{"", project, Double.parseDouble(nameList.size() + ""), Double.parseDouble(shaList_1.size() + ""), unique_mlp, unique_shaa, log_com, login_mlp, log_category_1, log_percentage_1, c_mj1, c_mn1, Double.parseDouble(tot_list_1.size() + "")};
                    allobj.add(datas);
                    for (int b = 0; b < nameList.size(); b++) {
                        List<List<String>> allList_3 = lists.get(b);
                        List<String> shaList_3 = allList_3.get(0);
                        List<String> emailList_3 = allList_3.get(1);
                        List<String> loginList_3 = allList_3.get(2);
                        List<String> detailList_3 = allList_3.get(3);
                        Set<String> loginSet_3 = new LinkedHashSet<String>();

                        for (int i = 0; i < loginList_3.size(); i++) {
                            loginSet_3.add(loginList_3.get(i));

                        }

                        Iterator iterator = loginSet_3.iterator();
                        List<String> log_list3 = new ArrayList<>();
                        List<Double> tot_list = new ArrayList<>();
                        List<Double> com_list = new ArrayList<>();
                        while (iterator.hasNext()) {
                            log_list3.add((String) iterator.next());
                            double cc = 0;
                            tot_list.add(cc);
                            com_list.add(cc);
                        }
                        double total_unique = 0, total_vip = 0, total_scattered = 0, total_pervasive = 0, num_times = 0, total_main = 0;
                        String sha_collections = "";

                        Set<Integer> sha_unique = new LinkedHashSet<Integer>();
                        for (int c = 0; c < shaList_3.size(); c++) {
                            int c_shas = 0;
                            int fp = 0, mlp = 0;
                            for (int i = 0; i < lists.size(); i++) {
                                if (i != b) {
                                    List<List<String>> allList2 = lists.get(i);
                                    //for (int y = 0; y < lists.get(i).size(); y++) {
                                    List<String> shaList = allList2.get(0);
                                    List<String> emailList = allList2.get(1);
                                    List<String> loginList = allList2.get(2);

                                    if (shaList.contains(shaList_3.get(c))) {
                                        c_shas++;
                                        sha_unique.add(i);
                                        fp++;
                                    }
                                    /**
                                     * for (int j = 0; j < shaList.size(); j++)
                                     * { if
                                     * (shaList.get(j).equals(shaList_3.get(c)))
                                     * { c_shas++; fp ++; } } **
                                     */
                                }
                            }
                            if (shaList_1.contains(shaList_3.get(c))) {
                                c_shas++;
                                mlp++;
                            }
                            String cat_ = "";
                            if (c_shas == 0) {
                                total_unique++;
                                cat_ = "Unique";
                                sha_collections = sha_collections.concat(shaList_3.get(c) + ":" + cat_ + "/");

                                if (log_list3.contains(loginList_3.get(c))) {
                                    int index = log_list3.indexOf(loginList_3.get(c));
                                    if (!detailList_3.get(c).equals("")) {
                                        tot_list.set(index, tot_list.get(index) + Double.parseDouble(detailList_3.get(c).split("/")[4]));
                                    }
                                    com_list.set(index, com_list.get(index) + 1);
                                }

                            } else if (fp > 0 && mlp > 0 || mlp > 0 && fp == 0) {
                                total_vip++;
                                cat_ = "vip";
                            } else if (fp > 0 && mlp == 0) {
                                total_scattered++;
                                cat_ = "scattered";
                            } else if ((sha_unique.size() + 1) == lists.size() && mlp == 0) {
                                total_pervasive++;
                                cat_ = "pervasive";
                            }

                            //num_times += sha_unique.size();
                        }
                        double emp = 0;
                        List<Double> ind_1 = new ArrayList<>();
                        List<String> ind_2 = new ArrayList<>();

                        for (int i = 0; i < tot_list.size(); i++) {
                            if (tot_list.get(i).equals(emp)) {
                                ind_1.add(tot_list.get(i));
                                ind_2.add(log_list3.get(i));

                            }
                        }

                        //System.out.println(log_list3.size() + "\t" + tot_list.size());
                        tot_list.removeAll(ind_1);
                        log_list3.removeAll(ind_2);
                        int tot2 = 0;
                        List<Double> percL_2 = new ArrayList<>();
                        for (int i = 0; i < tot_list.size(); i++) {
                            tot2 += tot_list.get(i);
                        }

                        for (int i = 0; i < tot_list.size(); i++) {
                            double perc0 = (tot_list.get(i) / tot2) * 100;
                            percL_2.add(Double.valueOf(newFormat.format(perc0)));
                        }
                        MathsFunctions.InsertionSort2(log_list3, percL_2, tot_list, com_list);
                        int max2 = 0;
                        double c_mj2 = 0, c_mn2 = 0;
                        String email_collections = "", log_percentage_2 = "", log_category_2 = "";
                        String com_collections2 = "";
                        if (tot_list.size() > 0) {
                            String cat;
                            for (int i = 0; i < tot_list.size(); i++) {
                                if (i == 0) {
                                    cat = "Major";
                                    c_mj2++;
                                } else {
                                    if (max2 <= 80) {
                                        cat = "Major";
                                        c_mj2++;
                                    } else {
                                        c_mn2++;
                                        cat = "Minor";
                                    }
                                }
                                email_collections = email_collections.concat(log_list3.get(i) + ":" + tot_list.get(i) + "/");
                                log_percentage_2 = log_percentage_2.concat(log_list3.get(i) + ":" + percL_2.get(i) + "/");
                                com_collections2 = com_collections2.concat(log_list3.get(i) + ":" + com_list.get(i) + "/");
                                log_category_2 = log_category_2.concat(log_list3.get(i) + ":" + cat + "/");

                                max2 += percL_2.get(i);
                            }

                        }
                        //System.out.println(n_tot);
//                        double tot = Double.parseDouble(shaList_3.size() + "") + n_tot;
                        datas = new Object[]{Double.parseDouble((b + 1) + ""), "", nameList.get(b), Double.parseDouble(shaList_3.size() + ""), total_unique, sha_collections, com_collections2, email_collections, log_category_2, log_percentage_2, c_mj2, c_mn2, Double.parseDouble(log_list3.size() + "")};
                        allobj.add(datas);
                    }
                    String f_name = "repos_fdetails_dev_mj_mn2.xlsx";
                    Create_ExcelFile.createExcel2(allobj, 0, path_new + f_name, sheet);
                    //Create_Excel.createExcel2(allobj, 0, path_new + f_name, "google_play");
                    count++;
                }
            } catch (Exception ex) {
                // some exception handling here
                ex.printStackTrace();
            }
        }
    }
}
