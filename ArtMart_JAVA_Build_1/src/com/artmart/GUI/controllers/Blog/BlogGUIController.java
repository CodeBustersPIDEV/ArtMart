package com.artmart.GUI.controllers.Blog;

import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.Product.ReadyproductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.HasCategory;
import com.artmart.models.Media;
import com.artmart.models.Session;
import com.artmart.services.BlogService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private MenuItem cat;
    @FXML
    private MenuItem keyw;
    @FXML
    private ComboBox<BlogCategories> catComboBox;
    @FXML
    private Button sortBtn;
    @FXML
    private ChoiceBox<String> userOptions;

    private final BlogService blogService = new BlogService();
    private List<Blog> matchingBlogs;
    private List<BlogCategories> blogCategoriesList;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");

    private void initBlogs() {
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = this.blogService.getAllBlogs();
        blogList.forEach(blog -> {

            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card" + blog.getId());
                BlogCardController controller = loader.getController();
                Media img = this.blogService.getOneMediaByBlogID(blog.getId());
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = this.blogService.getAllBlogs();
        blogList.forEach(blog -> {
            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card" + blog.getId());
                BlogCardController controller = loader.getController();
                Media img = this.blogService.getOneMediaByBlogID(blog.getId());
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Map<String, String> profileActions = new HashMap<>();
       
        profileActions.put("", "");
        profileActions.put("Logout", "logout");
        profileActions.put("Profile", "profile");
        // Populate the choice box with display names
        userOptions.getItems().addAll(profileActions.keySet());
        // Add an event listener to handle the selected item's ID
        userOptions.setOnAction(event -> {
            String selectedItem = userOptions.getSelectionModel().getSelectedItem();
            String selectedId = profileActions.get(selectedItem);
            // Handle the action based on the selected ID
            if ("profile".equals(selectedId)) {

               userOptions.setValue("");
               if (this.session.getUserRole().equals("admin")) {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileAdmin.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileAdminController controller = loader.getController();
                        controller.setProfile(UserID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (this.session.getUserRole().equals("artist")) {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileArtist.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileArtistController controller = loader.getController();
                        controller.setProfile(UserID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileClient.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileClientController controller = loader.getController();
                        controller.setProfile(UserID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if ("logout".equals(selectedId)) {
                session.logOut("1");
                Stage stage = (Stage) userOptions.getScene().getWindow();
                stage.close();
                try {

                    stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setTitle("User Managment");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            }
        });
    }

    @FXML
    private void goBackToBlogMenu(ActionEvent event) {
        if (this.session.getUserRole().equals("admin")) {
            try {
                Stage stage = (Stage) backToBlogMenu.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        } else {
            Stage stage = (Stage) backToBlogMenu.getScene().getWindow();
            stage.close();
        }
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void setCategoryCombobox(ActionEvent event) {
        this.container.getChildren().clear();
        this.initBlogs();
        this.catComboBox.setVisible(true);
        this.searchText.setVisible(false);
        this.searchBtn.setVisible(false);
        this.cancelSearchBtn.setVisible(false);

        blogCategoriesList = blogService.getAllBlogCategories();
        blogCategoriesList.forEach(cat -> {
            this.catComboBox.getItems().add(cat);

        });

    }

    @FXML
    private void setKeywordsSearchField(ActionEvent event) {
        this.container.getChildren().clear();
        this.initBlogs();
        this.catComboBox.setVisible(false);
        this.searchText.setVisible(true);
        this.searchBtn.setVisible(true);
        this.cancelSearchBtn.setVisible(true);
    }

    @FXML
    private void searchByCategory(ActionEvent event) {
        List<HasCategory> hasCatList = new ArrayList<>();
        List<Blog> blogsByCat = new ArrayList<>();
        hasCatList = this.blogService.getAllCatbyBlog(this.catComboBox.getValue().getId());
        hasCatList.forEach(hasCat -> {
            blogsByCat.add(this.blogService.getOneBlog(hasCat.getBlog_id()));
        });

        this.container.getChildren().clear();
        UserDao userService = new UserDao();
        blogsByCat.forEach(blog -> {
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    private void sort(ActionEvent event) {
        this.container.getChildren().clear();
        UserDao userService = new UserDao();
        List<Blog> blogList = new ArrayList<>();
        blogList = this.blogService.getAllBlogsOrdered();
        blogList.forEach(blog -> {

            String username = userService.getUser(blog.getAuthor()).getUsername();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/BlogCard.fxml"));
                Pane pane = loader.load();
                pane.setId("blog_card" + blog.getId());
                BlogCardController controller = loader.getController();
                Media img = this.blogService.getOneMediaByBlogID(blog.getId());
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
                controller.setViewsLabel(Integer.toString(blog.getNb_views()));
                controller.setRatingLabel(String.valueOf(df.format(blog.getRating())));
                controller.setPublishDate(blog.getPublishDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
