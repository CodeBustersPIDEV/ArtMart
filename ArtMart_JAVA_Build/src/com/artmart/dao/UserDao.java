package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IUserDao;
import com.artmart.models.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    private Connection connection;

    public UserDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createAccountU(User user) {
        try {

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user ( name, email, birthday, phoneNumber, username, password, dateOfCreation, role) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setDate(3, (Date) user.getBirthday());
            statement.setInt(4, user.getPhone_nbr());
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPwd());
            statement.setTimestamp(7, timestamp);
            statement.setString(8, user.getRole());
          //  statement.setString(9, "/assets/user.jpeg");

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }
    }

    @Override
    public User getUser(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
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
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> viewListOfUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone_nbr(resultSet.getInt("phoneNumber"));
                user.setUsername(resultSet.getString("username"));
                user.setPwd(resultSet.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
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
            System.err.print(e.getMessage());
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
            statement.setDate(3, (Date) user.getBirthday());
            statement.setInt(4, user.getPhone_nbr());
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPwd());
            statement.setInt(7, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean authenticate(String username, String password) {
    try {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user WHERE username = ?"
        );
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("password");

            if (storedPassword.equals(hash(password))) {
                return true;
            }
        }
    } catch (SQLException e) {
        System.err.println("Error authenticating user: " + e.getMessage());
    }
    
    return false;
}

private String hash(String password) {
    try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception
            return null;
        }
    }
}






