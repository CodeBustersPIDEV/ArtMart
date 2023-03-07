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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private void exit(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }


}
