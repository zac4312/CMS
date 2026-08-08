package com.z_tech.CMS.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.z_tech.CMS.DTO.mailerData;
import com.z_tech.CMS.Service.MailService;

@Controller
@RequestMapping(value = "/mailer")
public class MailerController {
  
    public final MailService mailer;
    public MailerController(MailService mailService) { this.mailer = mailService;}

@PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> send_mail(mailerData d) {
        try {
            mailer.sendPlainText(d);
            return ResponseEntity.ok("goods");   
        } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MAILER FAILED: " + e);  }
   }

}
