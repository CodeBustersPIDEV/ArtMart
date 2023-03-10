package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.Wishlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDao implements IWishlistDao {

    private Connection conn;

    public WishlistDao() {
        try {
            this.conn = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (userid, productid, date,quantity,price) VALUES (?, ?, ?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, wishlist.getUserId());
            stmt.setInt(2, wishlist.getProductId());
            stmt.setDate(3, new java.sql.Date(wishlist.getDate().getTime()));
            stmt.setInt(4, wishlist.getQuantity());
            stmt.setDouble(5, wishlist.getPrice());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public Wishlist getWishlist(int id) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Wishlist(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("product_id"), rs.getDate("date"),rs.getInt("quantity"),rs.getDouble("price"));
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
    public List<Wishlist> getWishlistsByUserId(int id) {
        List<Wishlist> wishlists = new ArrayList<>();
        String sql = "SELECT * FROM wishlist where userid = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                wishlists.add(new Wishlist(rs.getInt("wishlist_ID"), rs.getInt("userid"), rs.getInt("productid"), rs.getDate("date"),rs.getInt("quantity"),rs.getDouble("price")));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return wishlists;
    }

    @Override
    public boolean updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET user_id = ?, product_id = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlist.getUserId());
            stmt.setInt(2, wishlist.getProductId());
            stmt.setDate(3, new java.sql.Date(wishlist.getDate().getTime()));
            stmt.setInt(4, wishlist.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteWishlist(int productId, int UserId) {
        String sql = "DELETE FROM wishlist WHERE UserID = ? and ProductID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, UserId);
            stmt.setInt(2, productId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }
}
