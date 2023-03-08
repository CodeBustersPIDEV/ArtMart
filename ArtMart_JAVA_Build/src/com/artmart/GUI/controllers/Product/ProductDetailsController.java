/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import static com.artmart.GUI.controllers.Product.EditReadyProductController.convertToReadyProduct;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.services.ProductService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button delete;
    @FXML
    private Button edit;
    @FXML
    private Button backBtn;

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDao = new ReadyProductDao();

    private ReadyproductsListController controller = new ReadyproductsListController();

    private ReadyProduct viewProd = new ReadyProduct();
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            this.price.setText(Integer.toString(this.viewProd.getPrice()));
            this.material.setText(this.viewProd.getMaterial());

            CategoriesDao c = new CategoriesDao();
            Categories cat = c.getCategoriesById(this.viewProd.getCategoryId());
            String catName = cat.getName();
            this.category.setText(catName);
            // Load the image from the file path stored in ReadyProduct object's image field
            Image image = new Image("file:" + this.viewProd.getImage());
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

}
