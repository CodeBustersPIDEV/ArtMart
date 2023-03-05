package com.artmart.services;

import com.artmart.dao.DatabaseUtilsDao;
import com.artmart.interfaces.IDatabaseUtilsDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DatabaseUtilsService implements IDatabaseUtilsDao {

    private DatabaseUtilsDao dao;

    @Override
    public void printTableNames() throws SQLException {
        dao.printTableNames(getConnection());
    }

    @Override
    public List<String> getTableColumnNames(String tableName) throws SQLException {
        return dao.getTableColumnNames(tableName);
    }

    @Override
    public List<String> getTableNames() throws SQLException {
        return dao.getTableNames();
    }

    private Connection getConnection() {
        // Set up connection details here
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<List<Object>> getTableData(String tableName) throws SQLException {
        return dao.getTableData(tableName);
    }
}
