package com.workfall.jwt_checking.document;

import com.workfall.jwt_checking.entity.AppUser;
import com.workfall.jwt_checking.utils.PermissionRoleMapping;
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
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private AppUser appUser ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        appUser.getRoles().forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = PermissionRoleMapping.getAuthorities(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
                }
        );
        return authorities ;
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
