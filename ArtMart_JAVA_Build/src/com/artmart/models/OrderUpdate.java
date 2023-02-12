package com.artmart.models;

import java.sql.*;

public class OrderUpdate {
    private int id;
    private int orderId;
    private String updateMessage;
    private Date date;

    public OrderUpdate() {
    }

    public OrderUpdate(int id, int orderId, String updateMessage, Date date) {
        this.id = id;
        this.orderId = orderId;
        this.updateMessage = updateMessage;
        this.date = date;
    }

    public OrderUpdate(int orderId, String updateMessage, Date date) {
        this.orderId = orderId;
        this.updateMessage = updateMessage;
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

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderUpdate{" + "id=" + id + ", orderId=" + orderId + ", updateMessage=" + updateMessage + ", date=" + date + '}';
    }
    
}
