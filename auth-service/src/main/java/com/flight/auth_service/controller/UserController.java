package com.flight.auth_service.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.flight.auth_service.dto.AssignRoleRequest;
import com.flight.auth_service.dto.RegisterRequest;
import com.flight.auth_service.dto.UserResponse;
import com.flight.auth_service.service.RoleService;
import com.flight.auth_service.service.UserService;

import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{username}/roles")
    public ResponseEntity<String> assignRole(@PathVariable String username, @RequestBody AssignRoleRequest request) {

        roleService.assignRoleToUser(username, request.getRoleName());
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PostMapping("/create")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        userService.createUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
}