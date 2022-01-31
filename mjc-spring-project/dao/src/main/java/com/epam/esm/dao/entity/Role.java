package com.epam.esm.dao.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.USERS_READ, Permission.USERS_MAKE_ORDER)),
    ADMIN(Set.of(Permission.USERS_READ_ADMIN, Permission.USERS_READ,
            Permission.USERS_WRITE, Permission.USERS_MODIFY,
            Permission.USERS_MAKE_ORDER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
