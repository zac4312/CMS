package com.z_tech.CMS.Models;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pages")
public class pages {
    
    @Id
    public UUID page_id; 
    
    @NotNull
    public String path2stable;

    @NotNull
    public String parent_dir;

    public enum page_status {
       deployed,
       pending,
       created,
       archived,
       failed
    }
        
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public page_status status_is;
    public UUID elements;
    public UUID owned_by;
    public UUID graphix;

    public pages(){}
    public pages(UUID elements, UUID owned_by, UUID graphix, String parent_dir, UUID id) {
        this.page_id = id;
        this.elements = elements;
        this.owned_by = owned_by;
        this.graphix = graphix;
        this.path2stable = String.format(parent_dir + this.page_id + ".html");
        this.status_is = page_status.pending;
        this.parent_dir = parent_dir;
    }
}
