/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class UserCardController implements Initializable {

    @FXML
    private Label usernameField;
    @FXML
    private Label roleField;
    @FXML
    private ImageView profilePic;
    UserService user_ser = new UserService();
    User user = new User();
boolean test1,test2,test3;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUserUsername(String username) {
        this.usernameField.setText(username);
    }

    public void setUserRole(String role) {
        this.roleField.setText(role);
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic.setImage(profilePic);
    }

    public void setUser(User u) {
        this.user = u;
    }

    public void OnClientProfile(ActionEvent event) {
        try {
            Stage stage = new Stage();
            if (user.getRole().equals("client")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileClient.fxml"));
                Parent root = loader.load();
                ProfileClientController controller = loader.getController();
                controller.setProfile(this.user.getUser_id());
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } else if (user.getRole().equals("artist")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileArtist.fxml"));
                Parent root = loader.load();
                ProfileArtistController controller = loader.getController();
                controller.setProfile(this.user.getUser_id());
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileAdmin.fxml"));
                Parent root = loader.load();
                ProfileAdminController controller = loader.getController();
                controller.setProfile(this.user.getUser_id());
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
public void OnDeleteProfile()
{
            if (user.getRole().equals("artist")) {
            test1 = user_ser.deleteAccountAr(user.getUser_id());
        } else if (user.getRole().equals("client")) {
            test2 = user_ser.deleteAccountC(user.getUser_id());
        }else
        {
            test3=user_ser.deleteAccountA(user.getUser_id());
        }
        if (test1 || test2||test3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Account deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not delete account");
            alert.showAndWait();
        }
}
}
