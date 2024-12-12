package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.document.Login;
import com.workfall.jwt_checking.dto.LoginDTO;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import com.workfall.jwt_checking.repo.LoginRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthLoginService {

    private final AuthenticationManager authenticationManager ;
    private final JwtService jwtService ;
    private final LoginRepo loginRepo;

    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail() , loginDTO.getPassword()));

        Login login = loginRepo.findByEmailIgnoreCase(loginDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException("N0T FOUND"));

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(appUser);

        login.setJwtToken(token);
        loginRepo.save(login);

        return new LoginResponseDTO(token);
    }

    public String logout(String token) {
        AppUser appUser = jwtService.getUserFromToken(token);
    }
}
