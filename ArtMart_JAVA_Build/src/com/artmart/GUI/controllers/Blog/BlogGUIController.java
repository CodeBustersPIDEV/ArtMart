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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlogGUIController implements Initializable {

    @FXML
    private VBox container;
    @FXML
    private Button backToBlogMenu;
    @FXML
    private Button goToAddBlog;
    @FXML
    private Button goToMyBlogs;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button cancelSearchBtn;
    private final BlogService blogService = new BlogService();
    private List<Blog> matchingBlogs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File f = new File("src/com/artmart/assets/BlogAssets/3493894fa4ea036cfc6433c3e2ee63b0.png");
        Image im = new Image(f.toURI().toString());
        ImageView imageView = new ImageView(im);
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        this.searchBtn.setGraphic(imageView);

        File f2 = new File("src/com/artmart/assets/BlogAssets/3973a31998338e76bea5d4c956c0060f.png");
        Image im2 = new Image(f2.toURI().toString());
        ImageView imageView2 = new ImageView(im2);
        imageView2.setFitWidth(16);
        imageView2.setFitHeight(16);
        this.cancelSearchBtn.setGraphic(imageView2);

        BlogService blogService = new BlogService();
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = blogService.getAllBlogs();
        blogList.forEach(blog -> {
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
                    controller.setImage(image);
                    controller.setBlogImage(image);
//                    System.out.println("image"+image.getHeight());
//                    controller.setCardCont1(image.getHeight());
//                    controller.setCardCont2(image.getHeight());
                } else {
                    File file = new File(img.getFile_path());
                    Image image = new Image(file.toURI().toString());
                    controller.setImage(image);
                    controller.setBlogImage(image);
//                    System.out.println("image"+image.getHeight());
//                    controller.setCardCont1(image.getHeight());
//                    controller.setCardCont2(image.getHeight());
                }
                controller.setBlogTitle(blog.getTitle());
                container.getChildren().add(pane);
                controller.setUsername(username);
                controller.setBlogID(Integer.toString(blog.getId()));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void goBackToBlogMenu(ActionEvent event) {
//        try {
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/MainView.fxml"));
//            Scene scene = new Scene(root);
//            stage.setResizable(false);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            System.out.print(e.getMessage());
//        }
        Stage stage = (Stage) backToBlogMenu.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToAddBlog(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/addBlog.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void goToMyBlogs(ActionEvent event) {
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

    @FXML
    private void search(ActionEvent event) {
        String keyword = this.searchText.getText();
        this.matchingBlogs = this.blogService.searchBlogsByTitle(keyword);
        this.container.getChildren().clear();
        UserDao userService = new UserDao();
        matchingBlogs.forEach(blog -> {
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
                    controller.setImage(image);
                    controller.setBlogImage(image);
                } else {
                    File file = new File(img.getFile_path());
                    Image image = new Image(file.toURI().toString());
                    controller.setImage(image);
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
        });
    }

    @FXML
    private void cancel(ActionEvent event) {
        this.searchText.setText("");
        this.container.getChildren().clear();
        BlogService blogService = new BlogService();
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = blogService.getAllBlogs();
        blogList.forEach(blog -> {
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
                    controller.setImage(image);
                    controller.setBlogImage(image);
                } else {
                    File file = new File(img.getFile_path());
                    Image image = new Image(file.toURI().toString());
                    controller.setImage(image);
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
        });
    }
}
