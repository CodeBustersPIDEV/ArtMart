package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.ShippingOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingOptionDao implements IShippingOptionDao{
    
    private Connection conn;

    public ShippingOptionDao() {
    try{
        this.conn = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createShippingOption(ShippingOption shippingOption){
        String sql = "INSERT INTO shipping_option (name, carrier, shipping_speed, shipping_fee, available_regions) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, shippingOption.getName());
            stmt.setString(2, shippingOption.getCarrier());
            stmt.setString(3, shippingOption.getShippingSpeed());
            stmt.setDouble(4, shippingOption.getShippingFee());
            stmt.setString(5, shippingOption.getAvailableRegions());
            return stmt.executeUpdate();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public ShippingOption getShippingOption(int id){
        String sql = "SELECT * FROM shipping_option WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ShippingOption(rs.getInt("id"), rs.getString("name"), rs.getString("carrier"),
                            rs.getString("shipping_speed"), rs.getDouble("shipping_fee"), rs.getString("available_regions"));
                } else {
                    return null;
                }
            }
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public List<ShippingOption> getShippingOptions(){
        List<ShippingOption> shippingOptions = new ArrayList<>();
        String sql = "SELECT * FROM shipping_option";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                shippingOptions.add(new ShippingOption(rs.getInt("id"), rs.getString("name"), rs.getString("carrier"),
                        rs.getString("shipping_speed"), rs.getDouble("shipping_fee"), rs.getString("available_regions")));
            }
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return shippingOptions;
    }

    @Override
    public boolean updateShippingOption(ShippingOption shippingOption){
        String sql = "UPDATE shipping_option SET name = ?, carrier = ?, shipping_speed = ?, shipping_fee = ?, available_regions = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, shippingOption.getName());
            stmt.setString(2, shippingOption.getCarrier());
            stmt.setString(3, shippingOption.getShippingSpeed());
            stmt.setDouble(4, shippingOption.getShippingFee());
            stmt.setString(5, shippingOption.getAvailableRegions());
            stmt.setInt(6, shippingOption.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteShippingOption(int id){
        String sql = "DELETE FROM shipping_option WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }

}
