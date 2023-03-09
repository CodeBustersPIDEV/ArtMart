/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ProductReviewDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.models.Categories;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
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
        this.price.setText(p.getPrice()+"");

        // Get the rating value using the ProductId field of the ReadyProduct object
        ReadyProductService readyProductService = new ReadyProductService();
        float rating = readyProductService.getRatingByProductId(p.getProductId());

        // Set the rating value in the rating label
        this.rating.setText(Float.toString(rating));

        // Load the image from the file path stored in ReadyProduct object's image field
        Image image = new Image("file:" + p.getImage());
        this.imagePreview.setImage(image);

        this.name.setText(p.getName());
    }

    public void setProductId(int pid) {
        this.pid.setText(Integer.toString(pid));
    }

    @FXML
    public void onDetails(ActionEvent event) {
        try {
            Stage stage = (Stage) details.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ProductDetails.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            ProductDetailsController controller2 = loader.getController();
            controller2.setUpData(this.pid.getText());
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
    public void onOrder(ActionEvent event){
    }
}
