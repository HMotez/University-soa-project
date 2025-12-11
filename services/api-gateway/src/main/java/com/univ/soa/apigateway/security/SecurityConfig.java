package com.univ.soa.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

// CORS imports
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        // ===== PUBLIC ENDPOINTS =====
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // ===== PUBLIC TEST ENDPOINTS =====
                        .pathMatchers("/actuator/**").permitAll()

                        // ===== EVERYTHING ELSE REQUIRES AUTH =====
                        .anyExchange().authenticated()
                )
                .build();
    }

    // ===============================================
    // CORS CONFIG (Allows frontend http://localhost:5173)
    // ===============================================
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
