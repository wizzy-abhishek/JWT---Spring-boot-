/*
package com.workfall.jwt_checking.scheduled;

import com.workfall.jwt_checking.document.Token;
import com.workfall.jwt_checking.repo.TokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final TokenRepo tokenRepo ;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    void clearBlackListDocument() {
        log.info("Scheduler triggered at: {}", new Date());

        List<Token> tokens = tokenRepo.
                findByExpirationTimeBefore(new Date(System.currentTimeMillis()));

        if (tokens.isEmpty()) {
            log.info("No expired tokens found to delete.");
            return;
        }

        List<String> idsToDelete = tokens.stream()
                .map(Token::get)
                .collect(Collectors.toList());

        log.info("Found {} expired tokens. Deleting tokens with IDs: {}", idsToDelete.size(), idsToDelete);

        tokenRepo.deleteAllById(idsToDelete);

        log.info("Successfully deleted {} tokens.", idsToDelete.size());
    }
}*/
