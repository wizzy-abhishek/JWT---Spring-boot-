package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.entity.AppUser;
import com.workfall.jwt_checking.entity.MasterRole;
import com.workfall.jwt_checking.entity.UserRole;
import com.workfall.jwt_checking.enums.Roles;
import com.workfall.jwt_checking.repo.AppUserRepo;
import com.workfall.jwt_checking.repo.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepo userRoleRepo;
    private final MasterRoleService masterRoleService;
    private final AppUserRepo appUserRepo;

    public AppUser createUserRoles(AppUser appUser, Set<Roles> roles) {
        roles.forEach(role -> {
            MasterRole masterRole = masterRoleService.findMasterRole(role);
            UserRole newUserRole = new UserRole();
            newUserRole.setAppUser(appUser);
            newUserRole.setMasterRole(masterRole);
            userRoleRepo.save(newUserRole);
            appUser.getUserRole().add(newUserRole);
        });
        return appUserRepo.save(appUser);
    }


    public List<UserRole> getUserRolesByUser(AppUser appUser) {
        return userRoleRepo.findAllByAppUser(appUser);
    }
}

