package com.workfall.jwt_checking.controller;

import com.workfall.jwt_checking.dto.AppUserDTO;
import com.workfall.jwt_checking.dto.LoginDTO;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import com.workfall.jwt_checking.dto.SignUpDTO;
import com.workfall.jwt_checking.service.AuthLoginService;
import com.workfall.jwt_checking.service.AuthService;
import com.workfall.jwt_checking.service.MasterRoleService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthLoginService authLoginService;
    private final MasterRoleService masterRoleService ;

    @PostMapping("/signUp")
    public ResponseEntity<LoginResponseDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        LoginResponseDTO loginResponseDTO = authService.signUp(signUpDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = authLoginService.login(loginDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/masterRole")
    public void masterRole(){
        masterRoleService.initializeMasterRoles();
    }
}

