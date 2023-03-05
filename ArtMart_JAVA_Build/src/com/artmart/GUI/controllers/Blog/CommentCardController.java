/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Comment;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

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
    private TextArea editComment_content = new TextArea();

    int blog_id;

    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Comment viewComment = new Comment();
    private User connectedUser = new User();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private int id;
    private boolean isEdited = false;
    private boolean isDeleted = false;

    /**
     * Initializes the controller class.
     */
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

    public boolean getIsEdited() {
        return this.isEdited;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void deleteComment(ActionEvent event) {
        int id = Integer.parseInt(this.commentID.getText());
        this.isDeleted = blogService.deleteComment(id);
    }

    @FXML
    private void editComment(ActionEvent event) {
        int id = Integer.parseInt(this.commentID.getText());
        this.viewComment = blogService.getOneComment(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Your Comment");
        alert.setHeaderText(null);
        alert.setWidth(125);
        alert.setGraphic(this.editComment_content);
        this.editComment_content.setText(this.viewComment.getContent());
        Comment editedComment = new Comment();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            editedComment.setContent(editComment_content.getText());
            this.isEdited = this.blogService.updateComment(id, editedComment);
            System.out.println(this.isEdited);
        }
    }

}
