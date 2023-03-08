/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtilsDao {

    private Connection sqlConnection;

    public DatabaseUtilsDao() {
        try {
            this.sqlConnection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    // constructor to initialize the connection
    public DatabaseUtilsDao(Connection connection) {
        this.sqlConnection = connection;
    }

    public static void printTableNames(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet resultSet = metaData.getTables(null, null, "%", types);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("TABLE_NAME"));
        }
    }

    public List<String> getTableColumnNames(String tableName) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            columnNames.add(resultSet.getString("COLUMN_NAME"));
        }
        conn.close();
        return columnNames;
    }

    public List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();
        DatabaseMetaData metadata = sqlConnection.getMetaData();
        ResultSet resultSet = metadata.getTables(null, null, "%", null);
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            tableNames.add(tableName);
        }
        return tableNames;
    }

    public List<List<Object>> getTableData(String tableName) throws SQLException {
        List<List<Object>> tableData = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();
        while (resultSet.next()) {
            List<Object> row = new ArrayList<>();
            for (int i = 1; i <= numColumns; i++) {
                row.add(resultSet.getObject(i));
            }
            tableData.add(row);
        }
        sqlConnection.close();
        return tableData;
    }

}
