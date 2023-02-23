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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Button userListBtn;
    @FXML
    private Button editProfileBtn;
    @FXML
    private Button addAdBtn;
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

    }

    public void setProfile(int id) {
        admin = user_ser.getAdmin(id);
        nameProfile.setText(admin.getName());
        usernameProfile.setText(admin.getUsername());
        emailProfile.setText(admin.getEmail());
        phoneProfile.setText(String.valueOf(admin.getPhone_nbr()));
        birthdayProfile.setText(admin.getBirthday().toString());
        departmentProfile.setText(admin.getDepartment());
        try {
            Image newImage = new Image(admin.getPicture());
            System.out.println(admin.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
    }

    @FXML
    private void OnUpdateBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) editProfileBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Parent root = loader.load();
            UpdateProfileController controller = loader.getController();
            controller.setUpdate(admin.getUser_id());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void OnUserListBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) userListBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/UserList.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML

    public void OnBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/SignUp.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("User Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProfileClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML

    private void OnAddBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) addAdBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/AddAdmin.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
