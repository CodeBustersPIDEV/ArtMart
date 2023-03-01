/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import static com.artmart.dao.UserDao.hashPassword;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class ResetPwdController implements Initializable {

    @FXML
    private PasswordField pwdField;
    @FXML
    private PasswordField cpwdField;
    @FXML
    private Button backBtn;
    @FXML
    private Button submitBtn;
    UserService user_ser = new UserService();
    User user = new User();
    int UserID;
    boolean a;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setId(int id) {
                
 UserID = id;
       user = user_ser.getUser(UserID);
    }
    @FXML
    public void OnResetPwd(ActionEvent event) {
      String name = user.getName();
      String email = user.getEmail();
        Date birthday = user.getBirthday();
        int phoneNumber = user.getPhone_nbr();
        String username = user.getUsername();
        String password = pwdField.getText();
        String Picture=user.getPicture();
        String confirmPassword = cpwdField.getText();
        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The passwords must be identical");
            alert.showAndWait();
        } else {

            System.out.println(password);
            User u = new User(phoneNumber, name, email, username, hashPassword(password), birthday, Picture);
            a = user_ser.updateAccountU(UserID, u);

            if (a) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Password reseted");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Oops!!Can not reset password");
                alert.showAndWait();
              
            }
        }
    }

    @FXML
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
        } catch (IOException ex) {
            Logger.getLogger(ProfileClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
