package com.multisystem.auth.authservice.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    // Will move this key to secure file
    private final String SECRET = "secret";

    public String generateToken(String userName){
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setSubject(userName)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
}
