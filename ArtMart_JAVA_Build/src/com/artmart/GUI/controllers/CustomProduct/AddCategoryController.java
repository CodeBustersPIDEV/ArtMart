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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AddCategoryController implements Initializable {

    @FXML
    private TextField nameF;
    @FXML
    private Button add;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onAddNew(ActionEvent event) throws SQLException, IOException {
        
        String name = nameF.getText();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Category");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a category name");
            alert.showAndWait();
            return; // exit the method if the name is empty
        }
        Categories x = new Categories(name);
        CategoriesService CategoriesService = new CategoriesService();
        int result = CategoriesService.createCategories(x);
        AdminBoardController w = new AdminBoardController();
        
        if (result > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Category");
            alert.setHeaderText(null);
            alert.setContentText("Category has been added successfully!");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add category");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add category!");
            alert.showAndWait();
        }
    }

    

}
