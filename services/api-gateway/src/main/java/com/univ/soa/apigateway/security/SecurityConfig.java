package com.univ.soa.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // Laisser la logique d'autorisation au filtre global JwtAuthenticationFilter
        // Nous configurons Spring Security pour qu'il autorise tout ce qui est public via le filtre
        // et bloque implicitement le reste (sauf si le filtre le valide)

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        // Autoriser les chemins publics DIRECTEMENT ici pour éviter les conflits
                        .pathMatchers("/auth/signin", "/auth/register").permitAll()

                        // Tous les autres chemins DOIVENT être authentifiés.
                        // Le filtre JwtAuthenticationFilter s'exécutera avant cette vérification
                        // pour définir le principal authentifié.
                        .anyExchange().authenticated()
                )
                .build();
    }
}
