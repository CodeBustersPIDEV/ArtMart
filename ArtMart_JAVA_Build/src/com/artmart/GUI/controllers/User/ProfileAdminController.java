/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Admin;
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
public class ProfileAdminController implements Initializable {

    @FXML
    private ImageView ProfilePic;
    @FXML
    private Label usernameProfile;
    @FXML
    private Label nameProfile;
    @FXML
    private Label emailProfile;
    @FXML
    private Label departmentProfile;
    @FXML
    private Label birthdayProfile;
    @FXML
    private Button editProfileBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Label phoneProfile;
    Admin admin = new Admin();
    UserService user_ser = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        admin = user_ser.getAdmin(1);
        nameProfile.setText(admin.getName());
        usernameProfile.setText(admin.getUsername());
        emailProfile.setText(admin.getEmail());
        phoneProfile.setText(String.valueOf(admin.getPhone_nbr()));
        birthdayProfile.setText(admin.getBirthday().toString());
        departmentProfile.setText(admin.getDepartment());
        /* try {
            Image newImage = new Image(admin.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }*/
    }

    @FXML
    private void OnUpdateBtn(ActionEvent event) {
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

    @FXML
    private void OnBack(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }

}
