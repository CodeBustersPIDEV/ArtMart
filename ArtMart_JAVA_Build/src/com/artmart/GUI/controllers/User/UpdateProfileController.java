/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class UpdateProfileController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField pwdField;
    @FXML
    private TextField Phone_nbrField;
    @FXML
    private TextField bioField;
    @FXML
    private ImageView ProfilePic;
    @FXML
    private Label Label;

    @FXML
    private Button updateBtn;
    private String imageUrl="";
    UserService user_ser = new UserService();
    User user = new User();
    Artist artist = new Artist();

    boolean test2, test1, a;
    int userID;
    Admin admin = new Admin();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUpdate(int id) {
        userID = id;
        user = user_ser.getUser(userID);
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        Phone_nbrField.setText(String.valueOf(user.getPhone_nbr()));
        usernameField.setText(user.getUsername());
        birthdayField.setText(user.getBirthday().toString());
        pwdField.setText(user.getPwd());

        try {
            Image newImage = new Image(user.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
        if (user.getRole().equals("artist")) {
            bioField.setVisible(true);
            Label.setText("Bio");
            Artist art = user_ser.getArtist(user.getUser_id());
            bioField.setText(art.getBio());

        } else if (user.getRole().equals("admin")) {
            bioField.setVisible(true);
            Label.setText("Department");
            Admin ad = user_ser.getAdmin(user.getUser_id());
            bioField.setText(ad.getDepartment());
        } else {
            bioField.setVisible(false);
        }
    }

    @FXML
    public void OnUpdate(ActionEvent event) {

        String name = nameField.getText();
        String email = emailField.getText();
        Date birthday = java.sql.Date.valueOf(birthdayField.getText());
        int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
        String username = usernameField.getText();
        String password = pwdField.getText();
        String bio = bioField.getText();
        String picture ;
        if(imageUrl.equals("")){
            picture=user.getPicture();
            //out.println(picture);
        }else
        {
           picture = imageUrl;
        }
        User u = new User(phoneNumber, name, email, username, password, birthday, picture);

        if (user.getRole().equals("artist")) {
            artist = new Artist(u);
            artist.setBio(bio);
            a = user_ser.updateAccountAr(userID, artist);
        } else if (user.getRole().equals("admin")) {
            admin = new Admin(u);
            admin.setDepartment(bio);
            a = user_ser.updateAccountA(userID, admin);
        } else if (user.getRole().equals("client")) {
            //System.out.println(u.getPicture());
            Client client = new Client(u);
            a = user_ser.updateAccountC(userID, client);
        }
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Account updated");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not update account");
            alert.showAndWait();
        }
        try {
            Stage stage = (Stage) updateBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
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
    public void OnDelete(ActionEvent event) {
        if (user.getRole().equals("artist")) {
            test1 = user_ser.deleteAccountAr(user.getUser_id());
        } else if (user.getRole().equals("client")) {
            test2 = user_ser.deleteAccountC(user.getUser_id());
        }
        if (test1 || test2) {
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

    @FXML
    public void OnUpload(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                imageUrl = selectedFile.toURI().toURL().toExternalForm();

                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ProfilePic.setImage(image);
                //  user.setPicture(ProfilePic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
