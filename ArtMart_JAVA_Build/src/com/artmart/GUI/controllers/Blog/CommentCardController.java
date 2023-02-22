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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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

    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Comment viewComment = new Comment();

    private int id;
    @FXML
    private Pane btnPane;
    @FXML
    private ComboBox<String> operations;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.id = Integer.parseInt(this.commentID.getText());
//        this.viewComment = blogService.getOneComment(id);
        File file = new File("src/com/artmart/assets/BlogAssets/menu.png");
        Image image = new Image(file.toURI().toString());
        this.iconBtn.setImage(image);
        MenuItem menuItem1 = new MenuItem("Edit");
        MenuItem menuItem2 = new MenuItem("Delete");

        this.operations.getItems().addAll(menuItem1.getText(), menuItem2.getText());
        this.iconBtn.setOnMouseClicked(e -> {
            this.operations.show();
        });

    }

}
