/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers;

import com.artmart.dao.ProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.PaymentOption;
import com.artmart.models.Product;
import com.artmart.models.ShippingOption;
import com.artmart.models.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class OrderGUIMenuController implements Initializable {

    private UserDao userDao = new UserDao();
    private ProductDao productDao = new ProductDao();
    private User connectedUser = new User();
    @FXML
    private Button createOrder;
    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private ComboBox<String> productComboBox;

    private List<User> userOptionsList;

    private List<Product> ProductOptionsList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userOptionsList = this.userDao.viewListOfUsers();
        //ProductOptionsList = this.productDao().getAll(;
        ObservableList<String> userOptions = FXCollections.observableArrayList(
                userOptionsList.stream().map(User::getName).collect(Collectors.toList())
        );
        this.userComboBox.setItems(userOptions);
    }

    @FXML
    private void OnOrderCreatePressEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Order.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void SelectUser(ActionEvent event) {
        int listId = userComboBox.getSelectionModel().getSelectedIndex();
        int selectedUserId = userOptionsList.get(listId).getUser_id();
        this.connectedUser = this.userDao.getUser(selectedUserId);
        System.out.println("New User Connected "+connectedUser);
    }

    @FXML
    private void SelectProduct(ActionEvent event) {
    }

}
