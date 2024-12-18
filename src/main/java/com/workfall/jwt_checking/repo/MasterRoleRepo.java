package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.entity.MasterRole;
import com.workfall.jwt_checking.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRoleRepo extends JpaRepository<MasterRole , Long> {

    Optional<MasterRole> findByRoles(Roles roles);
}
