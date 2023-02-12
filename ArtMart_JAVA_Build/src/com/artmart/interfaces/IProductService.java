package com.artmart.interfaces;

public interface IProductService {
    public Product createProduct(Product product);
    public Product readProduct(int productID);
    public Product updateProduct(Product product);
    public void deleteProduct(int productID);
}
