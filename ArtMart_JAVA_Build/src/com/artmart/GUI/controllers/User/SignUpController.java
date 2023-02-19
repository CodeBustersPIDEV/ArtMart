/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class SignUpController implements Initializable {

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
    private Button sign_up_btn;
    
    @FXML
    private ComboBox<String> identityField;
    private User user = new User();
    UserService user_ser = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> userO = FXCollections.observableArrayList("yes", "no");
        this.identityField.setItems(userO);
    }

    @FXML
    public void OnSignUp(ActionEvent event) {
        // Get form field values
        String name = nameField.getText();
        String email = emailField.getText();
        LocalDate birthday = birthdayField.getValue();
        int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
        String username = usernameField.getText();
        String password = pwdField.getText();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setBirthday(java.sql.Date.valueOf(birthday));
        user.setPhone_nbr(phoneNumber);
        user.setUsername(username);
      //user.setPwd(password);


        
        if (this.pwdField.equals(cpwdField)) {
            user.setPwd(pwdField.getText());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);

            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("The passwords must be identical");
            alert.show();
            
        }
        
         if (identityField.getValue().equals("yes")) {
            user.setRole("artist");
            Artist artist = new Artist(user);
            user_ser.createAccountAr(artist);
        } else  if (identityField.getValue().equals("no")) {
            user.setRole("client");
            Client client = new Client(user);
            user_ser.createAccountC(client);

        }
       
    }
    
     @FXML

    public void OnSignIn(ActionEvent event) {
         try {
            Stage stage = new Stage();
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
    
     public void OnBack(ActionEvent event) {
          Stage stage = (Stage) sign_up_btn.getScene().getWindow();
        stage.close();
    }
}
