package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.Media;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class AdminBlogPageController implements Initializable {

    @FXML
    private ScrollPane bigCont;
    @FXML
    private VBox container;
    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private Button backToBlogMenu;
    private List<User> userOptionsList;
    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private List<Blog> blogList = new ArrayList<>();
    private AdminBlogCardController controller;
    private User connectedUser = new User();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();

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
    public void refreshList() {
        this.container.getChildren().clear();
        int listId = userComboBox.getSelectionModel().getSelectedIndex();
        int selectedUserId = userOptionsList.get(listId).getUser_id();
        blogList = blogService.getAllBlogsByUser(selectedUserId);
        for (Blog blog : blogList) {
            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/AdminBlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card" + blog.getId());
                Media img = blogService.getOneMediaByBlogID(blog.getId());
                this.controller = loader.getController();
                if (img == null) {
                    File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
                    Image image = new Image(file.toURI().toString());
                    this.controller.setBlogImage(image);
                } else {
                    Image image = new Image(img.getFile_path());
                    this.controller.setBlogImage(image);
                }
                controller.setBlogTitle(blog.getTitle());
                container.getChildren().add(pane);
                controller.setBlogID(Integer.toString(blog.getId()));
                controller.setUsername(username);
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }

    @FXML
    private void backToBlogMenu(ActionEvent event) {
        try {
            Stage stage1 = (Stage) backToBlogMenu.getScene().getWindow();
            stage1.close();
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
