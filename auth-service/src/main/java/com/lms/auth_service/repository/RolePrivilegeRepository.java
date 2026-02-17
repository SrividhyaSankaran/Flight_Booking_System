package com.lms.auth_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.auth_service.entity.RolePrivilege;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    
    List<RolePrivilege> findByRoleId(Long roleId);

}
