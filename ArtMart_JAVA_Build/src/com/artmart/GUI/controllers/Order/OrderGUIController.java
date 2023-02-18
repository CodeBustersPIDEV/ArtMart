package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.PaymentOption;
import com.artmart.models.Product;
import com.artmart.models.ShippingOption;
import com.artmart.models.User;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class OrderGUIController implements Initializable {

    private final OrderService orderService = new OrderService();
    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField shippingAddressStreetTextField;
    @FXML
    private TextField shippingAddressCityTextField;
    @FXML
    private TextField shippingAddressStateTextField;
    @FXML
    private TextField shippingAddressZipTextField;
    @FXML
    private ComboBox<String> paymentMethodDropdownField;
    @FXML
    private ComboBox<String> shippingMethodDropdownField;
    @FXML
    private Spinner<Integer> quantityNumberField;

    private List<PaymentOption> paymentOptionsList;
    private List<ShippingOption> shippingOptionsList;
    private User userData = new User();
    private Product productToOrder = new Product();
    final int initialValue = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setUpSpinner();
        this.setUpList();
    }

    @FXML
    private void OnOrderConfirm(ActionEvent event) {
        try {
            int selectedPaymentIndex = paymentMethodDropdownField.getSelectionModel().getSelectedIndex();
            int selectedShippingIndex = shippingMethodDropdownField.getSelectionModel().getSelectedIndex();
            int selectedPaymentId = paymentOptionsList.get(selectedPaymentIndex).getId();
            int selectedShippingId = shippingOptionsList.get(selectedShippingIndex).getId();

            java.util.Date utilDate = new java.util.Date();
            java.sql.Date todayDate = new java.sql.Date(utilDate.getTime());

            String shippingAddress = this.shippingAddressStreetTextField.getText() + " "
                    + this.shippingAddressCityTextField.getText() + " "
                    + this.shippingAddressStateTextField.getText() + " "
                    + this.shippingAddressZipTextField.getText();

            Order newOrder = new Order(
                    this.userData.getUser_id(),
                    this.productToOrder.getProductId(),
                    this.quantityNumberField.getValue(),
                    selectedShippingId,
                    shippingAddress,
                    selectedPaymentId,
                    todayDate,
                    100
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Order/OrderGUIMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void setUpData(User udata, Product pdata) {
        this.userData = udata;
        this.productToOrder = pdata;
        this.userIdTextField.setText(this.userData.getName());
        this.productIdTextField.setText(this.productToOrder.getName());
    }

    private void setUpSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, initialValue);
        this.quantityNumberField.setValueFactory(valueFactory);
        this.quantityNumberField.getValueFactory().setValue(initialValue);
    }

    private void setUpList() {
        this.paymentOptionsList = this.orderService.getPaymentOptions();
        this.shippingOptionsList = this.orderService.getShippingOptions();
        ObservableList<String> paymentOptions = FXCollections.observableArrayList(
                paymentOptionsList.stream().map(PaymentOption::getName).collect(Collectors.toList())
        );
        ObservableList<String> shippingOptions = FXCollections.observableArrayList(
                shippingOptionsList.stream().map(ShippingOption::getName).collect(Collectors.toList())
        );
        this.paymentMethodDropdownField.setItems(paymentOptions);
        this.shippingMethodDropdownField.setItems(shippingOptions);
        this.paymentMethodDropdownField.getSelectionModel().selectFirst();
        this.shippingMethodDropdownField.getSelectionModel().selectFirst();
    }

}
