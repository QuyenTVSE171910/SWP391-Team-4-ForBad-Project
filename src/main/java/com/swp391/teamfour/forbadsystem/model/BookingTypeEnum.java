package com.swp391.teamfour.forbadsystem.model;

public enum BookingTypeEnum {
    SINGLE("Lịch đơn"),
    FIXED("Lịch cố định"),
    FLEXIBLE("lịch linh hoạt");

    private final String description;

    BookingTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
