package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ApplyalllistController implements Initializable {

    @FXML
    private VBox vBox;

    private ApplyDao applyDao = new ApplyDao();
    List<Apply> applies = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayApplies();
    }

    public void displayApplies() {
        try {
            applies.clear();
            vBox.getChildren().removeAll(vBox.getChildren());
            applies = applyDao.getAllApplies();
            for (Apply apply : applies) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyall.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ApplyallController applyCardArtistController = fxmlLoader.getController();
                applyCardArtistController.setApply(apply, this);
                vBox.getChildren().add(anchorPane);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
