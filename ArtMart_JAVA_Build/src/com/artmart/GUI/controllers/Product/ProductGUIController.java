/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.ProductDao;
import com.artmart.dao.ReadyProductDao;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class ProductGUIController implements Initializable {

    ReadyProductDao cc = new ReadyProductDao();
    ProductDao x = new ProductDao();

    @FXML
    private ImageView imagePreview1;
    @FXML
    private ImageView imagePreview2;
    @FXML
    private ImageView imagePreview3;
    @FXML
    private ImageView imagePreview4;
    @FXML
    private Button consultAllReadyProducts;
    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> imageUrls = null;
        try {
            imageUrls = cc.getRandomProductImages(4);
        } catch (SQLException ex) {
            Logger.getLogger(ProductGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (imageUrls.size() >= 1) {
            setProductImage(imagePreview1, imageUrls.get(0));
        }
        if (imageUrls.size() >= 2) {
            setProductImage(imagePreview2, imageUrls.get(1));
        }
        if (imageUrls.size() >= 3) {
            setProductImage(imagePreview3, imageUrls.get(2));
        }
        if (imageUrls.size() >= 4) {
            setProductImage(imagePreview4, imageUrls.get(3));
        }
    }

    private void setProductImage(ImageView imageView, String imageUrl) {
        try {
            Image productImage = new Image(imageUrl);
            imageView.setImage(productImage);
        } catch (IllegalArgumentException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    public void consultAllReadyProducts(ActionEvent event) {
        try {
            Stage stage = (Stage) consultAllReadyProducts.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Ready Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
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
