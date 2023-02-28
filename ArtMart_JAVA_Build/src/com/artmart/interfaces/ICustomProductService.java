package com.artmart.interfaces;

import com.artmart.models.CustomProduct;
import java.sql.SQLException;

import java.util.List;

public interface ICustomProductService {

    List<CustomProduct> getAllCustomProducts();
 public List<CustomProduct> getCustomProductsByClientId(int clientId) throws SQLException;
    CustomProduct getCustomProductById(int id );

    int createCustomProduct(CustomProduct customProduct,int clientID);

    int updateCustomProduct(CustomProduct customProduct);

    int deleteCustomProduct(int id);
public List<CustomProduct> searchCustomProductByName(String name) ;
}
