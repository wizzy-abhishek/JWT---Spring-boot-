package com.workfall.jwt_checking.document;

import com.workfall.jwt_checking.enums.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "appUser")
public class AppUser implements UserDetails {

    private String email ;

    private String password ;

    private Set<Roles> roles ;

    private boolean isAccExpired;

    private boolean isAccLocked ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            roles.forEach(role -> authorities
                    .add(new SimpleGrantedAuthority("ROLE_" + role.name())));
            return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isAccExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isAccLocked;
    }

}
