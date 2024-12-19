package com.workfall.jwt_checking.entity;

import com.workfall.jwt_checking.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MasterRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Enumerated(EnumType.STRING)
    private Roles roles ;

    private boolean isRoleActive ;
}
