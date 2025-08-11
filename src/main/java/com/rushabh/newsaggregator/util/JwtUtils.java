package com.rushabh.newsaggregator.util;

import com.rushabh.newsaggregator.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

public class JwtUtils {


    public static String generateToken(User user, String secret) {
        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .subject(user.getEmail()).signWith(getSigningKey(secret))
                .claim("roles",user.getRoles().stream().map(role-> "ROLE_"+role.getName().toUpperCase()).toList())
                .compact();
    }

    public static Claims verifyToken(String token, String secret) {
        try {
           Claims payload =  Jwts
                    .parser()
                    .verifyWith((SecretKey) getSigningKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return payload;
        } catch (ExpiredJwtException e) {
           System.out.println("Token has expired");
            return null;
        } catch (SecurityException e) {
            System.out.println("Invalid token signature");
            return null;
        } catch (MalformedJwtException e) {
            System.out.println("Malformed JWT token");
            return null;
        } catch (Exception e) {
            System.out.println("JWT validation error: " + e.getMessage());
            return null;
        }

    }

    private static Key getSigningKey(String secret) {
        byte[] keyByte = secret.getBytes();
        return Keys.hmacShaKeyFor(keyByte);
    }
}
