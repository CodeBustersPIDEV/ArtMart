/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Order;

import com.artmart.dao.ProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Product;
import com.artmart.models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class OrderGUIMenuController implements Initializable {

    private final UserDao userDao = new UserDao();
    private final ProductDao productDao = new ProductDao();
    private User connectedUser = new User();
    private Product productToOrder = new Product();
    private List<User> userOptionsList;
    //private List<Product> ProductOptionsList;

    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private TextField productId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
<<<<<<< Updated upstream
        userOptionsList = this.userDao.viewListOfUsers();
        //ProductOptionsList = this.productDao().getAll(;
        ObservableList<String> userOptions = FXCollections.observableArrayList(
                userOptionsList.stream().map(User::getName).collect(Collectors.toList())
        );
        this.userComboBox.setItems(userOptions);
        this.userComboBox.getSelectionModel().selectFirst();
        this.productId.setText("1");
=======
        try {
            userOptionsList = this.userDao.viewListOfUsers();
            //ProductOptionsList = this.productDao().getAll(;
            ObservableList<String> userOptions = FXCollections.observableArrayList(
                userOptionsList.stream().map(user -> user.getName() + " (" + user.getRole()+ ")").collect(Collectors.toList())
            );
            productOptionsList = this.productDao.getAllProduct();
            ObservableList<String> productComboBox = FXCollections.observableArrayList(
                    productOptionsList.stream().map(Product::getName).collect(Collectors.toList())
            );
            this.userComboBox.setItems(userOptions);
            this.userComboBox.getSelectionModel().selectFirst();
            this.productComboBox.setItems(productComboBox);
            this.productComboBox.getSelectionModel().selectFirst();
            this.SelectUserAndProduct();
        } catch (SQLException ex) {
            Logger.getLogger(OrderGUIMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
>>>>>>> Stashed changes
    }

    @FXML
    private void OnOrderCreatePressEvent(ActionEvent event) {
        try {
            this.SelectUserAndProduct();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/Order.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            OrderGUIController controller = loader.getController();
            controller.setUpData(connectedUser, productToOrder);

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private void SelectUserAndProduct() {
<<<<<<< Updated upstream
        try {
            int listId = userComboBox.getSelectionModel().getSelectedIndex();
            int selectedUserId = userOptionsList.get(listId).getUser_id();
            this.connectedUser = this.userDao.getUser(selectedUserId);
            this.productToOrder = this.productDao.getProductById(Integer.valueOf(this.productId.getText()));
        } catch (SQLException e) {
            System.out.println("No Product Found");
        }
=======
        int listId = userComboBox.getSelectionModel().getSelectedIndex();
        int selectedUserId = userOptionsList.get(listId).getUser_id();
        this.connectedUser = this.userDao.getUser(selectedUserId);
        System.out.println(this.productToOrder.toString());
>>>>>>> Stashed changes
    }

    @FXML
    private void onViewList(ActionEvent event) {
        try {
            this.SelectUserAndProduct();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            OrderListController controller = loader.getController();
            controller.setUser(connectedUser);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
