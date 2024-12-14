package com.workfall.jwt_checking.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document
public class TokenMapping {

    @Id
    private String email ;

    private List<Tokens> tokens = new ArrayList<>();

}
