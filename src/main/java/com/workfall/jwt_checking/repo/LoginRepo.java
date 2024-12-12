package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LoginRepo extends MongoRepository<Login , String> {

    Optional<Login> findByEmailIgnoreCase(String username);
}
