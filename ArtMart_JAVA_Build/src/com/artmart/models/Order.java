package com.artmart.models;

import java.sql.*;

public class Order {

    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private int shippingOption;
    private int paymentMethod;
    private String shippingAddress;
    private Date orderDate;
    private double totalCost;

    public Order() {
    }

    public Order(int id, int userId, int productId, int quantity, String shippingAddress,int shippingOption,int paymentMethod, Date orderDate, double totalCost) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.shippingOption = shippingOption;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    public int getShippingOption() {
        return shippingOption;
    }

    public void setShippingOption(int shippingOption) {
        this.shippingOption = shippingOption;
    }

    public Order(int userId, int productId, int quantity,int shippingOption, String shippingAddress, int paymentMethod, Date orderDate, double totalCost) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
        this.shippingOption = shippingOption;
        this.paymentMethod = paymentMethod;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + ", shippingAddress=" + shippingAddress + ", shippingOption=" + shippingOption + ", paymentMethod=" + paymentMethod + ", orderDate=" + orderDate + ", totalCost=" + totalCost + '}';
    }
    

}
