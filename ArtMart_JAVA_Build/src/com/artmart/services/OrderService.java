package com.artmart.services;

import com.artmart.GUI.controllers.Order.OrderGUIController;
import com.artmart.GUI.controllers.Order.OrderGUIMenuController;
import com.artmart.GUI.controllers.Order.OrderListController;
import com.artmart.dao.*;
import com.artmart.interfaces.IOrderService;
import com.artmart.models.Order;
import com.artmart.models.OrderRefund;
import com.artmart.models.OrderStatus;
import com.artmart.models.OrderUpdate;
import com.artmart.models.PaymentOption;
import com.artmart.models.Product;
import com.artmart.models.Receipt;
import com.artmart.models.SalesReport;
import com.artmart.models.Session;
import com.artmart.models.ShippingOption;
import com.artmart.models.User;
import com.artmart.models.Wishlist;
import com.artmart.utils.OrderCurrentStatus;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OrderService implements IOrderService {

    private final OrderDao orderDao = new OrderDao();
    private final UserDao userDao = new UserDao();
    private final OrderRefundDao orderRefundDao = new OrderRefundDao();
    private final OrderStatusDao orderStatusDao = new OrderStatusDao();
    private final OrderUpdateDao orderUpdateDao = new OrderUpdateDao();
    private final PaymentOptionDao paymentOptionDao = new PaymentOptionDao();
    private final ReceiptDao receiptDao = new ReceiptDao();
    private final SalesReportDao salesReportDao = new SalesReportDao();
    private final ShippingOptionDao shippingOptionDao = new ShippingOptionDao();
    private final WishlistDao wishlistDao = new WishlistDao();
    private final Session session = new Session();
    private User connectedUser = new User();

    public OrderService() {
        this.connectedUser = userDao.getUser(session.getUserID("1"));
    }

    @Override
    public int createOrder(Order order) {
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
    public List<Order> getOrdersById(int id) {
        return this.orderDao.getAllOrdersById(id);
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
        return this.orderStatusDao.createOrderStatus(orderStatus);
    }

    @Override
    public OrderStatus getOrderStatus(int id) {
        return this.orderStatusDao.getOrderStatus(id);
    }

    @Override
    public OrderStatus getOrderStatusByOrderId(int id) {
        return this.orderStatusDao.getOrderStatusByOrderId(id);
    }

    @Override
    public List<OrderStatus> getOrderStatuses() {
        return this.orderStatusDao.getOrderStatuses();
    }

    @Override
    public boolean updateOrderStatus(int id, OrderCurrentStatus status) {
        return this.orderStatusDao.updateOrderStatus(id, status);
    }

    @Override
    public boolean deleteOrderStatus(int id) {
        return this.orderStatusDao.deleteOrderStatus(id);
    }

    @Override
    public int createOrderUpdate(OrderUpdate orderUpdate) {
        return this.orderUpdateDao.createOrderUpdate(orderUpdate);
    }

    @Override
    public OrderUpdate getOrderUpdate(int id) {
        return this.orderUpdateDao.getOrderUpdate(id);
    }

    @Override
    public List<OrderUpdate> getOrderUpdates() {
        return this.orderUpdateDao.getOrderUpdates();
    }

    @Override
    public boolean updateOrderUpdate(OrderUpdate orderUpdate) {
        return this.orderUpdateDao.updateOrderUpdate(orderUpdate);
    }

    @Override
    public boolean deleteOrderUpdate(int id) {
        return this.orderUpdateDao.deleteOrderUpdate(id);
    }

    @Override
    public int createReceipt(Receipt receipt) {
        return this.receiptDao.createReceipt(receipt);
    }

    @Override
    public Receipt getReceipt(int id) {
        return this.receiptDao.getReceipt(id);
    }

    @Override
    public List<Receipt> getReceipts() {
        return this.receiptDao.getReceipts();
    }

    @Override
    public boolean updateReceipt(Receipt receipt) {
        return this.receiptDao.updateReceipt(receipt);
    }

    @Override
    public boolean deleteReceipt(int id) {
        return this.receiptDao.deleteReceipt(id);
    }

    @Override
    public Wishlist getWishlist(int id) {
        return this.wishlistDao.getWishlist(id);
    }

    @Override
    public List<Wishlist> getWishlistsByUserId(int id) {
        return this.wishlistDao.getWishlistsByUserId(id);
    }

    @Override
    public boolean updateWishlist(Wishlist wishlist) {
        return this.wishlistDao.updateWishlist(wishlist);
    }

    @Override
    public boolean deleteWishlist(int productId, int UserId) {
        return this.wishlistDao.deleteWishlist(productId, UserId);
    }

    @Override
    public int createOrderRefund(OrderRefund orderRefund) {
        return this.orderRefundDao.createOrderRefund(orderRefund);
    }

    @Override
    public OrderRefund getOrderRefund(int id) {
        return this.orderRefundDao.getOrderRefund(id);
    }

    @Override
    public List<OrderRefund> getOrderRefunds() {
        return this.orderRefundDao.getOrderRefunds();
    }

    @Override
    public boolean updateOrderRefund(OrderRefund orderRefund) {
        return this.orderRefundDao.updateOrderRefund(orderRefund);
    }

    @Override
    public boolean deleteOrderRefund(int id) {
        return this.orderRefundDao.deleteOrderRefund(id);
    }

    @Override
    public int createShippingOption(ShippingOption shippingOption) {
        return this.shippingOptionDao.createShippingOption(shippingOption);
    }

    @Override
    public ShippingOption getShippingOption(int id) {
        return this.shippingOptionDao.getShippingOption(id);
    }

    @Override
    public List<ShippingOption> getShippingOptions() {
        return this.shippingOptionDao.getShippingOptions();
    }

    @Override
    public boolean updateShippingOption(ShippingOption shippingOption) {
        return this.shippingOptionDao.updateShippingOption(shippingOption);
    }

    @Override
    public boolean deleteShippingOption(int id) {
        return this.shippingOptionDao.deleteShippingOption(id);
    }

    @Override
    public int createPaymentOption(PaymentOption paymentOption) {
        return this.paymentOptionDao.createPaymentOption(paymentOption);
    }

    @Override
    public PaymentOption getPaymentOption(int id) {
        return this.paymentOptionDao.getPaymentOption(id);
    }

    @Override
    public List<PaymentOption> getPaymentOptions() {
        return this.paymentOptionDao.getPaymentOptions();
    }

    @Override
    public boolean updatePaymentOption(PaymentOption paymentOption) {
        return this.paymentOptionDao.updatePaymentOption(paymentOption);
    }

    @Override
    public boolean deletePaymentOption(int id) {
        return this.paymentOptionDao.deletePaymentOption(id);
    }

    @Override
    public int createSalesReport(SalesReport salesReport) {
        return this.salesReportDao.createSalesReport(salesReport);
    }

    @Override
    public SalesReport getSalesReport(int id) {
        return this.salesReportDao.getSalesReport(id);
    }

    @Override
    public List<SalesReport> getSalesReports() {
        return this.salesReportDao.getSalesReports();
    }

    @Override
    public boolean updateSalesReport(SalesReport salesReport) {
        return this.salesReportDao.updateSalesReport(salesReport);
    }

    @Override
    public boolean deleteSalesReport(int id) {
        return this.salesReportDao.deleteSalesReport(id);
    }

    //Utils
    @Override
    public int addToShoppingCart(Product productToOrder, int quantity, double price) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added To Shopping Cart");
        alert.setHeaderText("Product Saved for your Shopping Cart");
        alert.showAndWait();
        return this.wishlistDao.createWishlist(new Wishlist(this.connectedUser.getUser_id(), productToOrder.getProductId(), Date.valueOf(formattedDate), quantity, price));
    }
    
    @Override
    public void goToCheckOut() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/Order.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            OrderGUIController controller = loader.getController();
            controller.setData(connectedUser);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Artmart");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void goToUsersOrderList() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            OrderListController controller = loader.getController();
            controller.setUser(connectedUser);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Artmart");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
