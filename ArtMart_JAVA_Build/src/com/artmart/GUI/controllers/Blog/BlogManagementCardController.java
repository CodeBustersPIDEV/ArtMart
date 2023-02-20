/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogManagementCardController implements Initializable {

    @FXML
    private Button delete_btn;
    @FXML
    private Label publishDate;
    @FXML
    private Label blog_id;
    @FXML
    private Button link_edit_btn;
    @FXML
    private Label blog_title;

    private final BlogService blogService = new BlogService();
//private BlogManagementPageController controller=new BlogManagementPageController();
//private AddBlogController controller2=new AddBlogController();

//private Blog b = new Blog();
//private FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogManagementPage.fxml"));
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setBlogTitle(String title) {
        this.blog_title.setText(title);
    }

    public void setPublishDate(String date) {
        this.publishDate.setText(date);
    }

    public void setBlogID(String blog_id) {
        this.blog_id.setText(blog_id);
    }

    @FXML
    private void deleteBlog(ActionEvent event) {
//        this.controller = this.loader.getController();
        int b_id = Integer.parseInt(this.blog_id.getText());
        boolean test1 = this.blogService.deleteHasCat(b_id);
        boolean test2 = this.blogService.deleteHasTag(b_id);
        boolean test3 = this.blogService.deleteAllComments(b_id);
        if (test1 && test2 && test3) {
            boolean test = this.blogService.deleteBlog(b_id);
//       this.controller.refreshList();

            if (test) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blog Deleted");
                alert.setHeaderText(null);
                alert.setContentText("Your blog has been deleted successfully.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogManagementPage.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.getMessage();
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

    @FXML
    private void goToEditBlog(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/EditBlog.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            EditBlogController controller = loader.getController();
            controller.setUpData(this.blog_id.getText());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
