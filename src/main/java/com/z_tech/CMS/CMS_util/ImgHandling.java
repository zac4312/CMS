package com.z_tech.CMS.CMS_util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgHandling {

    public void createUserDir(UUID userID) throws Exception {
        Path p1 = Paths.get("/home/zacm/IOfiles/CMS/" + userID + "/pages/stable_files/");    
        Path p2 = Paths.get("/home/zacm/IOfiles/CMS/" + userID + "/pages/past/");    
 
        try {
            Files.createDirectories(p1);
            System.out.println("CREATE DIR: " + p1);

            Files.createDirectories(p2);
            System.out.println("CREATE DIR: " + p2);
        } catch (IOException e) { System.out.println("User Directory has already been created"); e.printStackTrace(); }
    }

    public void handleUpload(MultipartFile file, String file_path) throws Exception {
        String mime = file.getContentType();
        
        Path p = Paths.get(file_path); 
        try {
            Files.copy(file.getInputStream(), p);
        } catch (IOException e) { System.out.println("Upload Failed: " + p); e.printStackTrace(); }
    }

    public byte[] returnImage(String file_path) throws Exception {        
    
        Path p = Paths.get(file_path);
        byte[] img = Files.readAllBytes(p);

        return img;
    }
}
