/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 21697
 */
public class VerificationTokenDao {

    private static Connection connection;

    public VerificationTokenDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    public static void StoreToken(String token, String email) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "UPDATE user SET token = ? WHERE email = ?"
            );
            statement.setString(1, token);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VerificationTokenDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static User verifyToken(String email, String token) {

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT token FROM user WHERE email = ?"
            );

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
                User user = new User();

                user.setToken(resultSet.getString("token"));

         return user;
        } catch (SQLException ex) {
            Logger.getLogger(VerificationTokenDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
