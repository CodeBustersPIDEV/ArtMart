/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.Product;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import com.artmart.utils.OrderCurrentStatus;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class OrderDetailController implements Initializable {

    private OrderService orderService;

    @FXML
    private Label orderId;
    @FXML
    private ComboBox<String> shippingOption;
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

    private Order order = new Order();
    private List<ShippingOption> shippingOptionsList = new ArrayList<>();
    private Product product = new Product();
    @FXML
    private Button updateBtn;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea descriptionField;

    private String status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.updateBtn.setOnAction(e -> {
            this.OnUpdateOrder();
        });
        this.ShpiingAddress.setEditable(false);
        this.orderService = new OrderService();
        this.shippingOptionsList = this.orderService.getShippingOptions();
        ObservableList<String> shippingOptions = FXCollections.observableArrayList(
                shippingOptionsList.stream().map(ShippingOption::getName).collect(Collectors.toList())
        );

        this.shippingOption.setItems(shippingOptions);
    }

    private void cancelOrder() {
        this.orderService.updateOrderStatus(order.getId(), OrderCurrentStatus.CANCELED);
    }

    private void OnUpdateOrder() {
        this.ShpiingAddress.setEditable(true);
        this.ShpiingAddress.setDisable(false);
        this.shippingOption.setDisable(false);
        this.updateBtn.setText("Save");
        this.updateBtn.setOnAction(e -> {
            this.saveUpdate();
        });
    }

    public void setupData(Order order, Product product, String status) {
        this.status = status;
        this.order = order;
        this.product = product;
        this.shippingOption.getSelectionModel().select(this.order.getShippingOption() - 1);
        this.setupUI();
    }

    private void saveUpdate() {
        this.ShpiingAddress.setEditable(false);
        this.ShpiingAddress.setDisable(true);
        this.shippingOption.setDisable(true);
        this.updateBtn.setText("Update Order");
        this.updateBtn.setOnAction(e -> {
            this.OnUpdateOrder();
        });
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

        switch (OrderCurrentStatus.valueOf(this.status)) {
            case PENDING:
                this.updateBtn.setDisable(false);
                this.closeButton.setDisable(false);
                this.ShpiingAddress.setDisable(true);
                this.shippingOption.setDisable(true);
                break;
            case PROCESSING:
                this.updateBtn.setDisable(false);
                this.closeButton.setDisable(true);
                this.ShpiingAddress.setDisable(false);
                this.shippingOption.setDisable(false);
                break;
            default:
                this.updateBtn.setDisable(true);
                this.closeButton.setDisable(true);
                this.ShpiingAddress.setDisable(true);
                this.shippingOption.setDisable(true);
                break;
        }
    }
}
