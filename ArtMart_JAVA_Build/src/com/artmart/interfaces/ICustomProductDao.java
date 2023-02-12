package com.artmart.interfaces;

import com.artmart.models.CustomProduct;



import java.sql.SQLException;


public interface ICustomProductDao {

    CustomProduct getCustomProductById(int id) throws SQLException;

    int createCustomProduct(CustomProduct customProduct) throws SQLException;

    int updateCustomProduct(int id,CustomProduct customProduct) throws SQLException;

    int deleteCustomProduct(int id) throws SQLException;
}
