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
import javafx.scene.text.Text;
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
    }

    @FXML
    private void goToEditBlog(ActionEvent event) {
    }
    
    
}
