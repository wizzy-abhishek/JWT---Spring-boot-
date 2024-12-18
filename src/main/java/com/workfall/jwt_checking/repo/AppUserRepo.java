package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser , String> {

    Optional<AppUser> findByEmailIgnoreCase(String username);
}
