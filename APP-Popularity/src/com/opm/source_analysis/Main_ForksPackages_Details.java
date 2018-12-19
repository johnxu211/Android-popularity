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
import java.util.List;

/**
 *
 * @author john
 */
public class Main_ForksPackages_Details {

    public static void main(String[] args) throws Exception {
        check();
    }

    private static void check() throws Exception {
        Object[] datas = null;
        String repos = "file_googleplay_com-shaa.xlsx";
        String repos1 = "Variant-Statistics.xlsx";

        String path = "/Users/john/Documents/";
        String path_new = "/Users/john/Documents/Destope_data_2018-05_18/00commits/00output_latest/refactoring_outputs/";
        //todo:
        String[] files = {repos};

        List<String> pList = ReadExcelFile_1Column.readColumnAsString(path + repos1, 3, 0, 2);
        for (int a = 0; a < files.length; a++) {

            int numbers = File_Details.getWorksheets(path + files[a]);
            int count = 0;
            int ct = 0;
            ArrayList< Object[]> allobj = new ArrayList<Object[]>();
            datas = new Object[]{"MLV Reponame", "FV Reponame", "Variant", "Variant2","Packagename"};

            int c_count = 0;
            
            while (count < numbers) {

                if (count == 0) {
                    allobj.add(datas);
                }
                String project = File_Details.setProjectName(path + files[a], count, "B2");
                String package_ = File_Details.setProjectName(path + files[a], count, "D2");
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + files[a], count, 2, 2);
                List<String> fpackage_ = ReadExcelFile_1Column.readColumnAsString(path + files[a], count, 3, 2);

                System.out.println(count + " : " + project);

                if (pList.contains(project)) {
                    c_count ++;
                    datas = new Object[]{project, project,"MLV_"+c_count, "MLV_"+c_count,package_};
                    allobj.add(datas);
                    for (int i = 0; i < nameList.size(); i++) {
                        datas = new Object[]{project, nameList.get(i), "FPV_"+c_count,"MLV_"+c_count,fpackage_.get(i)};
                        allobj.add(datas);
                    }

                    String f_name = files[a].replaceAll("file_googleplay_com-shaa", "file_googleplay_packages333");
                    //Create_Excel.createExcel(allobj, 0, path_new + f_name, File_Details.getWorksheetName(path + files[aa], count));
                    Create_Excel.createExcel2(allobj, 0, path_new + f_name, "packages_");
                }
                count++;
            }
        }
    }
}
