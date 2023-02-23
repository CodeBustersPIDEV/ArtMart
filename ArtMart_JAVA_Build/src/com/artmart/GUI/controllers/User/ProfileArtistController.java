/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Artist;
import com.artmart.models.User;
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
public class ProfileArtistController implements Initializable {

    @FXML
    private ImageView ProfilePic;
    @FXML
    private Button UploadPicBtn;
    @FXML
    private Label usernameProfile;
    @FXML
    private Label nameProfile;
    @FXML
    private Label emailProfile;
    @FXML
    private Label nbrArtProfile;
    @FXML
    private Label bioProfile;
    @FXML
    private Label birthdayProfile;
    @FXML
    private Button editProfileBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Label phoneProfile;
   
    private Artist artist = new Artist();
    UserService user_ser = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
 public void setProfile(int id) {
         artist = user_ser.getArtist(id);
        nameProfile.setText(artist.getName());
        usernameProfile.setText(artist.getUsername());
        emailProfile.setText(artist.getEmail());
        bioProfile.setText(artist.getBio());
        phoneProfile.setText(String.valueOf(artist.getPhone_nbr()));
        nbrArtProfile.setText(String.valueOf(artist.getNbr_artwork()));
        birthdayProfile.setText(artist.getBirthday().toString());
        try {
            Image newImage = new Image(artist.getPicture());
         //   System.out.println(artist.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
 }
    @FXML
    private void OnBack(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

    @FXML

    public void OnUpdateBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) editProfileBtn.getScene().getWindow();
             stage.close();
             stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Parent root = loader.load();
            UpdateProfileController controller = loader.getController();
            controller.setUpdate(artist.getUser_id());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }}
