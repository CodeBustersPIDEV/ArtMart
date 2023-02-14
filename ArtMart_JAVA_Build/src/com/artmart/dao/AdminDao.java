package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IAdminDao;
import com.artmart.models.Admin;
import com.artmart.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.artmart.services.UserService;

public class AdminDao implements IAdminDao {
    
    private Connection connection;
    
    public AdminDao() {
        try {
            this.connection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }
    
    @Override
    public int createAccountA(Admin admin) {
        try {
            
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO user ( name, email, birthday, phoneNumber, username, password, dateOfCreation, role) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getEmail());
            statement.setDate(3, admin.getBirthday());
            statement.setInt(4, admin.getPhone_nbr());
            statement.setString(5, admin.getUsername());
            statement.setString(6, admin.getPwd());
            statement.setTimestamp(7, timestamp);
            statement.setString(8, admin.getRole());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            } else {
                return 0;
            }
            PreparedStatement adminStatement = connection.prepareStatement(
                    "INSERT INTO admin (user_ID, department) "
                    + "VALUES (?, ?)"
            );
            adminStatement.setInt(1, userId);
            adminStatement.setString(2, admin.getDepartment());
            adminStatement.executeUpdate();
            return 1;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public Admin getAdmin(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM admin WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdmin_id(resultSet.getInt("admin_ID"));
                admin.setUser_id(resultSet.getInt("user_ID"));
                admin.setDepartment(resultSet.getString("department"));
                UserService user_ser = new UserService();
                User ad = new User();
                ad = user_ser.getUser(user_id);
                
                return admin;
            }
        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return null;
    }
    
    @Override
    public List<User> viewListOfUsersA() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User();
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
            System.err.println("Error occured");
        }
        return null;
    }
    
    @Override
    public boolean deleteAccountA(int user_id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM admin WHERE user_ID = ?"
            );
            statement.setInt(1, user_id);
            statement.executeUpdate();
            UserService user_ser = new UserService();
            return user_ser.deleteAccountU(user_id);
        } catch (SQLException e) {
            System.err.println("Error occured");
        }
        return false;
    }
    
    @Override
    public boolean updateAccountA(int user_id, Admin admin) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE admin SET  department= ? WHERE user_ID = ?"
            );
            
            statement.setString(1, admin.getDepartment());
            
            statement.setInt(2, user_id);
            statement.executeUpdate();
            UserService user_ser = new UserService();
            return user_ser.updateAccountU(user_id, admin);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
