package com.example.URL.Shortening.Service.controller;

import com.example.URL.Shortening.Service.DTO.AuthRequestDTO;
import com.example.URL.Shortening.Service.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @PostMapping("/log")
    @Operation(summary = "Logging in",
            description = "Enter  email and password to login. In response we got 'User logged in successfully...' but in post man it will return 'jwt token'")
    @ApiResponses(value = {@ApiResponse(responseCode="201",
            description = "Login feature for existing user")
    })
    public String generateToken(@RequestBody AuthRequestDTO authRequest, HttpServletRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
            String token = jwtUtil.generateToken(authRequest.getEmail());
            String referer = request.getHeader("Referer");
            boolean fromSwagger = (referer != null && referer.contains("swagger"));
            if(fromSwagger)
                return "User logged in successfully...";
            else
                return token;
        } catch(Exception e) {
            throw e;
        }
    }
}
