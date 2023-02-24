package com.artmart.GUI.controllers.Blog;

import com.artmart.GUI.controllers.MainViewController;
import com.artmart.GUI.controllers.User.LoginController;
import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.Media;
import com.artmart.models.Session;
import com.artmart.services.BlogService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlogGUIController implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private Button backToBlogMenu;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BlogService blogService = new BlogService();
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = blogService.getAllBlogs();
        for (Blog blog : blogList) {
            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card" + blog.getId());
                BlogCardController controller = loader.getController();
                Media img = blogService.getOneMediaByBlogID(blog.getId());
                if (img == null) {
                    File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
                    Image image = new Image(file.toURI().toString());
                    controller.setBlogImage(image);
                } else {
                    File file = new File(img.getFile_path());
                    Image image = new Image(file.toURI().toString());
                    controller.setBlogImage(image);
                }
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

    @FXML
    private void goBackToBlogMenu(ActionEvent event) {
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
