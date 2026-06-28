package com.z_tech.CMS.Models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "app_users")
public class app_users {
    @Id
    public UUID user_id;
    
    @NotNull
    public String username;

    @NotNull
    public String password;

    public app_users(){}
    public app_users(String name, String pw) {
        this.password = pw;
        this.username = name;
    }
}


