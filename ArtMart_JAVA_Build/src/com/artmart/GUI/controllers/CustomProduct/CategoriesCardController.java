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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class CategoriesCardController implements Initializable {

    @FXML
    private Text id;
    @FXML
    private Text name;
  private Categories p=new Categories();
  private CategorieslistController controller=new CategorieslistController();
   private final CategoriesService CategoriesService = new CategoriesService(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     public void setCategories(Categories param,CategorieslistController controller){
    this.p=param;
    this.controller = controller;
    this.id.setText(Integer.toString(p.getCategories_ID()));
    this.name.setText(p.getName());
}
       @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.CategoriesService.deleteCategories(this.p.getCategories_ID());
        this.controller.makeList();
    }

    @FXML
    private void onupdate(ActionEvent event) throws SQLException {
       try {
        // create a new instance of the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        // set the location of the FXML file for the EditCpController
        loader.setLocation(getClass().getResource("/com/artmart/GUI/views/CustomProduct/EditCategory.fxml"));
        // load the FXML file and get the root node of the scene graph
        Parent root = loader.load();
 EditCategoryController editController = loader.getController();
editController.setCategoriesID(this.p.getCategories_ID());
        Scene scene = new Scene(root);
        // create a new stage and set the scene
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit Custom Product");
        // close the current window
        Stage currentStage = (Stage) id.getScene().getWindow();
        currentStage.close();
        // show the new window
        stage.show();
    } catch (IOException e) {
        System.out.print(e.getMessage());
    }
    }}
