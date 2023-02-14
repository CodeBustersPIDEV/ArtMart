package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.ShippingOption;
import java.sql.SQLException;
import java.util.List;

public class ShippingOptionDao implements IShippingOptionDao{
    
    private Connection connection;

    public ShippingOptionDao() {
             try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
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
