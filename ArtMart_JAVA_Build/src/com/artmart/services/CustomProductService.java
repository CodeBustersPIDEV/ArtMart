package com.artmart.services;
import com.artmart.interfaces.ICustomProductDao;
import com.artmart.dao.CustomProductDao;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;
import java.util.List;



public class CustomProductService implements ICustomProductDao {
    
    private CustomProductDao customProductDao = new CustomProductDao();

    @Override
    public CustomProduct getCustomProductById(int id) throws SQLException {
        return customProductDao.getCustomProductById(id);
    }

    @Override
    public int createCustomProduct(CustomProduct customProduct) throws SQLException {
        return customProductDao.createCustomProduct(customProduct);
    }

    @Override
    public int updateCustomProduct(int id,CustomProduct customProduct) throws SQLException {
        return customProductDao.updateCustomProduct(id,customProduct);
    }

    @Override
    public int deleteCustomProduct(int id) throws SQLException {
        return customProductDao.deleteCustomProduct(id);
    }

    @Override
    public List<CustomProduct> getAllCustomProducts() throws SQLException {
return customProductDao.getAllCustomProducts();
    }
}
