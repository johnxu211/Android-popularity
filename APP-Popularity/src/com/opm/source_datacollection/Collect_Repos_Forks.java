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
import com.opm.read_gitrepos.ReadReposForks;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class Collect_Repos_Forks {
    public static void main(String[] args) throws Exception {
        collect();
    }

    private static void collect() throws Exception {
        int ct = 0;
        Object[] datas = null;
        String file1 = "repos_gp_refac_com_comnbined_statistics.xlsx";

        String[] files = {file1};
        String path = "";
        String path_new = "";

        for (int a = 0; a < files.length; a++) {
            int a_count = 0;
            int numbers = File_Details.getWorksheets(path + files[a]);
            int count = 0;
            
            
            List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + files[a], count, 0, 1);
            
            int b_c = 0;
            for (int b = 0; b < nameList.size(); b++) {
                System.out.println(b + " : " + nameList.get(b));
                ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                datas = new Object[]{"N.S", "Repos", "Forks", "Total_Commits", "Created_at", "First_Commits", "Last_Commits"};
                allobj.add(datas);
                datas = new Object[]{"", nameList.get(b),  "", "", "", "", ""};
                allobj.add(datas);
                List<String> fork_details = ReadReposForks.getDatas(nameList.get(b), Constants.getToken(), ct);
                ct = Integer.parseInt(fork_details.get(fork_details.size() - 1));
                fork_details.remove(fork_details.size() - 1);
                for (int i = 0; i < fork_details.size(); i++) {
                    double f_count = Double.parseDouble(fork_details.get(i).split("/")[0]);
                    String full_name = fork_details.get(i).split("/")[1] + "/" + fork_details.get(i).split("/")[2];
                    String created_ = fork_details.get(i).split("/")[3];
                    String first_com = fork_details.get(i).split("/")[4];
                    String last_com = fork_details.get(i).split("/")[5];

                    datas = new Object[]{Double.parseDouble((i + 1) + ""), "", full_name, f_count, created_, first_com, last_com};
                    allobj.add(datas);
                    String f_name = files[a].replaceAll("repos_gp_refac_com_comnbined_statistics", "repos_forks_new2");
                    Create_Excel.createExcel2(allobj, 0, path_new + f_name, nameList.get(b).split("/")[0] + "_" + b_c);
                    

                }
                b_c ++;

            }
        }
    }
}
