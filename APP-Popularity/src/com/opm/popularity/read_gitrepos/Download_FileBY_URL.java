/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.read_gitrepos;

import com.opm.popularity.core.Call_URL;
import com.opm.popularity.core.JSONUtilsConversion;
import com.opm.popularity.core.ParseXmlDocument;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

/**
 *
 * @author john
 */
public class Download_FileBY_URL {

    public static Set<String> downloads(String project, String shaa, String mlp_package, String[] tokens, int ct) throws ParseException {
        int p = 1; // Page number parameter
        int i = 0; // Commit Counter

        double count = 0;
        Set<String> p_list = new LinkedHashSet<String>();

        if (ct == (tokens.length)) {/// the the index for the tokens array...
            ct = 0; //// go back to the first index......
        }
        String jsonString = new Call_URL().callURL("https://api.github.com/repos/" + project + "/commits/" + shaa + "?access_token=" + tokens[ct++]);

        JSONParser parser = new JSONParser();
        if (JSONUtilsConversion.isValidJSONObject(jsonString) == true) {
            JSONObject jSONObject = (JSONObject) parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) jSONObject.get("files");
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject obj = (JSONObject) jsonArray.get(j);
                String filename = (String) obj.get("filename");
                String status = (String) obj.get("status");
                String raw_url = (String) obj.get("raw_url");

                String[] split_file = filename.split("/");
                if (filename.contains("AndroidManifest.xml")) {
                    String manifest_xml = Call_URL.callURL(raw_url);
                   //System.out.println("  " + filename + " \t " + status + " \t" + raw_url);
                    //System.out.println("               "+url_string);
                    Document doc = ParseXmlDocument.document(manifest_xml);
                    if (doc != null) {
                        doc.getDocumentElement().normalize();
                        String package_name = doc.getDocumentElement().getAttribute("package");
                        System.out.println("     Package : "+ package_name);
                        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                        String isValid = Call_URL.callURL("https://play.google.com/store/apps/details?id=" + package_name);
                        if (!isValid.equals("Error")) {
                            //found = "found";
                            if (!package_name.equals(mlp_package)) {
                                p_list.add(package_name);
                                System.out.println("   on google_play!");
                            }
                            //System.out.println("               Found on Google play!");
                        } else {
                            //System.out.println("               Not Found...");
                        }
                    }
                }
            }

        }
        p_list.add(ct + "");
        return p_list;
    }

}
