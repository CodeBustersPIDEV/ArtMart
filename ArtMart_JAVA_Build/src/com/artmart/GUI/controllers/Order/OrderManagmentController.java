package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.services.OrderService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderManagmentController implements Initializable {

    private final OrderService orderService = new OrderService();
    private List<Order> orderList;
    @FXML
    private Button manageShippBtn;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, String> orderID_Col;
    @FXML
    private TableColumn<Order, String> user_Col;
    @FXML
    private TableColumn<Order, String> product_Col;
    @FXML
    private TableColumn<Order, String> orderDate_Col;
    @FXML
    private TableColumn<Order, String> shippingAdd_Col;
    @FXML
    private Button managePayBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.refreshList();
    }

    @FXML

    private void OnClickShippment(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/EditShippingOptions.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnClickPayment(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/EditPaymentOptions.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void refreshList() {
        this.orderList = this.orderService.getOrders();
        ObservableList<Order> items = FXCollections.observableArrayList(
                this.orderList.stream().collect(Collectors.toList())
        );
        orderID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_Col.setCellValueFactory(new PropertyValueFactory<>("userId"));
        product_Col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        shippingAdd_Col.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));
        orderDate_Col.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        this.orderTableView.setItems(items);
        this.orderTableView.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Order order = row.getItem();
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/AdminOrderDetail.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        AdminOrderDetailController detailController = loader.getController();
                        detailController.setupData(order);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(OrderManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row;
        });

    }

}
