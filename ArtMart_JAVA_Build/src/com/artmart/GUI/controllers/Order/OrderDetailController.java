package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.Product;
import com.artmart.models.Receipt;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import com.artmart.utils.OrderCurrentStatus;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class OrderDetailController implements Initializable {

    @FXML
    private Label orderId;
    @FXML
    private TextArea ShpiingAddress;
    @FXML
    private Label orderStatus;
    @FXML
    private Label orderDate;
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
    private Label paymentMethod;
    @FXML
    private Label orderQuantity;
    @FXML
    private Label orderCost;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label shippingMethod;

    private Order order = new Order();
    private String status;
    private OrderService orderService;
    private OrderListController orderListController;
    private Product product = new Product();
    private ShippingOption shippingOption = new ShippingOption();
    @FXML
    private Label estimatedTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ShpiingAddress.setDisable(true);
        this.ShpiingAddress.setEditable(false);
        this.orderService = new OrderService();
    }

    private void cancelOrder() {
        this.orderStatus.setText("CANCELED");
        this.orderService.updateOrderStatus(order.getId(), OrderCurrentStatus.CANCELED);
        this.orderListController.refreshList();
    }

    public void setupData(Order order, Product product, String status, OrderListController listController) {
        this.shippingOption = this.orderService.getShippingOption(order.getShippingOption());
        this.status = status;
        this.order = order;
        this.product = product;
        this.setupUI();
        this.orderListController = listController;
    }

    @FXML
    private void OnCancelOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel " + this.product.getName() + " Order");
        alert.setHeaderText("Are you sure you want to cancel " + this.product.getName() + " order?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            cancelOrder();
        }
    }

    private void setupUI() {
        this.shippingMethod.setText(this.shippingOption.getName());
        this.orderStatus.setText(this.status);
        this.orderId.setText("" + this.order.getId());
        this.ShpiingAddress.setText(this.order.getShippingAddress());
        this.orderDate.setText(this.order.getOrderDate().toString());
        this.productName.setText(this.product.getName());
        this.productCategory.setText("" + this.product.getCategoryId());
        this.descriptionField.setText(this.product.getDescription());
        this.productDimension.setText(this.product.getDimensions());
        this.productWeight.setText(this.product.getWeight() + "");
        this.productMat.setText(this.product.getMaterial());
        this.paymentMethod.setText(this.order.getPaymentMethod() + "");
        this.orderQuantity.setText(this.order.getQuantity() + "");
        this.orderCost.setText("" + this.order.getTotalCost());
        this.estimatedTime.setText(getFormattedEstimatedDeliveryDate());
        switch (OrderCurrentStatus.valueOf(this.status)) {
            case PENDING:
                this.closeButton.setDisable(false);
                break;
            default:
                this.closeButton.setDisable(true);
                break;
        }
    }

    @FXML
    private void handleGenerateReceiptButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Receipt");
        fileChooser.setInitialFileName("receipt_" + this.order.getId() + ".txt");
        File file = fileChooser.showSaveDialog((Stage) closeButton.getScene().getWindow());

        if (file != null) {
            // Write the receipt to the selected file
            try (PrintWriter writer = new PrintWriter(file)) {
                String template = "=======================\n"
                        + "      ORDER RECEIPT\n"
                        + "=======================\n"
                        + "Order ID: " + this.order.getId() + "\n"
                        + "Customer Address: " + this.order.getShippingAddress() + "\n"
                        + "Product Name : " + this.product.getName() + "\n"
                        + "Product Category : " + this.product.getCategoryId() + "\n"
                        + "Product Description : : " + this.product.getDescription() + "\n"
                        + "Product Dimensions  : : " + this.product.getDimensions() + "\n"
                        + "Product Weight      : : " + this.product.getWeight() + "\n"
                        + "Product Material    : : " + this.product.getMaterial() + "\n"
                        + "Quantity: " + this.order.getQuantity() + "\n"
                        + "Total Amount: $" + this.order.getTotalCost() + "\n"
                        + "=======================\n";
                writer.write(template);
                this.orderService.createReceipt(new Receipt(this.order.getId(), this.product.getProductId(), this.order.getQuantity(), this.order.getTotalCost(), 10, this.order.getTotalCost(), this.order.getOrderDate()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
}
