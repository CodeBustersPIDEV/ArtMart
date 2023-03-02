/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.models.Categories;
import com.artmart.services.CategoriesService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class CategoryCardController implements Initializable {

    @FXML
    private Label category;

    private Categories categoryIns;

    private ArtistReadyProductsListController artistReadyProductsListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCategories(Categories cat, ArtistReadyProductsListController artistReadyProductsListController) {
        this.categoryIns = cat;
        this.artistReadyProductsListController = artistReadyProductsListController;
        this.category.setText(categoryIns.getName());
    }

    public Label getCategoryLabel() {
        return category;
    }

    public String getCategoryName() {
        return category.getText();
    }
}
