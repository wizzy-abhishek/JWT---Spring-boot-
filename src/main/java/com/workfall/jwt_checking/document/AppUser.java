package com.workfall.jwt_checking.document;

import com.workfall.jwt_checking.enums.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@Document
public class AppUser {

    private String email ;
    private String password ;
    private Set<Roles> roles ;
    private boolean isAccountExpired ;
    private boolean isAccountLocked ;
}