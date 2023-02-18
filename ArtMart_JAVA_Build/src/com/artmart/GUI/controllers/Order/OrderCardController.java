package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.Product;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import com.artmart.utils.OrderCurrentStatus;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

public class OrderCardController implements Initializable {

    private final ProductService productDao = new ProductService();

    private final OrderService orderService = new OrderService();
    
    private OrderListController controller;

    @FXML
    private Text productNameValue;
    @FXML
    private Text quantityValue;
    @FXML
    private Text AddressValue;
    @FXML
    private Text orderValue;
    @FXML
    private Text costValue;
    @FXML
    private Text statusValue;
    @FXML
    private Button cancelBtn;

    private Order order;
    
    private Product product;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setupData(Order order,OrderListController controller) {
        this.order = order;
        this.controller = controller;
        try {
            this.product = this.productDao.getProductById(this.order.getProductId());
            this.productNameValue.setText(this.product.getName());
            this.quantityValue.setText(String.valueOf(this.order.getQuantity()));
            this.statusValue.setText(this.orderService.getOrderStatusByOrderId(order.getId()).getStatus());
            this.AddressValue.setText(String.valueOf(this.order.getShippingAddress()));
            this.orderValue.setText(String.valueOf(this.order.getOrderDate()));
            this.costValue.setText(String.valueOf(this.order.getTotalCost()));
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @FXML
    private void OnCancelEvent(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cancel " + this.product.getName() + " Order");
        alert.setHeaderText("Are you sure you want to cancel " + this.product.getName() + " order?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            cancelOrder();
        }
    }

    private void cancelOrder() {
        this.orderService.updateOrderStatus(order.getId(),OrderCurrentStatus.CANCELED);
        this.controller.refreshList();
    }
}
