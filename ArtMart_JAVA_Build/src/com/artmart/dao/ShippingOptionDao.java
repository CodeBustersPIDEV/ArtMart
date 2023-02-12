package com.artmart.dao;

import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.ShippingOption;
import java.util.List;

public class ShippingOptionDao implements IShippingOptionDao{
    
    private Connection connection;

    public ShippingOptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createShippingOption(ShippingOption shippingOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ShippingOption getShippingOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ShippingOption> getShippingOptions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateShippingOption(ShippingOption shippingOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteShippingOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
