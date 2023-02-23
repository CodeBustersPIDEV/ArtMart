/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Admin;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class AddAdminController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private DatePicker birthdayField;
    @FXML
    private PasswordField pwdField;
    @FXML
    private PasswordField cpwdField;
    @FXML
    private TextField Phone_nbrField;
    @FXML
    private TextField departmentField;
    @FXML
    private Button backBtn;
    UserService user_ser = new UserService();
    private int test1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML

    public void OnAddAdmin(ActionEvent event) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            Date birthday = java.sql.Date.valueOf(birthdayField.getValue());
            int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
            String username = usernameField.getText();
            String password = pwdField.getText();
            String confirmPassword = cpwdField.getText();
            String department = departmentField.getText();
            String emailFormat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || birthdayField.getValue().toString().isEmpty() || Phone_nbrField.getText().isEmpty() || department.isEmpty()) {
                Warning("You have to fill all the fields");
            } else {
                if (!password.equals(confirmPassword)) {
                    Warning("The passwords must be identical");
                } else if (!email.matches(emailFormat)) {
                    Warning("The email must be valid");
                } else {
                    User user = new User(phoneNumber, name, email, username, password, birthday);
                    Admin admin = new Admin(user);
                    admin.setDepartment(department);
                    test1 = user_ser.createAccountA(admin);
                    if (test1 == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Account created");
                        alert.showAndWait();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Oops!!Can not create account");
                        alert.showAndWait();
                    }
                }
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An Error occured");
            alert.showAndWait();

        }
    }

    public void Warning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    private void OnBack(ActionEvent event) {
       try{ Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
        stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/SignUp.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("User Managment");
            stage.setScene(scene);
            stage.show();
    }   catch (IOException ex) {
            Logger.getLogger(ProfileClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
