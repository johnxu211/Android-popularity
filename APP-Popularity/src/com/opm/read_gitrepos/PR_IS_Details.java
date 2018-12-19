/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.simple.parser.ParseException;
import com.opm.repos.Repos_Isissues;
import com.opm.repos.Repos_PullRequests;

/**
 *
 * @author john
 */
public class PR_IS_Details {
    public static String pr_details(String project, String[] tokens, int ct , String toDay) throws ParseException, java.text.ParseException{
        List<String> pullsList = new ArrayList<>();
        List<String> forksList = new ArrayList<>();
        List<String> issuesList = new ArrayList<>();

        pullsList = Repos_PullRequests.getDatas(project, tokens, ct);
        ct = Integer.parseInt(pullsList.get(pullsList.size() - 1));
        pullsList.remove(pullsList.size() - 1);

        forksList = com.opm.repos.Repos_Forks.getDatas(project, tokens, ct);
        ct = Integer.parseInt(forksList.get(forksList.size() - 1));
        forksList.remove(forksList.size() - 1);

        issuesList = Repos_Isissues.getDatas(project, tokens, ct);
        ct = Integer.parseInt(issuesList.get(issuesList.size() - 1));
        issuesList.remove(issuesList.size() - 1);

        long pl = 0, pc = 0, ff = 0, is = 0, ic = 0, s1 = 0, w = 0;
        // Pullrequests
        for (int a = 0; a < pullsList.size(); a++) {
            String[] pull_split = pullsList.get(a).split(":");
            String state = pull_split[0];
            String pulls_date = pull_split[1];

            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cc1 = Calendar.getInstance();
            cc1.setTime(ss.parse(pulls_date));

            Calendar cc4 = Calendar.getInstance();
            cc4.setTime(ss.parse(toDay));

            if ((cc4.after(cc1) || cc4.equals(cc1))) {
                if (state.equals("open")) {
                    pl += 1;
                } else if (state.equals("closed")) {
                    pc += 1;
                }
            }

        }

        /// Issuess
        for (int a = 0; a < issuesList.size(); a++) {
            String[] issues_split = issuesList.get(a).split(":");
            String state = issues_split[0];
            String issues_date = issues_split[1];

            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cc1 = Calendar.getInstance();
            cc1.setTime(ss.parse(issues_date));

            Calendar cc4 = Calendar.getInstance();
            cc4.setTime(ss.parse(toDay));

            if ((cc4.after(cc1) || cc4.equals(cc1))) {
                if (state.equals("open")) {
                    is += 1;
                } else if (state.equals("closed")) {
                    ic += 1;
                }
            }

        }

        /// Forks
        for (int a = 0; a < forksList.size(); a++) {
            String forks = forksList.get(a);
            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cc1 = Calendar.getInstance();
            cc1.setTime(ss.parse(forks));

            Calendar cc4 = Calendar.getInstance();
            cc4.setTime(ss.parse(toDay));

            if ((cc4.after(cc1) || cc4.equals(cc1))) {
                ff += 1;
            }

        }
        
        return pl+"/"+pc+"/"+is+"/"+ic+"/"+ff+"/"+ct;
    }
}
