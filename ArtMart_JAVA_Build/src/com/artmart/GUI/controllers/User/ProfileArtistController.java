/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Artist;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class ProfileArtistController implements Initializable {

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
    private Label bioProfile;
    @FXML
    private Label nbrArtProfile;
    
    @FXML
    private Button backBtn;
    
    private Artist artist = new Artist();
    UserService user_ser = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        artist=user_ser.getArtist(6);
               nameProfile.setText(artist.getName());
               usernameProfile.setText(artist.getUsername());
               emailProfile.setText(artist.getEmail());
               bioProfile.setText(artist.getBio());
              phoneProfile.setText(String.valueOf(artist.getPhone_nbr()));
               nbrArtProfile.setText(String.valueOf(artist.getNbr_artwork()));
               birthdayProfile.setText(artist.getBirthday().toString());
               //profilePic
               
    }
public void OnBack(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }
}
