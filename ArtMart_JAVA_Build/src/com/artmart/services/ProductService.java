package com.artmart.services;

import com.artmart.models.Product;
import com.artmart.dao.ProductDao;
import com.artmart.interfaces.IProductDao;
import java.sql.SQLException;
import java.util.List;

public class ProductService implements IProductDao {

    private final ProductDao productDao = new ProductDao();

    @Override
    public Product getProductById(int id) throws SQLException {
        return productDao.getProductById(id);
    }

    @Override
    public int createProduct(Product product) throws SQLException {
        return productDao.createProduct(product);
    }

    @Override
    public int updateProduct(int id, Product product) throws SQLException {
        return productDao.updateProduct(id, product);
    }

    @Override
    public void deleteProduct(int id) throws SQLException {

    }

    @Override
    public List<Product> getAllProduct() throws SQLException {
       return productDao.getAllProduct();
    }

}
