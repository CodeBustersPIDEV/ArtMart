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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class AddCategoriesController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private Button addButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addButton(ActionEvent event) throws SQLException {
        
            String n = name.getText();
            if (n.isEmpty()) {
Alert alert = new Alert(Alert.AlertType.WARNING);
alert.setTitle("Add Category");
alert.setHeaderText(null);
alert.setContentText("Please enter a category name");
alert.showAndWait();
return; // exit the method if the name is empty
}
    Categories x= new Categories(n);
      CategoriesService CategoriesService = new CategoriesService();
    int result = CategoriesService.createCategories(x);
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

    @FXML
    private void back(ActionEvent event) throws SQLException, IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/categorieslist.fxml"));
    Parent root = loader.load();
    CategorieslistController controller = loader.getController();
    controller.makeList();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    
}
