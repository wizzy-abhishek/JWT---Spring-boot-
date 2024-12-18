package com.workfall.jwt_checking.dto;

import com.workfall.jwt_checking.enums.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SignUpDTO {

    String email ;

    String password ;

    Set<Roles> roles = new HashSet<>();
}
