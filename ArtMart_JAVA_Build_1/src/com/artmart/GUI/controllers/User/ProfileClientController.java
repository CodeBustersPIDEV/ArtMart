/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Client;
import com.artmart.models.Session;
import com.artmart.models.User;
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
import javafx.scene.text.Text;
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
    private Text usernameProfile;
    @FXML
    private Label birthdayProfile;
    @FXML
    private Label nbrOrderProfile;
    @FXML
    private Label nbrCusDemProfile;
   
    @FXML
    private Button editProfileBtn;
    @FXML
    private Button logout;

    @FXML
    private ImageView ProfilePic;

    UserService user_ser = new UserService();
    private Client client = new Client();
    private Session session = new Session();
    int UserID = session.getUserID("1");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setProfile(int id) {

        User test = user_ser.getUser(UserID);
        if (test.getRole().equals("admin")) {
            logout.setVisible(false);
        }
        client = user_ser.getClient(id);
        System.out.println(client);
        nameProfile.setText(client.getName());
        usernameProfile.setText(client.getUsername());
        emailProfile.setText(client.getEmail());
        nbrOrderProfile.setText(String.valueOf(client.getNbr_orders()));
        phoneProfile.setText(String.valueOf(client.getPhone_nbr()));
        nbrCusDemProfile.setText(String.valueOf(client.getNbr_cus_demands()));
        birthdayProfile.setText(client.getBirthday().toString());
        System.out.println(client.getPicture());
        try {
            Image newImage = new Image(client.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
    }

    @FXML
    public void OnLogOut(ActionEvent event) {
        session.logOut("1");
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        try {

            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
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
    public void OnEditProfile(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Parent root = loader.load();
            UpdateProfileController controller = loader.getController();
            controller.setUpdate(client.getUser_id());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
