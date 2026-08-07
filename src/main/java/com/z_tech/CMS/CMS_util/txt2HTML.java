package com.z_tech.CMS.CMS_util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

import com.z_tech.CMS.DTO.deplomentDTO;
import com.z_tech.CMS.DTO.pageData;
import com.z_tech.CMS.DTO.storage_dto;

@Component
public class txt2HTML {

    public void deployPage(deplomentDTO d) { 
        Path p1 = Paths.get(d.img_path); Path p2 = Paths.get(d.file_path);
        Path deployment = Paths.get(d.deployment_dir); 
        
        Path newImgPath_D = Paths.get(d.new_ImgPath);
        Path newFilePath_D = Paths.get(d.new_PagePath); 

        try { Files.createDirectories(deployment);} 
        catch (IOException err) {System.out.println("Failed 2 create dir"); err.printStackTrace(); }

        try {
            Files.copy(p1, newImgPath_D); //store img 2 deployment 
            Files.copy(p2, newFilePath_D); //store file 2 deployment
        
            if ( Files.exists(newFilePath_D) && Files.exists(newImgPath_D) )
            {   System.out.println("Page is deployed");
                System.out.println("filepath: " + newFilePath_D );
            }

        } catch (IOException e) { System.out.println("Failed 2 write files"); e.printStackTrace(); }
    }

    public void store_page(storage_dto s) {
        Path p1 = Paths.get(s.tmp_graphix); Path p2 = Paths.get(s.tmp_page);
        Path storage = Paths.get(s.storage_dir);
        Path newImgPath_S = Paths.get(s.new_ImgPath); Path newFilePath_S = Paths.get(s.new_PagePath);

        try { Files.createDirectories(storage); } 
        catch (IOException err) {System.out.println("Failed 2 create dir"); err.printStackTrace(); }

        try {
            Files.copy(p1, newImgPath_S); //store img 2 storage
            Files.copy(p2, newFilePath_S); //store file 2 storage
        
            if ( Files.exists(newFilePath_S) && Files.exists(newImgPath_S) ) 
            { System.out.println("Page is ready for deployment"); }

        } catch (IOException e) { System.out.println("Failed 2 write files"); e.printStackTrace(); }
    } 

    public boolean exists(String path) {
       Path p = Paths.get(path);
       boolean b = Files.exists(p);

        return b; 
    }

    public void stagePage(pageData d) throws Exception {

        String title = String.format("<h4>" + d.page_title + "</h4>");
        String description = String.format("</p>" + d.page_description + "</p>");
        String image = String.format( "<img src=" + d.img_path + ">");

        String page = 
            String.format (
                    "<!DOCTYPE html>"                                                        + "\n" +
                    "<html lang='en'>"                                                       + "\n" +
                    "<head>"                                                                 + "\n" +
                    "<meta charset='UTF-8'>"                                                 + "\n" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" + "\n" +
                    "<title>Document</title>"                                                + "\n" + 
                    "</head>"                                                                + "\n" +
                    "<body>"                                                                 + "\n" +

                    title                                                                    + "\n" +
                    description                                                              + "\n" +
                    image                                                                    + "\n" +

                    "<hr><hr>"                                                               + "\n" +
                
                    "<script src='test.js'> </script>"                                       + "\n" +

                    "</body>"                                                                + "\n" +
                    "</html>"
                );

        try {
            Path p = Paths.get(d.parent_dir);
            Files.createDirectories(p); 
            System.out.println("Directory Created: " + p.toString());
        } catch (Exception e) { System.out.println("Failed 2 Create Dir: " + d.parent_dir); e.printStackTrace(); }
        
        try {
            File f = new File(d.page_path);
            FileWriter writer = new FileWriter(f);
            writer.write(page); writer.close();
       
            System.out.println("File Created: " + f.toString());
        } catch (Exception e) { System.out.println("Failed 2 Create File: " + d.page_path); e.printStackTrace(); }
    }
}
