/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Comment;
import com.artmart.services.BlogService;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class CommentCardController implements Initializable {

    @FXML
    private MenuButton iconBtn;
    @FXML
    private Label username;
    @FXML
    private Label date;
    @FXML
    private Label comment_content;
    @FXML
    private Label commentID;
    @FXML
    private MenuItem deleteBtn;
    @FXML
    private MenuItem editBtn;
    private ComboBox<Integer> rating;
    HBox hBox = new HBox();

    private final BlogService blogService = new BlogService();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private Comment viewComment = new Comment();
    private TextArea editComment_content = new TextArea();
    int blog_id;
    private int id;
    private double blogRating;
    BlogPageController controller;

    /**
     * Initializes the controller class.
     */
    public void setController(BlogPageController controller) {
        this.controller = controller;
    }

    public void setAuthorVisibility() {
        this.iconBtn.setVisible(true);
    }

    public void setCommentContent(String content) {
        this.comment_content.setText(content);
    }

    public void setPostDate(String date) {
        this.date.setText(date);
    }

    public void setBlogID(int id) {
        blog_id = id;
    }

    public void setUsername(String user_name) {
        this.username.setText(user_name);
    }

    public void setCommentID(String comment_id) {
        this.commentID.setText(comment_id);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void calculateRating(int idBlog) {
        this.blogRating = this.blogService.calculateRating(idBlog);
        this.blogService.updateBlogRating(idBlog, this.blogRating);
        this.controller.getRatingLabel().setText(String.valueOf(df.format(this.blogRating)));

    }

    @FXML
    private void deleteComment(ActionEvent event) {
        this.id = Integer.parseInt(this.commentID.getText());
        blogService.deleteComment(id);
        this.controller.refresh(blog_id);
        calculateRating(blog_id);
    }

    @FXML
    private void editComment(ActionEvent event) {
        this.id = Integer.parseInt(this.commentID.getText());
        this.viewComment = blogService.getOneComment(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Your Comment");
        hBox.setSpacing(10);
        this.editComment_content.setText(this.viewComment.getContent());
        hBox.getChildren().addAll(this.editComment_content, this.rating);
        alert.setGraphic(hBox);
        Comment editedComment = new Comment();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            editedComment.setContent(editComment_content.getText());
            editedComment.setRating(this.rating.getSelectionModel().getSelectedItem());
            this.blogService.updateComment(id, editedComment);
            this.controller.refresh(blog_id);
            calculateRating(blog_id);
        }
    }

    public void setRating(ComboBox<Integer> rating) {
        this.rating = rating;
    }

}
