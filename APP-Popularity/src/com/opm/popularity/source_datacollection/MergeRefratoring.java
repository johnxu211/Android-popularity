/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_datacollection;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.core.MathsFunctions;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class MergeRefratoring {

    /**
     * *
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        DecimalFormat newFormat = new DecimalFormat("#.##");
        Object[] datas = null;
        String[] token = Constants.getToken();
        int ct = 0;

        String file1 = "stats_refrac_dev.xlsx";
        String category2 = "forks_category_400-800.xlsx";
        String category3 = "forks_category_800-1200.xlsx";
        String category4 = "forks_category_1200-1600.xlsx";
        String category5 = "forks_category_1600-1805.xlsx";

        String path = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        //todo:
        String[] FILES = {file1};

        for (int a = 0; a < FILES.length; a++) {
            int total_sheets = File_Details.getWorksheets(path + FILES[a]);
            int sheet_index = 0;
            List<String> pList = new ArrayList<>();
            List<String> fList = new ArrayList<>();
            List<List<String>> comList = new ArrayList<>();
            List<List<String>> devList = new ArrayList<>();
            List<List<Double>> changeList = new ArrayList<>();
            List<List<Double>> refracList = new ArrayList<>();

            while (sheet_index < total_sheets) {

                String project = File_Details.setProjectName(path + FILES[a], sheet_index, "A2");
                String fork = File_Details.setProjectName(path + FILES[a], sheet_index, "B2");
                List<String> commits = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 2, 2);
                List<Double> refrac = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 3, 2);
                List<String> dev = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 4, 2);
                List<Double> changes = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 5, 2);

                System.out.println(sheet_index + " : " + project + "\t" + fork);
                pList.add(project);
                fList.add(fork);
                comList.add(commits);
                devList.add(dev);
                refracList.add(refrac);
                changeList.add(changes);
                sheet_index++;
            }

            List<String> mlp = new ArrayList<>();
            //List<String> fp = new ArrayList<>();

            List<List<String>> com_List1 = new ArrayList<>();
            List<List<String>> dev_List1 = new ArrayList<>();
            List<List<Double>> change_List1 = new ArrayList<>();
            List<List<Double>> refrac_List1 = new ArrayList<>();

            for (int i = 0; i < pList.size(); i++) {
                List<Double> dList = refracList.get(i);
                if (fList.get(i).equals("")) {
                    mlp.add(pList.get(i));
                    //TODO::: ........ 
                    com_List1.add(comList.get(i));
                    dev_List1.add(devList.get(i));
                    refrac_List1.add(refracList.get(i));
                    change_List1.add(changeList.get(i));
                }
            }
            //pList.removeAll(mlp);
            //comList.removeAll(com_List1);
            //devList.removeAll(dev_List1);
            //refracList.removeAll(refrac_List1);
            //changeList.removeAll(change_List1);

            for (int i = 0; i < mlp.size(); i++) {
                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"N/S", "MLP", "Forks", "Dev_Refrac", "Dev_Changes", "Dev_Com", "Dev_Percentage", "Dev_Cat", "Tot_Refrac", "Tot_Changes", "Tot_Com", "Major", "Minor", "Tot_Dev"};// end of assigning the header to the object..
                allobj.add(datas);
                List<String> fp = new ArrayList<>();
                List<List<String>> com_List = new ArrayList<>();
                List<List<String>> dev_List = new ArrayList<>();
                List<List<Double>> change_List = new ArrayList<>();
                List<List<Double>> refrac_List = new ArrayList<>();
                for (int j = 0; j < pList.size(); j++) {
                    if (mlp.get(i).equals(pList.get(j)) && !fList.get(j).equals("")) {
                        fp.add(fList.get(j));
                        com_List.add(comList.get(j));
                        dev_List.add(devList.get(j));
                        change_List.add(changeList.get(j));
                        refrac_List.add(refracList.get(j));
                    }
                }
                ///TODO::: MAIN LINE STARTS HERE>>>>>>>>>>>>>>>>>
                Set<String> devSet = new LinkedHashSet<>();
                List<String> dList1 = new ArrayList<>();

                for (int j = 0; j < com_List1.get(i).size(); j++) {
                    devSet.add(dev_List1.get(i).get(j));
                }
                Iterator devIterator = devSet.iterator();
                while (devIterator.hasNext()) {
                    dList1.add((String) devIterator.next());
                }
                
                double tot0 = 0;
                List<Double> cList0 = new ArrayList<>();
                List<Double> rList0 = new ArrayList<>();
                List<Double> chList0 = new ArrayList<>();
                for (int j = 0; j < dList1.size(); j++) {
                    double refrac = 0;
                    double chang = 0;
                    double com = 0;
                    for (int k = 0; k < com_List1.get(i).size(); k++) {
                        if (dList1.get(j).equals(dev_List1.get(i).get(k))) {
                            refrac += refrac_List1.get(i).get(k);
                            chang += change_List1.get(i).get(k);
                            com += 1;
                        }
                    }
                    

                    tot0 += refrac;
                    cList0.add(com);
                    rList0.add(refrac);
                    chList0.add(chang);

                    
                }
                List<Double> perc_0 = new ArrayList<>();
                for (int k = 0; k < dList1.size(); k++) {
                    perc_0.add(Double.valueOf(newFormat.format((rList0.get(k) / tot0) * 100)));
                }
                MathsFunctions.InsertionSort3(dList1, perc_0, rList0, chList0, cList0);
                double max0 = 0;
                String mlp_devRefrac = "", mlp_devChang = "", mlp_devCom = "", mlp_devPerc = "", mlp_devCat = "";
                double tot_refrac = 0;
                double tot_chang = 0;
                double tot_com = 0;
                double mj0 = 0;
                double mn0 = 0;
                for (int j = 0; j < dList1.size(); j++) {
                    String cat = "";
                    tot_refrac += rList0.get(j);
                    tot_chang += chList0.get(j);
                    tot_com += cList0.get(j);
                    if (max0 <= 80) {
                        mj0 ++;
                        cat = "Major";
                    }else{
                        mn0 ++;
                        cat = "Minor";
                    }
                    mlp_devRefrac = mlp_devRefrac.concat(dList1.get(j) + ":" + rList0.get(j) + "/");
                    mlp_devChang = mlp_devChang.concat(dList1.get(j) + ":" + chList0.get(j) + "/");
                    mlp_devCom = mlp_devCom.concat(dList1.get(j) + ":" + cList0.get(j) + "/"); 
                    mlp_devPerc = mlp_devPerc.concat(dList1.get(j) + ":" + perc_0.get(j) + "/"); 
                    mlp_devCat = mlp_devCat.concat(dList1.get(j) + ":" + cat + "/"); 
                    max0 += perc_0.get(j);
                }

                datas = new Object[]{"", mlp.get(i), Double.parseDouble(fp.size() + ""), mlp_devRefrac, mlp_devChang, mlp_devCom, mlp_devPerc, mlp_devCat, tot_refrac, tot_chang, tot_com, mj0, mn0, mj0+mn0};// end of assigning the header to the object..
                allobj.add(datas);
                ///TODO::: MAIN LINE STOPS HERE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                
                ///TODO::: FORK PROJECT STARTS HERE >>>>>>>>>>>>>>>>>>>>>>>>>>>>
                for (int j = 0; j < fp.size(); j++) {
                    Set<String> devSet2 = new LinkedHashSet<>();
                    List<String> dList2 = new ArrayList<>();
                    for (int k = 0; k < com_List.get(j).size(); k++) {
                        devSet2.add(dev_List.get(j).get(k));
                    }
                    Iterator devIt = devSet2.iterator();
                    while (devIt.hasNext()) {
                        dList2.add((String) devIt.next());
                    }

                    List<Double> cList1 = new ArrayList<>();
                    List<Double> rList1 = new ArrayList<>();
                    List<Double> chList1 = new ArrayList<>();
                    double tot1 = 0;

                    for (int k = 0; k < dList2.size(); k++) {
                        double refrac = 0;
                        double chang = 0;
                        double com = 0;
                        for (int l = 0; l < com_List.get(j).size(); l++) {
                            if (dList2.get(k).equals(dev_List.get(j).get(l))) {
                                refrac += refrac_List.get(j).get(l);
                                chang += change_List.get(j).get(l);
                                com += 1;
                            }
                        }

                        cList1.add(com);
                        rList1.add(refrac);
                        chList1.add(chang);
                        tot1 += refrac;
                    }
                    List<Double> perc_1 = new ArrayList<>();
                    for (int k = 0; k < dList2.size(); k++) {
                        perc_1.add(Double.valueOf(newFormat.format((rList1.get(k) / tot1) * 100)));
                    }
                    MathsFunctions.InsertionSort3(dList2, perc_1, rList1, chList1, cList1);
                    double tot_refrac1 = 0;
                    double tot_chang1 = 0;
                    double tot_com1 = 0;
                    double max1 = 0;
                    double mj1 = 0;
                    double mn1 = 0;
                    String fp_devRefrac = "", fp_devChang = "", fp_devCom = "", fp_devPerc = "", fp_devCat = "";

                    for (int k = 0; k < dList2.size(); k++) {
                        String cat = "";
                        if (max1 <= 80) {
                            mj1++;
                            cat = "Major";
                        } else {
                            mn1++;
                            cat = "Minor";
                        }
                        tot_chang1 += chList1.get(k);
                        tot_refrac1 += rList1.get(k);
                        tot_com1 += cList1.get(k);
                        fp_devRefrac = fp_devRefrac.concat(dList2.get(k) + ":" + rList1.get(k) + "/");
                        fp_devChang = fp_devChang.concat(dList2.get(k) + ":" + chList1.get(k) + "/");
                        fp_devCom = fp_devCom.concat(dList2.get(k) + ":" + cList1.get(k) + "/");
                        fp_devPerc = fp_devPerc.concat(dList2.get(k) + ":" + perc_1.get(k) + "/");
                        fp_devCat = fp_devCat.concat(dList2.get(k) + ":" + cat + "/");
                        max1 += perc_1.get(k);
                    }
                    datas = new Object[]{Double.parseDouble((j + 1) + ""), "", fp.get(j), fp_devRefrac, fp_devChang, fp_devCom, fp_devPerc, fp_devCat, tot_refrac1, tot_chang1, tot_com1, mj1, mn1, mj1 + mn1};// end of assigning the header to the object..
                    allobj.add(datas);
                }
                ///TODO::: FORK PROJECT STOPS HERE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                String f_name = FILES[a].replaceAll("stats_refrac_dev", "refrac_merged_dev");
                Create_ExcelFile.createExcel(allobj, 0, path_new + f_name, mlp.get(i).split("/")[0] + "_" + i);
            }

        }
    }
}
