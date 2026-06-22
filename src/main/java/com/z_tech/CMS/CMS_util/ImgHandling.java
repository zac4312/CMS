package com.z_tech.CMS.CMS_util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgHandling {

    public void handleUpload(MultipartFile file, String img_path) throws Exception{
        String mime = file.getContentType(); 
        Path p = Paths.get(img_path);
        
        try {
            Files.copy(file.getInputStream(), p);
        } catch (IOException e) { System.out.println("Upload Failed"); e.printStackTrace(); }
    }

}
