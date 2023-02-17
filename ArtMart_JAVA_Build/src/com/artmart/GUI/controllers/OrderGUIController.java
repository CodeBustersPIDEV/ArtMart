package com.artmart.GUI.controllers;

import com.artmart.models.Order;
import com.artmart.models.PaymentOption;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class OrderGUIController implements Initializable {

    private final OrderService orderService = new OrderService();
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
    private DatePicker orderDatePickerField;
    @FXML
    private TextField totalCostTextField;
    @FXML
    private ComboBox<String> paymentMethodDropdownField;
    @FXML
    private ComboBox<String> shippingMethodDropdownField;

    private List<PaymentOption> paymentOptionsList;

    private List<ShippingOption> shippingOptionsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paymentOptionsList = this.orderService.getPaymentOptions();
        shippingOptionsList = this.orderService.getShippingOptions();
        ObservableList<String> paymentOptions = FXCollections.observableArrayList(
                paymentOptionsList.stream().map(PaymentOption::getName).collect(Collectors.toList())
        );
        ObservableList<String> shippingOptions = FXCollections.observableArrayList(
                shippingOptionsList.stream().map(ShippingOption::getName).collect(Collectors.toList())
        );
        this.paymentMethodDropdownField.setItems(paymentOptions);
        this.shippingMethodDropdownField.setItems(shippingOptions);
    }

    @FXML
    private void OnOrderConfirm(ActionEvent event) {
        try {
            int selectedPaymentIndex = paymentMethodDropdownField.getSelectionModel().getSelectedIndex();
            int selectedShippingIndex = shippingMethodDropdownField.getSelectionModel().getSelectedIndex();
            int selectedPaymentId = paymentOptionsList.get(selectedPaymentIndex).getId();
            int selectedShippingId = shippingOptionsList.get(selectedShippingIndex).getId();

            String shippingAddress = this.shippingAddressStreetTextField.getText() + " "
                    + this.shippingAddressCityTextField.getText() + " "
                    + this.shippingAddressStateTextField.getText() + " "
                    + this.shippingAddressZipTextField.getText();

            Order newOrder = new Order(
                    Integer.parseInt(this.userIdTextField.getText()),
                    Integer.parseInt(this.productIdTextField.getText()),
                    Integer.parseInt(this.quantityTextField.getText()),
                    selectedShippingId,
                    shippingAddress,
                    selectedPaymentId,
                    Date.valueOf(this.orderDatePickerField.getValue()),
                    Double.parseDouble(this.totalCostTextField.getText())
            );
            this.orderService.createOrder(newOrder);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setHeaderText(null);
            alert.setContentText("Your order has been placed.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number for User ID, Product ID, and Quantity.");
            alert.showAndWait();
        }
    }

    @FXML
    private void OnOrderCancel(ActionEvent event) {
        try {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/OrderGUIMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
