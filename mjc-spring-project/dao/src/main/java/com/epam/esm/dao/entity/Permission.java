package com.epam.esm.dao.entity;

public enum Permission {

    USERS_MAKE_ORDER("users:makeOrder"),
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    USERS_MODIFY("users:modify"),
    USERS_READ_ADMIN("users:readAdmin");

    private final String permissionName;

    Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermission() {
        return permissionName;
    }
}
