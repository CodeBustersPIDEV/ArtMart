package com.artmart.models;

import java.sql.*;

public class OrderRefund {

    private int id;
    private int orderId;
    private double refundAmount;
    private String reason;
    private Date date;

    public OrderRefund() {
    }

    public OrderRefund(int id, int orderId, double refundAmount, String reason, Date date) {
        this.id = id;
        this.orderId = orderId;
        this.refundAmount = refundAmount;
        this.reason = reason;
        this.date = date;
    }

    public OrderRefund(int orderId, double refundAmount, String reason, Date date) {
        this.orderId = orderId;
        this.refundAmount = refundAmount;
        this.reason = reason;
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

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderRefund{" + "id=" + id + ", orderId=" + orderId + ", refundAmount=" + refundAmount + ", reason=" + reason + ", date=" + date + '}';
    }

}
