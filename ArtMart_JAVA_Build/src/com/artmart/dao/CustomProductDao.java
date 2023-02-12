package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.models.CustomProduct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.artmart.interfaces.ICustomProductDao;
import com.artmart.interfaces.ICustomProductService;
import com.artmart.models.CustomProduct;



public class CustomProductDao implements ICustomProductService {

    private SQLConnection sqlConnection = SQLConnection.getInstance();

    @Override
    public CustomProduct getCustomProductById(int id) throws SQLException {
        String query = "SELECT * FROM custom_product WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            CustomProduct customProduct = new CustomProduct(
                resultSet.getInt("custom_product_ID"),
                resultSet.getInt("product_ID")
            );
            return customProduct;
        }

        return null;
    }

    public int createCustomProduct(CustomProduct customProduct) throws SQLException {
        String query = "INSERT INTO custom_product (product_ID) VALUES (?)";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, customProduct.getProductId());

        return statement.executeUpdate();
    }

    public int updateCustomProduct(CustomProduct customProduct) throws SQLException {
        String query = "UPDATE custom_product SET product_ID = ? WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, customProduct.getProductId());
        statement.setInt(2, customProduct.getCustomProductId());

        return statement.executeUpdate();
    }

    public int deleteCustomProduct(int id) throws SQLException {
        String query = "DELETE FROM custom_product WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.connection.prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }
}
