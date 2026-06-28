package com.z_tech.CMS.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.z_tech.CMS.DTO.pageData;
import com.z_tech.CMS.Models.pages;

@Repository
public interface CMS_repository extends JpaRepository<pages, UUID>{ 

    @Query
    (value = "SELECT e.description, e.title, eg.file_path, eg.graphix_id, u.user_id FROM pages p LEFT JOIN element e ON p.elements = e.element_id LEFT JOIN element_graphix eg ON e.graphix = eg.graphix_id LEFT JOIN app_users u ON p.owned_by = u.user_id WHERE u.user_id = :userID and p.page_id = :page_id", nativeQuery = true)
    pageData data (
            @Param("userID") UUID userID,
            @Param("page_id") UUID pageID
    ); 


}


