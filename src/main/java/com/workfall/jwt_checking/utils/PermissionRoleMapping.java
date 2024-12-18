package com.workfall.jwt_checking.utils;

import com.workfall.jwt_checking.enums.Permission;
import com.workfall.jwt_checking.enums.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.workfall.jwt_checking.enums.Permission.*;
import static com.workfall.jwt_checking.enums.Roles.*;

public class PermissionRoleMapping {

    private final static Map<Roles , Set<Permission>> rolePermissionMap = Map.of(
            USER , Set.of(USER_READ , USER_WRITE , USER_PUT , USER_DELETE),
            ADMIN , Set.of(ADMIN_READ ,ADMIN_WRITE , ADMIN_PUT , ADMIN_DELETE ,USER_READ , USER_WRITE , USER_PUT , USER_DELETE)
            );

    public static Set<SimpleGrantedAuthority> getAuthorities(Roles role){
        return rolePermissionMap.get(role)
                .stream().map(
                        permissions -> new SimpleGrantedAuthority(permissions.name())
                ).collect(Collectors.toSet());
    }

}
