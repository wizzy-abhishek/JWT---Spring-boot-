package com.workfall.jwt_checking.repo;

import com.workfall.jwt_checking.document.Tokens;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends MongoRepository<Tokens, String> {

/*  Optional<Token> findByEmailIgnoreCase(String username);

    List<Token> findByExpirationTimeBefore(Date date);

    List<Token> findAllByEmailIgnoreCase(String email);
 */
    Tokens findByJwtToken(String token);
}
