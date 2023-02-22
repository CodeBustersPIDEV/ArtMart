package com.artmart.interfaces;

import com.artmart.models.CustomProduct;
import java.sql.SQLException;

import java.util.List;

public interface ICustomProductService {

    List<CustomProduct> getAllCustomProducts();

    CustomProduct getCustomProductById(int id);

    int createCustomProduct(CustomProduct customProduct);

    int updateCustomProduct(CustomProduct customProduct);

    int deleteCustomProduct(int id);
public List<CustomProduct> searchCustomProductByName(String name) ;
}
