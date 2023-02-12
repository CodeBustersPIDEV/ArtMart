package com.artmart.services;

import java.sql.SQLException;
import java.util.List;

import com.artmart.models.CustomProduct;

public interface ICustomProductService {
    public List<CustomProduct> getAllCustomProducts() throws SQLException;
    public CustomProduct getCustomProductById(int customProductId) throws SQLException;
    public int createCustomProduct(CustomProduct customProduct) throws SQLException;
    public int updateCustomProduct(CustomProduct customProduct) throws SQLException;
    public int deleteCustomProduct(int customProductId) throws SQLException;
}
