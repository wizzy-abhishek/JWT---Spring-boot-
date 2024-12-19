package com.workfall.jwt_checking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured("ROLE_USER")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/getMessage")
    public ResponseEntity<String> adminChecking(){
        return ResponseEntity.ok("I am User");
    }
}
