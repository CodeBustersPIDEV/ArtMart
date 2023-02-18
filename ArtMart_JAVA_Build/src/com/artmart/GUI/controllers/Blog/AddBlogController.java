/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> blog_category;
    @FXML
    private TextArea blog_content;
    @FXML
    private Button add_imageBlog;
    @FXML
    private Button add_blog;
    Date sqlDate = new Date(System.currentTimeMillis());
    private final BlogService blogService = new BlogService();
        private List<BlogCategories> blogCategoriesList;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogCategoriesList=blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        this.blog_category.setItems(blogCatList);
        }
       
    
    @FXML
    private void add(ActionEvent event) throws IOException {
        try{
        FXMLLoader loader =new FXMLLoader(getClass().getResource("./MainView.fxml"));
        Blog blog=new Blog(blog_title.getText(),blog_content.getText(),sqlDate,2);
        blogService.addBlog(blog);
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
