/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.models.Categories;
import com.artmart.models.ReadyProduct;
import com.artmart.services.CategoriesService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ArtistReadyProductsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private TextField search;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    private List<ReadyProduct> readyProductslist;
    @FXML
    private VBox vBoxCat;
    private List<Categories> categorieslist;
    private final CategoriesService CategoriesService = new CategoriesService();
    private CategoryCardController categoryCardController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.displayList();
            this.makeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayList() throws SQLException {
        try {
            this.vBox.getChildren().clear();
            this.readyProductslist = this.readyProductService.getAllReadyProducts();
            this.readyProductslist.forEach(rp -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ArtistReadyProductCardController controller = loader.getController();
                    controller.setReadyProduct(rp, this);
                    root.setId("" + rp.getReadyProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                } catch (SQLException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeList() throws SQLException {
        this.vBoxCat.getChildren().clear();
        this.categorieslist = this.CategoriesService.getAllCategories();
        this.categorieslist.forEach(category -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/CategoryCard.fxml"));
                Node categoryCard = loader.load();
                CategoryCardController controller = loader.getController();
                controller.setCategories(category, this);
                categoryCard.setId("" + category.getCategories_ID());
                // Set the event handler for the category label
                Label categoryLabel = controller.getCategoryLabel();
                categoryLabel.setOnMouseClicked(event -> {
                    String categoryName = controller.getCategoryName();
                    try {
                        sortByCategoryName(categoryName);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                this.vBoxCat.getChildren().add(categoryCard);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addProduct.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddReadyProduct.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
     public void onAddCategory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddCategory.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onSearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(rP -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rP, this);
                root.setId("" + rP.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void sortByCategoryName(String categoryName) throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProductsByCategoryName(categoryName);
        this.readyProductslist.forEach(rp -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rp, this);
                root.setId("" + rp.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
