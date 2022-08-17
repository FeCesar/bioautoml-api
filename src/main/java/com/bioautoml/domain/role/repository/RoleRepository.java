package com.bioautoml.domain.role.repository;

import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.role.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleName(Role name);

}
