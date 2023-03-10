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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ApplyalllistController implements Initializable {

    @FXML
    private VBox vBox;

  private ApplyDao applyDao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyDao = new ApplyDao();
        displayApplies();
    }    
 private void displayApplies() {
        try {
            List<Apply> applies = applyDao.getAllApplies();
            for (Apply apply : applies) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyall.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
               ApplyallController applyCardArtistController = fxmlLoader.getController();
                applyCardArtistController.setApply(apply);
                vBox.getChildren().add(anchorPane);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
}
