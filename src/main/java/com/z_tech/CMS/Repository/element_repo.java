package com.z_tech.CMS.Repository;

import org.springframework.stereotype.Repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.z_tech.CMS.Models.element;

@Repository
public interface element_repo extends JpaRepository<element, UUID> { 

    @Query(value = "INSERT INTO element (element_id, title, description) values (:element_id, :title, :description) returning element_id", nativeQuery = true)
    UUID newElement(
            @Param("element_id") UUID element_id,
            @Param("title") String title,
            @Param("description") String descripiton
        ); 
}
