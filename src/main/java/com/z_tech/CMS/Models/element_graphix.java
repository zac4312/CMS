package com.z_tech.CMS.Models;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "element_graphix")
public class element_graphix {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
        public UUID graphix_id;

    @NotNull
        public String parent_dir;
        
    @Column(nullable = false, unique = true)
        public String file_path;

    @NotNull
        public long file_size;
    
    @NotNull
        public String original_file;

    public element_graphix Get_eGraphix() {
        return this;
    }
     
    public element_graphix() {}
    public element_graphix(String parent_dir, long file_size, String name) {

        this.graphix_id = UUID.randomUUID();
        this.parent_dir = parent_dir;
        this.file_path = parent_dir + this.graphix_id.toString() + ".jpg";
        this.file_size = file_size;
        this.original_file = name;
    }
}
