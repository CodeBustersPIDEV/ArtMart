package com.artmart.interfaces;

import com.artmart.models.*;
import com.artmart.utils.OrderCurrentStatus;
import java.util.List;

public interface IOrderService {

    int createOrder(Order order);

    Order getOrder(int id);

    List<Order> getOrders();
    
    List<Order> getOrdersById(int id);

    boolean updateOrder(Order order);

    boolean deleteOrder(int id);

    int createOrderStatus(OrderStatus orderStatus);

    OrderStatus getOrderStatus(int id);
    
    OrderStatus getOrderStatusByOrderId(int id);

    List<OrderStatus> getOrderStatuses();

    boolean updateOrderStatus(int id,OrderCurrentStatus status);

    boolean deleteOrderStatus(int id);

    int createOrderUpdate(OrderUpdate orderUpdate);

    OrderUpdate getOrderUpdate(int id);

    List<OrderUpdate> getOrderUpdates();

    boolean updateOrderUpdate(OrderUpdate orderUpdate);

    boolean deleteOrderUpdate(int id);

    int createReceipt(Receipt receipt);

    Receipt getReceipt(int id);

    List<Receipt> getReceipts();

    boolean updateReceipt(Receipt receipt);

    boolean deleteReceipt(int id);

    int createWishlist(Wishlist wishlist);

    Wishlist getWishlist(int id);

    List<Wishlist> getWishlists();

    boolean updateWishlist(Wishlist wishlist);

    boolean deleteWishlist(int id);

    int createOrderRefund(OrderRefund orderRefund);

    OrderRefund getOrderRefund(int id);

    List<OrderRefund> getOrderRefunds();

    boolean updateOrderRefund(OrderRefund orderRefund);

    boolean deleteOrderRefund(int id);

    int createShippingOption(ShippingOption shippingOption);

    ShippingOption getShippingOption(int id);

    List<ShippingOption> getShippingOptions();

    boolean updateShippingOption(ShippingOption shippingOption);

    boolean deleteShippingOption(int id);

    int createPaymentOption(PaymentOption paymentOption);

    PaymentOption getPaymentOption(int id);

    List<PaymentOption> getPaymentOptions();

    boolean updatePaymentOption(PaymentOption paymentOption);

    boolean deletePaymentOption(int id);

    int createSalesReport(SalesReport salesReport);

    SalesReport getSalesReport(int id);

    List<SalesReport> getSalesReports();

    boolean updateSalesReport(SalesReport salesReport);

    boolean deleteSalesReport(int id);
}
