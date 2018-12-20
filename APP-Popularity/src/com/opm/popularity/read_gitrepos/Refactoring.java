/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.DateOperations;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Refactoring {

    public static ArrayList<Object[]> refactors(String project, String path, String file, String[] tokens, int ct, ArrayList< Object[]> allobj) {
        List<String> comSet = null;
        Object[] datas = null;
        try {
            if (new File(path + file).exists()) {
                Scanner scan = new Scanner(new File(path + file));
                int com = 0, refrac = 0;
                int com_num = 0;
                int refrac_num = 0;
                comSet = new ArrayList<>();
                List<String> refracSet = new ArrayList<>();
                List<String> details = new ArrayList<>();
                List<List<String>> allSet = new ArrayList<>();
                int cont = 0;
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    //Here you can manipulate the string the way you want...!!!....
                    //System.out.println(line + "\t" + line.length());
                    if (line.length() == 40 && !line.contains(" ")) {
                        com++;
                        if (!comSet.contains(line)) {
                            String commits_details = Shaa_Details.details1(project, line, tokens, ct);
                            System.out.println(com + " : " + line + "\t" + commits_details);
                            if (DateOperations.compareDates(commits_details.split("/")[2], Constants.cons.TODAY_DATE)) {
                                details.add(commits_details);
                                comSet.add(line);
                                if (com > 1) {
                                    allSet.add(refracSet);
                                }
                                refracSet = new ArrayList<>();
                            }

                        }
                        //TODO::: ...........!!!
                        refrac_num = 0;
                    } else {
                        ////refrac++;
                        if (!refracSet.contains(line)) {
                            refracSet.add(line);
                        }
                        ////refrac_num++;
                    }
                }
                allSet.add(refracSet);
                for (int i = 0; i < comSet.size(); i++) {
                    List<String> rList = allSet.get(i);
                    datas = new Object[]{"", "", comSet.get(i), Double.parseDouble(rList.size() + ""),
                        details.get(i).split("/")[0] + "|" + details.get(i).split("/")[1] + "|" + details.get(i).split("/")[3], Double.parseDouble(details.get(i).split("/")[4]), details.get(i).split("/")[2]};
                    allobj.add(datas);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allobj;
    }
}
