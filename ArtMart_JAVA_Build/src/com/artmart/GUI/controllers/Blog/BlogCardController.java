/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void setBlogTitle(String title) {
        blog_title.setText(title);
    }
     public void setPublishDate(String date) {
        publishDate.setText(date);
    }
    public void setUsername(String user_name) {
        username.setText(user_name);
    }

    @FXML
    private void viewBlog(ActionEvent event) {
    }
    
}
