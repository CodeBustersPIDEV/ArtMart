/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.HasCategory;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
//    Date sqlDate = new Date(System.currentTimeMillis());
    private final BlogService blogService = new BlogService();
    private List<BlogCategories> blogCategoriesList;
    Blog resBlog = new Blog();
    BlogCategories resBlogCategories = new BlogCategories();
    private int test1,test2;
    @FXML
    private Button cancel_btn;


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
        Blog blog=new Blog(this.blog_title.getText(),this.blog_content.getText(),2);
        test1=blogService.addBlog(blog);
        if(test1==1){
        resBlog=blogService.getOneBlogByTitle(this.blog_title.getText());
        resBlogCategories=blogService.getOneBlogCategory(this.blog_category.getSelectionModel().getSelectedItem());
        HasCategory hc = new HasCategory(resBlog.getId(),resBlogCategories.getId());
        test2=blogService.addBlog2HasCat(hc);
        }

        if(test1==1 && test2==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Blog Posted");
            alert.setHeaderText(null);
            alert.setContentText("Your blog has been posteded.");
            alert.showAndWait();        
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not post your blog.");
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
}
