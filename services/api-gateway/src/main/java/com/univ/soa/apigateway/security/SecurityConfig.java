package com.univ.soa.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

// CORS related imports added here
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
// End CORS imports

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // Existing SecurityWebFilterChain remains unchanged
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // Autoriser TOUTES les requêtes - la sécurité est gérée par JwtAuthenticationFilter
                        .anyExchange().permitAll()
                )
                .build();
    }

    // ===============================================
    // 💡 CORS Configuration Bean Added Here
    // This allows your frontend (localhost:5173) to connect to the Gateway (localhost:8080).
    // ===============================================
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Frontend URL allowed to access the API (Vite Development Server Port)
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:5173"));

        // Methods required for your backend communication (GET, POST, and OPTIONS for preflight)
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers required (Authorization for JWT, Content-Type for JSON)
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Set max age for preflight requests (improves performance)
        corsConfig.setMaxAge(3600L);

        // Apply this configuration to ALL paths (/**) routed through the Gateway
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
    // ===============================================
}