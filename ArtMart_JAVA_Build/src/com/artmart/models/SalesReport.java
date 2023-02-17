package com.artmart.models;

import java.sql.*;

public class SalesReport {

    private int id;
    private int productId;
    private double totalSales;
    private double averageSalesPerDay;
    private Date date;

    public SalesReport() {
    }

    public SalesReport(int id, int productId, double totalSales, double averageSalesPerDay, Date date) {
        this.id = id;
        this.productId = productId;
        this.totalSales = totalSales;
        this.averageSalesPerDay = averageSalesPerDay;
        this.date = date;
    }

    public SalesReport(int productId, double totalSales, double averageSalesPerDay, Date date) {
        this.productId = productId;
        this.totalSales = totalSales;
        this.averageSalesPerDay = averageSalesPerDay;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    public void setAverageSalesPerDay(double averageSalesPerDay) {
        this.averageSalesPerDay = averageSalesPerDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalesReport{" + "id=" + id + ", productId=" + productId + ", totalSales=" + totalSales + ", averageSalesPerDay=" + averageSalesPerDay + ", date=" + date + '}';
    }

}
