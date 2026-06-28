package com.z_tech.CMS.DTO;

import java.util.UUID;

public class pageData{
    
    public String page_title;
    public String page_description;
    public String file_path;
    public UUID user_id;
    public UUID graphix_id;

    public pageData(){}
    public pageData(String title, String description, String file_path, UUID uID, UUID gID) {
        this.page_title = title;
        this.page_description = description;
        this.file_path = file_path;
        this.user_id = uID;
        this.graphix_id = gID;
    }
}
