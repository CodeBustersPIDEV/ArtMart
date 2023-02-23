/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Session;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameLoginField;
    @FXML
    private TextField pwdLoginField;
    @FXML
    private Label loginMsg;
    @FXML
    private Button LoginBtn;
    UserService user_ser = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnSignUp(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/SignUp.fxml"));
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
    private void OnSignIn(ActionEvent event) {
        String username = usernameLoginField.getText();
        String password = pwdLoginField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            boolean a = user_ser.authenticate(username, password);
          
                            loginMsg.setText("User found");

            if (a) {
                Session session = Session.getInstance();
                session.setUserId(user_ser.getUserIdByUsername(username));
                session.setUsername(username);
                session.setSessionId("1");
                session.logIn(session.getSessionId(),session);
                
                 try {
                     Stage stage = (Stage) LoginBtn.getScene().getWindow();
             stage.close();
             stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Mainview.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("User Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
                

            } else {
                loginMsg.setText("User not found");

            }

        } else if (!username.isEmpty() && password.isEmpty()) {
            loginMsg.setText("Please provide a password to login");

        } else if (username.isEmpty() && !password.isEmpty()) {
            loginMsg.setText("Please provide a username to login");

        } else {
            loginMsg.setText("Please provide a username and a password to login");
        }

    }
}
