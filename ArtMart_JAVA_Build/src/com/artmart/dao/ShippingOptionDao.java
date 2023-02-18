package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.ShippingOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingOptionDao implements IShippingOptionDao {

    private Connection conn;

    public ShippingOptionDao() {
        try {
            this.conn = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createShippingOption(ShippingOption shippingOption) {
        String sql = "INSERT INTO shippingoption (name, carrier, shippingspeed, shippingfee, availableregions) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, shippingOption.getName());
            stmt.setString(2, shippingOption.getCarrier());
            stmt.setString(3, shippingOption.getShippingSpeed());
            stmt.setDouble(4, shippingOption.getShippingFee());
            stmt.setString(5, shippingOption.getAvailableRegions());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public ShippingOption getShippingOption(int id) {
        String sql = "SELECT * FROM shippingoption WHERE shippingOption_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ShippingOption(rs.getInt("shippingOptionID"), rs.getString("name"), rs.getString("carrier"),
                            rs.getString("shippingspeed"), rs.getDouble("shippingfee"), rs.getString("availableregions"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public List<ShippingOption> getShippingOptions() {
        List<ShippingOption> shippingOptions = new ArrayList<>();
        String sql = "SELECT * FROM shippingoption";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                shippingOptions.add(new ShippingOption(rs.getInt("shippingOption_ID"), rs.getString("name"), rs.getString("carrier"),
                        rs.getString("shippingspeed"), rs.getDouble("shippingfee"), rs.getString("availableregions")));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return shippingOptions;
    }

    @Override
    public boolean updateShippingOption(ShippingOption shippingOption) {
        String sql = "UPDATE shippingoption SET name = ?, carrier = ?, shippingspeed = ?, shippingfee = ?, availableregions = ? WHERE shippingOption_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, shippingOption.getName());
            stmt.setString(2, shippingOption.getCarrier());
            stmt.setString(3, shippingOption.getShippingSpeed());
            stmt.setDouble(4, shippingOption.getShippingFee());
            stmt.setString(5, shippingOption.getAvailableRegions());
            stmt.setInt(6, shippingOption.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteShippingOption(int id) {
        String sql = "DELETE FROM shippingoption WHERE shippingOption_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

}
