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
import com.artmart.services.BlogService;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private List<BlogCategories> blogCategoriesList;
    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    private Blog viewBlog = new Blog();
    private int id;
    private boolean test1;
    private boolean test2;
    private boolean test3;
    private Blog resBlog = new Blog();
    private BlogCategories resBlogCategories = new BlogCategories();
    private Image image;
    private Media img = new Media();
    private Media imgEdited = new Media();
    private final Desktop desktop = Desktop.getDesktop();
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Initializes the controller class.
     *
     * @param b_ID
     */
    public void setUpData(String b_ID) {
        this.blogID.setText(b_ID);
        this.id = Integer.parseInt(this.blogID.getText());
        this.viewBlog = blogService.getOneBlog(id);
        this.img = this.blogService.getOneMediaByBlogID(id);
        if (!(img == null)) {
            File file = new File(img.getFile_path());
            this.image = new Image(file.toURI().toString());
        } else {
            File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
            this.image = new Image(file.toURI().toString());
        }

        this.blog_title.setText(this.viewBlog.getTitle());
        this.blog_content.setText(this.viewBlog.getContent());
        this.blog_category.getSelectionModel().selectFirst();
        this.blogImage.setImage(this.image);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogCategoriesList = blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        this.blog_category.setItems(blogCatList);
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
                File file = new File(this.img.getFile_path());
                if (file.delete()) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Failed to delete the file.");
                }
                this.test3 = blogService.updateMedia(img.getId(), imgEdited);
            }

            if (test1 && test2 && test3) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blog Updated");
                alert.setHeaderText(null);
                alert.setContentText("Your blog has been updated succesfully.");
                alert.showAndWait();
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
    private void editImageBlog(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.fileChooser.setTitle("Select an image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = this.fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            Path sourcePath = file.toPath();
            Path destinationPath = Paths.get("src/com/artmart/assets/BlogAssets/uploads/" + file.getName());
            setupMediaInfo(file, destinationPath.toString());
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
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
                System.out.println("Image updated successfully.");
            } catch (IOException ex) {
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

}
