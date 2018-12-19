/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Generalized_Refracoring {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        String[] token = Constants.getToken();
        int ct = 0;

        String google1 = "googleplay_com_dev_cd.xlsx";

        String refac1 = "refrac_merged_final.xlsx";
        String category2 = "forks_category_400-800.xlsx";
        String category3 = "forks_category_800-1200.xlsx";
        String category4 = "forks_category_1200-1600.xlsx";
        String category5 = "forks_category_1600-1805.xlsx";

        String path = "/Users/john/Desktop/";
        String path_refac = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/statistics/";
        //todo:
        String[] FILES = {google1};
        String[] FILES_REFAC = {refac1};
        List<String> pList = new ArrayList<>();
        List<List<String>> nameList = new ArrayList<>();
        List<List<String>> changeList = new ArrayList<>();
        List<List<String>> uniqueList = new ArrayList<>();
        List<List<Double>> comitsList = new ArrayList<>();

        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            int sheet_index = 0;
            while (sheet_index < numbers) {
                String project = File_Details.setProjectName(path + FILES[a], sheet_index, "B2");
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 2, 2);
                List<String> changed = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 7, 1);
                List<String> uComits = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], sheet_index, 6, 1);
                List<Double> comits = ReadExcelFile_1Column.readColumnAsNumeric(path + FILES[a], sheet_index, 3, 1);
                System.out.println(sheet_index + " : " + project);

                pList.add(project);
                changeList.add(changed);
                uniqueList.add(uComits);
                comitsList.add(comits);
                nameList.add(name);
                sheet_index++;
            }
        }
        for (int a = 0; a < FILES_REFAC.length; a++) {

            int numbers = File_Details.getWorksheets(path_refac + FILES_REFAC[a]);
            int sheet_index = 0;
            while (sheet_index < numbers) {
                ArrayList< Object[]> DataSets = new ArrayList<Object[]>();
                datas = new Object[]{"N/S", "MLP", "Forks", "Gen_Com", "Gen_Change", "", "Refac_Com", "Refac_Changed", "Refactor", "Dev_Gen", "Dev_Refac", "Dev_Both"};// end of assigning the header to the object..
                DataSets.add(datas);

                String project = File_Details.setProjectName(path_refac + FILES_REFAC[a], sheet_index, "B2");
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path_refac + FILES_REFAC[a], sheet_index, 2, 2);
                List<String> refacDev = ReadExcelFile_1Column.readColumnAsString(path_refac + FILES_REFAC[a], sheet_index, 3, 1);
                List<String> changDev = ReadExcelFile_1Column.readColumnAsString(path_refac + FILES_REFAC[a], sheet_index, 9, 1);
                List<String> comDev = ReadExcelFile_1Column.readColumnAsString(path_refac + FILES_REFAC[a], sheet_index, 15, 1);
                List<Double> refactor = ReadExcelFile_1Column.readColumnAsNumeric(path_refac + FILES_REFAC[a], sheet_index, 18, 1);
                List<Double> change = ReadExcelFile_1Column.readColumnAsNumeric(path_refac + FILES_REFAC[a], sheet_index, 19, 1);
                List<Double> comits = ReadExcelFile_1Column.readColumnAsNumeric(path_refac + FILES_REFAC[a], sheet_index, 20, 1);

                System.out.println(sheet_index + " : " + project);

                if (pList.contains(project)) {
                    int index = pList.indexOf(project);
                    List<String> cList = changeList.get(index);
                    List<String> uList = uniqueList.get(index);
                    List<String> nList = nameList.get(index);
                    List<Double> comList = comitsList.get(index);

                    Set<String> dev_totSet_0 = new LinkedHashSet<>();
                    Set<String> dev_beforeSet_0 = new LinkedHashSet<>();
                    Set<String> dev_afterSet_0 = new LinkedHashSet<>();
                    List<String> devChList1 = new ArrayList<>();
                    List<String> devCList1 = new ArrayList<>();
                    List<String> devChList2 = new ArrayList<>();
                    List<String> devCList2 = new ArrayList<>();
                    List<String> devRList2 = new ArrayList<>();
                    double tot_change = 0, tot_com = 0;
                    for (int i = 0; i < 1; i++) {
                        String[] splits_ch = cList.get(i).split("/");
                        String[] splits_com = uList.get(i).split("/");
                        for (int j = 0; j < splits_ch.length; j++) {
                            if (splits_ch[j].contains(":")) {
                                devCList1.add(splits_ch[j]);
                                devCList1.add(splits_com[j]);
                                tot_change += Double.parseDouble(splits_ch[j].substring(splits_ch[j].lastIndexOf(":") + 1, splits_ch[j].length()));
                                tot_com += Double.parseDouble(splits_com[j].substring(splits_com[j].lastIndexOf(":") + 1, splits_com[j].length()));
                                dev_beforeSet_0.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                                dev_totSet_0.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                            }

                        }

                    }
                    for (int i = 0; i < 1; i++) {
                        String[] splits_ch = changDev.get(i).split("/");
                        String[] splits_com = comDev.get(i).split("/");
                        String[] splits_ref = refacDev.get(i).split("/");
                        for (int j = 0; j < splits_ch.length; j++) {
                            if (splits_ch[j].contains(":")) {
                                devCList2.add(splits_ch[j]);
                                devCList2.add(splits_com[j]);
                                devRList2.add(splits_ref[j]);
                                dev_afterSet_0.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                                dev_totSet_0.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                            }

                        }
                    }

                    Iterator iterator = dev_totSet_0.iterator();
                    List<String> ddList = new ArrayList<>();
                    List<Double> ch1 = new ArrayList<>();
                    List<Double> com1 = new ArrayList<>();
                     List<Double> ch2 = new ArrayList<>();
                    List<Double> com2 = new ArrayList<>();
                    List<Double> refrac = new ArrayList<>();

                    while (iterator.hasNext()) {
                        ddList.add((String) iterator.next());
                         double cc = 0;
                        ch1.add(cc);
                        com1.add(cc);
                        refrac.add(cc);
                    }
                    

                    for (int i = 0; i < ddList.size(); i++) {
                        for (int j = 0; j < devCList1.size(); j++) {
                            if (devCList1.get(j).contains(":")) {
                                if (devCList1.get(j).substring(0, devCList1.get(j).lastIndexOf(":")).equals(ddList.get(i))) {
                                    ch1.set(i, ch1.get(i) + Double.parseDouble(devChList1.get(j).substring(devChList1.get(j).lastIndexOf(":") + 1, devChList1.get(j).length())));
                                    com1.set(i, com1.get(i) + Double.parseDouble(devCList1.get(j).substring(devCList1.get(j).lastIndexOf(":") + 1, devCList1.get(j).length())));
                                }
                            }
                        }
                    }

                    System.out.println(sheet_index + ": " + project + "\t" + comits.size() + "\t" + change.size() + "\t" + refactor.size());
                    datas = new Object[]{"", project, "", tot_com, tot_change, "", comits.get(0), change.get(0), refactor.get(0), Double.parseDouble(dev_beforeSet_0.size() + ""), Double.parseDouble(dev_afterSet_0.size() + ""), Double.parseDouble(dev_totSet_0.size() + "")};// end of assigning the header to the object..
                    DataSets.add(datas);

                    cList.remove(0);
                    uList.remove(0);
                    //nList.remove(0);
                    comList.remove(0);
                    refacDev.remove(0);
                    changDev.remove(0);
                    comDev.remove(0);
                    refactor.remove(0);
                    change.remove(0);
                    comits.remove(0);

                    Set<String> dev_totSet_1 = new LinkedHashSet<>();
                    Set<String> dev_beforeSet_1 = new LinkedHashSet<>();
                    Set<String> dev_afterSet_1 = new LinkedHashSet<>();

                    for (int i = 0; i < changDev.size(); i++) {
                        String[] splits_ch = changDev.get(i).split("/");
                        String[] splits_com = comDev.get(i).split("/");
                        String[] splits_ref = refacDev.get(i).split("/");
                        for (int j = 0; j < splits_ch.length; j++) {
                            if (splits_ch[j].contains(":")) {
                                dev_afterSet_1.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                                dev_totSet_1.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                            }
                        }
                    }

                    double tot_change1 = 0, tot_com1 = 0;
                    for (int i = 0; i < nList.size(); i++) {

                        String[] splits_ch = cList.get(i).split("/");
                        String[] splits_com = uList.get(i).split("/");
                        for (int j = 0; j < splits_ch.length; j++) {
                            if (splits_ch[j].contains(":")) {
                                tot_change1 += Double.parseDouble(splits_ch[j].substring(splits_ch[j].lastIndexOf(":") + 1, splits_ch[j].length()));
                                tot_com1 += Double.parseDouble(splits_com[j].substring(splits_com[j].lastIndexOf(":") + 1, splits_com[j].length()));
                                dev_beforeSet_1.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                                dev_totSet_1.add(splits_ch[j].substring(0, splits_ch[j].lastIndexOf(":")));
                            }

                        }
                        double def = 0;
                        if (name.contains(nList.get(i))) {
                            int id = name.indexOf(nList.get(i));
                            datas = new Object[]{Double.parseDouble((i + 1) + ""), "", nList.get(i), tot_com1, tot_change1, "", comits.get(id), change.get(id), refactor.get(id), Double.parseDouble(dev_beforeSet_1.size() + ""), Double.parseDouble(dev_afterSet_1.size() + ""), Double.parseDouble(dev_totSet_1.size() + "")};// end of assigning the header to the object..
                            DataSets.add(datas);

                        } else {
                            datas = new Object[]{Double.parseDouble((i + 1) + ""), "", nList.get(i), tot_com1, tot_change1, "", def, def, def};// end of assigning the header to the object..
                            DataSets.add(datas);
                        }
                    }
                }
                String f_name = FILES_REFAC[a].replaceAll("refrac_merged_", "statistics_");
                Create_Excel.createExcel2(DataSets, 0, path_new + f_name, project.split("/")[0] + "_" + sheet_index);
                sheet_index++;
            }

        }
    }
}
