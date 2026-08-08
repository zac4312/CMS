package com.z_tech.CMS.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.z_tech.CMS.DTO.mailerData;

@Service
public class MailService {
    
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPlainText(mailerData d) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mesias4388@gmail.com1");
        message.setSubject(d.sub);
        message.setText(d.body);

        mailSender.send(message);
    }
}
