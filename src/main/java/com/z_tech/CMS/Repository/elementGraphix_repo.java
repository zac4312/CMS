package com.z_tech.CMS.Repository;

import org.springframework.stereotype.Repository;

import com.z_tech.CMS.Models.element_graphix;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface elementGraphix_repo extends JpaRepository<element_graphix, UUID> {
    
    @Query("insert into element_graphix (graphix_id, parent_dir, file_path, file_size) values (:graphix_id, :parent_dir, :file_path, :file_size)")
    UUID add_graphix(
            @Param("graphix_id") UUID graphix_id,
            @Param("parent_dir") String parent_dir,
            @Param("file_path") String file_path,
            @Param("file_size") long file_size 
        );

}
