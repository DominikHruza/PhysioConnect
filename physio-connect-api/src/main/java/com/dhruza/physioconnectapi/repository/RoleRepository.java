package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.auth.model.Role;
import com.dhruza.physioconnectapi.auth.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
