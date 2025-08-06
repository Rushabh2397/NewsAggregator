package com.rushabh.newsaggregator.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtils {


    public static String generateToken(String subject, String secret) {
        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .subject(subject).signWith(getSigningKey(secret))
                .compact();
    }

    private static Key getSigningKey(String secret) {
        byte[] keyByte = secret.getBytes();
        return Keys.hmacShaKeyFor(keyByte);
    }
}
