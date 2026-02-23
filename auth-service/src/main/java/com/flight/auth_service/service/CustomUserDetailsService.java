package com.flight.auth_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flight.auth_service.entity.Role;
import com.flight.auth_service.entity.RolePrivilege;
import com.flight.auth_service.entity.User;
import com.flight.auth_service.entity.UserRole;
import com.flight.auth_service.repository.RolePrivilegeRepository;
import com.flight.auth_service.repository.UserRepository;
import com.flight.auth_service.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole userRole : user.getUserRoles()) {
                Role role = userRole.getRole();
                authorities.add(
                        new SimpleGrantedAuthority(role.getRoleName())
                );

                for (RolePrivilege rp : role.getRolePrivileges()) {
                        authorities.add(
                                new SimpleGrantedAuthority(
                                        rp.getPrivilege().getPrivilegeName()
                                )
                        );
                }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities
        );
    }
}
