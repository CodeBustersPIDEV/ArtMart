/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.Session;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private Button ProfileBtn;
    
    @FXML
    private ComboBox<String> identityField;

    UserService user_ser = new UserService();
    private int test1, test2;
        Session session=new Session();
            int UserID= session.getUserID("1");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> userO = FXCollections.observableArrayList("yes", "no");
        this.identityField.setItems(userO);
    }

    @FXML
    public void OnSignUp(ActionEvent event) {
        // Get form field values
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            Date birthday = java.sql.Date.valueOf(birthdayField.getValue());
            int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
            String username = usernameField.getText();
            String password = pwdField.getText();
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()|| name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("You have to fill all the fields");
                alert.showAndWait();

            }else{
            User user = new User(phoneNumber, name, email, username, password, birthday);
            
            if (identityField.getValue().equals("yes")) {
                Artist artist = new Artist(user);
                test1 = user_ser.createAccountAr(artist);
            } else if (identityField.getValue().equals("no")) {
                Client client = new Client(user);
                test2 = user_ser.createAccountC(client);
            }
            if (test1 == 1 || test2 == 1) {
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
            }}

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An Error occured");
            alert.showAndWait();

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

    public void OnProfile(ActionEvent event) {
        User user = user_ser.getUser(UserID);
       
        if(user.getRole().equals("artist")){
        
        try { Stage stage = (Stage) ProfileBtn.getScene().getWindow();
             stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileArtist.fxml"));
            Parent root = loader.load();
            ProfileArtistController controller = loader.getController();
            controller.setProfile(UserID);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }}else if (user.getRole().equals("client")){
        try {
             Stage stage = (Stage) ProfileBtn.getScene().getWindow();
             stage.close();
             stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileClient.fxml"));
            Parent root = loader.load();
            ProfileClientController controller = loader.getController();
            controller.setProfile(UserID);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }else if(user.getRole().equals("admin")){
        try { Stage stage = (Stage) ProfileBtn.getScene().getWindow();
             stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileAdmin.fxml"));
            Parent root = loader.load();
            ProfileAdminController controller = loader.getController();
            controller.setProfile(UserID);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }}

    public void OnBack(ActionEvent event) {
        Stage stage = (Stage) sign_up_btn.getScene().getWindow();
        stage.close();
    }
}
