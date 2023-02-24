/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class UserCustomController implements Initializable {

   @FXML
    private ImageView img;
      private Image image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         File file = new File("src/com/artmart/GUI/controllers/CustomProduct/artmart.PNG");
       this.image = new Image(file.toURI().toString());
       this.img.setImage(image);
        // TODO
    }    



    @FXML
    private void add(ActionEvent event) {
          try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/addCustom.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
         
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void consult(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/customproductslist.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
          
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void admin(ActionEvent event) {
                  try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Custom Product.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
}
