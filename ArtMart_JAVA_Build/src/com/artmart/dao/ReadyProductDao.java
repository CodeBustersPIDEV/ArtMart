package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Product;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ReadyProductDao {

    private Connection sqlConnection;

    public ReadyProductDao() {
        try {
            this.sqlConnection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    private ProductDao productDAO = new ProductDao();

    public ReadyProduct getReadyProductById(int id) throws SQLException {
        String query = "SELECT * FROM readyproduct WHERE ready_product_ID = ?";
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
                ReadyProduct readyProduct = new ReadyProduct(
                        resultSet.getInt("ready_product_ID"),
                        productId,
                        product
                );
                return readyProduct;
            }
        }
        return null;
    }

    public List<ReadyProduct> getAllReadyProducts() throws SQLException {
        List<ReadyProduct> readyProducts = new ArrayList<>();
        String query = "SELECT * FROM readyproduct";
        
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
                ReadyProduct readyProduct = new ReadyProduct(
                        resultSet.getInt("ready_product_ID"),
                        this.productDAO.getProductById(resultSet.getInt("product_ID"))
                );
                readyProducts.add(readyProduct);
        }
        return readyProducts;
    }

    public int createReadyProduct(Product p) throws SQLException {
        String query = "INSERT INTO readyproduct (product_ID) VALUES (?)";
        productDAO.createProduct(p);
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, p.getProductId());
        return statement.executeUpdate();
    }

    public int updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException {
        productDAO.updateProduct(id, readyProduct);
        String query = "UPDATE readyproduct SET product_ID = ? WHERE ready_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, readyProduct.getProductId());
        statement.setInt(2, id);
        return statement.executeUpdate();
    }

    public int deleteReadyProduct(int id) throws SQLException {
        String query = "DELETE FROM readyproduct WHERE ready_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeUpdate();
    }

}
