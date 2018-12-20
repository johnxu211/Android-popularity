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
import java.util.List;
import java.util.Set;
import com.opm.popularity.read_gitrepos.Download_AndroidManifest;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class IdentifyFork_Googleplay_UsingManifest {

    public static void main(String[] args) throws Exception {
        check();
    }

    private static void check() throws Exception {
        Object[] datas = null;
        String repos = "googleplay6_uniqueforks2.xlsx";

        String path = "";
        String path_new = "";
        //todo:
        String[] files = {repos};

        for (int a = 0; a < files.length; a++) {

            int totalsheet = File_Details.getWorksheets(path + files[a]);
            int sheet_index = 0;
            int ct = 0;
            ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
            datas = new Object[]{"MLV Reponame", "MLP packagename", "FV Reponame", "FV Packagename"};

            while (sheet_index < totalsheet) {

                if (sheet_index == 0) {
                    DataSet.add(datas);
                }
                String project = File_Details.setProjectName(path + files[a], sheet_index, "B2");
                String package_ = File_Details.setProjectName(path + files[a], sheet_index, "C2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + files[a], sheet_index, 2, 2);

                System.out.println(sheet_index + " : " + project);
               
                for (int i = 0; i < nameList.size(); i++) {

                    System.out.println("       "+i + " : " + nameList.get(i));
                    Set<String> pSet = Download_AndroidManifest.downloads(nameList.get(i), Constants.getToken(), ct);

                    Iterator iterator = pSet.iterator();
                    List<String> p_list = new ArrayList<>();
                    while (iterator.hasNext()) {
                        p_list.add((String) iterator.next());
                    }
                    ct = Integer.parseInt(p_list.get(p_list.size() - 1));
                    p_list.remove(p_list.get(p_list.size() - 1));
                    String packages = "";
                    if (p_list.size() == 1) {
                        packages = p_list.get(0);
                    } else if (p_list.size() > 1) {
                        for (int j = 0; j < p_list.size(); j++) {
                            if (j < p_list.size() - 1) {
                                packages = packages.concat(p_list.get(j) + " , ");
                            }
                            if (j == p_list.size() - 1) {
                                packages = packages.concat(p_list.get(j) + "");
                            }
                        }
                    }

                    if (p_list.size() > 0) {
                        datas = new Object[]{project, package_, nameList.get(i), packages};
                        DataSet.add(datas);
                        String f_name = files[a].replaceAll("googleplay6_uniqueforks", "googleplay_forks");
                        Create_ExcelFile.createExcel2(DataSet, 0, path_new + f_name, "repos_fork_google");
                    }

                }

                sheet_index++;
            }
        }
    }
}
