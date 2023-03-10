package com.artmart.models;

import java.sql.*;

public class Wishlist {

    private int id;
    private int userId;
    private int productId;
    private Date date;
    private int quantity;
    private double price;
    
    public Wishlist() {
    }

    public Wishlist(int userId, int productId, Date date, int Quantity, double Price) {
        this.userId = userId;
        this.productId = productId;
        this.date = date;
        this.quantity = Quantity;
        this.price = Price;
    }

    public Wishlist(int id, int userId, int productId, Date date, int Quantity, double Price) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.date = date;
        this.quantity = Quantity;
        this.price = Price;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int Quantity) {
        this.quantity = Quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = Price;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Wishlist{" + "id=" + id + ", userId=" + userId + ", productId=" + productId + ", date=" + date + ", Quantity=" + quantity + ", Price=" + price + '}';
    }
    

}
