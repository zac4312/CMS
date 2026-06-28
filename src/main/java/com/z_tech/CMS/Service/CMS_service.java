package com.z_tech.CMS.Service;

import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.z_tech.CMS.Models.app_users;
import com.z_tech.CMS.Models.element;
import com.z_tech.CMS.Models.element_graphix;
import com.z_tech.CMS.Models.pages;
import com.z_tech.CMS.Repository.CMS_repository;
import com.z_tech.CMS.Repository.elementGraphix_repo;
import com.z_tech.CMS.Repository.element_repo;
import com.z_tech.CMS.Repository.user_repository;

import jakarta.transaction.Transactional;

import com.z_tech.CMS.CMS_util.ImgHandling;
import com.z_tech.CMS.CMS_util.txt2HTML;
import com.z_tech.CMS.DTO.imageData;
import com.z_tech.CMS.DTO.pageData;

@Service
public class CMS_service {
   
       public final CMS_repository CMSaction;  public final elementGraphix_repo elementGraphix_action; public final user_repository usr_action; public final element_repo element_action;
       public final txt2HTML page_handling; public final ImgHandling img_handling;
        
       public final SecretKey key;

    public CMS_service (CMS_repository act, txt2HTML con, ImgHandling iH, elementGraphix_repo eG_act, user_repository usr, SecretKey k, element_repo element_act) { 
        this.CMSaction = act; this.page_handling = con; this.img_handling = iH; this.elementGraphix_action = eG_act; this.usr_action = usr; this.key = k; this.element_action = element_act; 
    }

    public void newUser(app_users usr) throws Exception{
        try {
            UUID user_id =  usr_action.new_client(usr.username, usr.password);
            img_handling.createUserDir(user_id);
        } catch (Exception e) { e.printStackTrace(); }
        }
    
    imageData saveImg(MultipartFile file, String usrID) throws Exception{
        String original_file = file.getOriginalFilename(); 
        long file_size = file.getSize(); 
        String parent_dir = String.format("/home/zacm/IOfiles/CMS/" + usrID + "/pages/stable_file/");
 
        element_graphix eG = new element_graphix(parent_dir, file_size, original_file);

        System.out.println("\n" + "graphix:" + "\n" +
                    "parent dir: " + eG.parent_dir + "\n" +
                    "file path: " + eG.file_path + "\n" +
                    "orginal name: " + original_file + "\n"
                );

        UUID graphix_id =  elementGraphix_action.add_graphix(eG.graphix_id, eG.parent_dir, eG.file_path, eG.file_size, eG.original_file);
        
        imageData data = new imageData(eG.file_path, graphix_id);

        System.out.println("\n" + "GRAPHIX: " + graphix_id + "\n");

        try {
         img_handling.handleUpload(file, eG.file_path);
        } catch (IOException e) { System.out.println("SERVICE upload stage FAILED FAILED"); e.printStackTrace(); }

        return data;

    }

    UUID createElement(element e, String usrID) {
        element Element = new element(e.title, e.description, e.graphix); 
        
        System.out.println("\n" + "create Element user: " + usrID + "\n");

        UUID element_id =  element_action.newElement(Element.title, Element.title, Element.graphix);

        return element_id;
    }
    
    @Transactional
    public void newPage(element e, MultipartFile file, String usrID) throws Exception {
        System.out.println("\n" + "newPage user: " + usrID + "\n");
        
        UUID id = UUID.fromString(usrID); 
   
        try {
            imageData image_data = saveImg(file, usrID); //Element Graphix DB save

            System.out.println("saveImg: " + image_data.graphix_id);
                    
            element elmnt = new element(e.title, e.description, image_data.graphix_id); // ELEMENT DB  SAVE 
            UUID element_id = createElement(elmnt, usrID);
        
            System.out.println("createElement: " + element_id);

            pages p = new pages(element_id, id);  //PAGES DB SAVED
            CMSaction.save(p);
 
            pageData data = CMSaction.data(id, p.page_id);
            page_handling.makePage(data);
    
            System.out.println("Join query data: " + "\n" +
                    "graphix id: "+ data.graphix_id +"\n"+
                    "graphix file path: " + data.img_path +"\n"+
                    "element title: " + data.page_title +"\n"+
                    "element description:  "+ data.page_description +"\n"+
                    "user Id " + data.user_id +"\n");
                                            
       } catch (Exception err) { 
           System.out.println("New Page Err "); err.printStackTrace(); } 
    }

    public String login(String pw) {
        String id = usr_action.login(pw);
        
        System.out.println("Service USER: " + id);
        System.out.println("PW: " + pw);

        String jws = Jwts.builder()
            .subject(id)
            .issuedAt(new Date())
            .expiration(Date.from
                    (Instant.now().plus(Duration.ofHours(26))))
            .signWith(key)
            .compact();

       System.out.println("JWS: " + jws + " key: " + key);

        return jws;
    }
} 

