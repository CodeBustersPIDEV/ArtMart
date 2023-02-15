package com.artmart.interfaces;

import com.artmart.models.CustomProduct;



import java.sql.SQLException;
import java.util.List;


public interface ICustomProductDao {
   List<CustomProduct> getAllCustomProducts() throws SQLException;
    CustomProduct getCustomProductById(int id) throws SQLException;

    int createCustomProduct(CustomProduct customProduct) throws SQLException;

    int updateCustomProduct(int id,CustomProduct customProduct) throws SQLException;

    int deleteCustomProduct(int id) throws SQLException;
}
