/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.ProductReview;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author rymae
 */
public interface IProductReviewDao {

    List<ProductReview> getAllProductReviews() throws SQLException;

    ProductReview getProductReviewById(int id) throws SQLException;

    int createProductReview(ProductReview productReview) throws SQLException;

    int updateProductReview(int id, ProductReview productReview) throws SQLException;

    int deleteProductReview(int id) throws SQLException;
}
