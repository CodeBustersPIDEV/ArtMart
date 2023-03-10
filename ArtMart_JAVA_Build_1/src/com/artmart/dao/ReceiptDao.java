package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.Receipt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDao implements IOrderReceiptDao {

    private Connection conn;

    public ReceiptDao() {
        try {
            this.conn = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createReceipt(Receipt receipt) {
        String sql = "INSERT INTO receipt (orderid, productid, quantity, price, tax, totalcost, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, receipt.getOrderId());
            stmt.setInt(2, receipt.getProductId());
            stmt.setInt(3, receipt.getQuantity());
            stmt.setDouble(4, receipt.getPrice());
            stmt.setDouble(5, receipt.getTax());
            stmt.setDouble(6, receipt.getTotalCost());
            stmt.setDate(7, new java.sql.Date(receipt.getDate().getTime()));
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public Receipt getReceipt(int id) {
        String sql = "SELECT * FROM receipt WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Receipt(rs.getInt("receipt_ID"), rs.getInt("orderid"), rs.getInt("productid"), rs.getInt("quantity"),
                            rs.getDouble("price"), rs.getDouble("tax"), rs.getDouble("totalcost"), rs.getDate("date"));
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
    public List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        String sql = "SELECT * FROM receipt";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                receipts.add(new Receipt(rs.getInt("receipt_ID"), rs.getInt("orderid"), rs.getInt("productid"), rs.getInt("quantity"),
                        rs.getDouble("price"), rs.getDouble("tax"), rs.getDouble("totalcost"), rs.getDate("date")));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return receipts;
    }

    @Override
    public boolean updateReceipt(Receipt receipt) {
        String sql = "UPDATE receipt SET orderid = ?, productid = ?, quantity = ?, price = ?, tax = ?, totalcost = ?, date = ? WHERE receipt_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receipt.getOrderId());
            stmt.setInt(2, receipt.getProductId());
            stmt.setInt(3, receipt.getQuantity());
            stmt.setDouble(4, receipt.getPrice());
            stmt.setDouble(5, receipt.getTax());
            stmt.setDouble(6, receipt.getTotalCost());
            stmt.setDate(7, new java.sql.Date(receipt.getDate().getTime()));
            stmt.setInt(8, receipt.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteReceipt(int id) {
        String sql = "DELETE FROM receipt WHERE receipt_ID = ?";
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
