package com.z_tech.CMS.Service;

import io.jsonwebtoken.Jwts;

import java.io.IOException;
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
import com.z_tech.CMS.DTO.DTO_element.NewElementObj;

@Service
public class CMS_service {
   
       public final CMS_repository CMSaction;  public final elementGraphix_repo elementGraphix_action; public final txt2HTML convert;  
       public final user_repository usr_action; public final ImgHandling img_handling;

       public final SecretKey key;

    public CMS_service (CMS_repository act, txt2HTML con, ImgHandling iH, elementGraphix_repo eG_act, user_repository usr, SecretKey k) { 
        this.CMSaction = act; this.convert = con; this.img_handling = iH; this.elementGraphix_action = eG_act; this.usr_action = usr; this.key = k; 
    }
 
    public UUID saveImg(MultipartFile file, String usrID) throws Exception{
        String name = file.getOriginalFilename();  
        long file_size = file.getSize(); 
        String parent_dir = String.format("/home/zacm/IOfiles/" + usrID + "/");

        String file_path = String.format(parent_dir + name); 
        
        element_graphix eG = new element_graphix(file_path, parent_dir, file_size);
        UUID graphix_id =  elementGraphix_action.add_graphix(eG.graphix_id, eG.parent_dir, eG.file_path, eG.file_size);
        
        try {
         img_handling.handleUpload(file, file_path);
        } catch (IOException e) { System.out.println("SERVICE upload stage FAILED FAILED"); e.printStackTrace(); }

        return graphix_id;

    }

    public void createElement(NewElementObj e, String usrID) {
        UUID by_user = UUID.fromString(usrID);
        element Element = new element(e.title, e.description, e.graphix, by_user);
        CMSaction.save(Element);
        System.out.println("SERVICE: create element succeded");
    }
    
    @Transactional
    public void newPage(NewElementObj e, String usrID) throws Exception {
        try {
           createElement(e, usrID);
           convert.makePage(e);

       } catch (IOException err) { 
           System.out.println("ERR "); err.printStackTrace(); } 
    }

    public String login(String pw) throws Exception {
        
        String id = usr_action.login(pw);   
        String jws = Jwts.builder()
            .subject(id)
            .signWith(key)
            .compact();
    
        return jws;

    }        
} 
