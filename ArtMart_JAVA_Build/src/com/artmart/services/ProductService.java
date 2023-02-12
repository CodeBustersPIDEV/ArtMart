package com.artmart.services;

import com.artmart.interfaces.IProductService;
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

    public int updateProduct(Product product) throws SQLException {
        return productDao.updateProduct(product);
    }

    public void deleteProduct(int id) throws SQLException {
       
    }

}
