package com.artmart.interfaces;

import com.artmart.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();
    Product getProductById(int id);
    int createProduct(Product product);
    int updateProduct(Product product);
    int deleteProduct(int id);
}
