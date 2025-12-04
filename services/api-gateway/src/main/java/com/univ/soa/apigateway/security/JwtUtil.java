package com.univ.soa.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    // The secret key is loaded from application.properties
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Retrieves the HMAC signing key from the application secret.
     * @return The signing Key object.
     */
    private Key getSignKey() {
        // Generates the Key using the secret loaded from configuration
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts all claims (payload data) from the JWT.
     * @param token The JWT string.
     * @return Claims object containing the token payload.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates if a token is syntactically correct and not expired.
     * @param token The JWT string.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            // Log the exception for debugging (e.g., SignatureException, ExpiredJwtException)
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}