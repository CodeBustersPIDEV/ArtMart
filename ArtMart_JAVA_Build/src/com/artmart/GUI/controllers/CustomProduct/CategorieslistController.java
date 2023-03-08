/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;


import com.artmart.models.Categories;
import com.artmart.services.CategoriesService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class CategorieslistController implements Initializable {

    @FXML
    private VBox vBox;
private List<Categories> categorieslist;
 private final CategoriesService CategoriesService = new CategoriesService(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           try {
            this.makeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeList() throws SQLException {
        this.vBox.getChildren().clear();
        this.categorieslist = this.CategoriesService.getAllCategories();
        this.categorieslist.forEach(category -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/CategoriesCard.fxml"));
                Parent root = loader.load();
                CategoriesCardController controller = loader.getController();
                controller.setCategories(category,this);
                root.setId("" + category.getCategories_ID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }

    @FXML
    private void OnAdd(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/addCategories.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
             Stage currentStage = (Stage) vBox.getScene().getWindow();
             currentStage.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
           
        }
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Custom Product.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    

    }    
    
