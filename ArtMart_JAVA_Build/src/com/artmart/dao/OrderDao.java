package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.artmart.models.Order;
import com.artmart.interfaces.*;
import java.util.List;
import java.util.ArrayList;

public class OrderDao implements IOrderServiceDao{
    
    private Connection connection;

    public OrderDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createOrder(Order order) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Order (UserID, ProductID, Quantity, ShippingAddress, PaymentMethod, OrderDate, TotalCost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getPaymentMethod());
            statement.setDate(6, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(7, order.getTotalCost());
             result = statement.executeUpdate();
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return result;
    }

    @Override
    public Order readOrder(int id) {
        Order order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Order WHERE ID = ?"
            );
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                order = new Order();
                order.setId(result.getInt("ID"));
                order.setUserId(result.getInt("UserID"));
                order.setProductId(result.getInt("ProductID"));
                order.setQuantity(result.getInt("Quantity"));
                order.setShippingAddress(result.getString("ShippingAddress"));
                order.setPaymentMethod(result.getString("PaymentMethod"));
                order.setOrderDate(result.getDate("OrderDate"));
                order.setTotalCost(result.getDouble("TotalCost"));
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order`");
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("ID"));
                order.setUserId(resultSet.getInt("UserID"));
                order.setProductId(resultSet.getInt("ProductID"));
                order.setQuantity(resultSet.getInt("Quantity"));
                order.setShippingAddress(resultSet.getString("ShippingAddress"));
                order.setPaymentMethod(resultSet.getString("PaymentMethod"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                orders.add(order);
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return orders;
    }


    @Override
    public boolean updateOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE `Order` SET UserID = ?, ProductID = ?, Quantity = ?, ShippingAddress = ?, PaymentMethod = ?, OrderDate = ?, TotalCost = ? WHERE ID = ?");
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getPaymentMethod());
            statement.setDate(6, order.getOrderDate());
            statement.setDouble(7, order.getTotalCost());
            statement.setInt(8, order.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `Order` WHERE ID = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }
}
