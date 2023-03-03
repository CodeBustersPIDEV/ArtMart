package com.artmart.interfaces;

import com.artmart.models.ProductReview;
import java.sql.SQLException;
import java.util.List;

public interface IProductReviewDao {

    List<ProductReview> getAllProductReviews() throws SQLException;
    List<ProductReview> getAllProductReviewsByProdId(int id) throws SQLException;
    
    ProductReview getProductReviewById(int id) throws SQLException;

    int createProductReview(ProductReview productReview) throws SQLException;

    int updateProductReview(int id, ProductReview productReview) throws SQLException;

    int deleteProductReview(int id) throws SQLException;

    float getRatingByProductId(int id) throws SQLException;

    int getProductReviewId(int id) throws SQLException;
}
