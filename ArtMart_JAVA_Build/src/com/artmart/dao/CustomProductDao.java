package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import java.sql.Connection;


public class CustomProductDao {

    private Connection sqlConnection;

    public CustomProductDao () {
    try{
        this.sqlConnection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
    
    
    private ProductDao productDAO = new ProductDao();

    public CustomProduct getCustomProductById(int id) throws SQLException {
        String query = "SELECT * FROM customproduct WHERE custom_product_ID = ?";
        //Pull Product Desc from Prodcut DAO
        PreparedStatement statement = sqlConnection.prepareStatement(query);
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
    
    //PullAllCustom products

    public int createCustomProduct(Product baseProduct) throws SQLException {
        String query = "INSERT INTO customproduct (product_ID) VALUES (?)";
        productDAO.createProduct(baseProduct);
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, baseProduct.getProductId());
        return statement.executeUpdate();
    }

    public int updateCustomProduct(int id,CustomProduct customProduct) throws SQLException {
        productDAO.updateProduct(id,customProduct);
        String query = "UPDATE customproduct SET product_ID = ? WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, customProduct.getProductId());
        statement.setInt(2, id);

        return statement.executeUpdate();
    }

    public int deleteCustomProduct(int id) throws SQLException {
        String query = "DELETE FROM customproduct WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }
}
