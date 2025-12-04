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

        // 1. Public Routes: Allow /auth/login and /auth/register to pass immediately
        if (path.contains("/auth/login") || path.contains("/auth/register")) {
            return chain.filter(exchange);
        }

        // 2. Check for Token Presence
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return unauthorized(exchange, "Missing Authorization header");
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. Check for "Bearer " format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Invalid Authorization header format");
        }

        // Extract the token (substring from index 7)
        String token = authHeader.substring(7);

        // 4. Validate the Token
        if (!jwtUtil.isTokenValid(token)) {
            return unauthorized(exchange, "Invalid or expired token");
        }

        // 5. Token is valid → proceed to the next filter/route
        return chain.filter(exchange);
    }

    /**
     * Sets the response status to 401 Unauthorized and completes the request.
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // Optionally, you can write the error message to the response body here.
        // For simplicity, we just complete the response with 401.
        return exchange.getResponse().setComplete();
    }
}