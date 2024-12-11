package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepo extends MongoRepository<AppUser , String> {
}
