package com.riwi.backend.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    // Rutas públicas que no requieren autenticación
    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/login",               // Ruta de login
            "/auth/register",            // Ruta de registro
            "/swagger-ui.html",          // Página principal de Swagger
            "/swagger-ui/**",            // Todos los recursos estáticos de Swagger
            "/v3/api-docs/**",           // API Docs
            "/swagger-resources/**",     // Recursos Swagger
            "/webjars/**",               // Archivos webjar
            "/configuration/**"          // Configuraciones Swagger
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF (ya que estamos usando JWT)
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a las rutas públicas sin autenticación
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        // Requerir autenticación para todas las demás rutas
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Usar stateless para JWT
                )
                .authenticationProvider(authenticationProvider) // Usar tu proveedor de autenticación personalizado
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Filtro JWT antes de la autenticación
                .build();
    }
}
