package com.artmart.services;
import com.artmart.interfaces.ICustomProductDao;
import com.artmart.dao.CustomProductDao;
import java.sql.SQLException;
import com.artmart.models.CustomProduct;



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
    public int updateCustomProduct(CustomProduct customProduct) throws SQLException {
        return customProductDao.updateCustomProduct(customProduct);
    }

    @Override
    public int deleteCustomProduct(int id) throws SQLException {
        return customProductDao.deleteCustomProduct(id);
    }
}
