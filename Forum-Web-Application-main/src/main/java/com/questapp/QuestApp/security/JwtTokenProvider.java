package com.questapp.QuestApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Generate a secure key at runtime if needed
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${questapp.app.expiresIn}")
    private Long expiration; // Token expiration time (sec).

    public String generateToken(Authentication auth) {
        JwtUserDetails user = (JwtUserDetails) auth.getPrincipal();
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key)  // Use the generated key for signing
                .compact();
    }

    Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)  // Use the same key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(key)  // Use the same key for validation
                    .build()
                    .parseClaimsJws(authToken);
            return !isTokenExpired(authToken);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String authToken) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(key)  // Use the same key for parsing
                .build()
                .parseClaimsJws(authToken)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
