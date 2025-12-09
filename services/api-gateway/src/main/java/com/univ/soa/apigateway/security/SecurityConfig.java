package com.univ.soa.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        // ===== PUBLIC ENDPOINTS =====
                        .pathMatchers("/auth/**").permitAll()     // allow ALL /auth/*
                        .pathMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // ===== PUBLIC TEST ENDPOINTS (optional) =====
                        .pathMatchers("/actuator/**").permitAll()

                        // ===== EVERYTHING ELSE REQUIRES AUTH =====
                        .anyExchange().authenticated()
                )
                .build();
    }
}
