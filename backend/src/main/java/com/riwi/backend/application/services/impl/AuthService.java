package com.riwi.backend.application.services.impl;

// Importaciones necesarias para el funcionamiento del servicio
import com.riwi.backend.application.dtos.requests.LoginRequest;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.infrastructure.helpers.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service // Anotación que marca esta clase como un servicio de Spring
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager; // Manejador de autenticación inyectado

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Implementación personalizada de UserDetailsService

    @Autowired
    private JwtUtil jwtUtil; // Utilidad para el manejo de JWT

    // Método para autenticar al usuario y generar un token
    public String login(LoginRequest loginRequest) {
        // Se intenta autenticar al usuario utilizando el AuthenticationManager
        // Se crea un objeto UsernamePasswordAuthenticationToken con el nombre de usuario y la contraseña proporcionados
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),  // Nombre de usuario
                        loginRequest.getPassword()   // Contraseña
                )
        );

        // Carga el usuario autenticado a partir del contexto de seguridad
        // Aquí se utiliza el UserDetailsService para recuperar los detalles del usuario
        User user = (User) userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Se genera y retorna un token JWT basado en el usuario autenticado
        // Este token se puede utilizar para autenticar futuras solicitudes del usuario

        System.out.println("El usuario con email " + user.getUsername() + " " +
                "se autentico correctamente");

        return jwtUtil.generateToken(user);
    }

    // Método privado para obtener el nombre de usuario del contexto de seguridad
    private String getCurrentAuthenticatedName() {
        // Obtiene la autenticación actual desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Retorna el nombre de usuario si la autenticación es válida, de lo contrario retorna null
        return authentication != null ? authentication.getName() : null;
    }
}
