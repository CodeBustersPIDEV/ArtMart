/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Blog;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

private final BlogService blogService=new BlogService();
private BlogManagementPageController controller=new BlogManagementPageController();
private FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogManagementPage.fxml"));

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
        this.controller = loader.getController();
        int b_id = Integer.parseInt(this.blog_id.getText());
        boolean test1= this.blogService.deleteHasCat(b_id);
        boolean test2= this.blogService.deleteHasTag(b_id);
        boolean test3= this.blogService.deleteAllComments(b_id);
        if(test1 && test2 && test3){
        boolean test=this.blogService.deleteBlog(b_id);
         if(test){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Blog Deleted");
//            alert.setHeaderText(null);
//            alert.setContentText("Your blog has been deleted successfully.");
//            alert.showAndWait();  
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not delete your blog.");
            alert.showAndWait();    
            }
        }  
       this.controller.refreshList();
    }
    
    @FXML
    private void goToEditBlog(ActionEvent event) {
    }
    
    
}
