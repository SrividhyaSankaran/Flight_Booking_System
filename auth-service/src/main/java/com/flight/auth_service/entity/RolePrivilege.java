package com.flight.auth_service.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "role_privileges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePrivilege {

    @EmbeddedId
    private RolePrivilegeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("privilegeId")
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;

    @CreationTimestamp
    private LocalDateTime createdAt;
}