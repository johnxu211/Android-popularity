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
import java.util.List;
import java.util.Set;
import com.opm.read_gitrepos.Download_AndroidManifest;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Fork_Googleplay {

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

            int numbers = File_Details.getWorksheets(path + files[a]);
            int count = 0;
            int ct = 0;
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"MLV Reponame", "MLP packagename", "FV Reponame", "FV Packagename"};

            while (count < numbers) {

                if (count == 0) {
                    allobj.add(datas);
                }
                String project = File_Details.setProjectName(path + files[a], count, "B2");
                String package_ = File_Details.setProjectName(path + files[a], count, "C2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + files[a], count, 2, 2);

                System.out.println(count + " : " + project);
               
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
                        allobj.add(datas);
                        String f_name = files[a].replaceAll("googleplay6_uniqueforks", "googleplay_forks");
                        Create_Excel.createExcel2(allobj, 0, path_new + f_name, "repos_fork_google");
                    }

                }

                count++;
            }
        }
    }
}
