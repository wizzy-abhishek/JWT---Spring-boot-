package com.workfall.jwt_checking.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Login {

    private String email ;

    private String password ;

    private String jwtToken ;

}
