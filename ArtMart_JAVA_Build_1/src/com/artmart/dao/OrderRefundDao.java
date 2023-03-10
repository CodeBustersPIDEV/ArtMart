package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.OrderRefund;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRefundDao implements IOrderRefundDao {

    private Connection connection;

    public OrderRefundDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createOrderRefund(OrderRefund orderRefund) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orderrefund (orderid, RefundAmount, Reason,Date) VALUES (?, ?, ?,?)")) {
            preparedStatement.setInt(1, orderRefund.getOrderId());
            preparedStatement.setDouble(2, orderRefund.getRefundAmount());
            preparedStatement.setString(3, orderRefund.getReason());
            preparedStatement.setDate(4, orderRefund.getDate());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return result;
    }

    @Override
    public OrderRefund getOrderRefund(int id) {
        OrderRefund orderRefund = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orderrefund WHERE orderRefund_ID = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderRefund = new OrderRefund();
                orderRefund.setId(resultSet.getInt("id"));
                orderRefund.setOrderId(resultSet.getInt("order_id"));
                orderRefund.setRefundAmount(resultSet.getDouble("refundamount"));
                orderRefund.setReason(resultSet.getString("reason"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
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
                orderRefund.setOrderId(resultSet.getInt("orderid"));
                orderRefund.setRefundAmount(resultSet.getDouble("RefundAmount"));
                orderRefund.setReason(resultSet.getString("reason"));
                orderRefunds.add(orderRefund);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return orderRefunds;
    }

    @Override
    public boolean updateOrderRefund(OrderRefund orderRefund) {
        try {
            String sql = "UPDATE orderrefund SET orderid = ?, RefundAmount = ?, date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderRefund.getOrderId());
            statement.setDouble(2, orderRefund.getRefundAmount());
            statement.setDate(3, new java.sql.Date(orderRefund.getDate().getTime()));
            statement.setInt(4, orderRefund.getId());
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            return rowsUpdated == 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
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
            statement.close();
            return rowsDeleted == 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return false;
        }
    }
}
