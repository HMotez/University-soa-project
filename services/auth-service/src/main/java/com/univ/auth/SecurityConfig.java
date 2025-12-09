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

                // Disable CORS (or configure properly if needed)
                .cors(AbstractHttpConfigurer::disable)

                // Stateless session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(authorize -> authorize

                        // Allow H2 console
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()

                        // Allow auth service
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()

                        // 🔥 ADD THIS LINE → Student Service does NOT require token
                        .requestMatchers(new AntPathRequestMatcher("/student-service/**")).permitAll()

                        // Everything else still requires authentication
                        .anyRequest().authenticated()
                )

                // Allow frames for H2 console
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
