package com.flight.auth_service.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RolePrivilegeId implements Serializable {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "privilege_id")
    private Long privilegeId;
}
