package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.AppUser;
import com.workfall.jwt_checking.dto.AppUserDTO;
import com.workfall.jwt_checking.dto.SignUpDTO;
import com.workfall.jwt_checking.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepo appUserRepo ;
    private final PasswordEncoder passwordEncoder ;

    @Override
    public UserDetails loadUserByUsername(String username){
        return appUserRepo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new BadCredentialsException("User with Email : " + username + " not found"));
    }

    public AppUser findUserByEmail(String email){
        return appUserRepo.findByEmailIgnoreCase(email)
                .orElseThrow(()-> new BadCredentialsException("User with email : " + email + " not found")) ;
    }

    public AppUserDTO signUp(SignUpDTO signUpDTO){

        Optional<AppUser> user = appUserRepo.findByEmailIgnoreCase(signUpDTO.getEmail());

        if (user.isPresent()){
            throw new BadCredentialsException("Email already present " + signUpDTO.getEmail());
        }

        AppUser toBeCreatedUser = returnAppUserEntity(signUpDTO);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        AppUser savingUser = appUserRepo.save(toBeCreatedUser);
        return returnAppUserDTO(savingUser);

    }

    private AppUser returnAppUserEntity(SignUpDTO signUpDTO){
        AppUser appUser = new AppUser();

        appUser.setEmail(signUpDTO.getEmail());
        appUser.setPassword(signUpDTO.getPassword());
        appUser.setAccExpired(false);
        appUser.setAccLocked(false);

        return appUser ;
    }

    private AppUserDTO returnAppUserDTO(AppUser appUser){
        AppUserDTO appUserDTO = new AppUserDTO();


        return appUserDTO ;
    }
}
