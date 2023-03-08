/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogCardController implements Initializable {

    @FXML
    private Label blog_title;
    @FXML
    private Button view_btn;
    @FXML
    private Label username;
    @FXML
    private Label publishDate;
    @FXML
    private Label blog_id;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Pane cardContainer;
    @FXML
    private Rectangle cont2;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label viewsLabel;
    private Image image;
    double h;
    private User connectedUser = new User();
    private final BlogService blogService = new BlogService();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        if (this.imagePreview.getImage() != null) {
//            h = this.imagePreview.getImage().getHeight();
//        }
//        System.out.println(image);
//        this.imagePreview.setFitWidth(this.image.getWidth());
//        this.imagePreview.setFitHeight(this.image.getHeight());

//        this.imagePreview.fitWidthProperty().bind(cardContainer.widthProperty());
//        this.cardContainer.setMaxHeight(this.imagePreview.getFitHeight());
//        this.cont2.setHeight(this.imagePreview.getFitHeight());
    }

    public void setBlogTitle(String title) {
        this.blog_title.setText(title);
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    public void setPublishDate(String date) {
        this.publishDate.setText(date);
    }

    public void setUsername(String user_name) {
        this.username.setText(user_name);
    }

    public void setBlogID(String blog_id) {
        this.blog_id.setText(blog_id);
    }

    public void setBlogImage(Image blog_image) {
        this.imagePreview.setImage(blog_image);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setRatingLabel(String rating) {
        this.ratingLabel.setText(rating);
    }

    public void setViewsLabel(String nb_views) {
        this.viewsLabel.setText(nb_views);
    }
    
    

    @FXML
    private void viewBlog(ActionEvent event) {
        this.blogService.updateBlogViews(Integer.parseInt(this.blog_id.getText()));
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            BlogPageController controller = loader.getController();
            controller.setUpData(this.blog_id.getText());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
