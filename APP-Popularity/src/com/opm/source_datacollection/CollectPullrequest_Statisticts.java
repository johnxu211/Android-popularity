/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.excel_.Create_Excel;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class CollectPullrequest_Statisticts {

    public static void main(String[] args) throws Exception {
        stats();
    }

    private static void stats() throws Exception {
        Object[] datas = null;
        //String file_stats = "Repos.xlsx";

        String file_collect1 = "file_pr_closed-shaa.xlsx";
        String file_variants = "Variant-Statistics.xlsx";

        String path_collect = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00pr_files/";
        String path_var = "/Users/john/Desktop/Dev_Commits/00New_Repos/statistics/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00pr_files/00details/";
        //todo:
        List<String> proj_List = ReadExcelFile_1Column.readColumnAsString(path_var + file_variants, 0, 0, 2);

        String[] files_collect = {file_collect1};

        ArrayList< Object[]> DataSets = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "PR MLP-FP", "# FP", "PR FR-MPL", "# FP", "PR FP-FP", "# FP"};

        for (int a = 0; a < files_collect.length; a++) {
            int sheet_index = 0;
            int total_sheets = File_Details.getWorksheets(path_collect + files_collect[a]);
            //System.out.println("Reading Collection Excel....!");
            while (sheet_index < total_sheets) {
                if (sheet_index == 0) {
                    DataSets.add(datas);
                }

                String project = File_Details.setProjectName(path_collect + files_collect[a], sheet_index, "B2");
                List<String> nList = ReadExcelFile_1Column.readColumnAsString(path_collect + files_collect[a], sheet_index, 2, 2);
                List<String> prList = ReadExcelFile_1Column.readColumnAsString(path_collect + files_collect[a], sheet_index, 4, 1);
                List<Double> numList = ReadExcelFile_1Column.readColumnAsNumeric(path_collect + files_collect[a], sheet_index, 6, 1);
                List<String> mlSet = new ArrayList<>();
                List<String> flSet = new ArrayList<>();
                List<String> fkSet = new ArrayList<>();

                List<String> nameList = new ArrayList<>();
                nameList.add(project);
                for (int i = 0; i < nList.size(); i++) {
                    nameList.add(nList.get(i));
                }

                System.out.println(sheet_index + " : " + project);
                double ml_fork = 0;
                double fk_ml = 0;
                double fk_fk = 0;
                for (int b = 0; b < nameList.size(); b++) {
                    String pPR = prList.get(b);
                    String[] splits_ml = pPR.split(" , ");
                    double f_ml_sheet_index = 0;
                    for (int i = 0; i < splits_ml.length; i++) {
                        String l_pr = splits_ml[i].split("-")[splits_ml[i].split("-").length - 1];
                        // System.out.println(i+"\t"+splits_ml[i]);
                        String proj =   splits_ml[i].substring(0,splits_ml[i].lastIndexOf("-") );
                        //System.out.println("                ");
                        l_pr = l_pr.replaceAll(":PR", "");
                        String fork_name = splits_ml[i].replaceAll(l_pr + ":PR", "");
                        //System.out.println("       "+i+"\t"+fork_name+"\t"+proj);
                        if (b == 0) {
                            if (Integer.parseInt(l_pr) != 0) {
                                ml_fork += Integer.parseInt(l_pr);                                
                                mlSet.add(proj);
                            }
                        } else {
                            if (i == 0) {
                                if (Integer.parseInt(l_pr) != 0) {
                                    fk_ml += Integer.parseInt(l_pr);
                                   // String fork_name = splits_ml[i].replaceAll(l_pr + ":PR", "");
                                    flSet.add(nameList.get(i));
                                }
                            }

                            if (i > 0) {
                                if (Integer.parseInt(l_pr) != 0) {
                                    fk_fk += Integer.parseInt(l_pr);
                                    //String fork_name = splits_ml[i].replaceAll(l_pr + ":PR", "");
                                    fkSet.add(proj);
                                }
                            }
                        }
                        if (Integer.parseInt(l_pr) != 0) {
                            f_ml_sheet_index++;
                        }
                        //System.out.print(l_pr + "\t");
                    }
                }

                if (proj_List.contains(project)) {
                    //System.out.println(sheet_index+": "+ml_fork+"\t"+fk_ml+"\t"+fk_fk);
                    //System.out.println(sheet_index+": "+mlSet.size()+"\t"+flSet.size()+"\t"+fkSet.size());
                    //System.out.println(sheet_index+": "+mlSet+"\t"+flSet+"\t"+fkSet);
                    datas = new Object[]{project, ml_fork, Double.parseDouble(mlSet.size() + ""), fk_ml, Double.parseDouble(flSet.size() + ""), fk_fk, Double.parseDouble(fkSet.size() + "")};
                    DataSets.add(datas);
                    String file_name = files_collect[a].replaceAll("file_pr_closed-shaa", "pr_closed_final3");
                    Create_Excel.createExcel2(DataSets, 0, path_new + file_name, "pr_statistics");

                }

                sheet_index++;
            }
        }

    }

}
