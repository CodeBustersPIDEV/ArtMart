package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IProductDao;
import com.artmart.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    private Connection sqlConnection;

    public ProductDao() {
        try {
            this.sqlConnection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @Override
    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE product_ID = ?";
        PreparedStatement statement = this.sqlConnection.prepareStatement(query);
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

    @Override
    public int createProduct(Product product) {
        try {
            PreparedStatement statement = this.sqlConnection.prepareStatement("INSERT INTO product (category_ID, name, description, dimensions, weight, material, image) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getDimensions());
            statement.setFloat(5, product.getWeight());
            statement.setString(6, product.getMaterial());
            statement.setString(7, product.getImage());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                System.err.print("Not Added");
                return 0;
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
            return 0;
        }

    }

    @Override
    public boolean updateProduct(int id, Product product) throws SQLException {
        System.out.println(id);
        System.out.println(product);
        String query = "UPDATE product SET category_ID = ?, name = ?, description = ?, dimensions = ?, weight = ?, material = ?, image = ? WHERE product_ID = ?";
        PreparedStatement statement = this.sqlConnection.prepareStatement(query);
        statement.setInt(1, product.getCategoryId());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getDimensions());
        statement.setFloat(5, product.getWeight());
        statement.setString(6, product.getMaterial());
        statement.setString(7, product.getImage());
        statement.setInt(8, id);
        statement.executeUpdate();
        return true;
    }

    @Override
    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM product WHERE product_ID = ?";
        PreparedStatement statement = this.sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public List<Product> getAllProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_ID, category_ID, name, description, dimensions, weight, material, image FROM product";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int product_ID = result.getInt("product_ID");
            int category_ID = result.getInt("category_ID");
            String name = result.getString("name");
            String description = result.getString("description");
            String dimensions = result.getString("dimensions");
            float weight = result.getFloat("weight");
            String material = result.getString("material");
            String image = result.getString("image");

            Product product = new Product(product_ID, category_ID, name, description, dimensions, weight, material, image);
            products.add(product);
        }
        return products;
    }
}
