package com.z_tech.CMS.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.z_tech.CMS.DTO.deplomentDTO;
import com.z_tech.CMS.DTO.pageData;
import com.z_tech.CMS.Models.pages;

@Repository
public interface CMS_repository extends JpaRepository<pages, UUID>{ 
    
    @Query( 
    value = 
        """
        select eg.file_path, p.path2stable, h.deployment_dir, eg.original_file, u.user_id, eg.graphix_id 
        from pages p
        left join element e on p.elements = e.element_id
        left join element_graphix eg on p.graphix = eg.graphix_id
        left join app_users u on p.owned_by = u.user_id
        left join handling h on h.of_page = p.page_id
        where u.user_id = :userID and p.page_id = :page_id 
        """,
    nativeQuery = true)
    deplomentDTO deployment_data(
        @Param("userID") UUID user,
        @Param("page_id") UUID page
    );

    @Modifying
    @Query(
    value = 
        """
        UPDATE pages SET
        parent_dir = :parent_dir,
        path2stable = :path,
        status_is = 'deployed'
        where page_id = :page_id
        """,
    nativeQuery = true)
    
    void move_page2storage(
        @Param("parent_dir") String parent_dir,
        @Param("path") String path,
        @Param("page_id") UUID page_id
    );

    @Modifying
    @Query(
    value = 
        """
        UPDATE pages SET
        parent_dir = :parent_dir,
        path2stable = :path,
        status_is = 'deployed'
        where page_id = :page_id
        """,
    nativeQuery = true)
    void move_page2deployment (
            @Param("parent_dir") String parent_dir,
            @Param("path") String path,
            @Param("page_id") UUID page_id
        );

    @Query
    (value = 
        """
        SELECT e.title, e.description, eg.file_path, p.path2stable, p.parent_dir, u.user_id, eg.graphix_id
        FROM pages p
        LEFT JOIN element e ON p.elements = e.element_id
        LEFT JOIN element_graphix eg ON p.graphix = eg.graphix_id
        LEFT JOIN app_users u ON p.owned_by = u.user_id
        WHERE u.user_id = :userID and p.page_id = :page_id
        """,
    nativeQuery = true)
    pageData data (
            @Param("userID") UUID userID,
             @Param("page_id") UUID pageID
    ); 
}


