package com.univ.soa.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // Disable CSRF as it's not typically required for stateless API Gateways
        // Permit all exchanges because the custom GlobalFilter (JwtAuthenticationFilter)
        // will handle the actual blocking/allowing based on the JWT token presence/validity.
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll()
                );
        return http.build();
    }
}