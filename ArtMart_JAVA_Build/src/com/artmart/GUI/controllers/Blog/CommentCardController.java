/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Comment;
import com.artmart.services.BlogService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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

    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Comment viewComment = new Comment();

    private int id;
    private boolean isEdited = false;

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

    public void setUsername(String user_name) {
        this.username.setText(user_name);
    }

    public void setCommentID(String comment_id) {
        this.commentID.setText(comment_id);
    }

    public boolean getIsEdited() {
        return this.isEdited;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            editComment_content.setText(viewComment.getContent());
            Comment editedComment = new Comment();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                editedComment.setContent(editComment_content.getText());
                this.blogService.updateComment(id, editedComment);
                refreshCommentSection();
            }

        });
        menuItem2 = new MenuItem("Delete");

//        this.operations.getItems().addAll(menuItem1.getText(), menuItem2.getText());
        this.operations.getItems().addAll(menuItem1, menuItem2);

        this.iconBtn.setOnMouseClicked(e -> {
            this.operations.show();
        });

    }

    private void refreshCommentSection() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogPage.fxml"));
        BlogPageController controller = loader.getController();

        controller.getContainer().getChildren().clear();

        controller.setupComments(Integer.parseInt(controller.getBlogID()));
    }

}
