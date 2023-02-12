package com.artmart.dao;

import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.OrderRefund;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRefundDao implements IOrderRefundDao{
    
    private Connection connection;

    public OrderRefundDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createOrderRefund(OrderRefund orderRefund) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderrefund (order_id, amount, reason) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, orderRefund.getOrderId());
            preparedStatement.setDouble(2, orderRefund.getRefundAmount());
            preparedStatement.setString(3, orderRefund.getReason());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public OrderRefund getOrderRefund(int id) {
        OrderRefund orderRefund = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orderrefund WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderRefund = new OrderRefund();
                orderRefund.setId(resultSet.getInt("id"));
                orderRefund.setOrderId(resultSet.getInt("order_id"));
                orderRefund.setRefundAmount(resultSet.getDouble("refundamount"));
                orderRefund.setReason(resultSet.getString("reason"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderRefund;
    }

    @Override
    public List<OrderRefund> getOrderRefunds() {
    List<OrderRefund> orderRefunds = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orderrefund")) {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            OrderRefund orderRefund = new OrderRefund();
            orderRefund.setId(resultSet.getInt("id"));
            orderRefund.setOrderId(resultSet.getInt("order_id"));
            orderRefund.setRefundAmount(resultSet.getDouble("refundamount"));
            orderRefund.setReason(resultSet.getString("reason"));
            orderRefunds.add(orderRefund);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orderRefunds;
}

    @Override
    public boolean updateOrderRefund(OrderRefund orderRefund) {
        try {
        String sql = "UPDATE orderrefund SET order_id = ?, refundamount = ?, date = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderRefund.getOrderId());
        statement.setDouble(2, orderRefund.getRefundAmount());
        statement.setDate(3, new java.sql.Date(orderRefund.getDate().getTime()));
        statement.setInt(4, orderRefund.getId());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }

    @Override
    public boolean deleteOrderRefund(int id) {
     try {
        String sql = "DELETE FROM orderrefund WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
    }
}
