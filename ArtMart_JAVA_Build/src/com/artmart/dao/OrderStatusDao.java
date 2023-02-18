package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.*;
import com.artmart.interfaces.*;
import com.artmart.models.OrderStatus;
import com.artmart.utils.OrderCurrentStatus;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDao implements IOrderStatusDao {

    private Connection connection;

    public OrderStatusDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createOrderStatus(OrderStatus orderStatus) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO OrderStatus (OrderID, Status, Date) VALUES (?, ?, ?)");
            stmt.setInt(1, orderStatus.getOrderId());
            stmt.setString(2, orderStatus.getStatus());
            stmt.setDate(3, orderStatus.getDate());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public OrderStatus getOrderStatus(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM OrderStatus WHERE orderStatus_ID = ?");
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(result.getInt("orderStatus_ID"));
                orderStatus.setOrderId(result.getInt("OrderID"));
                orderStatus.setStatus(result.getString("Status"));
                orderStatus.setDate(result.getDate("Date"));
                return orderStatus;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public OrderStatus getOrderStatusByOrderId(int id) {
        OrderStatus orderStatus = new OrderStatus();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM orderstatus WHERE OrderID = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                orderStatus = new OrderStatus(result.getInt("orderStatus_ID"),id,OrderCurrentStatus.valueOf(result.getString("Status")),result.getDate("Date"));
            }
        } catch (SQLException e) {
            System.err.print(e.getCause());
        }
        return orderStatus;
    }

    @Override
    public List<OrderStatus> getOrderStatuses() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderStatus");
            ResultSet resultSet = statement.executeQuery();
            List<OrderStatus> orderStatuses = new ArrayList<>();

            while (resultSet.next()) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(resultSet.getInt("orderStatus_ID"));
                orderStatus.setOrderId(resultSet.getInt("OrderID"));
                orderStatus.setStatus(resultSet.getString("Status"));
                orderStatus.setDate(resultSet.getDate("Date"));
                orderStatuses.add(orderStatus);
            }
            return orderStatuses;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateOrderStatus(OrderStatus orderStatus) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE OrderStatus SET OrderID = ?, Status = ?, Date = ? WHERE orderStatus_ID = ?");
            statement.setInt(1, orderStatus.getOrderId());
            statement.setString(2, orderStatus.getStatus());
            statement.setDate(3, new java.sql.Date(orderStatus.getDate().getTime()));
            statement.setInt(4, orderStatus.getId());
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrderStatus(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM OrderStatus WHERE orderStatus_ID = ?");
            stmt.setInt(1, id);
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }
}
