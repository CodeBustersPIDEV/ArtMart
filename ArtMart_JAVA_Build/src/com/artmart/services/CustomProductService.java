package com.artmart.services;

import com.artmart.interfaces.ICustomProductDao;
import com.artmart.dao.CustomProductDao;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import java.util.List;

public class CustomProductService implements ICustomProductDao {

    private final CustomProductDao customProductDao = new CustomProductDao();

    @Override
    public CustomProduct getCustomProductById(int id) throws SQLException {
        return customProductDao.getCustomProductById(id);
    }

    @Override
    public int createCustomProduct(Product baseProduct, int clientID) throws SQLException {
        return customProductDao.createCustomProduct(baseProduct, clientID);
    }

    @Override
    public int updateCustomProduct(int id, CustomProduct customProduct) throws SQLException {
        return customProductDao.updateCustomProduct(id, customProduct);
    }

    @Override
    public int deleteCustomProduct(int id) throws SQLException {
        return customProductDao.deleteCustomProduct(id);
    }

    @Override
    public List<CustomProduct> getAllCustomProducts() throws SQLException {
        return customProductDao.getAllCustomProducts();
    }
      public int getCustomPId(int id) throws SQLException {
        return customProductDao.getCustomPId(id);
      }
  public List<CustomProduct> searchCustomProductByName(String name) throws SQLException {
      return customProductDao.searchCustomProductByName(name);
  }

    @Override
    public List<CustomProduct> getCustomProductsByClientId(int clientId) throws SQLException {
  return customProductDao.getCustomProductsByClientId(clientId);
    }
 

   

  

   

    


  }
      

