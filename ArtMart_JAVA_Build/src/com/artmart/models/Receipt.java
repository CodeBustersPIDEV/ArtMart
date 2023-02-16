package com.artmart.models;

import java.sql.*;

public class Receipt {

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
    private double tax;
    private double totalCost;
    private Date date;

    public Receipt() {
    }

    public Receipt(int id, int orderId, int productId, int quantity, double price, double tax, double totalCost, Date date) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
        this.totalCost = totalCost;
        this.date = date;
    }

    public Receipt(int orderId, int productId, int quantity, double price, double tax, double totalCost, Date date) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
        this.totalCost = totalCost;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Receipt{" + "id=" + id + ", orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price + ", tax=" + tax + ", totalCost=" + totalCost + ", date=" + date + '}';
    }

}
