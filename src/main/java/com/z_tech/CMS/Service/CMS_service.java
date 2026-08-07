package com.z_tech.CMS.Service;

import io.jsonwebtoken.Jwts;

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
import com.z_tech.CMS.Models.handling;
import com.z_tech.CMS.Models.pages;
import com.z_tech.CMS.Repository.CMS_repository;
import com.z_tech.CMS.Repository.elementGraphix_repo;
import com.z_tech.CMS.Repository.element_repo;
import com.z_tech.CMS.Repository.handling_repo;
import com.z_tech.CMS.Repository.user_repository;

import jakarta.transaction.Transactional;

import com.z_tech.CMS.CMS_util.ImgHandling;
import com.z_tech.CMS.CMS_util.txt2HTML;
import com.z_tech.CMS.DTO.deplomentDTO;
import com.z_tech.CMS.DTO.imageData;
import com.z_tech.CMS.DTO.pageData;
import com.z_tech.CMS.DTO.storage_dto;

@Service
public class CMS_service {
   
       public final CMS_repository CMSaction;  public final elementGraphix_repo elementGraphix_action; public final user_repository usr_action; public final element_repo element_action; public final handling_repo handling_actions;
       public final txt2HTML page_handling; public final ImgHandling img_handling;
        
       public final SecretKey key;

    public CMS_service (CMS_repository act, txt2HTML con, ImgHandling iH, elementGraphix_repo eG_act, user_repository usr, SecretKey k, element_repo element_act, handling_repo h) { 
        this.CMSaction = act; this.page_handling = con; this.img_handling = iH; this.elementGraphix_action = eG_act; this.usr_action = usr; this.key = k; this.element_action = element_act; this.handling_actions = h;
    }

    public void newUser(app_users usr) throws Exception{
        try {
            UUID user_id =  usr_action.new_client(usr.username, usr.password);
            img_handling.createUserDir(user_id);
        } catch (Exception e) { e.printStackTrace(); }
        }
    
    imageData saveImg(MultipartFile file, String parent_dir) throws Exception{
        String original_file = file.getOriginalFilename(); 
        long file_size = file.getSize(); 
 
        element_graphix eG = new element_graphix(parent_dir, file_size, original_file); 
            System.out.println("\n" + "graphix:" + "\n" +
                    "file path: " + eG.file_path + "\n" +
                    "orginal name: " + original_file + "\n"
                );

        UUID graphix_id =  elementGraphix_action.add_graphix(eG.graphix_id, eG.file_path, eG.file_size, eG.original_file);
        
        imageData data = new imageData(eG.file_path, graphix_id, eG.original_file);

        System.out.println("\n" + "GRAPHIX: " + graphix_id + "\n");

       return data;

    }

    UUID createElement(element e, String usrID) {
        element Element = new element(e.title, e.description); 
        
        System.out.println("\n" + "create Element user: " + usrID + "\n");

        UUID element_id =  element_action.newElement(Element.element_id, Element.title, Element.title); 

        return element_id;
    }
    
    @Transactional
    public storage_dto staging(element e, MultipartFile file, String usrID) throws Exception {
        
            UUID owner = UUID.fromString(usrID); UUID page_id = UUID.randomUUID();
        
            handling h = new handling(owner, page_id);

            try { handling_actions.save(h); } catch (Exception handling_err) { handling_err.printStackTrace(); }

            System.out.println("handling page_id: " + h.storage_dir);
            
            try {
                UUID elements = createElement(e, usrID);
                imageData img = saveImg(file, h.editing_dir);

                pages p = new pages(elements, owner, img.graphix_id, h.editing_dir, page_id);
        
                try { CMSaction.save(p); } catch (Exception SavePage_err) { SavePage_err.printStackTrace(); }

                System.out.println("page creation page_id: " + p.page_id);
        
                try { 
                    pageData page = CMSaction.data(owner, p.page_id);

                    try {
                         page_handling.stagePage(page);  
                         img_handling.handleUpload(file, img.img_path);                  
                    } catch (Exception temp_err) { temp_err.printStackTrace(); }

                    if (page_handling.exists(page.page_path) && page_handling.exists(page.img_path)) {

                        storage_dto s = new storage_dto(img.img_path, p.path2stable, h.storage_dir, img.orginal_file, p.page_id, img.graphix_id);
                        return s;

                    } else { System.out.println("FAILED || to create temp file"); return null; }
 
                }catch (Exception getPageData_err) { getPageData_err.printStackTrace(); return null;}
    
            } catch (Exception PageObjects_err) { PageObjects_err.printStackTrace(); return null; }
    }

    @Transactional
    public void store_page(storage_dto storage) {
       
        try {
            elementGraphix_action.move_img2storage(storage.new_ImgPath, storage.graphix_id);
            CMSaction.move_page2storage(storage.storage_dir, storage.new_PagePath, storage.page_id);
        } catch (Exception err) {err.printStackTrace();}
        
        try { page_handling.store_page(storage); } 
        catch (Exception err) { err.printStackTrace(); }
    }  

    @Transactional
    public void deploy_page(String usr, String page) { 
        UUID user = UUID.fromString(usr);
        UUID page_id = UUID.fromString(page);
        deplomentDTO deployment =  CMSaction.deployment_data(user, page_id) ;
        
        deplomentDTO d = new deplomentDTO (
                deployment.img_path,
                deployment.file_path,
                deployment.deployment_dir, 
                deployment.graphix_name,
                deployment.page_id,
                deployment.graphix_id
        );
        
        System.out.println("deployment page_id = " + d.page_id);
        System.out.println("deployment deployment_dir = " + d.deployment_dir);
        System.out.println("deployment page_path = " + d.new_PagePath);
            
        try {
            elementGraphix_action.move_img2deployment(d.new_ImgPath, d.graphix_id);
            CMSaction.move_page2deployment(d.deployment_dir, d.new_PagePath, d.page_id);
        } catch (Exception err) {err.printStackTrace();}    
        
        try { page_handling.deployPage(d); } 
        catch (Exception err) { err.printStackTrace(); }
    } 

    public String login(String pw) {
        String id = usr_action.login(pw);
        
        String jws = Jwts.builder()
            .subject(id)
            .issuedAt(new Date())
            .expiration(Date.from
                    (Instant.now().plus(Duration.ofHours(26))))
            .signWith(key)
            .compact();

        return jws;
    }
} 

