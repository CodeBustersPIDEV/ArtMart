package com.artmart.interfaces;

import com.artmart.models.CustomProduct;



import java.util.List;  

public interface ICustomProductService {

    CustomProduct getCustomProductById(int id);

    List<CustomProduct> getAllCustomProducts();

    int createCustomProduct(CustomProduct customProduct);

    int updateCustomProduct(CustomProduct customProduct);

    int deleteCustomProduct(int id);

}
