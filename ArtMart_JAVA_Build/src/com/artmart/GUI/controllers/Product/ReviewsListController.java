/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.models.ProductReview;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
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
public class ReviewsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private Label pid;
    @FXML
    private VBox vBox;
    @FXML
    private Button backBtn;
    private List<ProductReview> productReviewsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            this.displayList();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void setProductId(int pid) {
        this.pid.setText(Integer.toString(pid));
    }

//    public void displayList() throws SQLException {
//        try {
//            this.vBox.getChildren().clear();
//            this.productReviewsList = this.readyProductService.getAllProductReviews();
//            this.productReviewsList.forEach(rp -> {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReviewCard.fxml"));
//                    Parent root = loader.load();
//                    ReviewCardController controller = loader.getController();
//                    controller.setProductReview(rp, this);
//                    root.setId("" + rp.getReviewId());
//                    this.vBox.getChildren().add(root);
//                } catch (IOException e) {
//                    System.out.print(e.getCause());
//                } catch (SQLException ex) {
//                    Logger.getLogger(ReviewsListController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));
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
