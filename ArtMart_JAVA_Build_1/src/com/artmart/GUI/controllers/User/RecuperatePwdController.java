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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class RecuperatePwdController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private Label message;
    @FXML
    private Button backBtn;
    @FXML
    private Button submitBtn;
    UserService user_ser = new UserService();
    int recuperatedUseId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnBack(ActionEvent event) {
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
        } catch (IOException ex) {
            Logger.getLogger(ProfileClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void OnSubmit(ActionEvent event) {

        String email = emailField.getText();
        if (!email.isEmpty()) {
            recuperatedUseId = user_ser.getUserIdByEmail(email);
            User u =user_ser.getUser(recuperatedUseId);
            System.out.println(recuperatedUseId);
            if (recuperatedUseId == 0) {
                message.setText("email not found.....Try again!");
            } else {
                String token=UUID.randomUUID().toString();
                user_ser.StoreToken(token, email);
                System.out.println("test begin");
                try {
                    user_ser.sendEmail(email,"Account Verification",user_ser.generateVerificationEmail(u.getUsername(),token));
                } catch (MessagingException ex) {
                    Logger.getLogger(RecuperatePwdController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("test end");
                try {
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/Verification.fxml"));
                Pane pane = loader.load();
                VerificationController controller = loader.getController();
                controller.setEmail(email);
                 Scene scene = new Scene(pane);
            stage.setTitle("User Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
            }

        } else {
            message.setText("Please write your email");
        }

    }

}
