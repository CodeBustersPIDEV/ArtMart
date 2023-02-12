package com.artmart.services;
import com.artmart.interfaces.ICustomProductService;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IProductService;
import com.artmart.services.ProductService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import com.artmart.models.CustomProduct;



public class CustomProductService implements ICustomProductService {
    private final SQLConnection connection;

    public CustomProductService() {
        this.connection = SQLConnection.getInstance();
    }

    @Override
    public void createCustomProduct(CustomProduct customProduct) {
        try {
            connection.connect();
            String query = "INSERT INTO custom_product (product_ID, custom_product_ID) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customProduct.getProductID());
            statement.setInt(2, customProduct.getCustomProductID());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CustomProduct readCustomProduct(int customProductID) {
        CustomProduct customProduct = null;
        try {
            connection.connect();
            String query = "SELECT * FROM custom_product WHERE custom_product_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customProductID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int productID = result.getInt("product_ID");
                customProduct = new CustomProduct(productID, customProductID);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customProduct;
    }

    @Override
    public void updateCustomProduct(CustomProduct customProduct) {
        try {
            connection.connect();
            String query = "UPDATE custom_product SET product_ID = ? WHERE custom_product_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customProduct.getProductID());
            statement.setInt(2, customProduct.getCustomProductID());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomProduct(int customProductID) {
        try {
            connection.connect();
            String query = "DELETE FROM custom_product WHERE custom_product_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customProductID);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
