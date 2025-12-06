package com.univ.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF completely for stateless API
                .csrf(AbstractHttpConfigurer::disable)

                // Disable CORS (or configure it properly if needed)
                .cors(AbstractHttpConfigurer::disable)

                // Stateless session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules - Use AntPathRequestMatcher explicitly
                .authorizeHttpRequests(authorize -> authorize
                        // Allow H2 console using AntPathRequestMatcher
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

                        // Allow all auth endpoints without authentication
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()

                        // All other requests need authentication
                        .anyRequest().authenticated()
                )

                // Disable frame options for H2 console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }
}