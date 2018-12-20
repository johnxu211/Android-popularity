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

•	com.opm.popularity.source_datacollection.Search_AndroidRepos.java – Collecting Android App repositories on GitHub

•	com.opm.popularity.source_datacollection.Collect_Repos_Forks.java – Collects the the forks of the main line projects.

•	com.opm.popularity.source_datacollection.CollectML_FPCommitsFullDetails.java – Collects all the commits details of both the main line project and its forks.that include, contributors login, names, email address, commit dates, changed LOC/commit, etc

•	com.opm.popularity.source_datacollection.CollectFork_UniqueCommits.java - Identify all the unique commits of the fork projects 

•	com.opm.popularity.source_datacollection.Collect_ForksFirstLastCommits.java - Collect the first and the last commit dates of the fork projects.

•	com.opm.popularity.source_datacollection.CollectPullrequest_Statisticts.java - Collects Apps pull requests including the direction of the pull request.

•	com.opm.popularity.source_datacollection.CollectProjects_Languages.java - Collects the languange of the main line projects.

=========================================================================

Data Analysis Scripts

•	com.opm.popularity.source_analysis.MaJor_Minor_MVAStatistics.java – Identify user experience and Catergorise as major, minor, Most valuable Author (MVA) etc.

•	com.opm.popularity.source_analysis.Merge_Developers_BYLogin.java – Merges the repos developers based on the same login details.

•	com.opm.popularity.source_analysis.Merge_MLP_FPCommits.java – Merges commits details of the mainline and the fork projects 

•	com.opm.popularity.source_analysis.EliminateRepos_Less6Commits.java - Eliminates all the repos with less than six (6) commits.

•	com.opm.popularity.source_analysis.UniqueMerged_Statistics.java - Identify both the unique and the merged commits of a fork project.

========================================================================


Please cite our work  - Bibtex

@inproceedings{Businge:SANER-ANdroid,
 
	author = {Businge, John and Openja, Moses and Kavaler, David and Bainomugisha, Engineer and Khomh, Foutse and Filkov, Vladimir},

	title = {Studying Android App Popularity by Cross-Linking GitHub and Google Play Store},

	booktitle = {SANER},

	year = {2019}
}


