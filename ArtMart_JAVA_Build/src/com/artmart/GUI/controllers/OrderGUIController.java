package com.artmart.GUI.controllers;

import com.artmart.models.Order;
import com.artmart.services.OrderService;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class OrderGUIController implements Initializable {

    OrderService orderService = new OrderService();
    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField shippingAddressStreetTextField;
    @FXML
    private TextField shippingAddressCityTextField;
    @FXML
    private TextField shippingAddressStateTextField;
    @FXML
    private TextField shippingAddressZipTextField;
    @FXML
    private TextField paymentMethodTextField;
    @FXML
    private DatePicker orderDatePickerField;
    @FXML
    private TextField totalCostTextField;
    @FXML
    private Button placeOrderBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void placeOrder(ActionEvent event) {
        
        String shippingAddress = this.shippingAddressStreetTextField.getText() + " "
                + this.shippingAddressCityTextField.getText() + " "
                + this.shippingAddressStateTextField.getText() + " "
                + this.shippingAddressZipTextField.getText();

        Order newOrder = new Order(
                Integer.parseInt(this.userIdTextField.getText()),
                Integer.parseInt(this.productIdTextField.getText()),
                Integer.parseInt(this.quantityTextField.getText()),
                shippingAddress,
                this.paymentMethodTextField.getText(),
                Date.valueOf(this.orderDatePickerField.getValue()),
                Double.parseDouble(this.totalCostTextField.getText())
        );
        
        this.orderService.createOrder(newOrder);
    }

}
