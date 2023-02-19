/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
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
    
    private final BlogService blogService=new BlogService();
    private UserDao userService=new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    @FXML
    private Button backBtn;

    /**
     * Initializes the controller class.
     */
     public void setUpData(String b_ID) {
        this.blog_id.setText(b_ID);
        this.id=Integer.parseInt(this.blog_id.getText());
        this.viewBlog= blogService.getOneBlog(id);
        
        this.blog_title.setText(this.viewBlog.getTitle());
        this.username.setText(userService.getUser(this.viewBlog.getAuthor()).getUsername());
        this.publishDate.setText(this.viewBlog.getPublishDate().toString());
        this.blog_content.setText(this.viewBlog.getContent());

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
