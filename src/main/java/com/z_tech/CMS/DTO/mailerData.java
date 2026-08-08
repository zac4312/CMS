package com.z_tech.CMS.DTO;

public class mailerData {

    public String subject; public String body;

    public mailerData(){}
    
    public mailerData(String sub, String body) {
        this.subject = sub;
        this.body = body;
    }
}
