package com.artmart.interfaces;

import com.artmart.models.Product;
import java.sql.SQLException;

public interface IProductDao {
    Product getProductById(int id) throws SQLException;
    int createProduct(Product product) throws SQLException;
    int updateProduct(int id,Product product) throws SQLException;
   void deleteProduct(int id) throws SQLException;
int createProductAndCustomProduct(Product baseProduct) throws SQLException;
}
