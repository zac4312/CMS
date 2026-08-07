package com.z_tech.CMS.DTO;
import java.util.UUID;

public class deplomentDTO {

    public String img_path;
    public String file_path;
    public String deployment_dir;
    public String graphix_name;
    public UUID page_id;
    public UUID graphix_id;

    public String new_ImgPath;
    public String new_PagePath;

    public deplomentDTO(){}
    public deplomentDTO(String img, String file, String deployment, String graphix_name, UUID page, UUID graphix) {
         this.file_path = file;
         this.img_path = img;
         this.deployment_dir = deployment;
         this.page_id = page;
         this.graphix_id = graphix;         
         this.graphix_name = graphix_name;

         this.new_ImgPath = String.format(this.deployment_dir + this.graphix_name);
         this.new_PagePath = String.format(this.deployment_dir + this.page_id);
    }
}
