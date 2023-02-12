package com.artmart.interfaces;
import com.artmart.models.*;
import java.util.List;

public interface IOrderService {
    
    //OrderDao
    Order createOrder(Order order);
    Order readOrder(int id);
    List<Order> getAllOrders();
    boolean updateOrder(Order order);
    boolean deleteOrder(int id);
    
    //OrderStatusDao 
    int createOrderStatus(OrderStatus orderStatus);
    OrderStatus getOrderStatus(int id);
    List<OrderStatus> getOrderStatuses();
    boolean updateOrderStatus(OrderStatus orderStatus);
    boolean deleteOrderStatus(int id);
    
    //OrderUpdateDao 
    int createOrderUpdate(OrderUpdate orderUpdate);
    OrderUpdate getOrderUpdate(int id);
    List<OrderUpdate> getOrderUpdates();
    boolean updateOrderUpdate(OrderUpdate orderUpdate);
    boolean deleteOrderUpdate(int id);
    
    //ReceiptDao 
    int createReceipt(Receipt receipt);
    Receipt getReceipt(int id);
    List<Receipt> getReceipts();
    boolean updateReceipt(Receipt receipt);
    boolean deleteReceipt(int id);
    
    //WishlistDao 
    int createWishlist(Wishlist wishlist);
    Wishlist getWishlist(int id);
    List<Wishlist> getWishlists();
    boolean updateWishlist(Wishlist wishlist);
    boolean deleteWishlist(int id);
    
    //OrderRefundDao 
    int createOrderRefund(OrderRefund orderRefund);
    OrderRefund getOrderRefund(int id);
    List<OrderRefund> getOrderRefunds();
    boolean updateOrderRefund(OrderRefund orderRefund);
    boolean deleteOrderRefund(int id);
    
    //ShippingOptionDao 
    int createShippingOption(ShippingOption shippingOption);
    ShippingOption getShippingOption(int id);
    List<ShippingOption> getShippingOptions();
    boolean updateShippingOption(ShippingOption shippingOption);
    boolean deleteShippingOption(int id);
    
    //PaymentOptionDao 
    int createPaymentOption(PaymentOption paymentOption);
    PaymentOption getPaymentOption(int id);
    List<PaymentOption> getPaymentOptions();
    boolean updatePaymentOption(PaymentOption paymentOption);
    boolean deletePaymentOption(int id);
    
    //SalesReportDao
    int createSalesReport(SalesReport salesReport);
    SalesReport getSalesReport(int id);
    List<SalesReport> getSalesReports();
    boolean updateSalesReport(SalesReport salesReport);
    boolean deleteSalesReport(int id);
}
