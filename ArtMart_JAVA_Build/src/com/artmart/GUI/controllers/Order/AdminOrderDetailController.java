package com.artmart.GUI.controllers.Order;

import com.artmart.dao.CategoriesDao;
import com.artmart.models.Order;
import com.artmart.models.OrderRefund;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    @FXML
    private Button RefundButton;
    @FXML
    private ImageView productImage;
    
    
    private Order order = new Order();
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    private final CategoriesDao catDao = new CategoriesDao();
    private OrderManagmentController orderListController;
    private Product product = new Product();
    private String status = "";
    private ShippingOption shippingOption = new ShippingOption();
    private List<ShippingOption> shippingOptionsList = new ArrayList<>();
    private List<PaymentOption> paymentOptionsList = new ArrayList<>();
    private List<OrderCurrentStatus> orderOptionsList = new ArrayList<>();
    @FXML
    private Button closeButton;

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
    }

    public void setupData(Order order, OrderManagmentController manager) throws SQLException {
        this.order = order;
        this.status = this.orderService.getOrderStatusByOrderId(this.order.getId()).getStatus();
        this.orderListController = manager;
        this.shippingOption = this.orderService.getShippingOption(order.getShippingOption());
        this.product = this.productService.getProductById(this.order.getProductId());
        this.setupUI();
        this.statusBox.getSelectionModel().select(status);
    }

    private void setupUI() {
        try {
            if (OrderCurrentStatus.valueOf(status).equals(OrderCurrentStatus.REFUNDED)) {
                this.RefundButton.setDisable(true);
            }
            this.orderId.setText("" + this.order.getId());
            this.productName.setText(this.product.getName());
            this.productCategory.setText(this.catDao.getCategoryNameById(this.product.getCategoryId()));
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
            Image image = new Image(this.product.getImage());
            this.productImage.setImage(image);
        } catch (SQLException ex) {
            Logger.getLogger(AdminOrderDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if (this.orderDatePicker.getValue() != null) {
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
        this.orderListController.refreshList();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Order deleted successfully");
        alert.setHeaderText("Order deleted successfully");
    }

    @FXML
    private void OnDeleteButton(ActionEvent event) {
        this.orderService.deleteOrder(this.order.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Updated successfully");
        alert.setHeaderText("Order Updated successfully");
        this.orderListController.refreshList();
    }

    @FXML
    private void OnRefundButton(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Refund Manager");
        dialog.setHeaderText("Enter The Reason of Refund Please");
        dialog.setContentText("Reason:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            this.orderService.updateOrderStatus(this.order.getId(), OrderCurrentStatus.REFUNDED);
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date todayDate = new java.sql.Date(utilDate.getTime());
            this.orderService.createOrderRefund(new OrderRefund(this.order.getId(), this.order.getTotalCost(), result.get(), todayDate));
        }
        this.orderListController.refreshList();
    }
}
