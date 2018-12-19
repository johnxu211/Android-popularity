# Android-popularity
Title:

Studying Android App Popularity by Cross-Linking GitHub and Google Play Store

Abstract

The incredible success of the mobile App economy has been attracting software developers hoping for new or repeated success. Surviving in the fierce competitive App market involves in part planning ahead of time for the success of the App on the marketplace. Prior research has shown that App success can be viewed through its proxy--popularity. An important question, then, is what factors differentiates popular from unpopular Apps? GitHub, a software project forge, and Google Play store, an app market, are both crowdsourced, and provide some publicly available data that can be used to cross-link source code and app download popularity.
In this study, we examined how technical and social features of Open Source Software Apps, mined from two crowdsourced websites, relate to App popularity. We observed that both the technical and the social factors play significant roles in explaining App popularity.  However, the combined factors have a low effect size in explaining App popularity, as measured by average user rating on Google Play. Interestingly on GitHub, we found that social factors have a higher power in explaining the popularity compared to all the technical factors we investigated.

=====================================================================================

Requirements

• poi-3.14.jar or higher

• poi-ooxml-3.14.jar or higher

• poi-ooxml-schemas-3.14.jar or higher

• xmlbeans-2.6.0.jar or higher

• json-simple-1.1.1.jar or higher

• jsoup-1.11.3.jar or higher

=========================================================================

Data Mining Scripts

•	Search_for_Repos.java – Collecting Android App repositories on GitHub

•	FindMLP_GooglePlayApps.java – Collects the Apps that are on Google Play store

•	FindMLP_GooglePlayApps.java – Collects all the mainline variants on Google Play store

•	Fetch_CommitsDetail.java - Collects commit details of the Apps that include, contributors login, names, email address, commit dates, changed LOC/commit, etc

•	PullRequests.java - Collects the pull requests of the Apps

•	Collect_GooglePlayStatistics.java - Collects Apps Google Play meta-data

•	Count_ActiveFP.java - Collects active forks

•	Developer_MJ_MN.java - Collects the code authorship metrics (i.e., major, minor and most valuable author-MVA)

========================================================================


Please cite our work  - Bibtex

@inproceedings{Businge:SANER-ANdroid,
 
 author = {Businge, John and Openja, Moses and Kavaler, David and Bainomugisha, Engineer and Khomh, Foutse and Filkov, Vladimir},
 
 title = {Studying Android App Popularity by Cross-Linking GitHub and Google Play Store},
 
 booktitle = {SANER},
 
 year = {2019}
}


