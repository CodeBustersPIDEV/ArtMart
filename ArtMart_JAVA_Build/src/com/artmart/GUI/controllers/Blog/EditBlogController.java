/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.HasCategory;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class EditBlogController implements Initializable {

    @FXML
    private Label pageTitle;
    @FXML
    private TextField blog_title;
    @FXML
    private ComboBox<String> blog_category;
    @FXML
    private TextArea blog_content;
    @FXML
    private Button add_imageBlog;
    @FXML
    private Button edit_blog;
    @FXML
    private Button cancel_btn;
    @FXML
    private Label blogID;

    private List<BlogCategories> blogCategoriesList;
    private final BlogService blogService=new BlogService();
    private UserDao userService=new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    private boolean test1;
    private boolean test2;
    private Blog resBlog = new Blog();
    private BlogCategories resBlogCategories = new BlogCategories();


    
    /**
     * Initializes the controller class.
     */
    

    
    public void setUpData(String b_ID) {
        this.blogID.setText(b_ID);
        this.id=Integer.parseInt(this.blogID.getText());
        this.viewBlog= blogService.getOneBlog(id);
        
        this.blog_title.setText(this.viewBlog.getTitle());
        this.blog_content.setText(this.viewBlog.getContent());
        this.blog_category.getSelectionModel().selectFirst();

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogCategoriesList=blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        this.blog_category.setItems(blogCatList);
    }    

    @FXML
    private void edit(ActionEvent event) {
        try{
        Blog blog=new Blog(this.blog_title.getText(),this.blog_content.getText());
        this.test1=blogService.updateBlog(this.id,blog);
        if(this.test1){
        resBlog=blogService.getOneBlogByTitle(this.blog_title.getText());
        resBlogCategories=blogService.getOneBlogCategory(this.blog_category.getSelectionModel().getSelectedItem());
        HasCategory hc = new HasCategory(resBlog.getId(),resBlogCategories.getId());
        this.test2=blogService.updateHasCat(this.id,hc);
        }

        if(test1 && test2){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Blog Updated");
            alert.setHeaderText(null);
            alert.setContentText("Your blog has been updated succesfully.");
            alert.showAndWait();        
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not update your blog.");
            alert.showAndWait();    
}
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An Error occured");
            alert.showAndWait();            

        }
    }

    @FXML
    private void goBackToMenu(ActionEvent event) {
         try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogManagementPage.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
    
    
}
