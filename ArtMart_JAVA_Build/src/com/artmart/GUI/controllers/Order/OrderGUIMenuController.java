package com.artmart.GUI.controllers.Order;

import com.artmart.services.OrderService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class OrderGUIMenuController implements Initializable {

    private final OrderService orderService = new OrderService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void OnOrderCreatePressEvent(ActionEvent event) {
        this.orderService.goToCheckOut();
    }

    @FXML
    private void onViewList(ActionEvent event) {
        this.orderService.goToUsersOrderList();
    }

    @FXML
    private void OnAdminViewList(ActionEvent event) {
        
    }
}
