package com.artmart.GUI.controllers.Product;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.models.Categories;
import com.artmart.models.ReadyProduct;
import com.artmart.services.OrderService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReadyProductCardController implements Initializable {

    @FXML
    private Label pid;
    @FXML
    private Text name;
    @FXML
    private Label category;
    @FXML
    private Label price;
    @FXML
    private Label rating;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Button details;
    @FXML
    private Button reviews;
    @FXML
    private Button order;

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDao = new ReadyProductDao();

    private ReadyproductsListController controller = new ReadyproductsListController();

    private ProductDetailsController controller2 = new ProductDetailsController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setReadyProduct(ReadyProduct param, ReadyproductsListController controller) throws SQLException {
        this.p = param;
        this.controller = controller;
        this.pid.setText(Integer.toString(p.getProductId()));
        CategoriesDao c = new CategoriesDao();
        Categories cat = c.getCategoriesById(p.getCategoryId());
        String catName = cat.getName();
        this.category.setText(catName);
        this.price.setText(p.getPrice() + "");
        ReadyProductService readyProductService = new ReadyProductService();
        float rating = readyProductService.getRatingByProductId(p.getProductId());
        this.rating.setText(Float.toString(rating));
        Image image = new Image(p.getImage());
        this.imagePreview.setImage(image);
        this.name.setText(p.getName());
    }

    public void setProductId(int pid) {
        this.pid.setText(Integer.toString(pid));
    }

    @FXML
    public void onDetails(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ProductDetails.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            ProductDetailsController controller = loader.getController();
            controller.setUpData(this.pid.getText());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Ready Product details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    public void onReviews(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReviewsList.fxml"));
            Parent root = loader.load();
            ReviewsListController contr = loader.getController();
            contr.setProductId(this.p.getReadyProductId());
            contr.setReadyProduct(this.p);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Reviews List");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    public void onOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Place an Order");
        alert.setHeaderText(null);

        // Create custom layout
        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField();
        VBox vbox = new VBox(quantityLabel, quantityTextField);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Set custom layout to dialog pane
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                int quantity = Integer.parseInt(quantityTextField.getText());
                OrderService orderService = new OrderService();
                orderService.addToShoppingCart(p, quantity, p.getPrice());
            }
        });
    }
}
