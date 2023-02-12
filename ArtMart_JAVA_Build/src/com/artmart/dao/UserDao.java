/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.interfaces.IUserDao;
import com.artmart.models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author 21697
 */
public class UserDao implements IUserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createAccountU(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user ( name, email, birthday, phoneNumber, username, password, dateOfCreation, role) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setDate(3, (Date)user.getBirthday());
            statement.setInt(4, user.getPhone_nbr());
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPwd());
            statement.setTimestamp(7, timestamp);
            statement.setString(8, user.getRole());
            statement.executeUpdate();

            return 1;
        } catch (SQLException e) {
            System.err.println("Error occured");
            return 0;
        }
    }

    @Override
    public User getUser(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE user_ID = ?"
            );
            statement.setInt(1,user_id);
            ResultSet resultSet= statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_ID"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone_nbr(resultSet.getInt("phoneNumber"));
                user.setUsername(resultSet.getString("username"));
                user.setPwd(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAccountU(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM user WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAccountU(int user_id, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE user SET name = ?, email = ?, birthday = ?, phoneNumber = ?, username = ?, password = ? WHERE user_ID = ?"
            );
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setDate(3,(Date) user.getBirthday());
            statement.setInt(4, user.getPhone_nbr());
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPwd());
            statement.setInt(7, user_id);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
