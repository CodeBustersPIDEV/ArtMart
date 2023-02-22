/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.ReadyProductDao;
import com.artmart.models.ReadyProduct;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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

    private ReadyProduct p = new ReadyProduct();

    private ReadyProductDao rPDap = new ReadyProductDao();

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

}
