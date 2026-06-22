package com.z_tech.CMS.CMS_util;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class token_handling {
    
    public String decrypt_token(Header auth) {
        Object claim = auth.get("authorization");  CharSequence claimStr = claim.toString().replaceAll("Bearer ", "");
        SecretKey key = Jwts.SIG.HS256.key().build();
        
        Jws<Claims> token = Jwts.parser()
            .decryptWith(key)
            .build()
            .parseSignedClaims(claimStr);

        String tokenStr = token.toString();

        return tokenStr;
    }

}
