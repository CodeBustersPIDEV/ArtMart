package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.artmart.models.ProductReview;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductReviewDao {

    private Connection sqlConnection;

    public ProductReviewDao() {
        try {
            this.sqlConnection = SQLConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    public ProductReview getProductReviewById(int id) throws SQLException {
        ProductReview productReview = null;
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "SELECT * FROM productreview"
                    + "WHERE review_ID = ?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                productReview = new ProductReview();
                productReview.setReviewId(resultSet.getInt("review_ID"));
                productReview.setReadyProductId(resultSet.getInt("ready_product_ID"));
                productReview.setUserId(resultSet.getInt("user_ID"));
                productReview.setTitle(resultSet.getString("title"));
                productReview.setText(resultSet.getString("text"));
                productReview.setRating(resultSet.getInt("rating"));
                productReview.setDate(resultSet.getDate("date"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return productReview;
    }
    
    public int getProductReviewId(int id) throws SQLException {
        String query = "SELECT ready_product_ID FROM productreview WHERE review_ID = ?";
        PreparedStatement statement = sqlConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("ready_product_ID");
        } else {
            return 0;
        }

    }
   public List<ProductReview> getAllProductReviewsByProdId(int id) {
        List<ProductReview> reviews = new ArrayList<>();
        try {
            String query="SELECT * FROM productreview where ready_product_ID = ?";
            PreparedStatement statement = sqlConnection.prepareStatement(query);
            statement.setInt(1,id);
            System.out.println(statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProductReview productReview = new ProductReview();
                productReview.setReviewId(resultSet.getInt("review_ID"));
                productReview.setReadyProductId(id);
                productReview.setUserId(resultSet.getInt("user_ID"));
                productReview.setTitle(resultSet.getString("title"));
                productReview.setText(resultSet.getString("text"));
                productReview.setRating(resultSet.getInt("rating"));
                productReview.setDate(resultSet.getDate("date"));
                reviews.add(productReview);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return reviews;
    }
   
    public List<ProductReview> getAllProductReviews() {
        List<ProductReview> reviews = new ArrayList<>();
        try {
            Statement statement = sqlConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM productreview");
            while (resultSet.next()) {
                ProductReview productReview = new ProductReview();
                productReview.setReviewId(resultSet.getInt("review_ID"));
                productReview.setReadyProductId(resultSet.getInt("ready_product_ID"));
                productReview.setUserId(resultSet.getInt("user_ID"));
                productReview.setTitle(resultSet.getString("title"));
                productReview.setText(resultSet.getString("text"));
                productReview.setRating(resultSet.getInt("rating"));
                productReview.setDate(resultSet.getDate("date"));
                reviews.add(productReview);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return reviews;
    }

    public int createProductReview(ProductReview productReview) {
        try {
            String query = "INSERT INTO productreview (ready_product_ID, user_ID, title, text, rating, date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = sqlConnection.prepareStatement(query);
            statement.setInt(1, productReview.getReadyProductId());
            statement.setInt(2, productReview.getUserId());
            statement.setString(3, productReview.getTitle());
            statement.setString(4, productReview.getText());
            statement.setInt(5, productReview.getRating());
            statement.setDate(6, (Date) productReview.getDate());

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public int updateProductReview(int id, ProductReview productReview) {
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "UPDATE productreview"
                    + "SET ready_product_ID = ?, user_ID = ?, title = ?, text = ?, rating = ?, date = ?"
                    + "WHERE review_ID = ?"
            );
            statement.setInt(1, productReview.getReadyProductId());
            statement.setInt(2, productReview.getUserId());
            statement.setString(3, productReview.getTitle());
            statement.setString(4, productReview.getText());
            statement.setInt(5, productReview.getRating());
            statement.setDate(6, (Date) productReview.getDate());
            statement.setInt(7, id);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public int deleteProductReview(int id) {
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "DELETE FROM productreview"
                    + "WHERE review_ID = ?"
            );
            statement.setInt(1, id);

            return statement.executeUpdate();
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return 0;
    }

    public float getRatingByProductId(int productId) {
        float rating = 0;
        try {
            PreparedStatement statement = sqlConnection.prepareStatement(
                    "SELECT AVG(rating) AS avg_rating FROM productreview WHERE ready_product_ID = ?"
            );
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rating = resultSet.getFloat("avg_rating");
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
        return Math.round(rating * 10) / 10f;
    }

}
