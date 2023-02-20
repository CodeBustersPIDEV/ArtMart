/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Client;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class ProfileClientController implements Initializable {

    @FXML
    private Label nameProfile;
    @FXML
    private Label emailProfile;
    @FXML
    private Label phoneProfile;
    @FXML
    private Label usernameProfile;
    @FXML
    private Label birthdayProfile;
    @FXML
    private Label nbrOrderProfile;
    @FXML
    private Label nbrCusDemProfile;
    @FXML
    private Button backBtn;
    @FXML
    private ImageView ProfilePic;
    
    UserService user_ser = new UserService();
    private Client client = new Client();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
               client=user_ser.getClient(6);
               nameProfile.setText(client.getName());
               usernameProfile.setText(client.getUsername());
               emailProfile.setText(client.getEmail());
               nbrOrderProfile.setText(String.valueOf(client.getNbr_orders()));
              phoneProfile.setText(String.valueOf(client.getPhone_nbr()));
               nbrCusDemProfile.setText(String.valueOf(client.getNbr_cus_demands()));
               birthdayProfile.setText(client.getBirthday().toString()); 
                      System.out.println(client.getPicture());
                       try{ 
           Image newImage = new Image(client.getPicture());
        ProfilePic.setImage(newImage);
         } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }


    }
    @FXML
    public void OnBack(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }
        @FXML
    public void OnEditProfile(ActionEvent event) {
      try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("User Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
