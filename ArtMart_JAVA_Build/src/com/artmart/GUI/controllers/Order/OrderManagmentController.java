package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.services.OrderService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

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
    private TableColumn<Order, Void> operationColumn;
    @FXML
    private Button managePayBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.orderList = this.orderService.getOrders();
        ObservableList<Order> items = FXCollections.observableArrayList(
                this.orderList.stream().collect(Collectors.toList())
        );
        // set up the columns
        orderID_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_Col.setCellValueFactory(new PropertyValueFactory<>("userId"));
        product_Col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        shippingAdd_Col.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));
        orderDate_Col.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        this.orderTableView.setItems(items);

        this.operationColumn.setCellFactory(param -> new TableCell<Order, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox buttonsContainer = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    System.out.println("Edit button clicked");
                });

                deleteButton.setOnAction(event -> {
                    System.out.println("Delete button clicked");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonsContainer);
                }
            }
        });
    }

    @FXML

    private void OnClickShippment(ActionEvent event) {
    }

    @FXML
    private void OnClickPayment(ActionEvent event) {
    }

}
