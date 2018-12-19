/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.source_analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author john
 */
public class Google_Stats {

    public static void main(String[] args) throws Exception {
        Object[] datas = null;
        ArrayList<Double> numApps = new ArrayList<Double>();
        ArrayList<String> appPackages = new ArrayList<String>();
        Set<String> devIds = new HashSet<String>();

        devIds.add("/store/apps/developer?id=Fredrik+Fornwall");
        devIds.add("/store/apps/developer?id=YESCO");

        countDeveloperApps(devIds, numApps, appPackages);
        //appDetails();
    }

    public static void countDeveloperApps(Set<String> devIds, ArrayList<Double> numApps,
            ArrayList<String> appPackages) {

        Document doc = null;
        Object[] datas = null;

        String gp = "https://play.google.com";
        try {
            for (String devId : devIds) {
                System.out.println(gp + devId);
                doc = Jsoup.connect(gp + devId).userAgent(
                        "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36")
                        .maxBodySize(0).get();

                int num = 0;
                Elements appNameElements = doc.getElementsByAttributeValue("class", "reason-set");
                for (Element appNameElement : appNameElements) {
                    Elements appNameElements1 = appNameElement.getElementsByTag("a");
                    String packageID = appNameElements1.get(0).attr("href");
                    //System.out.println(devId+" : "+packageID);
                    appPackages.add(packageID);
                    System.out.println(num+"\t : "+packageID);
                    num++;
                }
                numApps.add((double) num);

                //System.out.println("");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return allobj;

    }
}
