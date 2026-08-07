package com.z_tech.CMS.DTO;

import java.util.UUID;

public class pageData{
    
    public String page_title;
    public String page_description;
    public String img_path;
    public String page_path;
    public String parent_dir;
    public UUID user_id;
    public UUID graphix_id;

    public pageData(){}
    public pageData(String title, String description, String img_path, String page_path, String parent_dir, UUID uID, UUID gID) {
        this.page_title = title;
        this.page_description = description;
        this.img_path = img_path;
        this.page_path = page_path;
        this.parent_dir = parent_dir;
        this.user_id = uID;
        this.graphix_id = gID;
    }
}
