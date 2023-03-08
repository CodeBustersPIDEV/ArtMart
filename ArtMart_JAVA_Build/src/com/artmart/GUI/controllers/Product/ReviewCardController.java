/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
import com.artmart.models.User;
import com.artmart.services.ProductService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class ReviewCardController implements Initializable {

    @FXML
    private Label pid;
    @FXML
    private Label rid;
    @FXML
    private Text title;
    @FXML
    private Label text;
    @FXML
    private Text date;
    @FXML
    private Text username;
    @FXML
    private Text rating;

    private ProductReview p = new ProductReview();

    private ReadyProductDao rPDao = new ReadyProductDao();
    private final ReadyProductService readyProductService = new ReadyProductService();

    private ReviewsListController controller = new ReviewsListController();

    private ProductReview viewReview = new ProductReview();
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public List<ProductReview> setProductReview(int prodId) {
        try {
            this.pid.setText(prodId + "");
            List<ProductReview> listReviews = this.readyProductService.getAllProductReviewsByProdId(prodId);
            return listReviews;

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to get data from database");
            alert.showAndWait();
        }
        return null;
    }
}
