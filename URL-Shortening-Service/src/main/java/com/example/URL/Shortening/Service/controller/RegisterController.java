package com.example.URL.Shortening.Service.controller;

import com.example.URL.Shortening.Service.DTO.UserRequestDTO;
import com.example.URL.Shortening.Service.DTO.UserResponseDTO;
import com.example.URL.Shortening.Service.entity.UserEntity;
import com.example.URL.Shortening.Service.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    @Operation(summary = "Register new User",
            description = "Enter username, email and password to register")
    @ApiResponses(value = {@ApiResponse(responseCode="201",
            description = "Creates a new User",
            content=@Content(mediaType = "application/json",
                    schema=@Schema(implementation = UserResponseDTO.class)))

    })
    public UserResponseDTO register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserEntity newUser = convertToEntity(userRequestDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return convertToResponse(newUser);

    }

    private UserResponseDTO convertToResponse(UserEntity newUser) {
        return UserResponseDTO.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .build();
    }

    private UserEntity convertToEntity(UserRequestDTO userRequestDTO) {
        return UserEntity.builder()
                .username(userRequestDTO.getUsername())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }
}
