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
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.MessagingException;

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
    private Hyperlink log_in_btn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button ProfileBtn;

    @FXML
    private ComboBox<String> identityField;
    int VerifE;
    UserService user_ser = new UserService();
    private int test1, test2;
    Session session = new Session();
    int UserID = session.getUserID("1");
    LocalDate date = LocalDate.of(1970, 1, 1);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> userO = FXCollections.observableArrayList("yes", "no");
        this.identityField.setItems(userO);
        birthdayField.setValue(date);
    }

    @FXML
    public void OnSignUp(ActionEvent event) {
        // Get form field values

        String name = nameField.getText();
        String email = emailField.getText();
        Date birthday = java.sql.Date.valueOf(birthdayField.getValue());
        int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
        String username = usernameField.getText();
        String password = pwdField.getText();
        String confirmPassword = cpwdField.getText();
        String emailFormat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String pwdPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        LocalDate currentDate = LocalDate.now();
        VerifE = user_ser.getUserIdByEmail(email);
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || birthdayField.getValue().toString().isEmpty() || Phone_nbrField.getText().isEmpty() || identityField.getValue().isEmpty()) {
            Warning("You have to fill all the fields");
        } else {
            if (!password.equals(confirmPassword)) {
                Warning("The passwords must be identical");
            }
            if (!email.matches(emailFormat)) {
                Warning("The email must be valid");
            }
            if (!password.matches(pwdPattern)) {
                Warning("Password must contain at least one uppercase letter, one digit, and be at least 8 characters long");
            }
            if (birthdayField.getValue().isAfter(currentDate)) {
                Warning("The birthday date must not exceed today's date");

            }
            if (VerifE == 0) {
                if (password.equals(confirmPassword) && email.matches(emailFormat) && password.matches(pwdPattern) && !birthdayField.getValue().isAfter(currentDate)) {
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
                        alert.setContentText("Account created but you need to verify your account before you start");
                        alert.showAndWait();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Oops!!Can not create account");
                        alert.showAndWait();
                    }

                    nameField.setText("");
                    emailField.setText("");
                    usernameField.setText("");
                    pwdField.setText("");
                    cpwdField.setText("");
                    Phone_nbrField.setText("");
                    birthdayField.setValue(date);
                    String token = UUID.randomUUID().toString();

                    user_ser.StoreToken(token, email);
                    System.out.println("test begin");
                    try {
                        user_ser.sendEmail(email, "Account Verification", user_ser.generateVerificationEmail(username, token));
                    } catch (MessagingException ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("test end");
                    try {
                        Stage stage = (Stage) sign_up_btn.getScene().getWindow();
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
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("An Error occured");
                        alert.showAndWait();
                    }

                } else {
                    Warning("This email already exists");
                }
            }
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
    public void OnSignIn(ActionEvent event) {
        try {
            Stage stage = (Stage) log_in_btn.getScene().getWindow();
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

    public void goToProfile(ActionEvent event, String profile) {
        try {
            Stage stage = (Stage) ProfileBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/" + profile + ".fxml"));
            Parent root = loader.load();
            if (profile.equals("ProfileArtist")) {
                ProfileArtistController controller = loader.getController();
                controller.setProfile(UserID);

            } else if (profile.equals("ProfileAdmin")) {
                ProfileAdminController controller = loader.getController();
                controller.setProfile(UserID);

            } else if (profile.equals("ProfileClient")) {
                ProfileClientController controller = loader.getController();
                controller.setProfile(UserID);

            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }

    public void OnProfile(ActionEvent event) {

        User user = user_ser.getUser(UserID);
        System.out.println(user);
        if (user.getRole().equals("artist")) {
            goToProfile(event, "ProfileArtist");
        } else if (user.getRole().equals("client")) {
            goToProfile(event, "ProfileClient");

        } else if (user.getRole().equals("admin")) {
            goToProfile(event, "ProfileAdmin");
        }

    }

    public void OnBack(ActionEvent event) {
        try {
            Stage stage = (Stage) sign_up_btn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void OnLogOut(ActionEvent event) {
        session.logOut("1");
        Stage stage = (Stage) logOutBtn.getScene().getWindow();
        stage.close();
        OnSignIn(event);

    }
}
