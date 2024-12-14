package com.workfall.jwt_checking.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document
public class Tokens {

    @Id
    private String jwtToken ;

    private Date expirationTime ;

    private boolean revoked ;
}
