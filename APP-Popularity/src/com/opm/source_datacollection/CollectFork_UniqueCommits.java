/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.excel_.Create_Excel;
import com.opm.core.DateOperations;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import com.opm.read_gitrepos.ReadReposCommits;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.opm.read_gitrepos.Read_ReposCreation_Date;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class CollectFork_UniqueCommits {

    public static void main(String[] args) throws Exception {
        indentify();
    }

    /**
     *
     * @throws Exception
     */
    private static void indentify() throws Exception {
        ////String toDay = "2017-07-06T00:00:00Z";
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        String file1 = "repos_forks_new2.xlsx";
        
        String path = "";
        String path_new = "";
        String[] FILES = {file1};
        int ct = 0;
        int sheet_count = 0;
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            int count = 0;

            while (count < numbers) {

                int flag = 0;
                String project = File_Details.setProjectName(path + FILES[a], count, "B2");
                //String package_ = File_Details.setProjectName(path + FILES[a], count, "C2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 2, 2);
                List<String> createList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 4, 2);
                List<String> firstList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 5, 2);
                List<String> lastList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 6, 2);
                String sheet = File_Details.getWorksheetName(path + FILES[a], count);

                String min_date = DateOperations.sorts(createList, lastList).split("/")[0];
                String max_date = DateOperations.sorts(createList, lastList).split("/")[1];

                List<List<List<String>>> lists = new ArrayList<>();

                System.out.println(count + " \t" + project);

                // System.out.println(count+" : "+min_date);
                List<List<String>> allList_1 = ReadReposCommits.count(project, "mlp", min_date, max_date, tokens, ct);
                List<String> shaList_1 = allList_1.get(0);
                List<String> dateList_1 = allList_1.get(1);
                List<String> messageList_1 = allList_1.get(2);
                
                
                
                String pFirst = "date",pLast = "date";
                String dateOBJ = Read_ReposCreation_Date.creation(project, tokens, ct);
                String created = dateOBJ.split("/")[0];
                ct = Integer.parseInt(dateOBJ.split("/")[1]);
                
                if (dateList_1.size()>0) {
                    pLast = dateList_1.get(0);
                    pFirst = dateList_1.get(dateList_1.size()-1);
                }

                System.out.println(count + " :ML " + shaList_1.size());
                String shaa_mlp = "";
                for (int i = 0; i < shaList_1.size(); i++) {
                    shaa_mlp = shaa_mlp.concat(shaList_1.get(i) + "/");
                }

                for (int i = 0; i < nameList.size(); i++) {
                    List<List<String>> allList = ReadReposCommits.count(nameList.get(i), "fp", createList.get(i), lastList.get(i), tokens, ct);
                    lists.add(allList);
                    List<String> modelL = allList.get(allList.size() - 1);
                    ct = Integer.parseInt(modelL.get(modelL.size() - 1));
                    //System.out.println(allList.get(0).size() + "\t" + allList.get(0));
                    //System.out.println(allList.get(1).size() + "\t" + allList.get(1));
                    //System.out.println(allList.get(2).size() + "\t" + allList.get(2));
                }

                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"N.s", "MLP", "FP","Created_at","FirstCom","LastCom" ,"COM", "UNIQUE", "VIP", "SCATTERED", "PERVASIVE", "Times", "Shaas"
                };// end of assigning the header to the object..
                allobj.add(datas);
                datas = new Object[]{"", project, "",created,pFirst,pLast,Double.parseDouble(shaList_1.size()+""), "", "", "", "", "", shaa_mlp};
                allobj.add(datas);

                List< ArrayList< Object[]>> obj_list = new ArrayList<>();
                for (int b = 0; b < nameList.size(); b++) {
                    List<List<String>> allList_3 = lists.get(b);
                    List<String> shaList_3 = allList_3.get(0);
                    List<String> dateList_3 = allList_3.get(1);
                    List<String> messageList_3 = allList_3.get(2);

                    System.err.println("         " + b + "  : " + nameList.get(b) + "\t" + shaList_3.size());

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
                                List<String> dateList = allList2.get(1);
                                List<String> messageList = allList2.get(2);
                                if (shaList.contains(shaList_3.get(c))) {
                                    c_shas++;
                                    sha_unique.add(i);
                                    fp++;
                                }
                                /**
                                 * for (int j = 0; j < shaList.size(); j++) { if
                                 * (shaList.get(j).equals(shaList_3.get(c))) {
                                 * c_shas++; fp ++; } } **
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

                    if (total_unique > 0) {
                        flag++;
                        datas = new Object[]{Double.parseDouble(b + ""), "", nameList.get(b),createList.get(b),firstList.get(b),lastList.get(b), Double.parseDouble(shaList_3.size() + ""), total_unique, total_vip, total_scattered, total_pervasive, Double.parseDouble(sha_unique.size() + ""), sha_collections};
                        allobj.add(datas);
                    }

                }
                if (flag > 0) {
                    String f_name = FILES[a].replaceAll("repos_forks_new2", "repos_forks_unique_new2");
                    Create_Excel.createExcel2(allobj, 0, path_new + f_name, project.split("/")[0]+"_"+sheet_count);
                    sheet_count ++;
                }

                count++;
            }
        }
    }
}
