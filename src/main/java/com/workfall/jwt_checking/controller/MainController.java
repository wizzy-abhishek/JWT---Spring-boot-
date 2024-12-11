package com.workfall.jwt_checking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main-controller")
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/unauthorized-api")
    public ResponseEntity<String> unauthorizedAPI(){
        return ResponseEntity.ok("MY NAME IS JOHN CENA");
    }
}
