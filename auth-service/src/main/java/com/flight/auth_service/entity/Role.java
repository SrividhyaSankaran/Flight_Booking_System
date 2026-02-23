package com.flight.auth_service.entity;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePrivilege> rolePrivileges = new HashSet<>();

    public Set<Privilege> getPrivileges() {
    return rolePrivileges.stream()
            .map(RolePrivilege::getPrivilege)
            .collect(Collectors.toSet());
}
}