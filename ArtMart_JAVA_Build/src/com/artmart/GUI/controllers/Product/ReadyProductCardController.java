/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.CustomProduct.EditCpController;
import com.artmart.dao.ReadyProductDao;
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
public class ReadyProductCardController implements Initializable {

    @FXML
    private Label pid;
    @FXML
    private Text name;
    @FXML
    private Label material;
    @FXML
    private Label weight;
    @FXML
    private Label dimensions;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label price;
    @FXML
    private ImageView imagePreview;

    @FXML
    private Button delete;

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDao = new ReadyProductDao();

    private ReadyproductsListController controller = new ReadyproductsListController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setReadyProduct(ReadyProduct param, ReadyproductsListController controller) {
        this.p = param;
        this.controller = controller;
        this.pid.setText(Integer.toString(p.getProductId()));
        this.category.setText(Integer.toString(p.getCategoryId()));
        this.dimensions.setText(p.getDimensions());
        this.material.setText(p.getMaterial());
        //this.price.setText(p.getPrice());

        // Load the image from the file path stored in ReadyProduct object's image field
        Image image = new Image("file:" + p.getImage());
        this.imagePreview.setImage(image);

        this.name.setText(p.getName());
        this.description.setText(p.getDescription());
        this.weight.setText("" + p.getWeight());
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
            // create a new instance of the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            // set the location of the FXML file for the EditReadyProductController
            loader.setLocation(getClass().getResource("/com/artmart/GUI/views/Product/EditReadyProduct.fxml"));
            // load the FXML file and get the root node of the scene graph
            Parent root = loader.load();
            EditReadyProductController editController = loader.getController();
            editController.setProductId(this.p.getProductId());
            Scene scene = new Scene(root);
            // create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Ready Product");
            // close the current window
            Stage currentStage = (Stage) delete.getScene().getWindow();
            currentStage.close();
            // show the new window
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
