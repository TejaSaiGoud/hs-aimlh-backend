package com.anurag.homeService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

      @Value("${jwt.secret}")
      private String secret;

      @Value("${jwt.expiration}")
      private long expiration;

      private SecretKey getSigningKey() {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
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
