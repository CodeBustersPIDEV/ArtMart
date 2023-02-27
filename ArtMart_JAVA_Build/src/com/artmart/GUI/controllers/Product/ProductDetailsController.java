/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.models.Categories;
import com.artmart.models.ReadyProduct;
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
    private Label pid;
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
        this.dimensions.setText(p.getDimensions());
        this.material.setText(p.getMaterial());
        this.price.setText(Integer.toString(p.getPrice()));

        // Load the image from the file path stored in ReadyProduct object's image field
        Image image = new Image("file:" + p.getImage());
        this.imagePreview.setImage(image);

        this.name.setText(p.getName());
        this.description.setText(p.getDescription());
        this.weight.setText("" + p.getWeight());
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/MainView.fxml"));
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
