/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogCardController implements Initializable {

    @FXML
    private Text blog_title;
    @FXML
    private Button view_btn;
    @FXML
    private Label username;
    @FXML
    private Label publishDate;
    @FXML
    private Label blog_id;

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
    public void setUsername(String user_name) {
        this.username.setText(user_name);
    }
    public void setBlogID(String blog_id) {
        this.blog_id.setText(blog_id);
    }

    @FXML
    private void viewBlog(ActionEvent event) {
          try {
//        this.SelectUserAndProduct();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogPage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            BlogPageController controller = loader.getController();
            controller.setUpData(this.blog_id.getText());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
}
