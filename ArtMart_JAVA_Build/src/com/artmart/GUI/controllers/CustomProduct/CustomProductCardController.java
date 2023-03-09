/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.GUI.controllers.Blog.EditBlogController;
import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.dao.CategoriesDao;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.artmart.dao.CustomProductDao;
import com.artmart.models.CustomProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class CustomProductCardController implements Initializable {

    @FXML
    private Text pid;
    @FXML
    private Text nameTxt;
    @FXML
    private Text descTxt;
    @FXML
    private Text WaightTxt;
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    private Session session = new Session();
    int UserID = session.getUserID("1");
    UserService user_ser = new UserService();
    @FXML
    private Label username;
    private CustomProduct p = new CustomProduct();
    private CategoriesDao s = new CategoriesDao();
    private CustomProductDao cPDao = new CustomProductDao();

    private CustomproductslistController controller = new CustomproductslistController();
    @FXML
    private Button deleteBtn;
    @FXML
    private Text CID;
    @FXML
    private Text dim;
    @FXML
    private Text mat;

    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User connectedUser = user_ser.getUser(UserID);
        username.setText(connectedUser.getUsername());
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

    public void setCustomProduct(CustomProduct param, CustomproductslistController controller) throws SQLException {
        this.p = param;
        this.controller = controller;
        this.pid.setText(Integer.toString(p.getProductId()));
        // Retrieve the category name from the database using the CategoryDao
        String categoryName = s.getCategoryNameById(p.getCategoryId());
        this.CID.setText(categoryName);
        this.dim.setText(p.getDimensions());
        this.mat.setText(p.getMaterial());

        // Load the image from the file path stored in CustomProduct object's image field
        Image image = new Image("file:" + p.getImage());
        this.img.setImage(image);

        this.nameTxt.setText(p.getName());
        this.descTxt.setText(p.getDescription());
        this.WaightTxt.setText("" + p.getWeight());
    }

    @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.cPDao.deleteCustomProduct(this.p.getCustomProductId());
        this.controller.makeList();
        this.controller.calculateTotalWeight();
        this.controller.calculateProduct();
    }
//    public void setProductId(int pid) {
//        this.pid.setText(Integer.toString(pid));
//    }
//
//    

    @FXML
    private void goupdate(ActionEvent event) throws SQLException {
        try {
            // create a new instance of the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            // set the location of the FXML file for the EditCpController
            loader.setLocation(getClass().getResource("/com/artmart/GUI/views/CustomProduct/EditCp.fxml"));
            // load the FXML file and get the root node of the scene graph
            Parent root = loader.load();
            EditCpController editController = loader.getController();
            editController.setUpData(this.pid.getText());
            editController.setProductId(this.p.getProductId());
            Scene scene = new Scene(root);
            // create a new stage and set the scene
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Custom Product");
            // close the current window
            Stage currentStage = (Stage) deleteBtn.getScene().getWindow();
            currentStage.close();
            // show the new window
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
