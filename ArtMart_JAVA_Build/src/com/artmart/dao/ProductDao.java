package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.models.Product;
import com.artmart.interfaces.IProductDao;
import com.artmart.models.Product;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao implements IProductDao{

    private SQLConnection sqlConnection = SQLConnection.getInstance();

    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE product_ID = ?";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Product product = new Product(
                resultSet.getInt("product_ID"),
                resultSet.getInt("category_ID"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("dimensions"),
                resultSet.getFloat("weight"),
                resultSet.getString("material"),
                resultSet.getString("image")
            );
            return product;
        }

        return null;
    }

    public int createProduct(Product product) throws SQLException {
        String query = "INSERT INTO product (category_ID, name, description, dimensions, weight, material, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, product.getCategoryId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getDimensions());
        statement.setFloat(5, product.getWeight());
        statement.setString(6, product.getMaterial());
        statement.setString(7, product.getImage());

        return statement.executeUpdate();
    }

    public int updateProduct(Product product) throws SQLException {
        String query = "UPDATE product SET category_ID = ?, name = ?, description = ?, dimensions = ?, weight = ?, material = ?, image = ? WHERE product_ID = ?";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, product.getCategoryId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getDimensions());
        statement.setFloat(5, product.getWeight());
        statement.setString(6, product.getMaterial());
        statement.setString(7, product.getImage());
        statement.setInt(8, product.getProductId());

        return statement.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM product WHERE product_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, id);
    }
    }