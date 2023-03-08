/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AddReviewController implements Initializable {

    @FXML
    private TextArea textF;
    @FXML
    private TextField titleF;
    @FXML
    private TextField ratingF;
    @FXML
    private ComboBox<ReadyProduct> productF;
    private final ReadyProductDao readyProductDao = new ReadyProductDao();
    @FXML
    private Button add;
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

    ReadyProductService readyProductService = new ReadyProductService();
    UserService user_ser = new UserService();
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
            // Get all ready products from the database
            List<ReadyProduct> products = readyProductDao.getAllReadyProducts();

            // Add ready products to the ComboBox
            productF.getItems().addAll(products);

            // Set the ComboBox to display ready products names
            productF.setCellFactory(new Callback<ListView<ReadyProduct>, ListCell<ReadyProduct>>() {
                @Override
                public ListCell<ReadyProduct> call(ListView<ReadyProduct> param) {
                    return new ListCell<ReadyProduct>() {
                        @Override
                        protected void updateItem(ReadyProduct item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                            } else {
                                setText(item.getName());
                            }
                        }
                    };
                }
            });

            // Set the ComboBox to use the ready product's name as the selected value
            productF.setConverter(new StringConverter<ReadyProduct>() {
                @Override
                public String toString(ReadyProduct prod) {
                    return prod == null ? null : prod.getName();
                }

                @Override
                public ReadyProduct fromString(String prodName) {
                    return products.stream()
                            .filter(category -> category.getName().equals(prodName))
                            .findFirst()
                            .orElse(null);
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onAddNew(ActionEvent event) throws SQLException, IOException, MessagingException, ParseException {
        String title = titleF.getText();
        String text = textF.getText();
        float rating = Float.parseFloat(ratingF.getText());
        int us = this.connectedUser.getUser_id();

        // Get the current date and time
        Date currentDate = new Date();

        // Create a date format string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the current date as a string
        String dateString = dateFormat.format(currentDate);

        // Parse the date string into a Date object
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = myDate;

        // Get the selected category from the combo box
        ReadyProduct selectedProduct = (ReadyProduct) productF.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Please select a category!");
            alert.showAndWait();
            return;
        }

        ProductReview baseRev = new ProductReview(selectedProduct.getReadyProductId(), us, title, text, rating, date);

        int result = readyProductService.createProductReview(baseRev);
        if (result > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Review has been added successfully!");
            alert.showAndWait();

//            //test email
//            String productName = nameF.getText();
//            User u = user_ser.getUser(this.session.getUserId());
//            String email = this.connectedUser.getEmail();
//            System.out.println("test begin");
//            readyProductService.sendEmail(email, "New product added", readyProductService.generateVerificationEmail(u.getName(), productName));
//            System.out.println("test reached");
            // Close the current window
            Stage stage = (Stage) add.getScene().getWindow();
            stage.close();

            // Load and display the ArtistReadyProductsList interface
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReviewsList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } else {
            // Display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add ready product!");
            alert.showAndWait();
        }

    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
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
