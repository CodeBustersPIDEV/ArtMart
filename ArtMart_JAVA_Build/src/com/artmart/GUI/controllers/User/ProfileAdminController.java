package com.artmart.GUI.controllers.User;

import com.artmart.models.Admin;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileAdminController implements Initializable {

    @FXML
    private ImageView ProfilePic;
    @FXML
    private Text usernameProfile;
    @FXML
    private Label nameProfile;
    @FXML
    private Label emailProfile;
    @FXML
    private Label departmentProfile;
    @FXML
    private Label birthdayProfile;

    @FXML
    private Button editProfileBtn;
    @FXML
    private Button addAdBtn;

    @FXML
    private Label phoneProfile;
    @FXML
    private Button logout;

    private Session session = new Session();
    int UserID = session.getUserID("1");
    Admin admin = new Admin();
    UserService user_ser = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setProfile(int id) {

        User test = user_ser.getUser(UserID);
        if (test.getRole().equals("admin")) {
            logout.setVisible(false);
        }
        admin = user_ser.getAdmin(id);
        nameProfile.setText(admin.getName());
        usernameProfile.setText(admin.getUsername());
        emailProfile.setText(admin.getEmail());
        phoneProfile.setText(String.valueOf(admin.getPhone_nbr()));
        birthdayProfile.setText(admin.getBirthday().toString());
        departmentProfile.setText(admin.getDepartment());
        try {
            Image newImage = new Image(admin.getPicture());
            System.out.println(admin.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
    }

    @FXML
    public void OnLogOut(ActionEvent event) {
        session.logOut("1");
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        try {

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

    @FXML
    private void OnUpdateBtn(ActionEvent event) {
        try {
            Stage stageToClose = (Stage)editProfileBtn.getScene().getWindow();
            stageToClose.close();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Parent root = loader.load();
            UpdateProfileController controller = loader.getController();
            controller.setUpdate(admin.getUser_id());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void OnAddBtn(ActionEvent event) {
        try {
            Stage stage = (Stage) addAdBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/AddAdmin.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
