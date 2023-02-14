package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.PaymentOption;
import java.sql.SQLException;
import java.util.List;

public class PaymentOptionDao implements IPaymentOptionDao{
    
    private Connection connection;

    public PaymentOptionDao() {
                 try{
        this.connection = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
   @Override
    public int createPaymentOption(PaymentOption paymentOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaymentOption getPaymentOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaymentOption> getPaymentOptions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updatePaymentOption(PaymentOption paymentOption) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletePaymentOption(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
