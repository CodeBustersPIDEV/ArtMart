package com.artmart.interfaces;

import com.artmart.models.Product;



import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    Product getProductById(int id) throws SQLException;
    int createProduct(Product product) throws SQLException;
    int updateProduct(Product product) throws SQLException;
   void deleteProduct(int id) throws SQLException;
}
