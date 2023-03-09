package com.artmart.GUI.controllers.Order;

import com.artmart.dao.ProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Product;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.models.Wishlist;
import com.artmart.services.OrderService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class OrderGUIMenuController implements Initializable {

    private final UserDao userDao = new UserDao();
    private final ProductDao productDao = new ProductDao();
    private Session session = new Session();
    private Product productToOrder = new Product();
    private List<Product> productOptionsList;
    private final OrderService orderSerice = new OrderService();
    private User connectedUser = new User();
    
    @FXML
    private Button createOrder;
    @FXML
    private Button viewOrderList;
    @FXML
    private ComboBox<String> productComboBox;
    @FXML
    private Button adminViewOrderList;
    @FXML
    private Button saveToWishListBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.connectedUser = userDao.getUser(session.getUserID("1"));
        try {
            productOptionsList = this.productDao.getAllProduct();
            ObservableList<String> productComboBox = FXCollections.observableArrayList(
                    productOptionsList.stream().map(Product::getName).collect(Collectors.toList())
            );
            this.productComboBox.setItems(productComboBox);
            this.productComboBox.getSelectionModel().selectFirst();
            this.productToOrder = this.productOptionsList.get(this.productComboBox.getSelectionModel().getSelectedIndex());
        } catch (SQLException ex) {
            Logger.getLogger(OrderGUIMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void OnOrderCreatePressEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/Order.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            OrderGUIController controller = loader.getController();
            controller.setData(connectedUser);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onViewList(ActionEvent event) {
        try {
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

    @FXML
    private void OnSelectProduct(ActionEvent event) {
        this.productToOrder = this.productOptionsList.get(this.productComboBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void OnSaveToWishList(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added To Wishlist");
        alert.setHeaderText("Product Saved for your wishlist");
        alert.showAndWait();
        this.orderSerice.createWishlist(new Wishlist(this.connectedUser.getUser_id(), this.productToOrder.getProductId(), Date.valueOf(formattedDate),1,10));
    }


    @FXML
    private void OnAdminViewList(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderManagment.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OrderGUIMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
