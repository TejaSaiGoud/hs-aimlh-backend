package com.anurag.homeService.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

      @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
      private String secret;

      @Value("${jwt.expiration:86400000}")
      private long expiration;

      private SecretKey getSigningKey() {
            byte[] keyBytes = secret.trim().getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);
      }

      public String generateToken(String email, String role) {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expiration);

            return Jwts.builder()
                        .subject(email)
                        .claim("role", role)
                        .issuedAt(now)
                        .expiration(expiryDate)
                        .signWith(getSigningKey())
                        .compact();
      }

      private Claims extractAllClaims(String token) {
            return Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
      }

      public String extractEmail(String token) {
            return extractAllClaims(token).getSubject();
      }

      public String extractRole(String token) {
            return extractAllClaims(token).get("role", String.class);
      }

      public boolean isValid(String token) {
            try {
                  Claims claims = extractAllClaims(token);
                  return !claims.getExpiration().before(new Date());
            } catch (Exception e) {
                  return false;
            }
      }
}
