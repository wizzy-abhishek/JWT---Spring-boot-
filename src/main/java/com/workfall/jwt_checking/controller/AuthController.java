package com.workfall.jwt_checking.controller;

import com.workfall.jwt_checking.dto.AppUserDTO;
import com.workfall.jwt_checking.dto.LoginDTO;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import com.workfall.jwt_checking.dto.SignUpDTO;
import com.workfall.jwt_checking.service.AuthLoginService;
import com.workfall.jwt_checking.service.AuthService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthLoginService authLoginService;

    @PostMapping("/signUp")
    public ResponseEntity<AppUserDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(authService.signUp(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse) {
        LoginResponseDTO loginResponseDTO = authLoginService.login(loginDTO);

        Cookie cookie = new Cookie("jwtToken", loginResponseDTO.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwtToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new BadCredentialsException("JWT Token not found in cookies"));

        return ResponseEntity.ok(authLoginService.logout(token));
    }
}

