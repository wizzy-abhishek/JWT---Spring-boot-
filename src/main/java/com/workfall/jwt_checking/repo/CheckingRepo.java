package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.Checking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckingRepo extends MongoRepository<Checking , String> {
}
