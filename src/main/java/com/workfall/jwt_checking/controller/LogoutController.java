package com.workfall.jwt_checking.controller;

import com.workfall.jwt_checking.service.AuthLoginService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {

    private final AuthLoginService authLoginService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String token = authLoginService.extractTokenFromHeader(request);

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("No token found");
        }

        authLoginService.logout(token);

        return ResponseEntity.ok("User logged out successfully");
    }
}
