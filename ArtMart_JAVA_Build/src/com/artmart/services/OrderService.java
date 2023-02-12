package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.dao.*;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService{
    
    private Connection connection;
    
    private OrderDao orderDao;
    private OrderRefundDao orderRefundDao;
    private OrderStatusDao orderStatusDao;
    private OrderUpdateDao orderUpdateDao;
    private PaymentOptionDao paymentOptionDao;
    private ReceiptDao receiptDao;
    private SalesReportDao salesReportDao;
    private ShippingOptionDao shippingOptionDao;
    private WishlistDao wishlistDao;
    
    public OrderService() {
        try{
            
        this.connection = SQLConnection.getInstance().getConnection();
        this.orderDao = new OrderDao(this.connection);
        this.orderRefundDao = new OrderRefundDao(this.connection);
        this.orderStatusDao = new OrderStatusDao(this.connection);
        this.orderUpdateDao = new OrderUpdateDao(this.connection);
        this.paymentOptionDao = new PaymentOptionDao(this.connection);
        this.receiptDao = new ReceiptDao(this.connection);
        this.salesReportDao = new SalesReportDao(this.connection);
        this.shippingOptionDao = new ShippingOptionDao(this.connection);
        this.wishlistDao = new WishlistDao(this.connection);
        
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public Order createOrder(Order order) {
        return this.orderDao.createOrder(order);
    }

    @Override
    public Order getOrder(int id) {
        return this.orderDao.readOrder(id);
    }

    @Override
    public List<Order> getOrders() {
        return this.orderDao.getAllOrders();
    }

    @Override
    public boolean updateOrder(Order order) {
         return this.orderDao.updateOrder(order);
    }

    @Override
    public boolean deleteOrder(int id) {
         return this.orderDao.deleteOrder(id);
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
    public OrderUpdate createOrderUpdate(OrderUpdate orderUpdate) {
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
    public Receipt createReceipt(Receipt receipt) {
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
    public Wishlist createWishlist(Wishlist wishlist) {
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
    public OrderRefund createOrderRefund(OrderRefund orderRefund) {
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
    public ShippingOption createShippingOption(ShippingOption shippingOption) {
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
    public PaymentOption createPaymentOption(PaymentOption paymentOption) {
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
    public SalesReport createSalesReport(SalesReport salesReport) {
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
