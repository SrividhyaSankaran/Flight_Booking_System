package com.flight.auth_service.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flight.auth_service.dto.RegisterRequest;
import com.flight.auth_service.dto.UserRequest;
import com.flight.auth_service.dto.UserResponse;
import com.flight.auth_service.entity.Role;
import com.flight.auth_service.entity.RolePrivilege;
import com.flight.auth_service.entity.User;
import com.flight.auth_service.entity.UserRole;
import com.flight.auth_service.entity.UserRoleId;
import com.flight.auth_service.exception.RoleNotFoundException;
import com.flight.auth_service.exception.UserAlreadyExistsException;
import com.flight.auth_service.repository.RoleRepository;
import com.flight.auth_service.repository.UserRepository;
import com.flight.auth_service.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


     public User createUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(request.getUsername());
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(user);

        Role customerRole = roleRepository
                .findByRoleName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default role missing"));

        UserRole userRole = UserRole.builder()
                .id(new UserRoleId(user.getId(), customerRole.getId()))
                .user(user)
                .role(customerRole)
                .build();

        userRoleRepository.save(userRole);
        return savedUser;
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponse getUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }


    private UserResponse mapToResponse(User user) {

        Set<String> roles = new HashSet<>();
        Set<String> privileges = new HashSet<>();

        for (UserRole ur : user.getUserRoles()) {

            Role role = ur.getRole();
            roles.add(role.getRoleName());

            for (RolePrivilege rp : role.getRolePrivileges()) {
                privileges.add(rp.getPrivilege().getPrivilegeName());
            }
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(roles)
                .privileges(privileges)
                .build();
    }
    
}
