package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import java.util.ArrayList;
import java.util.List;


public class CustomProductDao {

    private final SQLConnection sqlConnection = SQLConnection.getInstance();
    
    private ProductDao productDAO = new ProductDao();
    
    
public CustomProduct getCustomProductById(int id) throws SQLException {
    String query = "SELECT * FROM customproduct WHERE custom_product_ID = ?";
    String productQuery = "SELECT * FROM product WHERE product_ID = ?";
        
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    statement.setInt(1, id);
    ResultSet resultSet = statement.executeQuery();

    if (resultSet.next()) {
        int productId = resultSet.getInt("product_ID");
        
        PreparedStatement productStatement = sqlConnection.getConnection().prepareStatement(productQuery);
        productStatement.setInt(1, productId);
        ResultSet productResultSet = productStatement.executeQuery();
        
        if (productResultSet.next()) {
            Product product = new Product(
                productResultSet.getInt("product_ID"),
                productResultSet.getInt("category_ID"),
                productResultSet.getString("name"),
                productResultSet.getString("description"),
                productResultSet.getString("dimensions"),
                productResultSet.getInt("weight"),
                productResultSet.getString("material"),
                productResultSet.getString("image")
            );
            CustomProduct customProduct = new CustomProduct(
                resultSet.getInt("custom_product_ID"),
                productId,
                product
            );
            return customProduct;
        }
    }

    return null;
}

    
    public List<CustomProduct> getAllCustomProducts() throws SQLException {
    List<CustomProduct> customProducts = new ArrayList<>();
    String query = "SELECT * FROM customproduct";
    String productQuery = "SELECT * FROM product WHERE product_ID = ?";
        
    PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
    ResultSet resultSet = statement.executeQuery();

    while (resultSet.next()) {
        int productId = resultSet.getInt("product_ID");
        
        PreparedStatement productStatement = sqlConnection.getConnection().prepareStatement(productQuery);
        productStatement.setInt(1, productId);
        ResultSet productResultSet = productStatement.executeQuery();
        
        if (productResultSet.next()) {
            Product product = new Product(
                productResultSet.getInt("product_ID"),
                productResultSet.getInt("category_ID"),
                productResultSet.getString("name"),
                productResultSet.getString("description"),
                productResultSet.getString("dimensions"),
                productResultSet.getInt("weight"),
                productResultSet.getString("material"),
                productResultSet.getString("image")
            );
            CustomProduct customProduct = new CustomProduct(
                resultSet.getInt("custom_product_ID"),
                productId,
                product
            );
            customProducts.add(customProduct);
        }
    }

    return customProducts;
}

    
    //PullAllCustom products

    public int createCustomProduct(Product baseProduct) throws SQLException {
        String query = "INSERT INTO customproduct (product_ID) VALUES (?)";
        productDAO.createProduct(baseProduct);
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, baseProduct.getProductId());
        return statement.executeUpdate();
    }

    public int updateCustomProduct(int id,CustomProduct customProduct) throws SQLException {
        productDAO.updateProduct(id,customProduct);
        String query = "UPDATE customproduct SET product_ID = ? WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, customProduct.getProductId());
        statement.setInt(2, id);

        return statement.executeUpdate();
    }

    public int deleteCustomProduct(int id) throws SQLException {
        String query = "DELETE FROM customproduct WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }
}
