package com.artmart.GUI.controllers.User;

import static com.artmart.dao.UserDao.hashPassword;
import com.artmart.models.Session;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.MessagingException;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameLoginField;
    @FXML
    private TextField pwdLoginField;
    @FXML
    private Label loginMsg;
    @FXML
    private Button LoginBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Hyperlink forgotPwdLink;

    @FXML
    private Button activateAccountBtn;
    UserService user_ser = new UserService();
    User u = new User();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        activateAccountBtn.setVisible(false);
    }

    @FXML
    private void OnSignUp(ActionEvent event) {
        try {
            Stage stage = (Stage) signUpBtn.getScene().getWindow();
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
    private void OnSignIn(ActionEvent event) {
        String username = usernameLoginField.getText();
        String password = pwdLoginField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {

            int id = user_ser.getUserIdByUsername(username);
            u = user_ser.getUser(id);

            if (u.getPwd().equals(hashPassword(password))) {
                if (u.getBlocked() == true) {
                    loginMsg.setText("This user is blocked");
                } else {
                    if (!u.isEnabled() == true) {
                        loginMsg.setText("This account is no activated");
                        activateAccountBtn.setVisible(true);
                    } else {
                        boolean a = user_ser.authenticate(username, password);
                        if (a) {
                            Session session = Session.getInstance();
                            session.setUserId(id);
                            session.setUsername(username);
                            session.setUserRole(u.getRole());
                            session.setSessionId("1");
                            String phoneNumber = user_ser.getPhoneNumberById(u.getUser_id());
                            session.setPhoneNumber(phoneNumber);
                            session.logIn(session.getSessionId(), session);
                            if (u.getRole().equals("admin")) {
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
                            } else if (u.getRole().equals("artist")) {
                                try {
                                    Stage stage = (Stage) LoginBtn.getScene().getWindow();
                                    stage.close();
                                    stage = new Stage();
                                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
                                    Scene scene = new Scene(root);
                                    stage.setResizable(false);
                                    stage.setTitle("User Managment");
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException e) {
                                    System.out.print(e.getMessage());
                                }
                            } else if (u.getRole().equals("client")) {
                                try {
                                    Stage stage = (Stage) LoginBtn.getScene().getWindow();
                                    stage.close();
                                    stage = new Stage();
                                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductsList.fxml"));
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
                    }
                }
            } else {
                loginMsg.setText("Incorrect password");
            }

        } else if (!username.isEmpty() && password.isEmpty()) {
            loginMsg.setText("Please provide a password to login");

        } else if (username.isEmpty() && !password.isEmpty()) {
            loginMsg.setText("Please provide a username to login");

        } else {
            loginMsg.setText("Please provide a username and a password to login");
        }

    }

    @FXML
    private void OnForgotPwd(ActionEvent event) {
        try {
            Stage stage = (Stage) forgotPwdLink.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/recuperatePwd.fxml"));
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
    private void OnActivateAccount(ActionEvent event) {
        String email = u.getEmail();
        String token = UUID.randomUUID().toString();

        user_ser.StoreToken(token, email);
        System.out.println("test begin");
        try {
            user_ser.sendEmail(email, "Account Verification", user_ser.generateVerificationEmail(u.getUsername(), token));
        } catch (MessagingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("test end");
        try {
            Stage stage = (Stage) activateAccountBtn.getScene().getWindow();
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
}
