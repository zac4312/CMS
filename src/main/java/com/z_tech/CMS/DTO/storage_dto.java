package com.z_tech.CMS.DTO;

import java.util.UUID;

public class storage_dto {
 
    public String tmp_page;
    public String tmp_graphix;

    public String storage_dir;
    public UUID page_id;
    public UUID graphix_id;

    public String new_ImgPath;
    public String new_PagePath;

    public storage_dto(){}
    public storage_dto(String img, String file, String storage, String graphix_name, UUID page, UUID graphix){
         this.tmp_page = file;
         this.tmp_graphix = img;
         this.storage_dir = storage;
         this.page_id = page;
         this.graphix_id = graphix; 
         this.new_ImgPath = String.format(this.storage_dir + this.graphix_id);
         this.new_PagePath = String.format(this.storage_dir + this.page_id);
    }
}


