package com.z_tech.CMS.Models;

import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "pages")
public class pages {
    
    @Id
    public UUID page_id; 
    
    @NotNull
    public String parent_dir;

    @NotNull
    public String path2stable;

    public UUID elements;
    public UUID owned_by;

    public pages(){}
    public pages(UUID elements, UUID owned_by) {
        this.page_id = UUID.randomUUID();
        this.elements = elements;
        this.owned_by = owned_by;
        this.parent_dir =String.format("/home/zacm/IOfiles/CMS/" + owned_by + "/pages/stable_file/");
        this.path2stable = String.format(this.parent_dir + this.page_id.toString());
    }
}
