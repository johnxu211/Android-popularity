package com.opm.source_datacollection;


import com.opm.core.Call_URL;
import com.opm.excel_.Create_Excel;
import com.opm.read_gitrepos.Read_ReposTags;
import com.opm.util.Constants;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * 
 * @author Openja Moses
 * This file search for all the android apps from github 
 * having at least 2 forks.....
 * 
 * 
 */
public class Search_AndroidRepos {
    
    private static int counter = 0;
    public static void main(String[] args) throws Exception {
        searchRepos();
    }
    private static void searchRepos() throws Exception {
        int ct = 0;
        Object[] datas = null;
        String path_new = "/Users/john/Desktop/DESKTOP/Files/00New_Repos/";
        JSONParser parser = new JSONParser();
        
        ArrayList<String> repoNames = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> descript = new ArrayList<String>();
        ArrayList<String> logins = new ArrayList<String>();
        ArrayList<Double> forks = new ArrayList<Double>();
        ArrayList<Double> size = new ArrayList<Double>();
        ArrayList<Integer> stars = new ArrayList<Integer>();
        ArrayList<Integer> openIssues = new ArrayList<Integer>();
        ArrayList<String> created_at = new ArrayList<String>();
        ArrayList<String> updated_at = new ArrayList<String>();
        ArrayList<String> language = new ArrayList<String>();
        ArrayList<Double> tags = new ArrayList<Double>();
        
        ArrayList< Object[]> DataSet = new ArrayList<Object[]>();
        datas = new Object[]{"Repos_Name", "Names", "Created_at", "login", "Fork", "Size", "Tags", "Language", "Description"};
        DataSet.add(datas);

        try {
           
            int x = 0; // control to break out of the infinite loop
            int p = 1; // Page number parameter	            
            while (true) {
                
                if (counter == Constants.getToken().length - 1) {
                    counter = 0;
                }
                
                if (p == 10) {
                    break;
                }
                String jsonString = Call_URL.callURL("https://api.github.com/search/repositories?q=android%20app+forks:>=91+fork:false&sort=forks&order=asc&page=" + p++ + "&per_page=100&access_token=" + Constants.getToken()[counter++]);
                
                //System.out.println(p + "=https://api.github.com/search/repositories?q=android%20app+forks:>=91+fork:false&sort=forks&order=asc&page=" + p);
                if (jsonString.equals("Error")) {
                    break;
                }
                //System.out.println(jsonString);
                String jsonData = "";
                String line = "";
                
                String inputLine;
                JSONObject obj = (JSONObject) parser.parse(jsonString);
                
                if (obj.toString().equals(null)) {
                    x = 1;//System.out.println("Empty JSON Object Returned: "+a.toString());
                    break;
                }
                // Loop through each item
                JSONArray items_array = (JSONArray) obj.get("items");

                //for (Object jsonObj : JSONArray) {
                for (int i = 0; i < items_array.size(); i++) {
                    JSONObject jsonObj = (JSONObject) items_array.get(i);
                    
                    String fullName = (String) (String) jsonObj.get("full_name");
                    
                    repoNames.add(fullName);
                    
                    String repoName = (String) (String) jsonObj.get("name");
                    names.add(repoName);
                    size.add(Double.parseDouble((Long) jsonObj.get("size") + ""));
                    
                    double count_tags = 0;
                    if ((String) jsonObj.get("tags_url") != null) {
                        count_tags = Double.parseDouble(Read_ReposTags.counts((String) jsonObj.get("tags_url"), ct, Constants.getToken()).split("/")[0]);
                        ct = Integer.parseInt(Read_ReposTags.counts((String) jsonObj.get("tags_url"), ct, Constants.getToken()).split("/")[1]);
                    }
                    tags.add(count_tags);
                    created_at.add((String) (String) jsonObj.get("created_at"));
                    JSONObject jsonObj_owner = (JSONObject) jsonObj.get("owner");
                    logins.add((String) jsonObj_owner.get("login"));
                    forks.add(Double.parseDouble((Long) jsonObj.get("forks_count") + ""));
                    String lang = "";
                    if ((String) jsonObj.get("language") != null) {
                        lang = (String) jsonObj.get("language");
                    }
                    language.add(lang);
                    String description = "";
                    if (jsonObj.get("description") != null) {
                        description = (String) jsonObj.get("description");
                        if (!((String) jsonObj.get("description")).toUpperCase().contains("DEPRECATED")
                                && !description.toLowerCase().contains("wrapper") && !description.toLowerCase().contains("library")
                                && !description.toLowerCase().contains("android client") && !description.toLowerCase().contains("sdk")) {
                            description = (String) jsonObj.get("description");
                        } else {
                            description = "";
                        }
                    }
                    descript.add((String) (String) jsonObj.get("description"));
                    
                }
            }

            //System.out.println(repoNames.size()+"\t"+names.size());
            for (int i = 0; i < repoNames.size(); i++) {
                datas = new Object[]{repoNames.get(i), names.get(i), created_at.get(i), logins.get(i), forks.get(i), size.get(i), tags.get(i), language.get(i), descript.get(i)};
                DataSet.add(datas);
            }
            String file_name = "repos.xlsx";
            String new_sheetName = "repos";
            Create_Excel.createExcel2(DataSet,0, path_new + file_name, new_sheetName);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
