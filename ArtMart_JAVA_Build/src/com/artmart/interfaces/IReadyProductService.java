/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.*;
import java.util.List;

/**
 *
 * @author rymae
 */
public interface IReadyProductService {

    //ReadyProductDao
    ReadyProduct getReadyProductById(int id);

    List<ReadyProduct> getAllReadyProducts();

    int createReadyProduct(ReadyProduct readyProduct);

    boolean updateReadyProduct(ReadyProduct readyProduct);

    boolean deleteReadyProduct(int id);

    //ProductReviewDao
    ProductReview getProductReviewById(int id);

    List<ProductReview> getAllProductReviews();

    int createProductReview(ProductReview productReview);

    boolean updateProductReview(ProductReview productReview);

    boolean deleteProductReview(int id);
}
