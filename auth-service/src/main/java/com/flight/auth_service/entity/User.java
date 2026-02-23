package com.flight.auth_service.entity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 150)
    private String username;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    private String status;

    private boolean isAccountNonLocked = true;
    private boolean isEnabled = true;
    private boolean isCredentialsNonExpired = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<Role> getRoles() {
    return userRoles.stream()
            .map(UserRole::getRole)
            .collect(Collectors.toSet());
}
}