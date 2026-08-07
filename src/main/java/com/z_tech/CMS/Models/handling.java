package com.z_tech.CMS.Models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "handling")
public class handling {
    
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID handling_id;

    @NotNull
    public String deployment_dir;

    @NotNull
    public String editing_dir;

    @NotNull
    public String storage_dir;

    @NotNull
    public String dump_dir;

    @NotNull
    public UUID owned_by;

    public handling(){}
    public handling(UUID owner, UUID page_id) {
        this.deployment_dir = String.format("/var/www/html/CMS_test/" + owner + "/pages/" + page_id + "/");
        this.editing_dir = String.format("/home/zacm/.tmp/CMS/" + owner + "/pages/" + page_id + "/");
        this.storage_dir = String.format("/home/zacm/IOfiles/CMS/" + owner + "/pages/stable/" + page_id + "/");
        this.dump_dir = String.format("/home/zacm/IOfiles/CMS/" + owner + "/pages/past/" + page_id + "/");
        this.owned_by = owner; 
    }
}
