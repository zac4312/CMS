package com.z_tech.CMS.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.z_tech.CMS.Models.app_users;

@Repository
public interface user_repository extends JpaRepository<app_users, UUID> { 
    
    @Query("Select user_id from app_users where password = :password")
    String login(@Param("password") String password);
}
