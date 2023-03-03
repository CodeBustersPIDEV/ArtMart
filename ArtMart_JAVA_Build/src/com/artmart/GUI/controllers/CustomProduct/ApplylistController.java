package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ApplylistController implements Initializable {
    @FXML
    private VBox vBox;

    private ApplyDao applyDao;

      @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyDao = new ApplyDao();
        displayApplies();
    }

    public void displayApplies() {
        try {
            List<Apply> applies = applyDao.getAllApplies();
            for (Apply apply : applies) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ApplyCardClient.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ApplyCardClientController applyCardClientController = fxmlLoader.getController();
                applyCardClientController.setApply(apply);
                vBox.getChildren().add(anchorPane);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exit() {
        Label label = new Label("Exit button clicked!");
        vBox.getChildren().add(label);
    }


}
