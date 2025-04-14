package com.riwi.backend.application.services.impl;

import com.riwi.backend.application.dtos.responses.UserWithoutId;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.domain.ports.service.IUserService;
import com.riwi.backend.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(UserWithoutId userDTO) {

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encryptPassword(userDTO.getPassword()))
                .build();

        userRepository.save(user);
        System.out.println("Usuario saved: " + user);
        return user;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password); // Encripta la contraseña
    }

}

