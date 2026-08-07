package com.z_tech.CMS.Controllers;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

import com.z_tech.CMS.CMS_util.ImgHandling;
import com.z_tech.CMS.CMS_util.token_handling;
import com.z_tech.CMS.DTO.storage_dto;
import com.z_tech.CMS.Models.app_users;
import com.z_tech.CMS.Models.element;
import com.z_tech.CMS.Service.CMS_service;

@Controller
@RequestMapping("/cms")
public class CMS_controller {
    
    public final CMS_service service; public final token_handling token_handling; public final ImgHandling img_handling;

@Autowired
       public final SecretKey key;

    public CMS_controller(CMS_service s, token_handling t_h, SecretKey k, ImgHandling img) { this.service = s; this.token_handling = t_h; this.key = k; this.img_handling = img;}

@PostMapping("/newUser")
    public ResponseEntity<?> newUser(@RequestBody app_users user) throws Exception{
       try {
         service.newUser(user); 
         return ResponseEntity.ok().body(null);

       } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail" + e); }
    }

@PostMapping("/usr_login")
    public ResponseEntity<?> usr_login(@RequestBody String password) {
    
    try {

     String token = service.login(password);
       return ResponseEntity.ok(token);       

    } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL" +  e); }

}

@PostMapping(value = "/pre_confirmation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> pre_confirmation(
            @RequestPart("element") element e,
            @RequestPart("file") MultipartFile file, 
            @RequestHeader("Authorization") String auth) throws Exception { 
        
        try {
            String user = token_handling.decrypt_token(auth, key);
            storage_dto storage = service.staging(e, file, user);

            service.store_page(storage);
            return ResponseEntity.ok(storage.page_id);

        } catch (Exception err) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL FAIL FAIL: " + err); }
    } 

@PostMapping(value = "/page-deployment")
    public ResponseEntity<?> deployment(
                @RequestHeader("Authorization") String auth,
                @RequestBody String page 
            ) throws Exception {
        try {

            System.out.println(page);

            String user = token_handling.decrypt_token(auth, key);
            service.deploy_page(user, page);
        } catch (Exception e) { e.printStackTrace(); }

        return ResponseEntity.ok("deployed");
    }

@PostMapping( value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> returnImage(@RequestPart("file_path") String file) throws Exception {
        try {
            byte[] img = img_handling.returnImage(file); 
                return ResponseEntity.ok(img);
        } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("return image failed: " + e ); }      
    }

}

