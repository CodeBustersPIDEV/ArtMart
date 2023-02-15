package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.OrderUpdate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderUpdateDao implements IOrderUpdateDao{
    
    private Connection connection;

    public OrderUpdateDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createOrderUpdate(OrderUpdate orderUpdate){
        String sql = "INSERT INTO order_update (order_id, update_message, date) VALUES (?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, orderUpdate.getOrderId());
            stmt.setString(2, orderUpdate.getUpdateMessage());
            stmt.setDate(3, new java.sql.Date(orderUpdate.getDate().getTime()));
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Creating order update failed, no rows affected.");
            }
            return stmt.executeUpdate();
        }catch (SQLException e) {
        System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public OrderUpdate getOrderUpdate(int id){
        String sql = "SELECT * FROM order_update WHERE id = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrderUpdate(rs.getInt("id"), rs.getInt("order_id"), rs.getString("update_message"), rs.getDate("date"));
                } else {
                    return null;
                }
            }
        }catch (SQLException e) {
        System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderUpdate> getOrderUpdates(){
        List<OrderUpdate> orderUpdates = new ArrayList<>();
        String sql = "SELECT * FROM order_update";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orderUpdates.add(new OrderUpdate(rs.getInt("id"), rs.getInt("order_id"), rs.getString("update_message"), rs.getDate("date")));
            }
        }catch (SQLException e) {
        System.err.print(e.getMessage());
        }
        return orderUpdates;
    }

    @Override
    public boolean updateOrderUpdate(OrderUpdate orderUpdate){
        String sql = "UPDATE order_update SET order_id = ?, update_message = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderUpdate.getOrderId());
            stmt.setString(2, orderUpdate.getUpdateMessage());
            stmt.setDate(3, new java.sql.Date(orderUpdate.getDate().getTime()));
            stmt.setInt(4, orderUpdate.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e) {
        System.err.print(e.getMessage());
        } 
        return false;
    }

    @Override
    public boolean deleteOrderUpdate(int id){
        String sql = "DELETE FROM order_update WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch (SQLException e) {
        System.err.print(e.getMessage());
        } 
        return false;
    }
}
