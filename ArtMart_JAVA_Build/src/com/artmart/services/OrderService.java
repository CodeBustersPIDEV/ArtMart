package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IOrderService;
import com.artmart.models.Order;
import com.artmart.models.OrderRefund;
import com.artmart.models.OrderStatus;
import com.artmart.models.OrderUpdate;
import com.artmart.models.PaymentOption;
import com.artmart.models.Receipt;
import com.artmart.models.SalesReport;
import com.artmart.models.ShippingOption;
import com.artmart.models.Wishlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService{
    
    private Connection connection;

    public OrderService(Connection connection) {
        this.connection = SQLConnection.getInstance().getConnection();
    }

    @Override
    public Order createOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Order (UserID, ProductID, Quantity, ShippingAddress, PaymentMethod, OrderDate, TotalCost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getPaymentMethod());
            statement.setDate(6, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setDouble(7, order.getTotalCost());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    order.setId(id);
                }
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return order;
    }

    @Override
    public Order readOrder(int id) {
        Order order = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Order WHERE ID = ?"
            );
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                order = new Order();
                order.setId(result.getInt("ID"));
                order.setUserId(result.getInt("UserID"));
                order.setProductId(result.getInt("ProductID"));
                order.setQuantity(result.getInt("Quantity"));
                order.setShippingAddress(result.getString("ShippingAddress"));
                order.setPaymentMethod(result.getString("PaymentMethod"));
                order.setOrderDate(result.getDate("OrderDate"));
                order.setTotalCost(result.getDouble("TotalCost"));
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order`");
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("ID"));
                order.setUserId(resultSet.getInt("UserID"));
                order.setProductId(resultSet.getInt("ProductID"));
                order.setQuantity(resultSet.getInt("Quantity"));
                order.setShippingAddress(resultSet.getString("ShippingAddress"));
                order.setPaymentMethod(resultSet.getString("PaymentMethod"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setTotalCost(resultSet.getDouble("TotalCost"));
                orders.add(order);
            }
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return orders;
    }


    @Override
    public boolean updateOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE `Order` SET UserID = ?, ProductID = ?, Quantity = ?, ShippingAddress = ?, PaymentMethod = ?, OrderDate = ?, TotalCost = ? WHERE ID = ?");
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getPaymentMethod());
            statement.setDate(6, order.getOrderDate());
            statement.setDouble(7, order.getTotalCost());
            statement.setInt(8, order.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM `Order` WHERE ID = ?");
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
           System.err.println(e.getCause().getMessage());
        }
        return false;
    }

    @Override
    public int createOrderStatus(OrderStatus orderStatus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderStatus getOrderStatus(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OrderStatus> getOrderStatuses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderStatus(OrderStatus orderStatus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteOrderStatus(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createOrderUpdate(OrderUpdate orderUpdate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderUpdate getOrderUpdate(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OrderUpdate> getOrderUpdates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderUpdate(OrderUpdate orderUpdate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteOrderUpdate(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public int createWishlist(Wishlist wishlist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Wishlist getWishlist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Wishlist> getWishlists() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateWishlist(Wishlist wishlist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteWishlist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createOrderRefund(OrderRefund orderRefund) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderRefund getOrderRefund(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<OrderRefund> getOrderRefunds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateOrderRefund(OrderRefund orderRefund) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteOrderRefund(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
