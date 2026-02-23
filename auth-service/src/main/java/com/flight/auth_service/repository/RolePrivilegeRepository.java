package com.flight.auth_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flight.auth_service.entity.RolePrivilege;
import com.flight.auth_service.entity.RolePrivilegeId;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilegeId> {
}
