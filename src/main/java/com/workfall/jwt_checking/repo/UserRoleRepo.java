package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.entity.AppUser;
import com.workfall.jwt_checking.entity.MasterRole;
import com.workfall.jwt_checking.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole , Long> {

    List<UserRole> findAllByAppUser(AppUser appUser);

    List<UserRole> findAllByMasterRole(MasterRole masterRole);
}
