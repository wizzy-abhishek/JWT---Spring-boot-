package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.Tokens;
import com.workfall.jwt_checking.dto.LoginResponseDTO;
import com.workfall.jwt_checking.entity.AppUser;
import com.workfall.jwt_checking.document.TokenMapping;
import com.workfall.jwt_checking.document.UserPrincipal;
import com.workfall.jwt_checking.dto.AppUserDTO;
import com.workfall.jwt_checking.dto.SignUpDTO;
import com.workfall.jwt_checking.repo.AppUserRepo;
import com.workfall.jwt_checking.repo.TokenMappingRepo;

import com.workfall.jwt_checking.repo.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepo appUserRepo ;
    private final PasswordEncoder passwordEncoder ;
    private final TokenMappingRepo tokenMappingRepo ;
    private final JwtService jwtService ;
    private final TokenRepo tokenRepo;
    private final UserRoleService userRoleService ;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = appUserRepo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException("User with Email : " + username + " not found"));

        return new UserPrincipal(appUser);
    }


    public AppUser findUserByEmail(String email){
        return appUserRepo.findByEmailIgnoreCase(email)
                .orElseThrow(()-> new BadCredentialsException("User with email : " + email + " not found")) ;
    }

    public LoginResponseDTO signUp(SignUpDTO signUpDTO){

        AppUser user = appUserRepo.findByEmailIgnoreCase(signUpDTO.getEmail())
                .orElse(null);

        if (user != null){
            throw new BadCredentialsException("Email already present " + signUpDTO.getEmail());
        }

        AppUser toBeCreatedUser = returnAppUserEntity(signUpDTO);
        TokenMapping generateTokenMapping = returnTokenMapping(signUpDTO);

        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        AppUser savingUser = appUserRepo.save(toBeCreatedUser);
        savingUser.getUserRole().add(userRoleService.createUserRole(savingUser , signUpDTO.getRoles()));
        tokenMappingRepo.save(generateTokenMapping);

        String token = jwtService.generateAccessToken(savingUser);
        Tokens savedToken = tokenRepo.save(returnNewToken(token));

        TokenMapping tokenMapping = tokenMappingRepo
                .findByEmailIgnoreCase(savingUser.getEmail());
        tokenMapping.getTokens().add(savedToken);

        tokenMappingRepo.save(tokenMapping);
        return new LoginResponseDTO(token);

    }

    private AppUser returnAppUserEntity(SignUpDTO signUpDTO){
        AppUser appUser = new AppUser();

        appUser.setEmail(signUpDTO.getEmail());
        appUser.setPassword(signUpDTO.getPassword());
        appUser.setAccountExpired(false);
        appUser.setAccountLocked(false);

        return appUser ;
    }

    private AppUserDTO returnAppUserDTO(AppUser appUser){
        AppUserDTO appUserDTO = new AppUserDTO();

        appUserDTO.setEmail(appUser.getEmail());

        return appUserDTO ;
    }

    private TokenMapping returnTokenMapping(SignUpDTO signUpDTO){
        TokenMapping tokenMapping = new TokenMapping();
        tokenMapping.setEmail(signUpDTO.getEmail());
        return tokenMapping ;
    }

    private Tokens returnNewToken(String token){
        Tokens tokens = new Tokens();
        tokens.setJwtToken(token);
        return tokens ;
    }
}
