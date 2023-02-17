package com.artmart.models;

import java.sql.*;

public class OrderStatus {

    private int id;
    private int orderId;
    private String status;
    private Date date;

    public OrderStatus() {
    }

    public OrderStatus(int id, int orderId, String status, Date date) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.date = date;
    }

    public OrderStatus(int orderId, String status, Date date) {
        this.orderId = orderId;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderStatus{" + "id=" + id + ", orderId=" + orderId + ", status=" + status + ", date=" + date + '}';
    }

}
