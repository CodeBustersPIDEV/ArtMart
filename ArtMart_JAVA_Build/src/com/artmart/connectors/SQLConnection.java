    package com.artmart.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection {
    private static SQLConnection instance;
    private Connection connection;
    private String host="localhost:3306";
    private String user="root";
    private String password="";
    private String database="artmart";

    private SQLConnection() {
    }

    public static SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public int executeUpdate(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeUpdate();
    }

    public void close() throws SQLException {
        connection.close();
    }
}
