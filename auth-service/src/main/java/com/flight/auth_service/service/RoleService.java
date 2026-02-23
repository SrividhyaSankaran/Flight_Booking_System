package com.flight.auth_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flight.auth_service.entity.Privilege;
import com.flight.auth_service.entity.Role;
import com.flight.auth_service.entity.RolePrivilege;
import com.flight.auth_service.entity.User;
import com.flight.auth_service.entity.UserRole;
import com.flight.auth_service.entity.UserRoleId;
import com.flight.auth_service.repository.PrivilegeRepository;
import com.flight.auth_service.repository.RolePrivilegeRepository;
import com.flight.auth_service.repository.RoleRepository;
import com.flight.auth_service.repository.UserRepository;
import com.flight.auth_service.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public void assignRoleToUser(String username, String roleName) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRoleId id = new UserRoleId(user.getId(), role.getId());

        if (userRoleRepository.existsById(id)) {
            return;
        }

        UserRole userRole = UserRole.builder()
                .id(id)
                .user(user)
                .role(role)
                .build();

        userRoleRepository.save(userRole);
    }
}
