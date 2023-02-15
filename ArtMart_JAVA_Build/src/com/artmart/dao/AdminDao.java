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
    private UserDao userDao = new UserDao();

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
            PreparedStatement adminStatement = connection.prepareStatement(
                    "INSERT INTO admin (user_ID, department) "
                    + "VALUES (?, ?)"
            );
            adminStatement.setInt(1, userDao.createAccountU(admin));
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
                Admin admin = new Admin(userDao.getUser(user_id));
                admin.setAdmin_id(resultSet.getInt("admin_ID"));
                admin.setUser_id(resultSet.getInt("user_ID"));
                admin.setDepartment(resultSet.getString("department"));

                return admin;
            }
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
            boolean us = userDao.deleteAccountU(user_id);
            return us;
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
            boolean user = userDao.updateAccountU(user_id, admin);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
