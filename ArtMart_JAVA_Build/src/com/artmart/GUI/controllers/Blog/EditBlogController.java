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
import com.artmart.models.HasTag;
import com.artmart.models.Media;
import com.artmart.models.Tag;
import com.artmart.services.BlogService;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private Button edit_blog;
    @FXML
    private Button cancel_btn;
    @FXML
    private Label blogID;
    @FXML
    private ImageView blogImage;
    @FXML
    private Button edit_imageBlog;
    @FXML
    private ComboBox<String> tagsInput;

    private List<BlogCategories> blogCategoriesList;
    private List<Tag> blogTagsList;
    private ObservableList<String> blogTList;
    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    private int test;
    private boolean test1;
    private boolean test2;
    private boolean test3;
    private boolean testImg = false;
    private Blog resBlog = new Blog();
    private BlogCategories resBlogCategories = new BlogCategories();
    private Image image;
    private Media img = new Media();
    private Media imgEdited = new Media();
    private final Desktop desktop = Desktop.getDesktop();
    private String tags = "";
    private String parts2 = "";
    private final FileChooser fileChooser = new FileChooser();
    private final String phpUrl = "http://localhost/PIDEV/upload.php";
    String boundary = "---------------------------12345";

    /**
     * Initializes the controller class.
     *
     * @param b_ID
     */
    public void setUpData(String b_ID) {
        this.blogID.setText(b_ID);
        this.id = Integer.parseInt(this.blogID.getText());
        this.viewBlog = blogService.getOneBlog(id);
        HasCategory hs = blogService.getCatbyBlog(id);
        HasTag ht = blogService.getTagbyBlog(id);
        this.img = this.blogService.getOneMediaByBlogID(id);
        if (!(img == null)) {
            this.image = new Image(img.getFile_path());
        } else {
            File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
            this.image = new Image(file.toURI().toString());
        }
        this.blog_title.setText(this.viewBlog.getTitle());
        this.blog_content.setText(this.viewBlog.getContent());
        this.blog_category.getSelectionModel().select(hs.getCategory_id() - 1);
        this.tagsInput.getSelectionModel().select(ht.getTag_id() - 1);
        this.blogImage.setImage(this.image);
        retrieve();

    }

    private void retrieve() {
        List<HasTag> hasTagsList;
        List<Tag> tagsList = new ArrayList<>();
        hasTagsList = blogService.getAllTagsbyBlog(id);
        hasTagsList.forEach(hasTag -> {
            tagsList.add(this.blogService.getOneTag(hasTag.getTag_id()));
        });
        tagsList.forEach(tag -> {
            this.parts2 += "#" + tag.getName();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogCategoriesList = blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        this.blog_category.setItems(blogCatList);

        blogTagsList = blogService.getAllTags();
        this.blogTList = FXCollections.observableArrayList(
                blogTagsList.stream().map(Tag::getName).collect(Collectors.toList())
        );
        this.tagsInput.setItems(this.blogTList);
        System.out.println(this.parts2);
    }

    @FXML
    private void edit(ActionEvent event) {
        try {
            Blog blog = new Blog(this.blog_title.getText(), this.blog_content.getText());
            this.test1 = blogService.updateBlog(this.id, blog);
            if (this.test1) {
                resBlog = blogService.getOneBlogByTitle(this.blog_title.getText());
                resBlogCategories = blogService.getOneBlogCategory(this.blog_category.getSelectionModel().getSelectedItem());
                HasCategory hc = new HasCategory(resBlog.getId(), resBlogCategories.getId());
                this.test2 = blogService.updateHasCat(this.id, hc);
                String[] parts = this.tags.split("#");
                for (String part : parts) {
                    Tag testTag = this.blogService.getOneTagByName(part);
                    if (testTag == null) {
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

                if (img == null) {
                    if (testImg) {
                        this.imgEdited.setBlog_id(resBlog.getId());
                        this.test = blogService.addMedia(imgEdited);
                    }
                } else {
                    if (testImg) {
                        this.test3 = blogService.updateMedia(img.getId(), imgEdited);
                    }
                }
            }

            if ((test1 && test2 && test3) || (test1 && test2) || (test1 && test2 && test == 1)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blog Updated");
                alert.setHeaderText(null);
                alert.setContentText("Your blog has been updated succesfully.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        Stage stage1 = (Stage) edit_blog.getScene().getWindow();
                        stage1.close();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Oops!!Can not update your blog.");
                alert.showAndWait();
            }
        } catch (Exception ex) {
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
            Stage stage1 = (Stage) cancel_btn.getScene().getWindow();
            stage1.close();
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
    private void editImageBlog(ActionEvent event) throws IOException {
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
                        this.blogImage.setImage(image);
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
        this.imgEdited.setFile_name(file.getName());
        this.imgEdited.setFile_path(imagePath);
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                this.imgEdited.setFile_type(reader.getFormatName().toUpperCase());
            }
        } catch (IOException ex) {
            System.out.println("Failed to get the image type.");
            ex.getMessage();
        }
    }

    @FXML
    private void inc_tag(ActionEvent event) {
        this.tags += "#" + this.tagsInput.getSelectionModel().getSelectedItem();
        System.out.println(tags);
    }

}
