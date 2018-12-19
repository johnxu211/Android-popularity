/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;
import com.opm.excel_.Create_Excel;
import com.opm.core.DateOperations;
import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import com.opm.read_gitrepos.Shaa_Details;
import com.opm.util.Constants;

/**
 *
 * @author john
 */
public class ReadRefactor_txt {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        readText();
    }

    private static void readText() throws Exception {
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;
        //String file1 = "alt236_68.txt";
        String file1 = "repos_commits_mlp_fp_first_last2.xlsx";
        String file2 = "repos_gp_refactoring_0-20.xlsx";
        String file3 = "repos_gp_refactoring_40-60.xlsx";
        String file4 = "repos_gp_refactoring_60-80.xlsx";
        String file5 = "repos_gp_refactoring_80-100.xlsx";
        String file6 = "repos_gp_refactoring_80-1001.xlsx";
        String file7 = "repos_gp_refactoring_100-end.xlsx";
        //String file8 = "repos_gp_refactoring_211-240.xlsx";
        //String file9 = "repos_gp_refactoring_241-262.xlsx";
        //String file10 = "repos_gp_refactoring_1-30.xlsx";

        String path = "Dev_Turnover/";
        String path_google = "Dev_Turnover/";
        String path_new = "";
        //try {
        // File[] listOfFiles = file(path);
        //TODO::::  ....!!
        String[] FILES = { file3, file4};

        for (int x = 0; x < FILES.length; x++) {
             String file_name = FILES[x].replaceAll("repos_gp_refactoring_", "repos_gp_refactor_outputs_f_");
            
            int count = 0;
            int numbers = File_Details.getWorksheets(path_google + FILES[x]);
            while (count < numbers) {
                List<String> project = ReadExcelFile_1Column.readColumnAsString(path_google + FILES[x], count, 0, 0);
                List<String> forks = ReadExcelFile_1Column.readColumnAsString(path_google + FILES[x], count, 1, 0);
                List<String> txtname = ReadExcelFile_1Column.readColumnAsString(path_google + FILES[x], count, 2, 0);

                Set<String> projSet = new LinkedHashSet<>();
                for (int i = 0; i < project.size(); i++) {
                    projSet.add(project.get(i));
                }

                List<String> projL = new ArrayList<>();
                Iterator iterator = projSet.iterator();
                while (iterator.hasNext()) {
                    projL.add((String) iterator.next());

                }
                //// starts here...
                for (int a = 0; a < projL.size(); a++) {
                    ///Mainline project goes here..!
                    ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                    datas = new Object[]{"Project", "Forks", "Commits", "Refractorings", "Developers", "Changes", "Date"};
                    allobj.add(datas);

                    datas = new Object[]{projL.get(a), "", "", "", "", "", ""};
                    allobj.add(datas);

                    try {
                        if (new File(path + txtname.get(forks.indexOf(projL.get(a)))).exists()) {
                            Scanner scan = new Scanner(new File(path + txtname.get(forks.indexOf(projL.get(a)))));
                            int com = 0, refrac = 0;
                            int com_num = 0;
                            int refrac_num = 0;
                            List<String> comSet = new ArrayList<>();
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
                                        String commits_details = Shaa_Details.details1(projL.get(a), line, tokens, ct);
                                        ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length-1]);
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
                            Create_Excel.createExcel2(allobj, 0, path_new + file_name, projL.get(a).split("/")[0] + "_" + a);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int b = 0; b < forks.size(); b++) {
                        if (projL.get(a).equals(project.get(b)) && !forks.get(b).equals(projL.get(a))) {
                            allobj = new ArrayList<Object[]>();
                            datas = new Object[]{"Project", "Forks", "Commits", "Refractorings", "Developers", "Changes", "Date"};
                            allobj.add(datas);
                            datas = new Object[]{projL.get(a), forks.get(b), "", "", "", "", ""};
                            allobj.add(datas);
                            try {
                                if (new File(path + txtname.get(b)).exists()) {
                                    Scanner scan = new Scanner(new File(path + txtname.get(b)));
                                    int com = 0, refrac = 0;
                                    int com_num = 0;
                                    int refrac_num = 0;
                                    List<String> comSet = new ArrayList<>();
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
                                                String commits_details = Shaa_Details.details1(forks.get(b), line, tokens, ct);
                                                ct = Integer.parseInt(commits_details.split("/")[commits_details.split("/").length-1]);
                                        
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

                                    Create_Excel.createExcel2(allobj, 0, path_new + file_name, projL.get(a).split("/")[0] + "_" + a + "_" + forks.get(b).split("/")[0] + "_" + b);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                ///Ends here..
                count++;
            }
            System.out.println("\nFile: "+FILES[x]+" Completed....!\n\n");
        }
    }
}
