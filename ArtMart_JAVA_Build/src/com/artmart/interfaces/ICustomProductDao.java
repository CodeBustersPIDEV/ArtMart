package com.artmart.interfaces;

import com.artmart.models.CustomProduct;
import com.artmart.models.Product;

import java.sql.SQLException;
import java.util.List;

public interface ICustomProductDao {

    List<CustomProduct> getAllCustomProducts() throws SQLException;

    CustomProduct getCustomProductById(int id) throws SQLException;

    int createCustomProduct(Product customProduct,int clientID) throws SQLException;
 public List<CustomProduct> getCustomProductsByClientId(int clientId) throws SQLException;
    int updateCustomProduct(int id, CustomProduct customProduct) throws SQLException;

    int deleteCustomProduct(int id) throws SQLException;
    public List<CustomProduct> searchCustomProductByName(String name) throws SQLException;
        public List<CustomProduct> searchCustomProductByName1(String name) throws SQLException;


}
