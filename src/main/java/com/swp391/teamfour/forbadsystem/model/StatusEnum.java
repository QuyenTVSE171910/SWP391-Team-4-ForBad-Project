package com.swp391.teamfour.forbadsystem.model;

public enum StatusEnum {
    PENDING("Đang chờ xử lý"),
    WAITING_FOR_CHECK_IN("Đang chờ check-in"),
    CHECKED_IN("Đã check-in"),
    CANCELLED("Đã hủy");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
