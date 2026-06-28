package com.z_tech.CMS.Models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "element")
public class element {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID element_id;

    public String title;
    public String description;

    public UUID graphix;
    
    public element GetElement() {
        return this;
    }
    
    public element() {}
    public element(String title, String description, UUID graphix) {
        this.title = title;
        this.description = description;
        this.graphix = graphix;
    }

}

