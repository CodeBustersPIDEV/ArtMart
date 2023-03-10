/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import static com.artmart.GUI.controllers.Product.EditReadyProductController.convertToReadyProduct;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ProductDetailsController implements Initializable {

    @FXML
    private Label prodID;
    @FXML
    private Text name;
    @FXML
    private Label category;
    @FXML
    private Label dimensions;
    @FXML
    private Label material;
    @FXML
    private Label description;
    @FXML
    private Label weight;
    @FXML
    private Label price;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Button order;

    @FXML
    private Button backBtn;
    private ChoiceBox<String> profileChoiceBox;

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDao = new ReadyProductDao();

    private ReadyproductsListController controller = new ReadyproductsListController();

    private ReadyProduct viewProd = new ReadyProduct();
    private int id;
    private Session session = new Session();
    int UserID = session.getUserID("1");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setUpData(String pid) {
        try {
            this.prodID.setText(pid);
            this.id = Integer.parseInt(this.prodID.getText());
            ReadyProductService readyProductService = new ReadyProductService();

            int productId = readyProductService.getReadyProductId(id);
            if (productId == 0) {
                throw new SQLException("Failed to get product ID from database");
            }
            ProductService productService = new ProductService();
            Product product = productService.getProductById(productId);

            this.viewProd = convertToReadyProduct(product);
            System.out.println(this.viewProd);

            this.name.setText(this.viewProd.getName());
            this.description.setText(this.viewProd.getDescription());
            this.dimensions.setText(this.viewProd.getDimensions());
            this.weight.setText(Float.toString(this.viewProd.getWeight()));
            this.viewProd = rPDao.getReadyProductById(productId);
            int pr = this.viewProd.getPrice();
            this.price.setText("" + pr);
            this.material.setText(this.viewProd.getMaterial());
            CategoriesDao c = new CategoriesDao();
            Categories cat = c.getCategoriesById(this.viewProd.getCategoryId());
            String catName = cat.getName();
            this.category.setText(catName);
            Image image = new Image(this.viewProd.getImage());
            this.imagePreview.setImage(image);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to get data from database");
            alert.showAndWait();
        }
    }

    public static ReadyProduct convertToReadyProduct(Product product) {
        ReadyProduct readyProduct = new ReadyProduct();
        readyProduct.setProductId(product.getProductId());
        readyProduct.setName(product.getName());
        readyProduct.setDescription(product.getDescription());
        readyProduct.setCategoryId(product.getCategoryId());
        readyProduct.setDimensions(product.getDimensions());
        readyProduct.setWeight(product.getWeight());
        readyProduct.setMaterial(product.getMaterial());
        readyProduct.setImage(product.getImage());
        return readyProduct;
    }

    @FXML
    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

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
        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField();
        VBox vbox = new VBox(quantityLabel, quantityTextField);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        alert.getDialogPane().setContent(vbox);
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                int quantity = Integer.parseInt(quantityTextField.getText());
                OrderService orderService = new OrderService();
                orderService.addToShoppingCart(p, quantity, p.getPrice());
            }
        });
    }

    private OrderService orderSerivce= new OrderService();
    @FXML
    private void GoToShoppingCart(ActionEvent event) {
        this.orderSerivce.goToCheckOut();
    }
}
