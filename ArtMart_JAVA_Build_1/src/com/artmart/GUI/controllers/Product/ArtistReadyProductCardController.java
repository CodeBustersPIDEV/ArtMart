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
public class ArtistReadyProductCardController implements Initializable {

    @FXML
    private Label pid;
    @FXML
    private Label userID;
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
    private Button details;
    @FXML
    private Button edit;

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDao = new ReadyProductDao();

    private ArtistReadyProductsListController controller = new ArtistReadyProductsListController();

    private ProductDetailsController controller2 = new ProductDetailsController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setReadyProduct(ReadyProduct param, ArtistReadyProductsListController controller) throws SQLException {
        this.p = param;
        this.controller = controller;
        this.pid.setText(Integer.toString(p.getProductId()));
        this.userID.setText(Integer.toString(p.getUserId()));
        
        CategoriesDao c = new CategoriesDao();
        Categories cat = c.getCategoriesById(p.getCategoryId());
        String catName = cat.getName();
        this.category.setText(catName);
        
        this.price.setText(Integer.toString(p.getPrice()));
        this.weight.setText(Float.toString(p.getWeight()));
        this.dimensions.setText(p.getDimensions());
        this.description.setText(p.getDescription());
        this.material.setText(p.getMaterial());

        Image image = new Image(p.getImage());
        this.imagePreview.setImage(image);

        this.name.setText(p.getName());
    }

    @FXML
    private void onDelete(ActionEvent event) throws SQLException {
        this.rPDao.deleteReadyProduct(this.p.getReadyProductId());
        this.controller.displayList();
    }

    public void setProductId(int pid) {
        this.pid.setText(Integer.toString(pid));
    }

    @FXML
    private void onEdit(ActionEvent event) throws SQLException {
        try {
            Stage stage = (Stage) edit.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/EditReadyProduct.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            EditReadyProductController controller = loader.getController();
            controller.setUpData(this.pid.getText());
            stage.setResizable(false);
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
}
