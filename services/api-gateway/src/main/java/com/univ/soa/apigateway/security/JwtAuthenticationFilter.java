package com.univ.soa.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ===== ALLOW ALL /auth/** ROUTES WITHOUT TOKEN =====
        // (register, login, signin, etc.)
        if (path.startsWith("/auth/")) {
            System.out.println("PUBLIC ROUTE: " + path);
            return chain.filter(exchange);
        }

        // ===== PROTECTED ROUTES BELOW =====

        // 1. Must contain Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return unauthorized(exchange, "Missing Authorization header for route: " + path);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 2. Must be in the form "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Invalid Authorization header format.");
        }

        // 3. Extract token
        String token = authHeader.substring(7);

        // 4. Validate token
        if (!jwtUtil.isTokenValid(token)) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        // 5. Token valid -> Continue
        System.out.println("GATEWAY: Valid token for route: " + path);
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        System.err.println("UNAUTHORIZED: " + message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
