package com.artmart.models;

import com.artmart.utils.OrderCurrentStatus;
import java.sql.*;

public class OrderStatus {

    private int id;
    private int orderId;
    private OrderCurrentStatus orderStatus;
    private Date date;

    public OrderStatus() {
    }

    public OrderStatus(int id, int orderId, OrderCurrentStatus status, Date date) {
        this.id = id;
        this.orderId = orderId;
        this.orderStatus = status;
        this.date = date;
    }

    public OrderStatus(int orderId, OrderCurrentStatus status, Date date) {
        this.orderId = orderId;
        this.orderStatus = status;
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
        return orderStatus.getStatus();
    }

    public void setStatus(String status) {
        this.orderStatus = OrderCurrentStatus.valueOf(status);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderStatus{" + "id=" + id + ", orderId=" + orderId + ", status=" + orderStatus.getStatus() + ", date=" + date + '}';
    }

}
