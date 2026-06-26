package com.z_tech.CMS.Controllers;

import java.util.UUID;

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
import com.z_tech.CMS.Models.element;
import com.z_tech.CMS.Service.CMS_service;

@Controller
@RequestMapping("/Item")
public class CMS_controller {
    
    public final CMS_service service;    public final token_handling token_handling; public final ImgHandling img_handling;
@Autowired
       public final SecretKey key;

    public CMS_controller(CMS_service s, token_handling t_h, SecretKey k, ImgHandling img) { this.service = s; this.token_handling = t_h; this.key = k; this.img_handling = img;}

@PostMapping("/usr_login")
public ResponseEntity<?> usr_login(@RequestBody String password) {
    
    try {

     String token = service.login(password);
       return ResponseEntity.ok(token);       

    } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL" +  e); }

}

@PostMapping(value = "/upload_img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 public ResponseEntity<?> AddImg (
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String auth 
        ){

    try {

      String user = token_handling.decrypt_token(auth, key);

        System.out.println("USER: " + user );

      UUID eG = service.saveImg(file, user);

      return ResponseEntity.ok(eG);

    } catch (Exception e) {System.out.println("auth: " + auth); return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL FAIL FAIL: " + e); }
}

@PostMapping("/page")
    public ResponseEntity<?> ShowPage(
            @RequestBody element e, 
            @RequestHeader("Authorization") String auth) throws Exception{ 

        try {
            String user = token_handling.decrypt_token(auth, key);
            service.newPage(e, user);

        System.out.println(
                "element added: "              + "\n" +
                  "user: "     + user          + "\n" + 
                  "desc: "     + e.description + "\n" +
                  "title: "    + e.title       + "\n" +
                  "graphix: "  + e.graphix     + "\n" 
        );

            return ResponseEntity.ok("goodz");

        } catch (Exception err) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL FAIL FAIL: " + err); }
    } 
    
@PostMapping( value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> returnImage(@RequestPart("file_path") String file) throws Exception {
        try {
            byte[] img = img_handling.returnImage(file); 
                return ResponseEntity.ok(img);
        } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("return image failed: " + e ); }      
    }

}

