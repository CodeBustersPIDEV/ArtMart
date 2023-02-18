package com.artmart.utils;

public enum OrderCurrentStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

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
        // handle invalid status
        return null;
    }
}
}
