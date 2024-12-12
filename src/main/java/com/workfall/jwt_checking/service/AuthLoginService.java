package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.dto.LoginDTO;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthLoginService {

    private final AuthenticationManager authenticationManager ;
    private final JwtService jwtService ;

    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail() , loginDTO.getPassword()));

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(appUser);

        return new LoginResponseDTO(token);
    }
}
