/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.popularity.util;

/**
 *
 * @author john
 */
public class Constants {

    
    public abstract class cons {

        public static final String REPO_FILE_NAME = "";
        public static final String CREATE_MISSING_FILES = "";

        public static final String FILE_1 = "Commits_Cleaned_1-500_1.xlsx";
        public static final String FILE_2 = "Commits_Cleaned_500-1000_1.xlsx";
        public static final String FILE_3 = "Collection_1200-1600.xlsx";
        public static final String FILE_4 = "Collection_1600-1805.xlsx";
        public static final String FILE_5 = "PROMISE.xlsx";

        public static final String FILE_PUL_1 = "pulls_Collection_1-400.xlsx";
        public static final String FILE_PUL_2 = "pulls_Collection_400-800.xlsx";
        public static final String FILE_PUL_3 = "pulls_Collection_1200-1600.xlsx";
        public static final String FILE_PUL_4 = "pulls_Collection_1600-1805.xlsx";
        public static final String FILE_PUL_5 = "pulls_PROMISE.xlsx";
        
        public static final String FILE_ISSUES_1 = "issues_Collection_1-400.xlsx";
        public static final String FILE_ISSUES_2 = "issues_Collection_400-800.xlsx";
        public static final String FILE_ISSUES_3 = "issues_Collection_1200-1600.xlsx";
        public static final String FILE_ISSUES_4 = "issues_Collection_1600-1805.xlsx";
        public static final String FILE_ISSUES_5 = "issues_PROMISE.xlsx";
        
        public static final String FILE_FORKS_1 = "forks_Collection_1-400.xlsx";
        public static final String FILE_FORKS_2 = "forks_Collection_400-800.xlsx";
        public static final String FILE_FORKS_3 = "forks_Collection_1200-1600.xlsx";
        public static final String FILE_FORKS_4 = "forks_Collection_1600-1805.xlsx";
        public static final String FILE_FORKS_5 = "forks_PROMISE.xlsx";
        

        public static final String FILE_PATH = "/Users/john/Desktop/DESKTOP/Files/00deleted_commits/00Percentage_final/";
        public static final String FILE_PATH2 = "/Users/john/Desktop/DESKTOP/Files/00deleted_commits/00Cleaned_final/";

        public static final String PERCENTAGE_FILE1 = "Percentage_Commits_Cleared_1-500_1.xlsx";
        public static final String PERCENTAGE_FILE2 = "Percentage_Commits_Cleared_500-1000_1.xlsx";
        public static final String PERCENTAGE_FILE3 = "Percentage_800-1200_1.xlsx";
        public static final String PERCENTAGE_FILE4 = "Percentage_1200-1600_1.xlsx";
        public static final String PERCENTAGE_FILE5 = "Percentage_1600-1805_1.xlsx";
        
        
        public static final String DELETED_FILE1 = "Percentage-Collection_1-400_1.xlsx";
        public static final String DELETED_FILE2 = "Del_Collection_400-800_1.xlsx";
        public static final String DELETED_FILE3 = "Percentage-Collection_1200-1600_1.xlsx";
        public static final String DELETED_FILE4 = "Percentage-Collection_1600-1805_1.xlsx";
        public static final String DELETED_FILE5 = "Percentage-PROMISE1.xlsx";

        public static final String CREATE_PERCENTAGE_DELETED = "";

        public static final String EMPTY_LOGIN = "login######";
        public static final String TODAY_DATE = "2018-10-31T00:00:00Z";
        public static final String TODAY_FORMAT = "2018-10-31";
        public static final int COMMITS_INVERVAL = 14; 
    }
    public static String[] getToken(){
       String[] tokens = {//"f919075dfb04ed6dc3b249944248a66cb87ffdd7",  
        };
        return tokens;
    }
    public static String[] selected_fork(){
        String fork1 = "forks_Collection_1-400.xlsx";
        String fork2 = "forks_Collection_1200-1600.xlsx";
        String fork3 = "forks_Collection_1600-1805.xlsx";
        String fork4 = "forks_Collection_1-400.xlsx";
        String fork5 = "forks_Collection_1-400.xlsx";
        
        String[] files = {fork1};
        return files;
    }

}
