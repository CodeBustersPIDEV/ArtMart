package com.artmart.services;

import com.artmart.models.Product;
import com.artmart.dao.ProductDao;
import com.artmart.interfaces.IProductDao;
import java.sql.SQLException;



public class ProductService implements IProductDao {

    private ProductDao productDao = new ProductDao();

    public Product getProductById(int id) throws SQLException {
        return productDao.getProductById(id);
    }

    public int createProduct(Product product) throws SQLException {
        return productDao.createProduct(product);
    }

    public int updateProduct(int id,Product product) throws SQLException {
        return productDao.updateProduct(id,product);
    }

    public void deleteProduct(int id) throws SQLException {
       
    }

}
