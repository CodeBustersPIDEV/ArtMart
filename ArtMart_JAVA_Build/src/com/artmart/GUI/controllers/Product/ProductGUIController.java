package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileClientController;
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
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");
    @FXML
    private Button goToBlogs;

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

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileClient.fxml"));
                try {
                    Parent root = loader.load();

                    ProfileClientController controller = loader.getController();
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

    @FXML
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

    @FXML
    private void goToBlogs(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/Blog.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }
        private void custom(ActionEvent event) throws IOException {
           try {
           Stage stage = (Stage) imagePreview4.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader
                        .load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
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