/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.GUI.controllers.CustomProduct.CustomProductCardController;
import com.artmart.models.Media;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class UserListController implements Initializable {

    @FXML
    private VBox vbox;
    UserService user_ser = new UserService();
    User user = new User();
    private List<User> userlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.makeList();
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeList() throws SQLException {
        this.vbox.getChildren().clear();
        this.userlist = this.user_ser.viewListOfUsers();
        this.userlist.forEach(u -> {
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

}
