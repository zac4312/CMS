package com.z_tech.CMS.Service;

import io.jsonwebtoken.Jwts;

import java.awt.Image;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.z_tech.CMS.Models.element;
import com.z_tech.CMS.Models.element_graphix;
import com.z_tech.CMS.Repository.CMS_repository;
import com.z_tech.CMS.Repository.elementGraphix_repo;
import com.z_tech.CMS.Repository.user_repository;

import jakarta.transaction.Transactional;

import com.z_tech.CMS.CMS_util.ImgHandling;
import com.z_tech.CMS.CMS_util.txt2HTML;

@Service
public class CMS_service {
   
       public final CMS_repository CMSaction;  public final elementGraphix_repo elementGraphix_action; public final txt2HTML convert;  
       public final user_repository usr_action; public final ImgHandling img_handling;
        
       public final SecretKey key;

    public CMS_service (CMS_repository act, txt2HTML con, ImgHandling iH, elementGraphix_repo eG_act, user_repository usr, SecretKey k) { 
        this.CMSaction = act; this.convert = con; this.img_handling = iH; this.elementGraphix_action = eG_act; this.usr_action = usr; this.key = k; 
    }
 
    public UUID saveImg(MultipartFile file, String usrID) throws Exception{
        String original_file = file.getOriginalFilename(); 
        long file_size = file.getSize(); 
        String parent_dir = String.format("/home/zacm/IOfiles/CMS/" + usrID + "/");
 
        element_graphix eG = new element_graphix(parent_dir, file_size, original_file);

        System.out.println("graphix:" + "\n" +
                    "parent dir: " + eG.parent_dir + "\n" +
                    "file path: " + eG.file_path + "\n" +
                    "orginal name: " + original_file 
                );

        UUID graphix_id =  elementGraphix_action.add_graphix(eG.graphix_id, eG.parent_dir, eG.file_path, eG.file_size, eG.original_file);

        System.out.println("GRAPHIX: " + graphix_id);

        try {
         img_handling.handleUpload(file, eG);
        } catch (IOException e) { System.out.println("SERVICE upload stage FAILED FAILED"); e.printStackTrace(); }

        return graphix_id;

    }

    public void createElement(element e, String usrID) {
        UUID by_user = UUID.fromString(usrID);
        element Element = new element(e.title, e.description, e.graphix, by_user); 
        
        System.out.println("create Element user: " + usrID);

        CMSaction.save(Element);
        System.out.println("SERVICE: create element succeded");
    }
    
    @Transactional
    public void newPage(element e, String usrID) throws Exception {
        System.out.println("newPage user: " + usrID);

        try {
           createElement(e, usrID);
           convert.makePage(e);
            
       } catch (IOException err) { 
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
/*
    public byte[] returnImage(UUID graphix_id) {
        element_graphix eg = elementGraphix_action.findById(graphix_id).orElseThrow(null);

        i
    }*/
} 
