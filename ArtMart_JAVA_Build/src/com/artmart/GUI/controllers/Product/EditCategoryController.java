/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.CustomProduct.CategorieslistController;
import com.artmart.dao.CategoriesDao;
import com.artmart.models.Categories;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class EditCategoryController implements Initializable {

    private int categoryid;
    @FXML
    private TextField name;
    private final CategoriesDao categoriesDao = new CategoriesDao();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void update(ActionEvent event) throws SQLException {
        String x = name.getText();
        if (x.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Category");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a category name");
            alert.showAndWait();
            return;
        }
        Categories u = new Categories(x);
        boolean a = categoriesDao.updateCategories(this.categoryid, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("category updated"); // display the name of the category in the message
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops! Can not update category");
            alert.showAndWait();
        }
    }
    private Categories Categories;

    public void setCategoriesID(int categoryid) throws SQLException {
        this.categoryid = categoryid;
        // Fetch the product from the database using the product ID
        this.Categories = categoriesDao.getCategoriesById(categoryid);
    }

    @FXML
    private void back(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/AdminBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
