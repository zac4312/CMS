package com.z_tech.CMS.CMS_util;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class token_handling {
    public String decrypt_token(String auth, SecretKey key) throws Exception {
        CharSequence claimStr = auth.replaceAll("Bearer ", "");        
            Claims token = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(claimStr)
                .getPayload();

        String sub = token.getSubject();     
        return sub;
    }
}
