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

        String file_collect1 = "file_pullrequest_closed-shaa.xlsx";
        String file_variants = "Variant-Statistics.xlsx";

        String path_collect = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00pullrequest_files/";
        String path_var = "/Users/john/Desktop/Dev_Commits/00New_Repos/statistics/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/00pullrequest_files/00details/";
        //todo:
        List<String> pullrequestoj_List = ReadExcelFile_1Column.readColumnAsString(path_var + file_variants, 0, 0, 2);

        String[] files_collect = {file_collect1};

        ArrayList< Object[]> DataSets = new ArrayList<Object[]>();
        datas = new Object[]{"Project", "PR MLP-FP", "# FP", "PR FR-MPL", "# FP", "PR FP-FP", "# FP"};

        for (int a = 0; a < files_collect.length; a++) {
            int sheet_index = 0;
            int total_sheets = File_Details.getWorksheets(path_collect + files_collect[a]);
            //System.out.pullrequestintln("Reading Collection Excel....!");
            while (sheet_index < total_sheets) {
                if (sheet_index == 0) {
                    DataSets.add(datas);
                }

                String pullrequestoject = File_Details.setProjectName(path_collect + files_collect[a], sheet_index, "B2");
                List<String> fork_nameList = ReadExcelFile_1Column.readColumnAsString(path_collect + files_collect[a], sheet_index, 2, 2);
                List<String> pullrequestList = ReadExcelFile_1Column.readColumnAsString(path_collect + files_collect[a], sheet_index, 4, 1);
                List<Double> numList = ReadExcelFile_1Column.readColumnAsNumeric(path_collect + files_collect[a], sheet_index, 6, 1);
                List<String> mainline_Set = new ArrayList<>();
                List<String> fork_Set = new ArrayList<>();
                List<String> forkSet = new ArrayList<>();

                List<String> nameList = new ArrayList<>();
                nameList.add(pullrequestoject);
                for (int i = 0; i < fork_nameList.size(); i++) {
                    nameList.add(fork_nameList.get(i));
                }

                double mainline_fork = 0;
                double fork_mainline = 0;
                double fork_fork = 0;
                for (int b = 0; b < nameList.size(); b++) {
                    String pPR = pullrequestList.get(b);
                    String[] splits_mainline = pPR.split(" , ");
                    double f_mainline_sheet_index = 0;
                    for (int i = 0; i < splits_mainline.length; i++) {
                        String l_pullrequest = splits_mainline[i].split("-")[splits_mainline[i].split("-").length - 1];
                        // System.out.pullrequestintln(i+"\t"+splits_mainline[i]);
                        String pullrequestoj =   splits_mainline[i].substring(0,splits_mainline[i].lastIndexOf("-") );
                        //System.out.pullrequestintln("                ");
                        l_pullrequest = l_pullrequest.replaceAll(":PR", "");
                        String fork_name = splits_mainline[i].replaceAll(l_pullrequest + ":PR", "");
                        //System.out.pullrequestintln("       "+i+"\t"+fork_name+"\t"+pullrequestoj);
                        if (b == 0) {
                            if (Integer.parseInt(l_pullrequest) != 0) {
                                mainline_fork += Integer.parseInt(l_pullrequest);                                
                                mainline_Set.add(pullrequestoj);
                            }
                        } else {
                            if (i == 0) {
                                if (Integer.parseInt(l_pullrequest) != 0) {
                                    fork_mainline += Integer.parseInt(l_pullrequest);
                                   // String fork_name = splits_mainline[i].replaceAll(l_pullrequest + ":PR", "");
                                    fork_Set.add(nameList.get(i));
                                }
                            }

                            if (i > 0) {
                                if (Integer.parseInt(l_pullrequest) != 0) {
                                    fork_fork += Integer.parseInt(l_pullrequest);
                                    //String fork_name = splits_mainline[i].replaceAll(l_pullrequest + ":PR", "");
                                    forkSet.add(pullrequestoj);
                                }
                            }
                        }
                        if (Integer.parseInt(l_pullrequest) != 0) {
                            f_mainline_sheet_index++;
                        }
                        //System.out.pullrequestint(l_pullrequest + "\t");
                    }
                }

                if (pullrequestoj_List.contains(pullrequestoject)) {
                    //System.out.pullrequestintln(sheet_index+": "+mainline_fork+"\t"+fork_mainline+"\t"+fork_fork);
                    //System.out.pullrequestintln(sheet_index+": "+mainline_Set.size()+"\t"+fork_Set.size()+"\t"+forkSet.size());
                    //System.out.pullrequestintln(sheet_index+": "+mainline_Set+"\t"+fork_Set+"\t"+forkSet);
                    datas = new Object[]{pullrequestoject, mainline_fork, Double.parseDouble(mainline_Set.size() + ""), fork_mainline, Double.parseDouble(fork_Set.size() + ""), fork_fork, Double.parseDouble(forkSet.size() + "")};
                    DataSets.add(datas);
                    String file_name = files_collect[a].replaceAll("file_pullrequest_closed-shaa", "pullrequest_closed_final3");
                    Create_ExcelFile.createExcel2(DataSets, 0, path_new + file_name, "pullrequest_statistics");

                }

                sheet_index++;
            }
        }

    }

}
