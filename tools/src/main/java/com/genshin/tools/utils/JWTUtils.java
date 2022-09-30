package com.genshin.tools.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


public class JWTUtils {
    public static final long EXPIRATION_TIME=3600*1000;
    public static final String key="ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIFAGHbtjfOwKr5ClneUJBsTSzRkQPu4Du9K1O727vX6/ ed25519-key-20220928"; // pastToken, newToken


    public static String generateToken(String userEmail, List<String> authority) throws Exception {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userEmail", userEmail);
        claims.put("expiration", new Date(Instant.now().toEpochMilli()+EXPIRATION_TIME));
        claims.put("authority", authority);
        System.out.println(key);
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,key)
                .compact();
        return token;
    }

    public static boolean validateToken(String token) {
        Claims claims=getClaimsFromToken(token);
        if (claims.get("expiration")!=null){
            Date date= new Date((Long) claims.get("expiration"));
            return date.after(new Date());
        }

        return false;
    }

    public static Claims getClaimsFromToken(String token) {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
    }
    
    public static String getUserEmail(String token){
        Claims claims=getClaimsFromToken(token);
        return String.valueOf(claims.get("userEmail"));
    }




}
