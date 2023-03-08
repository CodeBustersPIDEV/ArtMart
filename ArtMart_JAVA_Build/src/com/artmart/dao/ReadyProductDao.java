package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Product;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
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

    public ReadyProduct getReadyProductById(int id, int uID) throws SQLException {
        String query = "SELECT * FROM readyproduct WHERE ready_product_ID = ? AND user_ID = ?";
        String productQuery = "SELECT * FROM product WHERE product_ID = ?";

        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setInt(2, uID);
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

    public int getReadyProductId(int id, int uID) throws SQLException {
        String query = "SELECT product_ID FROM readyproduct WHERE ready_product_ID = ? AND user_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setInt(2, uID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("product_ID");
        } else {
            return 0;
        }
    }
    
    public int getReadyProductId(int id) throws SQLException {
        String query = "SELECT product_ID FROM readyproduct WHERE ready_product_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("product_ID");
        } else {
            return 0;
        }
    }

    public List<ReadyProduct> getAllReadyProducts(int uID) throws SQLException {
        List<ReadyProduct> readyProducts = new ArrayList<>();
        String query = "SELECT * FROM readyproduct WHERE user_ID = ?";

        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, uID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReadyProduct readyProduct = new ReadyProduct(
                    resultSet.getInt("ready_product_ID"),
                    this.productDAO.getProductById(resultSet.getInt("product_ID")),
                    resultSet.getInt("price"),
                    resultSet.getInt("user_ID")
            );
            readyProducts.add(readyProduct);
        }
        return readyProducts;
    }
    
    
    public List<ReadyProduct> getAllReadyProducts() throws SQLException {
        List<ReadyProduct> readyProducts = new ArrayList<>();
        String query = "SELECT * FROM readyproduct";

        PreparedStatement statement = sqlConnection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReadyProduct readyProduct = new ReadyProduct(
                    resultSet.getInt("ready_product_ID"),
                    this.productDAO.getProductById(resultSet.getInt("product_ID")),
                    resultSet.getInt("price"),
                    resultSet.getInt("user_ID")
            );
            readyProducts.add(readyProduct);
        }
        return readyProducts;
    }

    public int createReadyProduct(ReadyProduct p) throws SQLException {
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "INSERT INTO readyproduct (product_ID, price, user_ID) "
                    + "VALUES (?, ?, ?)"
            );
            statement.setInt(1, productDAO.createProduct(p));
            statement.setInt(2, p.getPrice());
            statement.setInt(3, p.getUserId());
            statement.executeUpdate();
            return 1;

        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public boolean updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException {
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "UPDATE readyproduct SET  price= ? WHERE ready_product_ID = ?"
            );

            statement.setInt(1, readyProduct.getPrice());

            statement.setInt(2, id);
            statement.executeUpdate();
            boolean readyPr = productDAO.updateProduct(id, readyProduct);
            return readyPr;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    public int deleteReadyProduct(int id) throws SQLException {
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "DELETE FROM readyproduct WHERE ready_product_ID = ?"
            );
            statement.setInt(1, id);
            statement.executeUpdate();
            this.productDAO.deleteProduct(id);
            return 1;
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public List<ReadyProduct> searchReadyProductByName(String name) throws SQLException {
        List<ReadyProduct> readyProducts = new ArrayList<>();
        String query = "SELECT * FROM readyproduct INNER JOIN product ON readyproduct.product_ID = product.product_ID WHERE product.name LIKE ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReadyProduct readyProduct = new ReadyProduct(
                    resultSet.getInt("ready_product_ID"),
                    this.productDAO.getProductById(resultSet.getInt("product_ID"))
            );
            readyProducts.add(readyProduct);
        }
        return readyProducts;
    }

    public static List<String> getRandomProductImages(int count) throws SQLException {
        ReadyProductDao readyProductDao = new ReadyProductDao();
        List<ReadyProduct> products = readyProductDao.getAllReadyProducts();
        List<String> images = new ArrayList<>();

        Collections.shuffle(products);
        for (int i = 0; i < count && i < products.size(); i++) {
            ReadyProduct product = products.get(i);
            String imagePath = product.getImage();
            File file = new File(imagePath);
            if (file.exists()) {
                images.add(file.toURI().toString());
            } else {
                System.out.println("Image file not found: " + imagePath);
            }
        }

        return images;
    }

    public List<ReadyProduct> getAllReadyProductsByCategoryName(String categoryName, int uID) throws SQLException {
        List<ReadyProduct> readyProducts = new ArrayList<>();
        String query = "SELECT readyproduct.ready_product_ID, readyproduct.product_ID, readyproduct.user_ID FROM readyproduct "
                + "INNER JOIN product ON readyproduct.product_ID = product.product_ID "
                + "INNER JOIN categories ON product.category_ID = categories.categories_ID "
                + "INNER JOIN user ON readyproduct.user_ID = user.user_ID "
                + "WHERE categories.name = ? AND readyproduct.user_ID = ?";

        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setString(1, categoryName);
        statement.setInt(2, uID);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReadyProduct readyProduct = new ReadyProduct(
                    resultSet.getInt("ready_product_ID"),
                    this.productDAO.getProductById(resultSet.getInt("product_ID"))
            );
            readyProducts.add(readyProduct);
        }
        return readyProducts;
    }
}
