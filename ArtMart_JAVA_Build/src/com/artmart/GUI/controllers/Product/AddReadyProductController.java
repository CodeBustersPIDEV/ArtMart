/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.CategoriesDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.services.ReadyProductService;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AddReadyProductController implements Initializable {

    @FXML
    private TextField nameF;
    @FXML
    private TextArea descriptionF;
    @FXML
    private TextField dimensionsF;
    @FXML
    private TextField weightF;
    @FXML
    private TextField priceF;
    @FXML
    private TextField materialF;
    @FXML
    private ComboBox<Categories> categoryF;
    @FXML
    private TextField imageField;
    @FXML
    private Button add;
    private final CategoriesDao categoriesDao = new CategoriesDao();
    @FXML
    private Button backBtn;
    @FXML
    private Button uploadImage;

    ReadyProductService readyProductService = new ReadyProductService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Get all categories from the database
            List<Categories> categories = categoriesDao.getAllCategories();

            // Add categories to the ComboBox
            categoryF.getItems().addAll(categories);

            // Set the ComboBox to display category names
            categoryF.setCellFactory(new Callback<ListView<Categories>, ListCell<Categories>>() {
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
            categoryF.setConverter(new StringConverter<Categories>() {
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
    private void onAddNew(ActionEvent event) throws SQLException, IOException {
        String name = nameF.getText();
        String description = descriptionF.getText();
        String dimensions = dimensionsF.getText();
        float weight = Float.parseFloat(weightF.getText());
        String material = materialF.getText();
        String imagePath = imageField.getText();
        float price = Float.parseFloat(priceF.getText());

        // Get the selected category from the combo box
        Categories selectedCategory = (Categories) categoryF.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Please select a category!");
            alert.showAndWait();
            return;
        }

        Product basePr = new Product(selectedCategory.getCategories_ID(), name, description, dimensions, weight, material, imagePath);

        ReadyProduct readyPr = new ReadyProduct(basePr);
        int result = readyProductService.createReadyProduct(readyPr);
        if (result > 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Ready product has been added successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add ready product!");
            alert.showAndWait();
        }
    }

    @FXML
    private void onUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(uploadImage.getScene().getWindow());
        if (selectedFile != null) {
            imageField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
