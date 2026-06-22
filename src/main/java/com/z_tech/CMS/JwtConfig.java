package com.z_tech.CMS;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class JwtConfig {
    
    @Value("${jwt.secret}")
    private String key;

    @Bean
    public SecretKey jwtKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
