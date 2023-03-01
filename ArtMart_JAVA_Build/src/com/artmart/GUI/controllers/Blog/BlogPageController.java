/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.Comment;
import com.artmart.models.HasCategory;
import com.artmart.models.Media;
import com.artmart.models.Session;
import com.artmart.services.BlogService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private Button backBtn;
    @FXML
    private ImageView blogImage;
    @FXML
    private ScrollPane commentSection;
    @FXML
    private TextArea comment_content;
    @FXML
    private Button post;
    @FXML
    private VBox commentContainer;
    @FXML
    private SplitPane comments;
    @FXML
    private Text blogContent;
    @FXML
    private Label categoryLabel;

    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    private Image image;
    private Media img = new Media();
    private BlogCategories resBlogCategories = new BlogCategories();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    @FXML
    private HBox tagContainer;

    /**
     * Initializes the controller class.
     *
     * @param b_ID
     */
    public VBox getContainer() {
        return this.commentContainer;
    }

    public String getBlogID() {
        return this.blog_id.getText();
    }

    public void setupComments(int bc_id) {
        List<Comment> commentList = new ArrayList<>();
        commentList = this.blogService.getAllComments(bc_id);
        if (commentList != null) {
            for (Comment blog : commentList) {
                String username = userService.getUser(blog.getAuthor()).getUsername();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/CommentCard.fxml"));
                    Pane pane = loader.load();
                    pane.setId("comment_card" + blog.getId());
                    CommentCardController controller = loader.getController();
                    this.commentContainer.getChildren().add(pane);
                    controller.setCommentContent(blog.getContent());
                    controller.setUsername(username);
                    controller.setCommentID(Integer.toString(blog.getId()));
                    controller.setPostDate(blog.getPublishDate().toString());
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }

    public void setUpData(String b_ID) {
        this.blog_id.setText(b_ID);
        this.id = Integer.parseInt(this.blog_id.getText());
        HasCategory hs = blogService.getCatbyBlog(id);
        this.resBlogCategories = blogService.getOneBlogCategory(hs.getCategory_id());
        this.categoryLabel.setText(this.resBlogCategories.getName());
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
        this.blogContent.setText(this.viewBlog.getContent());
        this.blogImage.setImage(this.image);
        this.setupComments(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/CommentCard.fxml"));
//        CommentCardController controller = loader.getController();
//        boolean test = controller.getIsEdited();
//        System.out.println(test);
//        if (test) {
//            this.comment_content.setText("");
//            this.commentContainer.getChildren().clear();
//            setupComments(this.id);
//        }
        this.session = (Session) user.get(user.keySet().toArray()[0]);

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

    @FXML
    private void postComment(ActionEvent event) {
        Comment comment = new Comment(this.comment_content.getText(), this.session.getUserId(), this.id);
        int test = this.blogService.addComment(comment);
        if (test == 1) {
            this.comment_content.setText("");
            this.commentContainer.getChildren().clear();
            setupComments(this.id);
        }
    }

}
