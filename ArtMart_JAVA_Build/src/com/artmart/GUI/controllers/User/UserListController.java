/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class UserListController implements Initializable {

    @FXML
    private VBox vbox;
     @FXML
    private TextField searchField;
      @FXML
    private Button backBtn;
    UserService user_ser = new UserService();
    User user = new User();
    private List<User> userlist;
    Session session = new Session();
    int UserID = session.getUserID("1");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userlist = this.user_ser.viewListOfUsers();
            this.makeList(userlist);
            
             searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        Search(newValue);
    });
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    public void makeList(List<User> Users) throws SQLException {
        this.vbox.getChildren().clear();  
        Users.forEach(u -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/UserCard.fxml"));
                Pane pane = loader.load();
                UserCardController controller = loader.getController();
                user = user_ser.getUser(u.getUser_id());
                String img = user.getPicture();
                String username = user.getUsername();
                String role = user.getRole();
                if (img == null) {
                    File file = new File("src/com/artmart/assets/user.jpg");
                    Image image = new Image(file.toURI().toString());
                    controller.setProfilePic(image);
                } else {
                    Image image = new Image(img);
                    controller.setProfilePic(image);
                }
                
                controller.setUserRole(role);
                controller.setUserUsername(username);
                controller.setUser(user);
                this.vbox.getChildren().add(pane);

            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }
public void Search(String searchText)
{    vbox.getChildren().clear();
 List<User> searchedUsers = userlist.stream()
            .filter(u -> u.getUsername().toLowerCase().contains(searchField.getText().toLowerCase()))
            .collect(Collectors.toList());
        try {
            this.makeList(searchedUsers);
          
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void OnBack(ActionEvent event){
   try{ Stage stage = (Stage) backBtn.getScene().getWindow();
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
}       catch (IOException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }}
}
