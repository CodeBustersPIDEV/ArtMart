package com.artmart.interfaces;

import com.artmart.models.SalesReport;
import java.util.List;

public interface ISalesReportDao {
    
    //SalesReportDao
    int createSalesReport(SalesReport salesReport);
    SalesReport getSalesReport(int id);
    List<SalesReport> getSalesReports();
    boolean updateSalesReport(SalesReport salesReport);
    boolean deleteSalesReport(int id);
}
