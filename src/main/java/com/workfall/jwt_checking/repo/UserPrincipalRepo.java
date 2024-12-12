package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.document.UserPrincipal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPrincipalRepo extends MongoRepository<UserPrincipal , String> {
    Optional<UserPrincipal> findByAppUser(AppUser appUser);
}
