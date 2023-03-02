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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class VerificationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField verificationField;
    @FXML
    private Label msg;
    @FXML
    private Button submitBtn;
     @FXML
    private Button backBtn;
    UserService user_ser = new UserService();
    String vEmail;
    User user = new User();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setEmail(String email) {
        this.vEmail = email;
        System.out.println(email);
    }

    public void OnSubmit(ActionEvent event) {
        String token = verificationField.getText();
          int UserId=user_ser.getUserIdByEmail(vEmail);
          user=user_ser.getUser(UserId);
          
        if (token.isEmpty()) {
            msg.setText("You must input your token");
        } else {
            String retrievedToken = user_ser.verifyToken(vEmail);
            if (retrievedToken.equals(token)) {
                if(!user.isEnabled()== true ){
                user_ser.enableUser(vEmail);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Your account has been activated");
                alert.showAndWait();

                try {
                    Stage stage = (Stage) submitBtn.getScene().getWindow();
                    stage.close();
                    stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setTitle("User Managment");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }}else{
                   try {
                    Stage stage = (Stage) submitBtn.getScene().getWindow();
                    stage.close();
                    stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/resetPwd.fxml"));
                    Parent root = loader.load();
                    ResetPwdController controller = loader.getController();
                    controller.setId(UserId);
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                } 
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid token");
                alert.showAndWait();

            }
        }

    }

    public void OnResend(ActionEvent event) {
        String token = UUID.randomUUID().toString();
        int userID=user_ser.getUserIdByEmail(vEmail);
        user=user_ser.getUser(userID);
        user_ser.StoreToken(token, vEmail);
        System.out.println("test begin");
        try {
            user_ser.sendEmail(vEmail, "Account Verification", user_ser.generateVerificationEmail(user.getUsername(),token));
        } catch (MessagingException ex) {
            Logger.getLogger(VerificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("test end");
    }
    public void OnBack(ActionEvent event) {
        
                try {
                    Stage stage = (Stage) backBtn.getScene().getWindow();
                    stage.close();
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
}
