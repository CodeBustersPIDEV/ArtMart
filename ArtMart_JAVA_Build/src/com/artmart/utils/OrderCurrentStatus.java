package com.artmart.utils;

public enum OrderCurrentStatus {
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    private final String status;

    OrderCurrentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public static OrderCurrentStatus getOrderStatus(String status) {
    try {
        return OrderCurrentStatus.valueOf(status);
    } catch (IllegalArgumentException e) {
        return null;
    }
}
}
