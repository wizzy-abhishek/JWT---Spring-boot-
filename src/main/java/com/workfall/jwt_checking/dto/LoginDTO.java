package com.workfall.jwt_checking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String email ;

    private String password ;

    private String jwtToken ;

}
