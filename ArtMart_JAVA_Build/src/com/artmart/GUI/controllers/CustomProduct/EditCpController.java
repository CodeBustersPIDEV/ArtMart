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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private CustomproductslistController controller = new CustomproductslistController();
    private final ProductDao productDao = new ProductDao();
    @FXML
    private Label prodid;
    
    // variable to hold the ID of the custom product
    private int customProductId;
    @FXML
    private Button backBtn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void edit(ActionEvent event) throws SQLException, IOException {
        String name = nameField.getText();
        String desc = descField.getText();
        String dim = dimField.getText();
        float weight = Float.parseFloat(weightField.getText());
        String material = materialField.getText();
        int category = Integer.parseInt(categoryComboBox.getText());
        String image = imageField.getText();

        // create a new product object with the updated values
        Product u = new Product(category, name, desc, dim, weight, material, image);
        // update the product using the ID of the custom product
        boolean a = productDao.updateProduct(this.customProductId, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product updated");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops! Can not update product");
            alert.showAndWait();
        }
    }
    
    // method to set the ID of the custom product
    public void setProductId(int customProductId) {
        this.customProductId = customProductId;
        this.prodid.setText(Integer.toString(customProductId));
    }

@FXML
private void goBack(ActionEvent event) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
    Parent root = loader.load();
    CustomproductslistController controller = loader.getController();
    controller.makeList();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}


}