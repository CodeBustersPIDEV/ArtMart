package com.artmart.GUI.controllers;

import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.dao.UserDao;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class MainViewController implements Initializable {

    @FXML
    private Button productBtn;
    @FXML
    private Button userBtn;
    @FXML
    private Button eventBtn;
    @FXML
    private Button orderBtn;
    @FXML
    private Button cProductBtn;
    @FXML
    private Button blogBtn;

    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    int UserID = session.getUserID("1");
    UserService user_ser = new UserService();
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private Label username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        connectedUser = user_ser.getUser(UserID);
        username.setText(connectedUser.getUsername());
        Map<String, String> profileActions = new HashMap<>();
        profileActions.put("", "");
        profileActions.put("Logout", "logout");
        profileActions.put("Profile", "profile");
        profileChoiceBox.getItems().addAll(profileActions.keySet());
        profileChoiceBox.setOnAction(event -> {
            String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
            String selectedId = profileActions.get(selectedItem);
            if ("profile".equals(selectedId)) {
                profileChoiceBox.setValue("");
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileAdmin.fxml"));
                try {
                    Parent root = loader.load();

                    ProfileAdminController controller = loader.getController();
                    controller.setProfile(UserID);
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("logout".equals(selectedId)) {
                session.logOut("1");
                Stage stage = (Stage) profileChoiceBox.getScene().getWindow();
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
        });
    }

    @FXML
    private void goToOrder(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderManagment.fxml"));
            Parent root = loader.load();
            stage.setResizable(false);
            stage.setTitle("Artmart");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void goToUser(ActionEvent event) {
        try {
            Stage stage = (Stage) userBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/userList.fxml"));
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
    private void goToProduct(ActionEvent event) {
        if (this.connectedUser.getRole().equals("artist")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
        if (this.connectedUser.getRole().equals("admin")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AdminBoard.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
        if (this.connectedUser.getRole().equals("client")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    @FXML
    private void goToEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) userBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/home_artist.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Event Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void goToCustomProduct(ActionEvent event) {
        if (this.connectedUser.getRole().equals("admin")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/AdminBoard.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Custom Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
        if (this.connectedUser.getRole().equals("client")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Custom Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
        if (this.connectedUser.getRole().equals("artist")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCustom.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Custom Product Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    @FXML
    private void goToBlog(ActionEvent event) {
        if (this.connectedUser.getRole().equals("admin")) {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Blog Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        } else {
            try {
                Stage stage = (Stage) userBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/Blog.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Blog Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }
    }

}
