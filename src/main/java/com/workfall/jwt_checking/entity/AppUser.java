package com.workfall.jwt_checking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String email ;

    private String password ;

    @OneToMany
    private List<UserRole> userRole = new ArrayList<>();

    private boolean isAccountExpired ;

    private boolean isAccountLocked ;
}