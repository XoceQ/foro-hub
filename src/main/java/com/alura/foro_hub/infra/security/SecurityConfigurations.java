package com.alura.foro_hub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfigurations(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF con la nueva API
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configuración sin estado
                )
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/login").permitAll()  // Permitir acceso público al login
                                .requestMatchers(HttpMethod.POST, "/topics/**").authenticated() // Requiere autenticación para crear topics
                                .requestMatchers(HttpMethod.PUT, "/topics/**").authenticated()  // Requiere autenticación para actualizar topics
                                .requestMatchers(HttpMethod.DELETE, "/topics/**").authenticated()  // Requiere autenticación para borrar topics
                                .anyRequest().permitAll()  // Permite acceso público a otras rutas
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Agregar el filtro personalizado para JWT
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}