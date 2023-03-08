/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.ProductReview;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.ReadyProductService;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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

    private ProductReview viewReview = new ProductReview();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setProductReview(ProductReview review) {
        this.viewReview = review;
        this.rid.setText("" + this.viewReview.getReviewId());
        this.title.setText("" + this.viewReview.getTitle());
        this.text.setText("" + this.viewReview.getText());
        this.date.setText("" + this.viewReview.getDate());
        this.rating.setText("" + this.viewReview.getRating());

        UserDao u = new UserDao();
        int uID = this.viewReview.getUserId();
        User user = u.getUser(uID);
        String userN = user.getName();
        this.username.setText("" + userN);
    }
}
