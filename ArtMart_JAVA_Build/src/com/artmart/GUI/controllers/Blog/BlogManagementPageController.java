/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.GUI.controllers.Blog.BlogManagementCardController;
import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogManagementPageController implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private ComboBox<String> userComboBox;
    private List<User> userOptionsList;
    private BlogService blogService=new BlogService();
    private UserDao userService=new UserDao();
    private List<Blog> blogList=new ArrayList<Blog>();
    private BlogManagementCardController controller;
    private User connectedUser = new User();                
    @FXML
    private ScrollPane bigCont;
    @FXML
    private Button backToBlogMenu;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.container.setAlignment(Pos.CENTER);
        this.container.setSpacing(5);
        userOptionsList = this.userService.viewListOfUsers();
        ObservableList<String> userOptions = FXCollections.observableArrayList(
                userOptionsList.stream().map(User::getName).collect(Collectors.toList())
        );
        this.userComboBox.setItems(userOptions);
    }    




    @FXML
    private void refreshList(ActionEvent event) {
        this.container.getChildren().clear();
        int listId = userComboBox.getSelectionModel().getSelectedIndex();
        int selectedUserId = userOptionsList.get(listId).getUser_id();
        this.connectedUser = this.userService.getUser(selectedUserId);
        blogList= blogService.getAllBlogsByUser(connectedUser.getUser_id());
        for(Blog blog : blogList){ 
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogManagementCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card"+blog.getId());
                this.controller = loader.getController();
                controller.setBlogTitle(blog.getTitle());
                container.getChildren().add(pane);                   
                controller.setBlogID(Integer.toString(blog.getId()));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
                }
            }
        
    }

    @FXML
    private void backToBlogMenu(ActionEvent event) {
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
