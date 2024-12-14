package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.document.TokenMapping;
import com.workfall.jwt_checking.document.Tokens;
import com.workfall.jwt_checking.document.UserPrincipal;
import com.workfall.jwt_checking.dto.LoginDTO;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import com.workfall.jwt_checking.repo.TokenMappingRepo;
import com.workfall.jwt_checking.repo.TokenRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepo tokenRepo;
    private final TokenMappingRepo tokenMappingRepo;

    public LoginResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        AppUser appUser = userPrincipal.getAppUser();

        revokeAllTokensOfAnUser(appUser);

        String token = jwtService.generateAccessToken(appUser);
        Tokens savedToken = tokenRepo.save(returnNewToken(token));

        TokenMapping tokenMapping = tokenMappingRepo.findByEmailIgnoreCase(appUser.getEmail());
        tokenMapping.getTokens().add(savedToken);

        tokenMappingRepo.save(tokenMapping);

        return new LoginResponseDTO(token);
    }

    public void logout(String token) {

        Tokens tokenFetched = tokenRepo.findByJwtToken(token);

        if (tokenFetched == null || tokenFetched.isRevoked()) {
            throw new BadCredentialsException("Invalid or already revoked token");
        }

        tokenFetched.setRevoked(true);
        tokenRepo.save(tokenFetched);

    }
    private void revokeAllTokensOfAnUser(AppUser appUser ){
        TokenMapping tokenMapping = tokenMappingRepo
                .findByEmailIgnoreCase(appUser.getEmail());

        List<Tokens> tokens = tokenMapping.getTokens();

        if (tokens.isEmpty()){
            log.info("All tokens empty while logging in of user {}" , appUser.getEmail());
            return;
        }

        tokens.forEach(token -> {
            token.setRevoked(true);
        });
        tokenRepo.saveAll(tokens);
        tokenMappingRepo.save(tokenMapping);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private Tokens returnNewToken(String token){
        Tokens tokens = new Tokens();

        tokens.setJwtToken(token);
        tokens.setRevoked(false);
        tokens.setExpirationTime(jwtService.getTokenExpireTime(token));

        return tokens ;
    }
}

