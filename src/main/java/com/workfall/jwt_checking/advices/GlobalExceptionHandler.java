package com.workfall.jwt_checking.advices;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIError> handleAuthenticationException(AuthenticationException ex) {
        APIError apiError = new APIError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIError> handleExpiredJwtException(ExpiredJwtException expiredJwtException){
        APIError apiError = new APIError(expiredJwtException.getMessage() , HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(apiError , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<APIError> handleTemperedJWT
            (MalformedJwtException malformedJwtException){
        APIError apiError = new APIError(malformedJwtException.getMessage() , HttpStatus.UNAUTHORIZED);
        apiError.getSubErrors().add("WARNING THEIR MIGHT BE TAMPERING IN REQUEST");

        return new ResponseEntity<>(apiError , HttpStatus.UNAUTHORIZED);
    }
}
