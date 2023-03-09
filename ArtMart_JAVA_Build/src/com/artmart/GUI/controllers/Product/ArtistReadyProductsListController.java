/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CategoriesService;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ArtistReadyProductsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private TextField search;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    private List<ReadyProduct> readyProductslist;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button profileButton;

    @FXML
    private Label profileLabel;
    @FXML
    private VBox vBoxCat;
    private List<Categories> categorieslist;
    private final CategoriesService CategoriesService = new CategoriesService();
    private CategoryCardController categoryCardController;

    @FXML
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.session = (Session) user.get(user.keySet().toArray()[0]);
            this.connectedUser = this.userService.getUser(this.session.getUserId());
            this.username.setText(this.connectedUser.getName());
            this.displayList();
            this.makeList();

            // Create a map of display names to IDs
            Map<String, String> profileActions = new HashMap<>();

            profileActions.put("", "");
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

                    profileChoiceBox.setValue("");
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileArtist.fxml"));
                    try {
                        Parent root = loader.load();
                        ProfileArtistController controller = loader.getController();
                        controller.setProfile(UserID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if ("Logout".equals(selectedItem)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayList() throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProducts(this.connectedUser.getUser_id());
        if (this.readyProductslist.isEmpty()) {
            // display a message if the list is empty
            Label emptyLabel = new Label("No products found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            // add new items to the vBox
            this.readyProductslist.forEach(rp -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ArtistReadyProductCardController controller = loader.getController();
                    controller.setReadyProduct(rp, this);
                    root.setId("" + rp.getReadyProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                } catch (SQLException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void makeList() throws SQLException {
        this.vBoxCat.getChildren().clear();
        this.categorieslist = this.CategoriesService.getAllCategories();
        if (this.categorieslist.isEmpty()) {
            // display a message if the list is empty
            Label emptyLabel = new Label("No categories found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            // add new items to the vBox
            this.categorieslist.forEach(category -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/CategoryCard.fxml"));
                    Node categoryCard = loader.load();
                    CategoryCardController controller = loader.getController();
                    controller.setCategories(category, this);
                    categoryCard.setId("" + category.getCategories_ID());
                    // Set the event handler for the category label
                    Label categoryLabel = controller.getCategoryLabel();
                    categoryLabel.setOnMouseClicked(event -> {
                        String categoryName = controller.getCategoryName();
                        try {
                            sortByCategoryName(categoryName, category.getCategories_ID());
                        } catch (SQLException ex) {
                            Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    this.vBoxCat.getChildren().add(categoryCard);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });
        }
    }

    

    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addProduct.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddReadyProduct.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void onAddCategory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddCategory.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onSearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(rP -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rP, this);
                root.setId("" + rP.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void sortByCategoryName(String categoryName, int uID) throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProductsByCategoryName(categoryName, this.connectedUser.getUser_id());
        this.readyProductslist.forEach(rp -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rp, this);
                root.setId("" + rp.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void goToProfile(String profile) {
        try {

            Stage stage = new Stage();
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

    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
