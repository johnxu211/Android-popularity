/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_datacollection;

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
public class RefracDevelopers {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";
        String merged1 = "merged_com_merged_cd.xlsx";
        String merged2 = "refrac_merged_dev.xlsx";
        //String file_collect2 = "Commits_Cleared_500-1000.xlsx";
        String path_merged1 = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        String path_merged2 = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        //String path_stats = "/Users/john/Desktop/Dev_Commits/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00googleplay/merged/";
        //todo:
        String[] files_merged1 = {merged1};
        String[] files_merged2 = {merged2};

        List<String> projList = new ArrayList<>();
        List<List<String>> comitsList = new ArrayList<>();
        List<List<String>> changesList = new ArrayList<>();
        List<List<String>> namesList = new ArrayList<>();
        for (int a = 0; a < files_merged1.length; a++) {
            int sheet_index = 0;
            int total_sheets = File_Details.getWorksheets(path_merged1 + files_merged1[a]);
            //System.out.println("Reading Collection Excel....!");
            while (sheet_index < total_sheets) {

                String project = File_Details.setProjectName(path_merged1 + files_merged1[a], sheet_index, "B2");
                List<String> names = ReadExcelFile_1Column.readColumnAsString(path_merged1 + files_merged1[a], sheet_index, 1, 1);
                List<String> changes1 = ReadExcelFile_1Column.readColumnAsString(path_merged1 + files_merged1[a], sheet_index, 4, 1);
                List<String> commits1 = ReadExcelFile_1Column.readColumnAsString(path_merged1 + files_merged1[a], sheet_index, 11, 1);
                System.out.println(sheet_index + " : " + project);
                projList.add(project);
                namesList.add(names);
                comitsList.add(commits1);
                changesList.add(changes1);
                sheet_index++;
            }
        }

        for (int a = 0; a < files_merged2.length; a++) {
            int sheet_index = 0;
            int total_sheets = File_Details.getWorksheets(path_merged2 + files_merged2[a]);
            while (sheet_index < total_sheets) {

                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                List<String> dataList = new ArrayList<>();
                String project = File_Details.setProjectName(path_merged2 + files_merged2[a], sheet_index, "B2");
                List<String> n = ReadExcelFile_1Column.readColumnAsString(path_merged2 + files_merged2[a], sheet_index, 2, 2);
                List<String> refactor = ReadExcelFile_1Column.readColumnAsString(path_merged2 + files_merged2[a], sheet_index, 3, 1);
                List<String> changes = ReadExcelFile_1Column.readColumnAsString(path_merged2 + files_merged2[a], sheet_index, 4, 1);
                List<String> commits = ReadExcelFile_1Column.readColumnAsString(path_merged2 + files_merged2[a], sheet_index, 5, 1);

                List<String> names = new ArrayList<>();
                names.add(project);
                for (int i = 0; i < n.size(); i++) {
                    names.add(n.get(i));
                }
                if (projList.contains(project)) {
                    int index = projList.indexOf(project);
                    Set<String> devSet = new LinkedHashSet<>();
                    List<String> changes1 = changesList.get(index);
                    List<String> commits1 = comitsList.get(index);
                    List<String> names1 = namesList.get(index);
                    for (int i = 0; i < changesList.get(index).size(); i++) {
                        String[] splits = changesList.get(index).get(i).split("/");
                        for (int j = 0; j < splits.length; j++) {
                            if (splits[j].contains(":")) {
                                devSet.add(splits[j].substring(0, splits[j].lastIndexOf(":")));
                            }
                        }
                    }
                    for (int i = 0; i < changes.size(); i++) {
                        String[] splits = changes.get(i).split("/");
                        for (int j = 0; j < splits.length; j++) {
                            if (splits[j].contains(":")) {
                                devSet.add(splits[j].substring(0, splits[j].lastIndexOf(":")));
                            }
                        }
                    }

                    dataList.add("");
                    Iterator iterator = devSet.iterator();
                    List<String> dev = new ArrayList<>();

                    while (iterator.hasNext()) {

                        dataList.add((String) iterator.next());
                        dev.add((String) iterator.next());

                    }
                    datas = new Object[dataList.size()];
                    datas = dataList.toArray(datas);
                    allobj.add(datas);
                    for (int b = 0; b < names1.size(); b++) {
                        List<Double> cch = new ArrayList<>();
                        List<Double> com = new ArrayList<>();
                        List<Double> cch2 = new ArrayList<>();
                        List<Double> com2 = new ArrayList<>();
                        List<Double> refac = new ArrayList<>();
                        double default_ = 0;
                        for (int i = 0; i < dev.size(); i++) {
                            cch.add(default_);
                            com.add(default_);
                            cch2.add(default_);
                            com2.add(default_);
                            refac.add(default_);
                        }
                        for (int i = 0; i < dev.size(); i++) {
                            String[] splits = changes1.get(b).split("/");
                            String[] splits2 = changes1.get(b).split("/");
                            for (int k = 0; k < splits.length; k++) {
                                if (splits[k].contains(":")) {
                                    if (dev.get(i).equals(splits[k].substring(0, splits[k].lastIndexOf(":")))) {
                                        cch.set(i, cch.get(i) + Double.parseDouble(splits[k].substring(splits[k].lastIndexOf(":") + 1, splits[k].length())));
                                        com.set(i, com.get(i) + Double.parseDouble(splits2[k].substring(splits2[k].lastIndexOf(":") + 1, splits2[k].length())));
                                    }
                                }
                            }
                        }

                        if (names.contains(names1.get(b))) {
                            int ind = names1.indexOf(names1.get(b));
                            String[] splits = changes.get(ind).split("/");
                            String[] splits2 = commits.get(ind).split("/");
                            String[] splits3 = refactor.get(ind).split("/");
                            for (int i = 0; i < dev.size(); i++) {
                                for (int j = 0; j < splits.length; j++) {
                                    if (splits[j].contains(":")) {
                                        if (dev.get(i).equals(splits[j].substring(0, splits[j].lastIndexOf(":")))) {
                                            cch2.set(i, cch2.get(i) + Double.parseDouble(splits[j].substring(splits[j].lastIndexOf(":") + 1, splits[j].length())));
                                            com2.set(i, com2.get(i) + Double.parseDouble(splits2[j].substring(splits2[j].lastIndexOf(":") + 1, splits2[j].length())));
                                            cch2.set(i, cch2.get(i) + Double.parseDouble(splits3[j].substring(splits3[j].lastIndexOf(":") + 1, splits3[j].length())));
                                        }
                                    }
                                }
                            }
                        }
                        List<String> ccdev = new ArrayList<>();
                        ccdev.add(names1.get(b));
                        for (int i = 0; i < dev.size(); i++) {
                            ccdev.add(cch.get(i) + "");
                        }
                        datas = new Object[ccdev.size()];
                        datas = ccdev.toArray(datas);
                        allobj.add(datas);

                    }

                    String file_name = files_merged2[a].replaceAll("refrac_merged_dev", "refac_general_");
                    Create_ExcelFile.createExcel(allobj, 0, path_new + file_name, project.split("/")[0] + "_" + sheet_index);

                    //TODO::: Write your file here....!             
                }
                sheet_index++;
            }
        }
    }
}
