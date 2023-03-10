package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.CustomProduct.CustomproductslistController;
import com.artmart.models.Categories;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CategoryCardController implements Initializable {

    @FXML
    private Label category;

    private Categories categoryIns;

    private ArtistReadyProductsListController artistReadyProductsListController;
        private CustomproductslistController CustomproductslistController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCategories(Categories cat, ArtistReadyProductsListController artistReadyProductsListController) {
        this.categoryIns = cat;
        this.artistReadyProductsListController = artistReadyProductsListController;
        this.category.setText(categoryIns.getName());
    }
   public void setCategories1(Categories cat, CustomproductslistController CustomproductslistController) {
        this.categoryIns = cat;
        this.CustomproductslistController = CustomproductslistController;
        this.category.setText(categoryIns.getName());
    }
    public Label getCategoryLabel() {
        return category;
    }

    public String getCategoryName() {
        return category.getText();
    }
}
