package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.entity.MasterRole;
import com.workfall.jwt_checking.enums.Roles;
import com.workfall.jwt_checking.repo.MasterRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterRoleService {

    private final MasterRoleRepo masterRoleRepo;

    /**
     * Creates default master roles (ADMIN and USER) if they do not exist.
     */
    public void initializeMasterRoles() {
        if (masterRoleRepo.findByRoles(Roles.ADMIN).isEmpty()) {
            MasterRole adminRole = new MasterRole();
            adminRole.setRoles(Roles.ADMIN);
            adminRole.setRoleActive(true);
            masterRoleRepo.save(adminRole);
        }

        if (masterRoleRepo.findByRoles(Roles.USER).isEmpty()) {
            MasterRole userRole = new MasterRole();
            userRole.setRoles(Roles.USER);
            userRole.setRoleActive(true);
            masterRoleRepo.save(userRole);
        }
    }

    /**
     * Finds a master role by its enum value.
     *
     * @param role the role to find.
     * @return the master role.
     */
    public MasterRole findMasterRole(Roles role) {
        return masterRoleRepo.findByRoles(role)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role.name()));
    }
}

