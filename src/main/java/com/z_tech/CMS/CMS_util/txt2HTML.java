package com.z_tech.CMS.CMS_util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.z_tech.CMS.DTO.pageData;

@Component
public class txt2HTML {
    
    public void makePage(pageData d) throws Exception {

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
                    "<div id='image'></div>"                                                 + "\n" +
                    "<hr><hr>"                                                               + "\n" +

                    "<script src='test.js'> </script>"                                       + "\n" +

                    "</body>"                                                                + "\n" +
                    "</html>"
                );

        try {
            File f = new File(d.page_path);
            FileWriter writer = new FileWriter(f);
            writer.write(page); writer.close();

        } catch (IOException err) { System.out.println("ERROR: "); err.printStackTrace(); }
    }


}
