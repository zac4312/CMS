package com.z_tech.CMS.CMS_util;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class token_handling {

    public String decrypt_token(String auth, SecretKey key) {
        CharSequence claimStr = auth.replaceAll("Bearer ", "");        
        Jws<Claims> token = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(claimStr);

        String sub = token.getPayload().getSubject(); 
        return sub;
    }

}
