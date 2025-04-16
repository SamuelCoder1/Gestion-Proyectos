package com.riwi.backend.controllers;

import com.riwi.backend.application.dtos.requests.LoginRequest;
import com.riwi.backend.application.dtos.responses.UserWithoutId;
import com.riwi.backend.application.services.impl.AuthService;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.domain.ports.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            String jwtToken = authService.login(loginRequest);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwtToken);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Credenciales inválidas"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Error en el servidor"));
        }
    }

    @Operation(summary =  "Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserWithoutId userDTO) {
        User user = userService.register(userDTO);
        return ResponseEntity.status(201).body(user);
    }
}

