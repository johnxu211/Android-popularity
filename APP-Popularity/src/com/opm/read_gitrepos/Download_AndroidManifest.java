/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.read_gitrepos;

import com.opm.core.Call_URL;
import com.opm.core.JSONUtils;
import com.opm.core.XmlDoc;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author john
 */
public class Download_AndroidManifest {

    public static Set<String> downloads(String project, String[] tokens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter

        double count = 0;
        List<String> p_list = new ArrayList<>();
        List<String> p_list2 = new ArrayList<>();
        List< List<String>> lists = new ArrayList<>();
        Set<String> pSet = new LinkedHashSet<String>();
        while (true) {////loop thru the pagess....
            if (ct == (tokens.length)) {/// the the index for the tokens array...
                ct = 0; //// go back to the first index......
            }
            String jsonString = new Call_URL().callURL("https://api.github.com/search/code?q=main+in:path+package+in:file+filename:AndroidManifest+repo:" + project + "+extension:xml&access_token=" + tokens[ct++] + "&page=" + p + "&per_page=100");
            if (jsonString.equals("Error")) {
                break;
            }
            JSONParser parser = new JSONParser();
            if (JSONUtils.isValidJSONObject(jsonString) == true) {

                JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
                if (jSONObject.toString().equals("{}")) {
                    /// Break out of the loop, when empty array is found!
                    break;
                }
                //System.out.println(jsonString);
                JSONArray jsonArray = (JSONArray) jSONObject.get("items");
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject obj = (JSONObject) jsonArray.get(j);
                    if (ct == (tokens.length)) {/// the the index for the tokens array...
                        ct = 0; //// go back to the first index......
                    }
                    String url_string = Call_URL.callURL((String) obj.get("url") + "&access_token=" + tokens[ct++]);
                    System.out.println((String) obj.get("url"));
                    if (JSONUtils.isValidJSONObject(url_string)) {
                        JSONObject url_obj = (JSONObject) parser.parse(url_string);
                        if (ct == (tokens.length)) {/// the the index for the tokens array...
                            ct = 0; //// go back to the first index......
                        }
                        String manifest_xml = Call_URL.callURL((String) url_obj.get("download_url") + "?access_token=" + tokens[ct++]);
                        System.out.println((String) url_obj.get("download_url"));
                        Document doc = XmlDoc.document(manifest_xml);
                        if (doc != null) {
                            doc.getDocumentElement().normalize();
                            String package_name = doc.getDocumentElement().getAttribute("package");
                            System.out.println("      Package :" + package_name);
                            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                            String isValid = Call_URL.callURL("https://play.google.com/store/apps/details?id=" + package_name);
                            if (!isValid.equals("Error")) {
                                //found = "found";
                                pSet.add(package_name);
                            }
                        }
                        //p_list2.add(package_name);
                    }

                }

            }

            p++;//// Goto the next Page.......
        } /// ******** End of while loop ......
        pSet.add(ct + "");
        lists.add(p_list);
        lists.add(p_list2);
        return pSet;

    }
}
