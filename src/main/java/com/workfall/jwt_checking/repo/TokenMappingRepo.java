package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.TokenMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenMappingRepo extends MongoRepository<TokenMapping , String> {

    TokenMapping findByEmailIgnoreCase(String username);
}
