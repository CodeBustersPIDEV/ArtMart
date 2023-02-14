package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.PaymentOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentOptionDao implements IPaymentOptionDao{
    
    private Connection connection;

    public PaymentOptionDao() {
    try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
    
    @Override
    public int createPaymentOption(PaymentOption paymentOption){
        String sql = "INSERT INTO payment_option (name, available_countries) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, paymentOption.getName());
            stmt.setString(2, paymentOption.getAvailableCountries());
            return stmt.executeUpdate();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public PaymentOption getPaymentOption(int id){
        String sql = "SELECT * FROM payment_option WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PaymentOption(rs.getInt("id"), rs.getString("name"), rs.getString("available_countries"));
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
    public List<PaymentOption> getPaymentOptions(){
        List<PaymentOption> paymentOptions = new ArrayList<>();
        String sql = "SELECT * FROM payment_option";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                paymentOptions.add(new PaymentOption(rs.getInt("id"), rs.getString("name"), rs.getString("available_countries")));
            }
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return paymentOptions;
    }

    @Override
    public boolean updatePaymentOption(PaymentOption paymentOption){
        String sql = "UPDATE payment_option SET name = ?, available_countries = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paymentOption.getName());
            stmt.setString(2, paymentOption.getAvailableCountries());
            stmt.setInt(3, paymentOption.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletePaymentOption(int id){
        String sql = "DELETE FROM payment_option WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }

}
