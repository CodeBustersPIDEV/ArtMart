/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ApplylistartistController implements Initializable {

    @FXML
    private VBox vBox;

     private ApplyDao applyDao;

      @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyDao = new ApplyDao();
        displayApplies();
    }

    private void displayApplies() {
        try {
            List<Apply> applies = applyDao.getAllApplies1();
            for (Apply apply : applies) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyCardArtist.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ApplyCardArtistController applyCardArtistController = fxmlLoader.getController();
                applyCardArtistController.setApply(apply);
                vBox.getChildren().add(anchorPane);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCustom.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    
}
