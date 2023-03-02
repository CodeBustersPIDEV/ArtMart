/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.models.ReadyProduct;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ReadyproductsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private TextField search;
    @FXML
    private RadioButton sortCat;
    @FXML
    private RadioButton sortPrice;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backBtn;
    private List<ReadyProduct> readyProductslist;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.displayList();
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ReadyProductCardController controller = loader.getController();
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
    
    

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                Parent root = loader.load();
                ReadyProductCardController controller = loader.getController();
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

    @FXML
    private void onSortByPrice(ActionEvent event) {
        this.sortCat.setSelected(false);
        this.sortPrice.setSelected(true);
        this.vBox.getChildren().clear();
        this.readyProductslist.sort(Comparator.comparing(ReadyProduct::getPrice));
        this.readyProductslist.forEach(RProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                Parent root = loader.load();
                ReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(RProduct, this);
                root.setId("" + RProduct.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void onSortByCategory(ActionEvent event) {
        this.sortCat.setSelected(true);
        this.sortPrice.setSelected(false);
        this.vBox.getChildren().clear();
        this.readyProductslist.sort(Comparator.comparing(ReadyProduct::getCategoryId));
        this.readyProductslist.forEach(RProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                Parent root = loader.load();
                ReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(RProduct, this);
                root.setId("" + RProduct.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
