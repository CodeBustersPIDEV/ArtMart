package com.artmart.interfaces;

import com.artmart.models.*;
import java.util.List;

public interface IReadyProductService {

    ReadyProduct getReadyProductById(int id);

    List<ReadyProduct> getAllReadyProducts();

    int createReadyProduct(ReadyProduct readyProduct);

    boolean updateReadyProduct(ReadyProduct readyProduct);

    boolean deleteReadyProduct(int id);

    ProductReview getProductReviewById(int id);

    List<ProductReview> getAllProductReviews();

    int createProductReview(ProductReview productReview);

    boolean updateProductReview(ProductReview productReview);

    boolean deleteProductReview(int id);
}
