/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogPageController implements Initializable {

    @FXML
    private Label blog_id;
    
    /**
     * Initializes the controller class.
     */
     public void setUpData(String b_ID) {
        this.blog_id.setText(b_ID);
                System.out.println(this.blog_id.getText());

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
}
