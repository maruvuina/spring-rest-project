package com.epam.esm.dao.entity;

import java.util.Set;

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
}
