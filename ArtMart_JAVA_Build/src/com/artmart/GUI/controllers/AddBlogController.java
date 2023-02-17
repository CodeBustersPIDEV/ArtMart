/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers;

import com.artmart.models.Blog;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class AddBlogController implements Initializable {

    @FXML
    private TextField blog_title;
    @FXML
    private ComboBox<?> blog_category;
    @FXML
    private TextArea blog_content;
    @FXML
    private Button add_imageBlog;
    @FXML
    private Button add_blog;
    Date sqlDate = new Date(System.currentTimeMillis());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void add(ActionEvent event) throws IOException {
        try{
        Blog blog=new Blog(blog_title.getText(),blog_content.getText(),sqlDate,1);
        BlogService blogService=new BlogService();
        blogService.addBlog(blog);
        FXMLLoader loader =new FXMLLoader(getClass().getResource("./Main_View.fxml"));
        Parent root;
        root=loader.load();
        add_blog.getScene().setRoot(root);
       
        new Alert(Alert.AlertType.INFORMATION, "sucess").show();
        }
        catch(IOException ex){
            new Alert(Alert.AlertType.INFORMATION, "failed").show();
            ex.fillInStackTrace();

        }
    }
    
}
