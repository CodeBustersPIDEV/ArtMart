package com.artmart.GUI.controllers.Order;

import com.artmart.dao.ProductDao;
import com.artmart.models.Order;
import com.artmart.models.PaymentOption;
import com.artmart.models.Product;
import com.artmart.models.User;
import com.artmart.models.Wishlist;
import com.artmart.services.OrderService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderGUIController implements Initializable {

    @FXML
    private Button placeOrderBtn;
    @FXML
    private Button cancelOrderBtn;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> name_col;
    @FXML
    private TableColumn<Product, String> desc_col;
    @FXML
    private TableColumn<Product, String> dimension_col;
    @FXML
    private TableColumn<Product, String> weight_col;
    @FXML
    private TableColumn<Product, String> mat_col;
    @FXML
    private Button backButton;

    private final OrderService orderSerivce = new OrderService();
    private final ProductDao productDao = new ProductDao();

    private List<Wishlist> usersWishList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private User user = new User();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setData(User data) {
        this.user = data;
        this.refreshlist();
    }

    @FXML
    private void OnOrderConfirm(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderCheckout.fxml"));
            Parent root = loader.load();
            OrderCheckOutController controller = loader.getController();
            controller.link(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(OrderGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void OnOrderCancel(ActionEvent event) {
    }

    @FXML
    private void OnBackBtnl(ActionEvent event) {
    }

    private void refreshlist() {
        this.usersWishList = this.orderSerivce.getWishlistsByUserId(this.user.getUser_id());
        if (this.usersWishList.isEmpty()) {
            placeOrderBtn.setDisable(true);
        }else {
        this.usersWishList.forEach((wishlist) -> {
            try {
                this.productList.add(this.productDao.getProductById(wishlist.getProductId()));
            } catch (SQLException ex) {
                Logger.getLogger(OrderGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ObservableList<Product> items = FXCollections.observableArrayList(
                this.productList.stream().collect(Collectors.toList())
        );
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        dimension_col.setCellValueFactory(new PropertyValueFactory<>("dimensions"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));
        mat_col.setCellValueFactory(new PropertyValueFactory<>("material"));
        productTableView.setItems(items);
        this.productTableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete From Wishlist");
                    alert.setHeaderText("Are you sure you want to delete " + row.getItem().getName() + " from your wishlist ?");
                    alert.setContentText("This action cannot be undone.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        this.DeletWishlist(row.getItem());
                    }
                }
            });
            return row;
        });
        }
    }

    private void DeletWishlist(Product p) {
        this.orderSerivce.deleteWishlist(p.getProductId(), this.user.getUser_id());
    }

    public void successfulPayment(int shippingOption, String shippingAddress, int paymentMethod) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        this.productList.forEach((product) -> {
            this.DeletWishlist(product);
        });
        this.productList.forEach((product) -> {
            Order newOrder = new Order(this.user.getUser_id(), product.getProductId(), 1, shippingOption, shippingAddress, paymentMethod, Date.valueOf(dtf.format(now)), 100);
            this.orderSerivce.createOrder(newOrder);
        });
    }
}
