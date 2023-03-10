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
import com.artmart.models.Media;
import com.artmart.models.Session;
import com.artmart.models.Tag;
import com.artmart.models.HasTag;
import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.awt.Desktop;
import java.io.BufferedReader;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

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
    @FXML
    private Label pageTitle;
    @FXML
    private Button cancel_btn;
    @FXML
    private ImageView blogImage_preview;
    @FXML
    private ComboBox<String> tagsInput;

    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private List<BlogCategories> blogCategoriesList;
    private List<Tag> blogTagsList;
    private ObservableList<String> blogTList;
    private Blog resBlog = new Blog();
    private BlogCategories resBlogCategories = new BlogCategories();
    private int test1, test2, test3;
    private boolean testImg = false;
    private final Desktop desktop = Desktop.getDesktop();
    private final FileChooser fileChooser = new FileChooser();
    private Media img = new Media();
    private Image image;
    private String tags = "";
    private User connectedUser = new User();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private final String phpUrl = "http://localhost/PIDEV/upload.php";
    String boundary = "---------------------------12345";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        blogCategoriesList = blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        blogTagsList = blogService.getAllTags();
        this.blogTList = FXCollections.observableArrayList(
                blogTagsList.stream().map(Tag::getName).collect(Collectors.toList())
        );
        this.tagsInput.setItems(this.blogTList);
        this.blog_category.setItems(blogCatList);
        File file = new File("src/com/artmart/assets/BlogAssets/alt.png");
        this.image = new Image(file.toURI().toString());
        this.blogImage_preview.setImage(image);
        this.blogImage_preview.setFitHeight(143);
        this.blogImage_preview.setFitWidth(163);
        this.blogImage_preview.setLayoutX(316);
        this.blogImage_preview.setLayoutY(305);
        this.session = (Session) user.get(user.keySet().toArray()[0]);
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        String blogTitle = this.blog_title.getText();
        String blogContent = this.blog_content.getText();
        if (!blogTitle.isEmpty() && !blogContent.isEmpty()) {
            try {
                Blog blog = new Blog(this.blog_title.getText(), this.blog_content.getText(), this.session.getUserId());
                test1 = blogService.addBlog(blog);
                if (test1 == 1) {
                    resBlog = blogService.getOneBlogByTitle(this.blog_title.getText());
                    resBlogCategories = blogService.getOneBlogCategory(this.blog_category.getSelectionModel().getSelectedItem());
                    HasCategory hc = new HasCategory(resBlog.getId(), resBlogCategories.getId());
                    test2 = blogService.addBlog2HasCat(hc);
                    String[] parts = this.tags.split("#");
                    for (String part : parts) {
                        Tag testTag = this.blogService.getOneTagByName(part);
                        if (testTag == null ) {
                            Tag testTag1 = new Tag(part);
                            int test = this.blogService.addTag(testTag1);
                            Tag resTag = blogService.getOneTagByName(part);
                            HasTag ht = new HasTag(resBlog.getId(), resTag.getId());
                            int testa = this.blogService.addBlog2HasTag(ht);
                        } else {
                            HasTag ht = new HasTag(resBlog.getId(), testTag.getId());
                            int testa = this.blogService.addBlog2HasTag(ht);
                        }
                    }

                    if (testImg) {
                        this.img.setBlog_id(resBlog.getId());
                        test3 = blogService.addMedia(img);
                    }
                }

                if ((test1 == 1 && test2 == 1) || (test1 == 1 && test2 == 1 && test3 == 1)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Blog Posted");
                    alert.setHeaderText(null);
                    alert.setContentText("Your blog has been posteded.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        try {
                            Stage stage1 = (Stage) add_blog.getScene().getWindow();
                            stage1.close();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Oops!!Can not post your blog.");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An Error occured");
                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the form");
            alert.showAndWait();
        }
    }

    @FXML
    private void goBackToMenu(ActionEvent event) {
        try {
            Stage stage1 = (Stage) cancel_btn.getScene().getWindow();
            stage1.close();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root;
            if (this.connectedUser.getRole().equals("admin")) {
                root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/Blog.fxml"));
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void openFileExplorer(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.fileChooser.setTitle("Select an image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = this.fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            this.testImg = true;
            byte[] imageData = Files.readAllBytes(file.toPath());

            URL url = new URL(phpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
            outputStream.write(imageData);
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            String destinationPath = "http://localhost/PIDEV/BlogUploads/" + file.getName();
            setupMediaInfo(file, destinationPath);

            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Image Upload");
                alert.setHeaderText(null);
                alert.setContentText("Image uploaded successfully.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        this.image = new Image(file.toURI().toString());
                        this.blogImage_preview.setImage(image);
                        this.blogImage_preview.setLayoutX(178);
                        this.blogImage_preview.setLayoutY(175);
                        this.blogImage_preview.setFitHeight(423);
                        this.blogImage_preview.setFitWidth(450);
                        this.add_imageBlog.setLayoutX(178);
                        this.add_imageBlog.setLayoutY(634);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An Error occured");
                alert.showAndWait();
            }
        }

    }

    private void setupMediaInfo(File file, String imagePath) {
        this.img.setFile_name(file.getName());
        this.img.setFile_path(imagePath);
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                this.img.setFile_type(reader.getFormatName().toUpperCase());
            }
        } catch (IOException ex) {
            System.out.println("Failed to get the image type.");
            ex.getMessage();
        }
    }

    @FXML
    private void inc_tag(ActionEvent event) {
        this.tags += "#" + this.tagsInput.getSelectionModel().getSelectedItem();
    }
}
