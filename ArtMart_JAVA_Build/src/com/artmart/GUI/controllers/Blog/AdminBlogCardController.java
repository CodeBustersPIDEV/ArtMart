package com.artmart.GUI.controllers.Blog;

import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AdminBlogCardController implements Initializable {

    @FXML
    private Pane cardContainer;
    @FXML
    private Rectangle cont2;
    @FXML
    private Button del_btn;
    @FXML
    private Label username;
    @FXML
    private Label publishDate;
    @FXML
    private Label blog_id;
    @FXML
    private ImageView imagePreview;
    @FXML
    private Label blog_title;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label viewsLabel;
    private Image image;
    private final BlogService blogService = new BlogService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setBlogTitle(String title) {
        this.blog_title.setText(title);
    }

    public void setCardCont1(double h) {
        this.cardContainer.setMaxHeight(h);
    }

    public void setCardCont2(double h) {
        this.cont2.setHeight(h);
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
        this.viewsLabel.setText(nb_views + " Views");
    }

    @FXML
    private void delete(ActionEvent event) {
        int b_id = Integer.parseInt(this.blog_id.getText());
        boolean test4 = this.blogService.deleteMedia(b_id);
        boolean test1 = this.blogService.deleteHasCat(b_id);
        boolean test2 = this.blogService.deleteHasTag(b_id);
        boolean test3 = this.blogService.deleteAllComments(b_id);
        if (test1 && test2 && test3 && test4) {
            boolean test = this.blogService.deleteBlog(b_id);
            if (test) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blog Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Your blog has been deleted successfully.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/AdminBlogPage.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not delete your blog.");
            alert.showAndWait();
        }
    }
}
