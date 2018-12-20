/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.excel_.Create_ExcelFile;
import com.opm.popularity.core.DateOperations;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opm.popularity.read_gitrepos.PR_IS_Details;
import com.opm.popularity.util.Constants;

/**
 *
 * @author john
 */
public class Fetch_CommitsDetail {

    public static int process(String project, String forkP, List<String> detailsList, List<String> dateL, String[] tokens, int ct, int date_interval, int interval, String new_file, String sheet) throws ParseException, java.text.ParseException, IOException {
        Object[] datas = null;
        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
        JSONParser parser = new JSONParser();
        datas = new Object[]{"Tag Date", "PR Open",
            "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
            "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Refactoring_Added_Deleted"

        };
        DataSet.add(datas);

        String fDate = "", lDate = "";
        //System.out.println(" SIZE: "+detailsList.size());
        if (detailsList.size() > 0) {
            fDate = DateOperations.sorts(dateL, dateL).split("/")[0];
            lDate = DateOperations.sorts(dateL, dateL).split("/")[1];

            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            String date2 = fDate;
            Calendar c3 = Calendar.getInstance();
            c3.setTime(s.parse(lDate.toString()));
            c3.add(Calendar.DATE, (interval - 1));  // number of days to add
            String dt33 = s.format(c3.getTime());  // dt is now the new date

            Date dateTODAY = s.parse(lDate);

            Date Ddate1 = s.parse(date2);
            Date Ddate2 = s.parse(dt33);
            int i2 = 1, i = 0;
            List<String> nextDate = new ArrayList<>();
            nextDate.add(fDate);
            List<String> ddList = new ArrayList<>();
            List< List<String>> list_all = new ArrayList<>();
            do {

                //System.out.println(fDate.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String subString = fDate.toString().substring(10, (fDate.toString().length() - 1));
                /// First Dates...
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(fDate.toString()));
                c.add(Calendar.DATE, interval * i);  // number of days to add
                String dt2 = sdf.format(c.getTime());  // dt is now the new date
                /// Second Date

                Calendar c2 = Calendar.getInstance();
                c2.setTime(sdf.parse(fDate.toString()));
                c2.add(Calendar.DATE, interval * i2);  // number of days to add
                String dt22 = sdf.format(c2.getTime());  // dt is now the new date

                //int increamented = Integer.parseInt(dt.substring(17,19)) + 1;
                int increamented = Integer.parseInt(subString.substring(subString.length() - 2, subString.length())) + 1;///  index number 17 - 19 from "2016-11-13T10:31:35Z" is 35  increament the min by 1
                String i_Z = increamented + "Z";
                /// 
                String sub = subString.substring(subString.length() - 3, subString.length() - 1);/// index number 17 - 19 from "2016-11-13T10:31:35Z" is 35
                String replace_sub = subString.replace(sub, increamented + "");/// Replace the Last string which is the minutes..

                String n_dt2 = dt22 + "" + replace_sub + "";// Concate to the date to make it full

                /// Now we can use the right variable names for the two dates interval
                String date1 = dt2 + "" + subString + "Z";
                date2 = dt22 + "" + subString + "Z";

                /// Now we assigns to the next method to get the commits within the two dates above....
                Ddate1 = s.parse(date2);

                List<String> ll = new ArrayList<>();
                for (int j = detailsList.size() - 1; j >= 0; j--) {
                    if (DateOperations.compareDates(date1, dateL.get(j)) == true
                            && DateOperations.compareDates(dateL.get(j), date2) == true) {
                        ll.add(detailsList.get(j));
                        
                       
                    }
                }
                list_all.add(ll);
                ddList.add(date1 + " - " + date2);
                ll = new ArrayList<>();
                i++;
                i2++;

            } while (Ddate2.compareTo(Ddate1) > 0);
            //int i2 = 1, i1 = 0, next = 0;
            List<List<String>> all_list = new ArrayList<>();
            String pr_details = PR_IS_Details.pr_details(project, tokens, ct, Constants.cons.TODAY_DATE);
            ct = Integer.parseInt(pr_details.split("/")[pr_details.split("/").length - 1]);
            int cc = 0;

            for (int j = 0; j < list_all.size(); j++) {
                List<String> lists = list_all.get(j);
                Set<String> emailSet = new LinkedHashSet<String>();
                for (int k = 0; k < lists.size(); k++) {
                    emailSet.add(lists.get(k).split("/")[1]);
                }
                Iterator iterator = emailSet.iterator();
                List<String> email_List = new ArrayList<>();
                List<String> final_lists = new ArrayList<>();
                while (iterator.hasNext()) {
                    email_List.add((String) iterator.next());
                    final_lists.add("Name/email/login/Location/Created_at/Updated_at/0/0/0/0/0_0_0_0");
                }
                List<String> DataCollectionList = new ArrayList<>();
                DataCollectionList.add(ddList.get(j));
                if (j == 0) {
                    DataCollectionList.add(pr_details.split("/")[0]);
                    DataCollectionList.add(pr_details.split("/")[1]);
                    DataCollectionList.add(pr_details.split("/")[2]);
                    DataCollectionList.add(pr_details.split("/")[3]);
                    DataCollectionList.add(pr_details.split("/")[4]);
                    DataCollectionList.add(project);
                    DataCollectionList.add(forkP);
                } else {
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                }
                for (int k = 0; k < lists.size(); k++) {

                    for (int l = 0; l < final_lists.size(); l++) {
                        
                        if (lists.get(k).split("/")[1].equals(email_List.get(l))) {
                            try{
                            long up = Long.parseLong(final_lists.get(l).split("/")[6]) + Long.parseLong(lists.get(k).split("/")[7]);
                            long gis = Long.parseLong(final_lists.get(l).split("/")[7]) + Long.parseLong(lists.get(k).split("/")[8]);
                            long fol = Long.parseLong(final_lists.get(l).split("/")[8]) + Long.parseLong(lists.get(k).split("/")[9]);
                            long fow = Long.parseLong(final_lists.get(l).split("/")[9]) + Long.parseLong(lists.get(k).split("/")[10]);

                            String comm_string = final_lists.get(l).split("/")[10];
                            //System.out.println("COMMITS: "+comm_string);
                            long com = Long.parseLong(comm_string.split("_")[0]) + 1;
                            long ch = Long.parseLong(comm_string.split("_")[1]) + Long.parseLong(lists.get(k).split("/")[11]);
                            long add = Long.parseLong(comm_string.split("_")[2]) + Long.parseLong(lists.get(k).split("/")[12]);
                            long del = Long.parseLong(comm_string.split("_")[3]) + Long.parseLong(lists.get(k).split("/")[13]);
                            final_lists.set(l, lists.get(k).split("/")[0] + "/" + lists.get(k).split("/")[1] + "/" + lists.get(k).split("/")[6] + "/" + lists.get(k).split("/")[5] + "/" + lists.get(k).split("/")[3] + "/" + lists.get(k).split("/")[4] + "/" + up + "/" + gis + "/" + fol + "/" + fow + "/" + com + "_" + ch + "_" + add + "_" + del);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                for (int k = 0; k < final_lists.size(); k++) {
                    DataCollectionList.add(final_lists.get(k));
                }
                datas = new Object[DataCollectionList.size()];
                datas = DataCollectionList.toArray(datas);
                DataSet.add(datas);
            }

            Create_ExcelFile.createExcel2(DataSet, 0, new_file, sheet);
            /// DOTO::::::::: ###################################
        }
        return ct;
    }
    
    
    public static int process2(String project, String forkP, List<String> detailsList,List<String> dateL, String fDate, String lDate, String[] tokens, int ct, int date_interval, int interval, String new_file, String sheet) throws ParseException, java.text.ParseException, IOException {
        Object[] datas = null;
        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
        JSONParser parser = new JSONParser();
        datas = new Object[]{"Tag Date", "PR Open",
            "PR Closed", "Is_Open", "Is_Closed", "Forks", "", "Project",
            "Name/email/login/Location/Created_at/Updated_at/Public_repos/Public_gists/Followers/Following/Commits_Refactoring_Added_Deleted"

        };
        DataSet.add(datas);
//System.out.println(" SIZE: "+detailsList.size());
        if (detailsList.size() > 0) {
            
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            String date2 = fDate;
            Calendar c3 = Calendar.getInstance();
            c3.setTime(s.parse(lDate));
            c3.add(Calendar.DATE, (interval - 1));  // number of days to add
            String dt33 = s.format(c3.getTime());  // dt is now the new date

            Date dateTODAY = s.parse(lDate);

            Date Ddate1 = s.parse(date2);
            Date Ddate2 = s.parse(dt33);
            int i2 = 1, i = 0;
            List<String> nextDate = new ArrayList<>();
            nextDate.add(fDate);
            List<String> ddList = new ArrayList<>();
            List< List<String>> list_all = new ArrayList<>();
            //System.out.println(fDate+"\t"+lDate);
            do {

                //System.out.println(fDate.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String subString = fDate.toString().substring(10, (fDate.toString().length() - 1));
                /// First Dates...
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(fDate.toString()));
                c.add(Calendar.DATE, interval * i);  // number of days to add
                String dt2 = sdf.format(c.getTime());  // dt is now the new date
                /// Second Date

                Calendar c2 = Calendar.getInstance();
                c2.setTime(sdf.parse(fDate.toString()));
                c2.add(Calendar.DATE, interval * i2);  // number of days to add
                String dt22 = sdf.format(c2.getTime());  // dt is now the new date

                //int increamented = Integer.parseInt(dt.substring(17,19)) + 1;
                int increamented = Integer.parseInt(subString.substring(subString.length() - 2, subString.length())) + 1;///  index number 17 - 19 from "2016-11-13T10:31:35Z" is 35  increament the min by 1
                String i_Z = increamented + "Z";
                /// 
                String sub = subString.substring(subString.length() - 3, subString.length() - 1);/// index number 17 - 19 from "2016-11-13T10:31:35Z" is 35
                String replace_sub = subString.replace(sub, increamented + "");/// Replace the Last string which is the minutes..

                String n_dt2 = dt22 + "" + replace_sub + "";// Concate to the date to make it full

                /// Now we can use the right variable names for the two dates interval
                String date1 = dt2 + "" + subString + "Z";
                date2 = dt22 + "" + subString + "Z";

                /// Now we assigns to the next method to get the commits within the two dates above....
                Ddate1 = s.parse(date2);

                List<String> ll = new ArrayList<>();
                for (int j = detailsList.size() - 1; j >= 0; j--) {
                    //System.err.println(j+" : "+date1+" : "+date2+" \t "+detailsList.get(j)+"\t"+dateL.get(j));
                    
                    if (DateOperations.compareDates(date1, dateL.get(j)) == true
                            && DateOperations.compareDates(dateL.get(j), date2) == true) {
                        ll.add(detailsList.get(j));

                         //System.out.println(j+"   "+detailsList.get(j)+" \t"+date1+" : "+date2+" : "+dateL.get(j));

                    }
                }
                list_all.add(ll);
                ddList.add(date1 + " - " + date2);
                //System.err.println("    "+date1+" \t  "+date2);
                ll = new ArrayList<>();
                i++;
                i2++;

            } while (Ddate2.compareTo(Ddate1) > 0);
            //int i2 = 1, i1 = 0, next = 0;
            //List<List<String>> all_list = new ArrayList<>();
            String pr_details = PR_IS_Details.pr_details(project, tokens, ct, Constants.cons.TODAY_DATE);
            ct = Integer.parseInt(pr_details.split("/")[pr_details.split("/").length - 1]);
            int cc = 0;

            //System.out.println("Size1:  "+list_all.size());
            for (int j = 0; j < list_all.size(); j++) {
                List<String> lists = list_all.get(j);
                
                Set<String> emailSet = new LinkedHashSet<String>();
                for (int k = 0; k < lists.size(); k++) {
                    emailSet.add(lists.get(k).split("/")[1]);
                }
                //System.out.println("           list: "+j+" : "+lists.+" \t "+emailSet.size());
                Iterator iterator = emailSet.iterator();
                List<String> email_List = new ArrayList<>();
                List<String> final_lists = new ArrayList<>();
                while (iterator.hasNext()) {
                    email_List.add((String) iterator.next());
                    final_lists.add("Name/email/login/Location/Created_at/Updated_at/0/0/0/0/0_0_0_0");
                }
                List<String> DataCollectionList = new ArrayList<>();
                DataCollectionList.add(ddList.get(j));
                if (j == 0) {
                    DataCollectionList.add(pr_details.split("/")[0]);
                    DataCollectionList.add(pr_details.split("/")[1]);
                    DataCollectionList.add(pr_details.split("/")[2]);
                    DataCollectionList.add(pr_details.split("/")[3]);
                    DataCollectionList.add(pr_details.split("/")[4]);
                    DataCollectionList.add(project);
                    DataCollectionList.add(forkP);
                } else {
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                    DataCollectionList.add("-");
                }
                for (int k = 0; k < lists.size(); k++) {

                    for (int l = 0; l < final_lists.size(); l++) {
                        if (lists.get(k).split("/")[1].equals(email_List.get(l))) {
                            try{
                            long up = Long.parseLong(final_lists.get(l).split("/")[6]) + Long.parseLong(lists.get(k).split("/")[7]);
                            long gis = Long.parseLong(final_lists.get(l).split("/")[7]) + Long.parseLong(lists.get(k).split("/")[8]);
                            long fol = Long.parseLong(final_lists.get(l).split("/")[8]) + Long.parseLong(lists.get(k).split("/")[9]);
                            long fow = Long.parseLong(final_lists.get(l).split("/")[9]) + Long.parseLong(lists.get(k).split("/")[10]);
                            
                            String comm_string = final_lists.get(l).split("/")[10];
                            //System.out.println("COMMITS: "+comm_string);
                            long com = Long.parseLong(comm_string.split("_")[0]) + 1;
                            long ch = Long.parseLong(comm_string.split("_")[1]) + Long.parseLong(lists.get(k).split("/")[11]);
                            long add = Long.parseLong(comm_string.split("_")[2]) + Long.parseLong(lists.get(k).split("/")[12]);
                            long del = Long.parseLong(comm_string.split("_")[3]) + Long.parseLong(lists.get(k).split("/")[13]);
                            final_lists.set(l, lists.get(k).split("/")[0] + "/" + lists.get(k).split("/")[1] + "/" + lists.get(k).split("/")[6] + "/" + lists.get(k).split("/")[5] + "/" + lists.get(k).split("/")[3] + "/" + lists.get(k).split("/")[4] + "/" + up + "/" + gis + "/" + fol + "/" + fow + "/" + com + "_" + ch + "_" + add + "_" + del);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                for (int k = 0; k < final_lists.size(); k++) {
                    DataCollectionList.add(final_lists.get(k));
                }
                //System.err.println("  Sisssse:     "+final_lists);
                datas = new Object[DataCollectionList.size()];
                datas = DataCollectionList.toArray(datas);
                //System.err.println("  Sizzzze:     "+datas.length);
                DataSet.add(datas);
            }
            //System.out.println("Size2:  "+DataSet.size());

            Create_ExcelFile.createExcel2(DataSet, 0, new_file, sheet);
            /// DOTO::::::::: ###################################
        }
        return ct;
    }
}
