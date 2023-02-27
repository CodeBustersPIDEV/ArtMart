/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.PaymentOption;
import com.artmart.models.Product;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import com.artmart.utils.OrderCurrentStatus;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminOrderDetailController implements Initializable {

    @FXML
    private Label orderId;
    @FXML
    private Label productName;
    @FXML
    private Label productCategory;
    @FXML
    private Label productDimension;
    @FXML
    private Label productWeight;
    @FXML
    private Label productMat;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label estimatedTime;
    @FXML
    private TextArea shpiingAddressField;
    @FXML
    private DatePicker orderDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField totalCostField;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private ComboBox<String> shippingOptionBox;
    @FXML
    private ComboBox<String> paymentOptionBox;

    private Order order = new Order();

    private final OrderService orderService = new OrderService();

    private final ProductService productService = new ProductService();

    private OrderManagmentController orderListController;

    private Product product = new Product();

    private ShippingOption shippingOption = new ShippingOption();

    private List<ShippingOption> shippingOptionsList = new ArrayList<>();
    private List<PaymentOption> paymentOptionsList = new ArrayList<>();
    private List<OrderCurrentStatus> orderOptionsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.paymentOptionsList = this.orderService.getPaymentOptions();
        this.shippingOptionsList = this.orderService.getShippingOptions();
        this.orderOptionsList = Arrays.asList(OrderCurrentStatus.values());

        ObservableList<String> paymentOptions = FXCollections.observableArrayList(
                paymentOptionsList.stream().map(PaymentOption::getName).collect(Collectors.toList())
        );
        ObservableList<String> shippingOptions = FXCollections.observableArrayList(
                shippingOptionsList.stream().map(ShippingOption::getName).collect(Collectors.toList())
        );
        ObservableList<String> orderStatusOptions = FXCollections.observableArrayList(
                orderOptionsList.stream().map(OrderCurrentStatus::getStatus).collect(Collectors.toList())
        );
        this.paymentOptionBox.setItems(paymentOptions);
        this.shippingOptionBox.setItems(shippingOptions);
        this.statusBox.setItems(orderStatusOptions);
        this.paymentOptionBox.getSelectionModel().selectFirst();
        this.shippingOptionBox.getSelectionModel().selectFirst();
        this.statusBox.getSelectionModel().selectFirst();
    }

    public void setupData(Order order) throws SQLException {
        this.order = order;
        this.shippingOption = this.orderService.getShippingOption(order.getShippingOption());
        this.product = this.productService.getProductById(this.order.getProductId());
        this.setupUI();
    }

    private void setupUI() {
        this.orderId.setText("" + this.order.getId());
        this.productName.setText(this.product.getName());
        this.productCategory.setText("" + this.product.getCategoryId());
        this.descriptionField.setText(this.product.getDescription());
        this.productDimension.setText(this.product.getDimensions());
        this.productWeight.setText(this.product.getWeight() + "");
        this.productMat.setText(this.product.getMaterial());
        this.estimatedTime.setText(getFormattedEstimatedDeliveryDate());
        this.shpiingAddressField.setText(this.order.getShippingAddress());
        this.orderDatePicker.getEditor().setText(this.order.getOrderDate().toString());
        this.quantityField.setText(this.order.getQuantity() + "");
        this.totalCostField.setText(this.order.getTotalCost() + "");
        this.totalCostField.setText(this.order.getTotalCost() + "");
    }

    public Date getEstimatedDeliveryDate() {
        int maxDeliveryTime = this.shippingOption.getMaxDeliveryTimeInDays();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.order.getOrderDate());
        calendar.add(Calendar.DAY_OF_MONTH, maxDeliveryTime);
        Date estimatedDeliveryDate = calendar.getTime();
        return estimatedDeliveryDate;
    }

    public String getFormattedEstimatedDeliveryDate() {
        Date estimatedDeliveryDate = this.getEstimatedDeliveryDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(estimatedDeliveryDate);
    }

    @FXML
    private void OnUpdateButtonButton(ActionEvent event) {
        int selectedPaymentIndex = paymentOptionBox.getSelectionModel().getSelectedIndex();
        int selectedShippingIndex = shippingOptionBox.getSelectionModel().getSelectedIndex();
        int selectedPaymentId = paymentOptionsList.get(selectedPaymentIndex).getId();
        int selectedShippingId = shippingOptionsList.get(selectedShippingIndex).getId();
        if(this.orderDatePicker.getValue()!=null){
        LocalDate localDate = this.orderDatePicker.getValue();
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        this.order.setOrderDate(date);
        }
        this.order.setPaymentMethod(selectedPaymentId);
        this.order.setShippingOption(selectedShippingId);
        this.order.setQuantity(Integer.valueOf(this.quantityField.getText()));
        this.order.setTotalCost(Double.valueOf(this.totalCostField.getText()));
        this.order.setShippingAddress(this.shpiingAddressField.getText());
        this.orderService.updateOrderStatus(this.order.getId(), OrderCurrentStatus.getOrderStatus(statusBox.getSelectionModel().getSelectedItem()));
        this.orderService.updateOrder(this.order);
    }

    @FXML
    private void OnDeleteButton(ActionEvent event) {
        this.orderService.deleteOrder(this.order.getId());
    }

    @FXML
    private void OnRefundButton(ActionEvent event) {
    }
}
