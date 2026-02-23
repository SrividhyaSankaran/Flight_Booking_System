package com.flight.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flight.auth_service.dto.AuthResponse;
import com.flight.auth_service.dto.LoginRequest;
import com.flight.auth_service.dto.LoginResponse;
import com.flight.auth_service.security.JwtUtil;
import com.flight.auth_service.service.AuthService;
import com.flight.auth_service.service.CustomUserDetailsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
