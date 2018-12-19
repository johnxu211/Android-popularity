/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_datacollection;

import com.opm.core.File_Details;
import com.opm.excel_.ReadExcelFile_1Column;
import com.opm.read_gitrepos.ReadReposCommits;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.json.simple.parser.JSONParser;
import com.opm.read_gitrepos.Read_ReposCreation_Date;
import com.opm.read_gitrepos.Shaa_Details;
import com.opm.source_analysis.Fetch_CommitsDetail;
import com.opm.util.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author john
 */
public class Collect_MainForkCommits {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        collect();
    }

    public static void collect() throws Exception {
        Object[] datas = null;
        int date_interval = 14;
        int interval = 14;
        String[] tokens = Constants.getToken();
        String fork1 = "repos_language_dev_mj_mn.xlsx";
        String fork2 = "repos_fdetails_unique_2.xlsx";

        String path = "";
        String path2 = "";
        String path_new = "";
        String[] FILES = {fork1};

        String[] FILES2 = {fork2};
        List<String> projList = new ArrayList<>();
        List<String> cList = new ArrayList<>();
        List<List<String>> namList = new ArrayList<>();
        List<List<String>> shaList = new ArrayList<>();
        List<List<String>> creatList = new ArrayList<>();
        for (int a = 0; a < FILES2.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES2[a]);
            int count = 0;
            int s = 0;

            while (count < numbers) {
                String project = File_Details.setProjectName(path + FILES2[a], count, "B2");
                String created = File_Details.setProjectName(path + FILES2[a], count, "D2");
                List<String> name = ReadExcelFile_1Column.readColumnAsString(path + FILES2[a], count, 2, 2);
                List<String> shaa = ReadExcelFile_1Column.readColumnAsString(path + FILES2[a], count, 12, 2);
                List<String> create = ReadExcelFile_1Column.readColumnAsString(path + FILES2[a], count, 3, 2);

                System.out.println(count + "   :  " + project);
                projList.add(project);
                cList.add(created);
                namList.add(name);
                shaList.add(shaa);
                creatList.add(create);

                count++;
            }
        }

        int ct = 0;
        for (int a = 0; a < FILES.length; a++) {
            int numbers = File_Details.getWorksheets(path + FILES[a]);
            String file_name = FILES[a].replaceAll("repos_language_dev_mj_mn", "repos_commit_lg_mlp_fp151_"+numbers );

            int count = 0;
            while (count < numbers) {
                long stopTime = 0;
                long elapsedTime = 0;
                long startTime1 = System.currentTimeMillis();

                String project = File_Details.setProjectName(path + FILES[a], count, "A2");
                String create = Read_ReposCreation_Date.creation(project, tokens, ct);
                String created_at = create.split("/")[0];
                ct = Integer.parseInt(create.split("/")[1]);

                //if (pList.contains(project)) {
                List<String> nameList = ReadExcelFile_1Column.readColumnAsString(path + FILES[a], count, 1, 2);
                //List<String> createList = creatList.get(projList.indexOf(project));
                //List<String> createList = new ArrayList<>();
                List<String> shaaList = shaList.get(projList.indexOf(project));

                String sheet = File_Details.getWorksheetName(path + FILES[a], count);

                List<String> sList = new ArrayList<>();
                for (int i = 0; i < shaaList.size(); i++) {
                    String[] splits = shaaList.get(i).split("/");
                    for (int j = 0; j < splits.length; j++) {
                        sList.add(splits[j].split(":")[0]);
                    }
                }

                System.out.println("ML: " + count + " \t" + project + "");

                XSSFSheet[] workSheet = new XSSFSheet[numbers];
                String projSheet = project.split("/")[0] + "_" + count;

                //todo:: getting the commits
                List<List<String>> allList_1 = ReadReposCommits.count(project, "mlp", created_at, Constants.cons.TODAY_DATE, tokens, ct);
                List<String> shaList_1 = allList_1.get(0);
                List<String> dateList_1 = allList_1.get(1);
                List<String> messageList_1 = allList_1.get(2);

                List<String> detailsL = new ArrayList<>();
                List<String> dateL = new ArrayList<>();
                for (int i = 0; i < shaList_1.size(); i++) {
                    String shaa_mlp = Shaa_Details.details(project, shaList_1.get(i), tokens, ct);
                    if (!shaa_mlp.equals("")) {
                        ct = Integer.parseInt(shaa_mlp.split("/")[shaa_mlp.split("/").length - 1]);
                        detailsL.add(shaa_mlp);
                        dateL.add(dateList_1.get(i));
                    }

                }

                ct = Fetch_CommitsDetail.process(project, "", detailsL, dateL, tokens, ct, date_interval, interval, file_name, projSheet);
                //System.out.println("      ElapsedTime in minutes = " + elapsedTime / (1000 * 60));
                for (int i = 0; i < nameList.size(); i++) {
                    int index = projList.indexOf(project);
                    if (namList.get(index).contains(nameList.get(i))) {
                        int idx = namList.get(index).indexOf(nameList.get(i));
                        String shaa = shaaList.get(idx);

                        System.out.println("    " + i + " \t" + nameList.get(i));
                        stopTime = 0;
                        elapsedTime = 0;
                        startTime1 = System.currentTimeMillis();

                        ArrayList< Object[]> allobj = new ArrayList<Object[]>();
                        JSONParser parser = new JSONParser();
                        datas = new Object[]{"Tag Date", "PR Open",
                            "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
                            "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Changed_Added_Deleted"
                        };
                        allobj.add(datas);

                        List<String> detailsList = new ArrayList<>();
                        List<String> dateList = new ArrayList<>();
                        //if (unique.get(i) > 0) {
                        String subSheet = projSheet + "_FP_" + i;
                        String[] split_shaa = shaa.split("/");
                        for (int j = 0; j < split_shaa.length; j++) {
                            String shaa_details = Shaa_Details.details(nameList.get(i), split_shaa[j].split(":")[0], tokens, ct);

                            try {
                                if (!shaa_details.equals("")) {
                                    ct = Integer.parseInt(shaa_details.split("/")[shaa_details.split("/").length - 1]);
                                    if (!shaa_details.equals("")) {
                                        detailsList.add(shaa_details);
                                        dateList.add(shaa_details.split("/")[2]);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ct = Fetch_CommitsDetail.process(project, nameList.get(i), detailsList, dateList, tokens, ct, date_interval, interval, file_name, subSheet);                   
                    }
                }
                count++;
            }
        }
    }
}
