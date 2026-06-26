package com.z_tech.CMS.CMS_util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.z_tech.CMS.Models.element_graphix;

@Component
public class ImgHandling {

    public void handleUpload(MultipartFile file, element_graphix eg) throws Exception{
        String mime = file.getContentType();

        Path parent_dir = Paths.get(eg.parent_dir);    
        try {
            Files.createDirectory(parent_dir);                
        } catch (IOException e) { System.out.println("User Directory has already been created"); }

        Path p = Paths.get(eg.file_path); 
        try {
            Files.copy(file.getInputStream(), p);
        } catch (IOException e) { System.out.println("Upload Failed"); e.printStackTrace(); }
    }

    public byte[] returnImage(String file_path) throws Exception {        
    
        Path p = Paths.get(file_path);
        byte[] img = Files.readAllBytes(p);

        return img;
    }
}
