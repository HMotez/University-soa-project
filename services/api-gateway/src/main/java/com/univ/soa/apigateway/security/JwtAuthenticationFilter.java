package com.univ.soa.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Liste des chemins qui NE DOIVENT PAS être protégés.
    // Correspondance exacte des chemins.
    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/auth/signin",
            "/auth/register"
            // Note: /auth/me is protected - requires authentication
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 1. VÉRIFICATION DES ROUTES PUBLIQUES
        if (OPEN_API_ENDPOINTS.contains(path)) {
            System.out.println("GATEWAY: Public route accessed: " + path + ". Bypassing JWT filter.");
            return chain.filter(exchange);
        }

        // --- Début de la Zone Protégée ---

        // 2. Vérification de la Présence de l'En-tête Authorization
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return unauthorized(exchange, "Missing Authorization header for protected route: " + path);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. Vérification du format "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() < 8) {
            return unauthorized(exchange, "Invalid Authorization header format. Must be 'Bearer <token>'");
        }

        // Extraction du token (substring à partir de l'index 7)
        String token = authHeader.substring(7);

        // 4. Validation du Token
        if (!jwtUtil.isTokenValid(token)) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        // 5. Token valide → Procéder
        System.out.println("GATEWAY: Valid token for route: " + path);
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        System.err.println("UNAUTHORIZED ACCESS: " + message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}