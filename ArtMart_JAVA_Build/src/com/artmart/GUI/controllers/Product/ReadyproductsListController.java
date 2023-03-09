/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ReadyproductsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private TextField search;
    @FXML
    private RadioButton sortCat;
    @FXML
    private RadioButton sortPrice;
    @FXML
    private Button searchBtn;
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button backBtn;
    private List<ReadyProduct> readyProductslist;

    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");
    @FXML
    private Button refresh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.session = (Session) user.get(user.keySet().toArray()[0]);
            this.connectedUser = this.userService.getUser(this.session.getUserId());
            this.username.setText(this.connectedUser.getName());
            this.displayList();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayList() throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProducts();
        if (this.readyProductslist.isEmpty()) {
            // display a message if the list is empty
            Label emptyLabel = new Label("No products found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            // add new items to the vBox
            this.readyProductslist.forEach(rp -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ReadyProductCardController controller = loader.getController();
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

    @FXML

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
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
    private void onSearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(rP -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                Parent root = loader.load();
                ReadyProductCardController controller = loader.getController();
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

    @FXML
    private void onSortByPrice(ActionEvent event) {
        this.sortCat.setSelected(false);
        this.sortPrice.setSelected(true);
        this.vBox.getChildren().clear();
        this.readyProductslist.sort(Comparator.comparing(ReadyProduct::getPrice));
        if (this.readyProductslist.isEmpty()) {
            // display a message if the list is empty
            Label emptyLabel = new Label("No products found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            // add new items to the vBox
            this.readyProductslist.forEach(RProduct -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ReadyProductCardController controller = loader.getController();
                    controller.setReadyProduct(RProduct, this);
                    root.setId("" + RProduct.getReadyProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                } catch (SQLException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @FXML
    private void onSortByCategory(ActionEvent event) {
        this.sortCat.setSelected(true);
        this.sortPrice.setSelected(false);
        this.vBox.getChildren().clear();
        this.readyProductslist.sort(Comparator.comparing(ReadyProduct::getCategoryId));
        if (this.readyProductslist.isEmpty()) {
            // display a message if the list is empty
            Label emptyLabel = new Label("No products found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            // add new items to the vBox
            this.readyProductslist.forEach(RProduct -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ReadyProductCardController controller = loader.getController();
                    controller.setReadyProduct(RProduct, this);
                    root.setId("" + RProduct.getReadyProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                } catch (SQLException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @FXML

    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));
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
