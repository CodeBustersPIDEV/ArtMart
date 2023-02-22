/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.BlogCategories;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class EditBlogCategoryController implements Initializable {

    @FXML
    private TextField cat_nameTextField;
    @FXML
    private Button editCategory;
    @FXML
    private Button goBack;
    @FXML
    private Label catID;
    private int id;
    private final BlogService blogService = new BlogService();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUpData(String c_ID) {
        this.catID.setText(c_ID);
        this.id = Integer.parseInt(this.catID.getText());
        BlogCategories c = this.blogService.getOneBlogCategory(id);
        this.cat_nameTextField.setText(c.getName());

    }

    @FXML
    private void edit(ActionEvent event) {
        BlogCategories c_edited = new BlogCategories(this.cat_nameTextField.getText());
        boolean test = this.blogService.updateBlogCategory(id, c_edited);
        if (test) {
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCategoryManagement.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

    }

    @FXML
    private void goBackToMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCategoryManagement.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
