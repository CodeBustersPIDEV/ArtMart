/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.ProductDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Session;
import com.artmart.models.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class ProductGUIController implements Initializable {

    ReadyProductDao cc = new ReadyProductDao();
    ProductDao x = new ProductDao();

    @FXML
    private ImageView imagePreview1;
    @FXML
    private ImageView imagePreview2;
    @FXML
    private ImageView imagePreview3;
    @FXML
    private ImageView imagePreview4;
    @FXML
    private Button consultAllReadyProducts;
    @FXML
    private Button backBtn;

    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button profileButton;

    @FXML
    private Label profileLabel;
    @FXML
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());

        List<String> imageUrls = null;
        try {
            imageUrls = cc.getRandomProductImages(4);
        } catch (SQLException ex) {
            Logger.getLogger(ProductGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (imageUrls.size() >= 1) {
            setProductImage(imagePreview1, imageUrls.get(0));
        }
        if (imageUrls.size() >= 2) {
            setProductImage(imagePreview2, imageUrls.get(1));
        }
        if (imageUrls.size() >= 3) {
            setProductImage(imagePreview3, imageUrls.get(2));
        }
        if (imageUrls.size() >= 4) {
            setProductImage(imagePreview4, imageUrls.get(3));
        }
        // Create a map of display names to IDs
        Map<String, String> profileActions = new HashMap<>();
        profileActions.put("Logout", "logout");
        profileActions.put("Profile", "profile");

        // Populate the choice box with display names
        profileChoiceBox.getItems().addAll(profileActions.keySet());

        // Add an event listener to handle the selected item's ID
        profileChoiceBox.setOnAction(event -> {
            String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
            String selectedId = profileActions.get(selectedItem);
            // Handle the action based on the selected ID
            if ("profile".equals(selectedId)) {
                profile.goToProfile(event, "ProfileClient");
            } else if ("logout".equals(selectedId)) {
                session.logOut("1");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void setProductImage(ImageView imageView, String imageUrl) {
        try {
            Image productImage = new Image(imageUrl);
            imageView.setImage(productImage);
        } catch (IllegalArgumentException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    public void consultAllReadyProducts(ActionEvent event) {
        try {
            Stage stage = (Stage) consultAllReadyProducts.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Ready Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
