/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opm.core;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author john
 */
public class Downloads {
    public static String downloas(String project, String downloads_path){
        String file_name = downloads_path+project.split("/")[0]+"_"+project.split("/")[1]+".zip";
        String message = "downloads success!";
        try {
            Path path = Paths.get(file_name);
            URI u = URI.create("https://api.github.com/repos/"+project+"/zipball/master");
            try (InputStream in = u.toURL().openStream()) {
                Files.copy(in, path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Downloads Failed: "+e;
        }
        return message;
    }
}
