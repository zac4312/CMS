package com.z_tech.CMS.CMS_util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.z_tech.CMS.Models.element;

@Component
public class txt2HTML {
    
    public void makePage(element e) throws Exception {

        String title = String.format("<h4>" + e.title + "</h4>");
        String description = String.format("</p>" + e.description + "</p>");

        String page = 
            String.format(
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

                    "</body>"                                                                + "\n" +
                    "</html>"
                );

        try {
            File f = new File("/var/www/html/CMS_test/page.html");
            FileWriter writer = new FileWriter(f);
            writer.write(page); writer.close();

        } catch (IOException err) { System.out.println("ERROR: "); err.printStackTrace(); }
    }


}
