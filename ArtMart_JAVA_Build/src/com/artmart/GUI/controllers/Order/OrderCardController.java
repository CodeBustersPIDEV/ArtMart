package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.Product;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrderCardController implements Initializable {

    private final ProductService productDao = new ProductService();
    private final OrderService orderService = new OrderService();

    @FXML
    private Text productNameValue;
    @FXML
    private Text AddressValue;
    @FXML
    private Text orderValue;
    @FXML
    private Text statusValue;

    private Order order;

    private Product product;
    @FXML
    private Button cancelBtn;
    private String status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setupData(Order order) {
        this.order = order;
        try {
            this.status = this.orderService.getOrderStatusByOrderId(order.getId()).getStatus();
            this.product = this.productDao.getProductById(this.order.getProductId());
            this.productNameValue.setText(this.product.getName());
            this.statusValue.setText(status);
            this.AddressValue.setText(String.valueOf(this.order.getShippingAddress()));
            this.orderValue.setText(String.valueOf(this.order.getOrderDate()));
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }
    }

    @FXML
    private void OnCancelEvent(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderDetail.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        OrderDetailController detailController = loader.getController();
        detailController.setupData(this.order, this.product,this.status);
        stage.setScene(scene);
        stage.show();
    }
}
