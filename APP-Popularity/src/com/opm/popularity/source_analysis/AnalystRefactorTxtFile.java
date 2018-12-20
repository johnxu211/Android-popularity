/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.source_analysis;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
import com.opm.popularity.core.File_Details;
import com.opm.popularity.excel_.ReadExcelFile_1Column;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.opm.popularity.read_gitrepos.Shaa_Details;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class AnalystRefactorTxtFile {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        readText();
    }

    private static void readText() {
        Object[] datas = null;
        String[] tokens = Constants.getToken();
        int ct = 0;
        //String file1 = "alt236_68.txt";
        String file2 = "merged_com_dev.xlsx";
        String path = "/Users/john/Desktop/Dev_Commits/00New_Repos/files_packages/Refactorings/Variability/";
        String path_google = "/Users/john/Desktop/Dev_Commits/00New_Repos/merged/";
        String path_new = "/Users/john/Desktop/Dev_Commits/00New_Repos/merged/00refract_stats/";
        try {
            File[] listOfFiles = file(path);
            //TODO::::  ....!!
            String[] FILES = {file2};

            int cc = 0;
            for (int a = 0; a < listOfFiles.length; a++) {

                if (listOfFiles[a].isFile()) {
                    cc++;
                    if (listOfFiles[a].getName().contains("cgeo_69.txt")) {
                        String[] splits = listOfFiles[a].getName().split("_");
                        int s_index = 0;
                        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
                        datas = new Object[]{"Project", "Forks", "Commits", "Refractorings", "Developers", "Changes", "Date"};
                        DataSet.add(datas);
                        if (splits.length == 2 || splits.length == 4) {
                            String sheet = splits[1];
                            sheet = sheet.replaceAll(".txt", "");
                            s_index = Integer.parseInt(sheet);
                        }
                        List<String> name = ReadExcelFile_1Column.readColumnAsString(path_google + FILES[0], s_index, 1, 2);
                        String project = File_Details.setProjectName(path_google + FILES[0], s_index, "B2");
                        String pName = "";
                        String pType = "";
                        if (splits.length == 2) {
                            pName = project;
                            pType = "MLP";
                        } else {
                            pType = "FP";
                            for (int i = 0; i < name.size(); i++) {
                                if (name.get(i).contains(splits[2])) {
                                    pName = name.get(i);
                                }
                            }
                        }
                        String sheet = listOfFiles[a].getName().split("_")[0] + "_" + listOfFiles[a].getName().split("_")[1];
                        int index = File_Details.getIndex(path_google + file2, sheet);
                        System.out.println(cc + ":\t " + listOfFiles[a].getName() + "\t: " + s_index);
                        //TODO:::: Scan the file here..!!
                        Scanner scan = new Scanner(new File(path + listOfFiles[a].getName()));
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
                            if (line.length() == 40 && !line.contains(" ")) {
                                com++;
                                if (!comSet.contains(line)) {
                                    String commits_details = Shaa_Details.details1(pName, line, tokens, ct);
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
                        if (pType.equals("MLP")) {
                            datas = new Object[]{project, "", "", "", "", "", ""};
                            DataSet.add(datas);
                        } else {
                            datas = new Object[]{project, pName, "", "", "", "", ""};
                            DataSet.add(datas);
                        }
                        //System.out.println(pName + "\t" + comSet.size() + "\t" + details.size() + "\t" + allSet.size());
                        for (int i = 0; i < comSet.size(); i++) {
                            List<String> rList = allSet.get(i);
                            datas = new Object[]{"", "", comSet.get(i), Double.parseDouble(rList.size() + ""),
                                details.get(i).split("/")[0] + "|" + details.get(i).split("/")[1] + "|" + details.get(i).split("/")[3], Double.parseDouble(details.get(i).split("/")[4]), details.get(i).split("/")[2]};
                            DataSet.add(datas);
                        }
                        String file_name = FILES[0].replaceAll("merged_com_dev", "stats_refrac_dev11");
                        Create_ExcelFile.createExcel2(DataSet, 0, path_new + file_name, project.split("/")[0] + "_" + cc);
                        ///// 
                    }
                } else if (listOfFiles[a].isDirectory()) {
                    //System.out.println("Directory " + listOfFiles[a].getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File[] file(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        /**
         * *
         * for (int i = 0; i < listOfFiles.length; i++) { if
         * (listOfFiles[i].isFile()) { System.out.println("File " +
         * listOfFiles[i].getName()); } else if (listOfFiles[i].isDirectory()) {
         * System.out.println("Directory " + listOfFiles[i].getName()); } } *
         */
        return listOfFiles;
    }

    private void readLine(String complete_path) {
        try {
            Scanner scan = new Scanner(new File(complete_path));
            int com = 0, refrac = 0;
            int com_num = 0;
            int refrac_num = 0;
            List<String> comList = new ArrayList<>();
            List<Integer> refracList = new ArrayList<>();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                //Here you can manipulate the string the way you want...!!!....
                //System.out.println(line + "\t" + line.length());
                if (line.length() == 40 && !line.contains(" ")) {
                    com++;
                    comList.add(line);
                    refracList.add(refrac_num);
                    //TODO::: ...........!!!
                    refrac_num = 0;
                } else {
                    refrac++;
                    refrac_num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
