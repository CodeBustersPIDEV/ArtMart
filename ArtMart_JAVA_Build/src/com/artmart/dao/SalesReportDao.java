package com.artmart.dao;

import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.*;
import com.artmart.models.SalesReport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesReportDao implements ISalesReportDao{
    
    private Connection conn;

    public SalesReportDao() {
    try{
        this.conn = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createSalesReport(SalesReport salesReport){
        String sql = "INSERT INTO sales_report (product_id, total_sales, average_sales_per_day, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, salesReport.getProductId());
            stmt.setDouble(2, salesReport.getTotalSales());
            stmt.setDouble(3, salesReport.getAverageSalesPerDay());
            stmt.setDate(4, new java.sql.Date(salesReport.getDate().getTime()));
            return stmt.executeUpdate();
  
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return 0;
    }

    @Override
    public SalesReport getSalesReport(int id){
        String sql = "SELECT * FROM sales_report WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new SalesReport(rs.getInt("id"), rs.getInt("product_id"), rs.getDouble("total_sales"), 
                            rs.getDouble("average_sales_per_day"), rs.getDate("date"));
                } else {
                    return null;
                }
            }
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return null;
    }

    @Override
    public List<SalesReport> getSalesReports(){
        List<SalesReport> salesReports = new ArrayList<>();
        String sql = "SELECT * FROM sales_report";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                salesReports.add(new SalesReport(rs.getInt("id"), rs.getInt("product_id"), rs.getDouble("total_sales"), 
                        rs.getDouble("average_sales_per_day"), rs.getDate("date")));
            }
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return salesReports;
    }

    @Override
    public boolean updateSalesReport(SalesReport salesReport){
        String sql = "UPDATE sales_report SET product_id = ?, total_sales = ?, average_sales_per_day = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salesReport.getProductId());
            stmt.setDouble(2, salesReport.getTotalSales());
            stmt.setDouble(3, salesReport.getAverageSalesPerDay());
            stmt.setDate(4, new java.sql.Date(salesReport.getDate().getTime()));
            stmt.setInt(5, salesReport.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean deleteSalesReport(int id){
        String sql = "DELETE FROM sales_report WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
        return false;
    }
}
