/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;
import com.artmart.dao.CategoriesDao;
import com.artmart.models.Categories;
import com.artmart.services.CustomProductService;
import com.artmart.models.Product;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
    private TextField imageField;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<Categories> categoryComboBox2;
     private final CategoriesDao categoriesDao = new CategoriesDao();
    @FXML
    private Button imageButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Get all categories from the database
            List<Categories> categories = categoriesDao.getAllCategories();

            // Add categories to the ComboBox
            categoryComboBox2.getItems().addAll(categories);

            // Set the ComboBox to display category names
            categoryComboBox2.setCellFactory(new Callback<ListView<Categories>, ListCell<Categories>>() {
                @Override
                public ListCell<Categories> call(ListView<Categories> param) {
                    return new ListCell<Categories>() {
                        @Override
                        protected void updateItem(Categories item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                            } else {
                                setText(item.getName());
                            }
                        }
                    };
                }
            });

            // Set the ComboBox to use the category name as the selected value
            categoryComboBox2.setConverter(new StringConverter<Categories>() {
                @Override
                public String toString(Categories category) {
                    return category == null ? null : category.getName();
                }

                @Override
                public Categories fromString(String categoryName) {
                    return categories.stream()
                            .filter(category -> category.getName().equals(categoryName))
                            .findFirst()
                            .orElse(null);
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

     
    @FXML
private void handleAddButtonAction(ActionEvent event) throws SQLException {
    String name = nameField.getText();
    String desc = descField.getText();
    String dim = dimField.getText();
    float weight = Float.parseFloat(weightField.getText());
    String material = materialField.getText();
  String imagePath = imageField.getText();
    
    // Get the selected category from the combo box
    Categories selectedCategory = (Categories) categoryComboBox2.getSelectionModel().getSelectedItem();

    if (selectedCategory == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Please select a category!");
        alert.showAndWait();
        return;
    }

    Product baseProduct = new Product(selectedCategory.getCategories_ID(), name, desc, dim, weight, material, imagePath);

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

    @FXML
    private void handleSelectImageAction(ActionEvent event) {
         FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Image");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());
    if (selectedFile != null) {
        imageField.setText(selectedFile.getAbsolutePath());
    }
}
    }

