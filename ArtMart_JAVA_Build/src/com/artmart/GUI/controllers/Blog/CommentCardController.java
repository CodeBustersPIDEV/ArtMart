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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class CommentCardController implements Initializable {

    @FXML
    private ImageView iconBtn;
    @FXML
    private Label username;
    @FXML
    private Label date;
    @FXML
    private Label comment_content;
    @FXML
    private Label commentID;
    @FXML
    private Pane btnPane;
    @FXML
    private MenuButton operations;
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

    MenuItem menuItem1;
    MenuItem menuItem2;

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
        this.connectedUser = this.userService.getUser(this.session.getUserId());
//        if (this.connectedUser.getRole().equals("Admin") || this.connectedUser.getUsername().equals(this.username.getText())) {
        File file = new File("src/com/artmart/assets/BlogAssets/menu.png");
        Image image = new Image(file.toURI().toString());
        this.iconBtn.setImage(image);
        menuItem1 = new MenuItem("Edit");
        menuItem1.setId(commentID.getText());
        TextArea editComment_content = new TextArea();
        editComment_content.setWrapText(true);
        editComment_content.setLayoutX(125);
        editComment_content.setLayoutY(75);
        menuItem1.setOnAction(e -> {
            menuItem1.setId(commentID.getText());
            int id = Integer.parseInt(menuItem1.getId());
            this.viewComment = blogService.getOneComment(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit Your Comment");
            alert.setHeaderText(null);
            alert.setWidth(125);
            alert.setGraphic(editComment_content);
            editComment_content.setText(this.viewComment.getContent());
            Comment editedComment = new Comment();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                editedComment.setContent(editComment_content.getText());
                this.isEdited = this.blogService.updateComment(id, editedComment);
            }

        });
        menuItem2 = new MenuItem("Delete");
        menuItem2.setOnAction(e -> {
            this.isEdited = true;
            menuItem2.setId(commentID.getText());
            int id = Integer.parseInt(menuItem2.getId());
            this.isDeleted = blogService.deleteComment(id);
            if (this.isDeleted) {
                System.out.println(this.isDeleted);
            }
        });

//        this.operations.getItems().addAll(menuItem1.getText(), menuItem2.getText());
        this.operations.getItems().addAll(menuItem1, menuItem2);

        this.iconBtn.setOnMouseClicked(e -> {
            this.operations.show();
        });
//        }
    }

    public void openCommentCardWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error opening Comment Card window: " + ex.getMessage());
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        openCommentCardWindow();
    }

}
