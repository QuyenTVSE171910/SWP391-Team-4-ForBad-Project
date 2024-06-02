package com.swp391.teamfour.forbadsystem.model;

public enum RoleEnum {
    ROLE_ADMIN("admin"),
    ROLE_MANAGER("manager"),
    ROLE_CUSTOMER("customer"),
    ROLE_STAFF("staff");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
