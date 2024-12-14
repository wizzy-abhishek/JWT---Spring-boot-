package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends MongoRepository<AppUser , String> {

    Optional<AppUser> findByEmailIgnoreCase(String username);
}
