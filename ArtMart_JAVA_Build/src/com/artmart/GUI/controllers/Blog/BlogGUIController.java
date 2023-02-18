package com.artmart.GUI.controllers.Blog;

import com.artmart.GUI.controllers.Blog.BlogCardController;
import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BlogGUIController implements Initializable {

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BlogService blogService=new BlogService();
        UserDao userService=new UserDao();
        List<Blog> blogList=new ArrayList<Blog>();
        blogList= blogService.getAllBlogs();
        for(Blog blog : blogList){ 
            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card"+blog.getId());
                BlogCardController controller = loader.getController();
                controller.setBlogTitle(blog.getTitle());
                container.getChildren().add(pane);                   
                controller.setUsername(username);
                controller.setBlogID(Integer.toString(blog.getId()));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
                }
            }
    }     
}
