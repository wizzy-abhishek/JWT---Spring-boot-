package com.workfall.jwt_checking.user;

import com.workfall.jwt_checking.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private AppUser appUser ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        appUser.getUserRole().forEach(userRole -> {
            String roleName = userRole.getMasterRole().getRoles().name();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleName));
        });

        return authorities;
    }


    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !appUser.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !appUser.isAccountLocked();
    }
}
