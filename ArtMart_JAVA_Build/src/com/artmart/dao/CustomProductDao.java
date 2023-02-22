package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CustomProductDao {

    private Connection sqlConnection;
    private ProductDao productDAO = new ProductDao();

    public CustomProductDao() {
        try {
            this.sqlConnection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }



    public CustomProduct getCustomProductById(int id) throws SQLException {
        String query = "SELECT * FROM customproduct WHERE custom_product_ID = ?";
        String productQuery = "SELECT * FROM product WHERE product_ID = ?";

        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int productId = resultSet.getInt("product_ID");

            PreparedStatement productStatement = sqlConnection.prepareStatement(productQuery);
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

    public int getCustomPId(int id) throws SQLException {
        String query = "SELECT product_ID FROM customproduct WHERE custom_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("product_ID");
        } else {
            return 0;
        }

    }

    
    
    
    
    
    
    
    
    public List<CustomProduct> getAllCustomProducts() throws SQLException {
        List<CustomProduct> customProducts = new ArrayList<>();
        String query = "SELECT * FROM customproduct";
        
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
                CustomProduct customProduct = new CustomProduct(
                        resultSet.getInt("custom_product_ID"),
                        this.productDAO.getProductById(resultSet.getInt("product_ID"))
                );
                customProducts.add(customProduct);
        }
        return customProducts;
    }

    public int createCustomProduct(Product baseProduct) {
        try {
            String query = "INSERT INTO customproduct (product_ID) VALUES (?)";

            PreparedStatement statement = sqlConnection.prepareStatement(query);
            statement.setInt(1, productDAO.createProduct(baseProduct));
            return statement.executeUpdate();

        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }

    }

    public int updateCustomProduct(int id, CustomProduct customProduct) throws SQLException {
        productDAO.updateProduct(id, customProduct);
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
        statement.executeUpdate();
        this.productDAO.deleteProduct(this.getCustomPId(id));
        return 1;
    }
    
    public List<CustomProduct> searchCustomProductByName(String name) throws SQLException {
    List<CustomProduct> customProducts = new ArrayList<>();
    String query = "SELECT * FROM customproduct INNER JOIN product ON customproduct.product_ID = product.product_ID WHERE product.name LIKE ?";
    PreparedStatement statement = sqlConnection.prepareStatement(query);
    statement.setString(1, "%" + name + "%");
    ResultSet resultSet = statement.executeQuery();

    while(resultSet.next()) {
        CustomProduct customProduct = new CustomProduct(
            resultSet.getInt("custom_product_ID"),
            this.productDAO.getProductById(resultSet.getInt("product_ID"))
        );
        customProducts.add(customProduct);
    }
    return customProducts;
}

public List<CustomProduct> getAllCustomProductsSortedByName() throws SQLException {
    List<CustomProduct> customProducts = new ArrayList<>();
    String query = "SELECT * FROM customproduct INNER JOIN product ON customproduct.product_ID = product.product_ID ORDER BY product.name";

    PreparedStatement statement = sqlConnection.prepareStatement(query);
    ResultSet resultSet = statement.executeQuery();

    while(resultSet.next()) {
        CustomProduct customProduct = new CustomProduct(
                resultSet.getInt("custom_product_ID"),
                this.productDAO.getProductById(resultSet.getInt("product_ID"))
        );
        customProducts.add(customProduct);
    }
    return customProducts;
}

public List<CustomProduct> getAllCustomProductsSortedByWeight() throws SQLException {
    List<CustomProduct> customProducts = new ArrayList<>();
    String query = "SELECT * FROM customproduct INNER JOIN product ON customproduct.product_ID = product.product_ID ORDER BY product.weight";

    PreparedStatement statement = sqlConnection.prepareStatement(query);
    ResultSet resultSet = statement.executeQuery();

    while(resultSet.next()) {
        CustomProduct customProduct = new CustomProduct(
                resultSet.getInt("custom_product_ID"),
                this.productDAO.getProductById(resultSet.getInt("product_ID"))
        );
        customProducts.add(customProduct);
    }
    return customProducts;
}


}

