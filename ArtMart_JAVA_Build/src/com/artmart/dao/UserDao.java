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
import java.util.logging.Level;
import java.util.logging.Logger;

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
            statement.setString(6, hashPassword(user.getPwd()));
            statement.setTimestamp(7, timestamp);
            statement.setString(8, user.getRole());

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
                user.setPicture(resultSet.getString("picture"));
                user.setBlocked(resultSet.getBoolean("blocked"));
                user.setEnabled(resultSet.getBoolean("enabled"));

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
                user.setUser_id(resultSet.getInt("user_ID"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone_nbr(resultSet.getInt("phoneNumber"));
                user.setRole(resultSet.getString("role"));
                user.setUsername(resultSet.getString("username"));
                user.setPwd(resultSet.getString("password"));
                user.setBlocked(resultSet.getBoolean("blocked"));
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
                    "UPDATE user SET name = ?, email = ?, birthday = ?, phoneNumber = ?, username = ?, password = ?, picture= ? WHERE user_ID = ?"
            );
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setDate(3, (Date) user.getBirthday());
            statement.setInt(4, user.getPhone_nbr());
            statement.setString(5, user.getUsername());
            statement.setString(6, user.getPwd());
            statement.setString(7, user.getPicture());
            statement.setInt(8, user_id);
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
            ResultSet user_result = statement.executeQuery();
            password = hashPassword(password);
            System.out.println(password);
            if (user_result.next()) {
                String storedPassword = user_result.getString("password");
                System.out.println(storedPassword);
                if (storedPassword.equals(password)) {
                    return true;
                }

            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return false;
    }

    @Override
    public int getUserIdByUsername(String username) {

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            );

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_ID"));

                return user.getUser_id();

            }
        } catch (SQLException e) {
            System.err.println("Error " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int getUserIdByEmail(String email) {

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM user WHERE email = ?"
            );

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_ID"));

                return user.getUser_id();

            }
        } catch (SQLException e) {
            System.err.println("Error  " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean blockUser(int user_id, boolean state) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE user SET blocked = ? WHERE user_ID = ?"
            );
            statement.setBoolean(1, state);
            statement.setInt(2, user_id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean enableUser(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE user SET enabled = ? WHERE email = ?"
            );
            statement.setBoolean(1, true);
            statement.setString(2, email);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    public String getClientNameById(int clientId) throws SQLException {
        String clientName = null;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        statement = connection.prepareStatement("SELECT name FROM user WHERE user_ID = ?");
        statement.setInt(1, clientId);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {
            clientName = resultSet.getString("name");
        }
        return clientName;
    }

    public static String hashPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());
            byte[] hashedPasswordBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void StoreToken(String token, String email) {
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

    @Override
    public String verifyToken(String email) {

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT token FROM user WHERE email = ?"
            );

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();

                user.setToken(resultSet.getString("token"));
                String retrivedToken = user.getToken();
                return retrivedToken;
            }

        } catch (SQLException ex) {
            Logger.getLogger(VerificationTokenDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    public String getPhoneNumberById(int userId) {
        String phoneNumber = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT phoneNumber FROM user WHERE user_ID = ?");
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                phoneNumber = resultSet.getString("phoneNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of creation
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return phoneNumber;
    }

}
