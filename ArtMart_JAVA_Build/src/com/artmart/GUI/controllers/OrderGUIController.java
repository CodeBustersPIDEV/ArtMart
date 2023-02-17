package com.artmart.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class OrderGUIController implements Initializable {

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
    private TextField orderDateTextField;
    @FXML
    private TextField totalCostTextField;
    @FXML
    private Button placeOrderBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.placeOrderBtn.setOnAction(e->{
            System.out.print("Hello");
        }); 
    }    
    
}
