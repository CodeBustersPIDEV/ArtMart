package com.artmart.dao;

import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.Receipt;
import java.util.List;

public class ReceiptDao implements IOrderReceiptDao{
    
    private Connection connection;

    public ReceiptDao(Connection connection) {
        this.connection = connection;
    }

       @Override
    public int createReceipt(Receipt receipt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Receipt getReceipt(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Receipt> getReceipts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateReceipt(Receipt receipt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteReceipt(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
