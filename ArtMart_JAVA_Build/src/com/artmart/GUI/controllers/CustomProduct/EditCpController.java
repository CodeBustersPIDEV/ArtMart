/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ProductDao;
import com.artmart.models.Product;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class EditCpController implements Initializable {

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
    private Button edit_cp;
    
    private Product viewProduct = new Product();
   private CustomproductslistController controller=new CustomproductslistController();
       private final ProductDao productDao = new ProductDao();
    @FXML
    private Label prodid;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void edit(ActionEvent event) throws SQLException {
        
        
    String name = nameField.getText();
    String desc = descField.getText();
    String dim = dimField.getText();
    float weight = Float.parseFloat(weightField.getText());
    String material = materialField.getText();
     int category =Integer.parseInt(categoryComboBox.getText());
    String image = imageField.getText();

       Product u = new Product(category,name, desc, dim, weight, material,image);
   boolean a = productDao.updateProduct(34, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("product updated");
            alert.showAndWait();
      
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not update product");
            alert.showAndWait();
        }
      

    }

    }
    


    

