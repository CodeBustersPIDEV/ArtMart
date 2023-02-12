package com.artmart.dao;

import java.sql.Connection;
import com.artmart.interfaces.*;
import com.artmart.models.SalesReport;
import java.util.List;

public class SalesReportDao implements ISalesReportDao{
    
    private Connection connection;

    public SalesReportDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createSalesReport(SalesReport salesReport) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SalesReport getSalesReport(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SalesReport> getSalesReports() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateSalesReport(SalesReport salesReport) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteSalesReport(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
