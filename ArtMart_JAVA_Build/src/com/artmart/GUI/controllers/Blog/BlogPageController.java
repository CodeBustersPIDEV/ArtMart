/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.Media;
import com.artmart.services.BlogService;
import java.io.File;
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
import javafx.scene.control.TextArea;
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
public class BlogPageController implements Initializable {

    @FXML
    private Label blog_id;
    @FXML
    private Label blog_title;
    @FXML
    private Label username;
    @FXML
    private Label publishDate;
    @FXML
    private TextArea blog_content;
    @FXML
    private Button backBtn;
    @FXML
    private ImageView blogImage;

    private final BlogService blogService = new BlogService();
    private UserDao userService = new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    private Image image;
    private Media img = new Media();
    private Rectangle blogImageBox;

    /**
     * Initializes the controller class.
     *
     * @param b_ID
     */
    public void setUpData(String b_ID) {
        this.blog_id.setText(b_ID);
        this.id = Integer.parseInt(this.blog_id.getText());
        this.viewBlog = blogService.getOneBlog(id);
        this.img = this.blogService.getOneMediaByBlogID(id);
        if (!(img == null)) {
            File file = new File(img.getFile_path());
            this.image = new Image(file.toURI().toString());
        } else {
            File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
            this.image = new Image(file.toURI().toString());
        }

        this.blog_title.setText(this.viewBlog.getTitle());
        this.username.setText(userService.getUser(this.viewBlog.getAuthor()).getUsername());
        this.publishDate.setText(this.viewBlog.getPublishDate().toString());
        this.blog_content.setText(this.viewBlog.getContent());
        this.blogImage.setImage(this.image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void goBackToMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/Blog.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
