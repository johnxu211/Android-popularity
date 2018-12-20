/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.opm.popularity.read_gitrepos.Download_FileBY_URL;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Fork_Googleplay_UsingUnique {

    public static void main(String[] args) throws Exception {
        check();
    }

    private static void check() throws Exception {
        Object[] datas = null;
        String file1 = "googleplay6_uniqueforks2.xlsx";
        // String fork_shaa2 = "fork_shas_500-1000.xlsx";
        //String file_google = "google_play-All.xlsx";

        String path_shaas = "";
        String path_google = "";
        String path_new = "";
        //todo:
        String[] files = {file1};

        for (int a = 0; a < files.length; a++) {

            int numbers = File_Details.getWorksheets(path_shaas + files[a]);
            int count = 0;
            int ct = 0;
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            while (count < numbers) {
                datas = new Object[]{"MLV Reponame", "MLP packagename", "FV Reponame", "FV Packagename", "Total_pack"};
                if (count == 0) {
                    allobj.add(datas);
                }

                String project = File_Details.setProjectName(path_shaas + files[a], count, "B2");
                String package_ = File_Details.setProjectName(path_shaas + files[a], count, "C2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path_shaas + files[a], count, 2, 2);
                List<String> shaaList = ReadExcelFile_1Column.readColumnAsString(path_shaas + files[a], count, 9, 2);
                List<Double> unique =  ReadExcelFile_1Column.readColumnAsNumeric(path_shaas + files[a], count, 4, 2);;
                //String project = File_Details.setProjectName(path_shaas + files[a], count, "B2");
                String sheet = File_Details.getWorksheetName(path_shaas + files[a], count);

                System.out.println(count + " : " + project + " \t " + nameList.size() + "\t" + shaaList.size());
                for (int i = 0; i < nameList.size(); i++) {
                    if (unique.get(i) > 0) {
                        String[] splits_1 = shaaList.get(i).split("/");
                        System.out.println(" ************************************************************************* ");

                        System.out.println("  " + i + " \t " + nameList.get(i) + " \t " + splits_1.length);

                        List<String> pList = new ArrayList<>();
                        for (int j = 0; j < splits_1.length; j++) {
                            if (splits_1[j].split(":").length > 1) {
                                Set<String> pSet = Download_FileBY_URL.downloads(nameList.get(i), splits_1[j].split(":")[0], package_, Constants.getToken(), ct);
                                Iterator iterator = pSet.iterator();
                                List<String> p_list = new ArrayList<>();
                                while (iterator.hasNext()) {
                                    p_list.add((String) iterator.next());
                                }
                                ct = Integer.parseInt(p_list.get(p_list.size() - 1));
                                p_list.remove(p_list.size() - 1);
                                if (p_list.size() > 0) {
                                    for (int k = 0; k < p_list.size(); k++) {
                                        pList.add(p_list.get(k));
                                    }
                                }

                            }
                        }
                        String packages = "";
                        if (pList.size() == 1) {
                            packages = pList.get(0);
                        } else if (pList.size() > 1) {
                            for (int j = 0; j < pList.size(); j++) {
                                if (j < pList.size() - 1) {
                                    packages = packages.concat(pList.get(j) + " , ");
                                }
                                if (j == pList.size() - 1) {
                                    packages = packages.concat(pList.get(j) + "");
                                }
                            }
                        }

                        if (pList.size() > 0) {
                            datas = new Object[]{project, package_, nameList.get(i), packages, Double.parseDouble(pList.size() + "")};
                            allobj.add(datas);
                            String f_name = files[a].replaceAll("googleplay6_uniqueforks", "googleplay_forks");
                            Create_ExcelFile.createExcel2(allobj, 0, path_new + f_name, "using_unique");
                        }
                    }
                }

                count++;
            }
        }
    }
}
