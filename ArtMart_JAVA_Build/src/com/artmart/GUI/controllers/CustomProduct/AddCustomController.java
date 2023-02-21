/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;
import com.artmart.services.CustomProductService;
import com.artmart.models.Product;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class AddCustomController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField descField;
    @FXML
    private TextField dimField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField materialField;
    @FXML
    private TextField categoryComboBox;
    @FXML
    private TextField imageField;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<?> categoryComboBox2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
private void handleAddButtonAction(ActionEvent event) throws SQLException {
    String name = nameField.getText();
    String desc = descField.getText();
    String dim = dimField.getText();
    float weight = Float.parseFloat(weightField.getText());
    String material = materialField.getText();
     int category =Integer.parseInt(categoryComboBox.getText());
    String image = imageField.getText();

    Product baseProduct = new Product(category, name, desc, dim, weight, material, image);

CustomProductService customProductService = new CustomProductService();
int result = customProductService.createCustomProduct(baseProduct);
    if (result > 0) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Custom product has been added successfully!");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Failed to add custom product!");
        alert.showAndWait();
    }
}

}
