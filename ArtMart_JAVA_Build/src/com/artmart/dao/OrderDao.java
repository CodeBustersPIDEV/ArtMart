package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.artmart.models.Order;
import com.artmart.interfaces.*;
import com.artmart.models.OrderStatus;
import com.artmart.models.OrderUpdate;
import com.artmart.utils.OrderCurrentStatus;
import java.util.List;
import java.util.ArrayList;

public class OrderDao implements IOrderServiceDao {

    private Connection connection;
    private final OrderStatusDao orderStatusDao = new OrderStatusDao();
    private final OrderUpdateDao orderUpdateDao = new OrderUpdateDao();

    public OrderDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createOrder(Order order) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `order`(UserID, ProductID, Quantity, ShippingMethod, ShippingAddress, PaymentMethod, OrderDate, TotalCost) "
                    + "VALUES (?, ?,?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, order.getShippingOption());
            statement.setString(5, order.getShippingAddress());
            statement.setInt(6, order.getPaymentMethod());
            statement.setDate(7, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(8, order.getTotalCost());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.err.print("created");
                result = generatedKeys.getInt(1);
                this.orderStatusDao.createOrderStatus(new OrderStatus(result, OrderCurrentStatus.PENDING, order.getOrderDate()));
            } else {
                System.err.print("not created");
                result = 0;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return result;
    }

    @Override
    public Order readOrder(int id) {
        Order order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Order WHERE order_ID = ?"
            );
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                order = new Order();
                order.setId(result.getInt("order_ID"));
                order.setUserId(result.getInt("UserID"));
                order.setProductId(result.getInt("ProductID"));
                order.setQuantity(result.getInt("Quantity"));
                order.setShippingAddress(result.getString("ShippingAddress"));
                order.setShippingOption(result.getInt("ShippingMethod"));
                order.setPaymentMethod(result.getInt("PaymentMethod"));
                order.setOrderDate(result.getDate("OrderDate"));
                order.setTotalCost(result.getDouble("TotalCost"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
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
                order.setId(resultSet.getInt("order_ID"));
                order.setUserId(resultSet.getInt("UserID"));
                order.setProductId(resultSet.getInt("ProductID"));
                order.setQuantity(resultSet.getInt("Quantity"));
                order.setShippingAddress(resultSet.getString("ShippingAddress"));
                order.setShippingOption(resultSet.getInt("ShippingMethod"));
                order.setPaymentMethod(resultSet.getInt("PaymentMethod"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersById(int id) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM `Order` WHERE UserID = ?"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("order_ID"));
                order.setUserId(resultSet.getInt("UserID"));
                order.setProductId(resultSet.getInt("ProductID"));
                order.setQuantity(resultSet.getInt("Quantity"));
                order.setShippingAddress(resultSet.getString("ShippingAddress"));
                order.setShippingOption(resultSet.getInt("ShippingMethod"));
                order.setPaymentMethod(resultSet.getInt("PaymentMethod"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return orders;
    }

    @Override
    public boolean updateOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE `Order` SET UserID = ?, ProductID = ?, Quantity = ?, ShippingMethod = ?, ShippingAddress = ?, PaymentMethod = ?, OrderDate = ?, TotalCost = ? WHERE order_ID = ?");
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, order.getShippingOption());
            statement.setString(5, order.getShippingAddress());
            statement.setInt(6, order.getPaymentMethod());
            statement.setDate(7, order.getOrderDate());
            statement.setDouble(8, order.getTotalCost());
            statement.setInt(9, order.getId());
            this.orderUpdateDao.createOrderUpdate(new OrderUpdate(order.getId(), order.toString(), order.getOrderDate()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `Order` WHERE order_ID = ?");
            statement.setInt(1, id);
            this.orderStatusDao.deleteOrderStatus(id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }
}
