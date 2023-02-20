/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Blog;
import com.artmart.models.BlogCategories;
import com.artmart.models.HasCategory;
import com.artmart.models.Media;
import com.artmart.services.BlogService;
import java.awt.Desktop;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private final BlogService blogService = new BlogService();
    private List<BlogCategories> blogCategoriesList;
    private Blog resBlog = new Blog();
    private BlogCategories resBlogCategories = new BlogCategories();
    private int test1, test2, test3;
    private Desktop desktop = Desktop.getDesktop();
    private final FileChooser fileChooser = new FileChooser();
    private Media img = new Media();
    private Image image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blogCategoriesList = blogService.getAllBlogCategories();
        ObservableList<String> blogCatList = FXCollections.observableArrayList(
                blogCategoriesList.stream().map(BlogCategories::getName).collect(Collectors.toList())
        );
        this.blog_category.setItems(blogCatList);
        File file = new File("src/com/artmart/assets/BlogAssets/alt.png");
        this.image = new Image(file.toURI().toString());
        this.blogImage_preview.setImage(image);
    }

    @FXML
    private void add(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./MainView.fxml"));
            Blog blog = new Blog(this.blog_title.getText(), this.blog_content.getText(), 2);
            test1 = blogService.addBlog(blog);
            if (test1 == 1) {
                resBlog = blogService.getOneBlogByTitle(this.blog_title.getText());
                resBlogCategories = blogService.getOneBlogCategory(this.blog_category.getSelectionModel().getSelectedItem());
                HasCategory hc = new HasCategory(resBlog.getId(), resBlogCategories.getId());
                this.img.setBlog_id(resBlog.getId());
                test3 = blogService.addMedia(img);
                test2 = blogService.addBlog2HasCat(hc);
            }

            if (test1 == 1 && test2 == 1 && test3 == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blog Posted");
                alert.setHeaderText(null);
                alert.setContentText("Your blog has been posteded.");
                alert.showAndWait();
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

    @FXML
    private void openFileExplorer(ActionEvent event) {
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
                        this.blogImage_preview.setImage(image);
                        this.blogImage_preview.setLayoutX(14);
                        this.blogImage_preview.setLayoutY(155);
                        this.blogImage_preview.setFitHeight(252);
                        this.blogImage_preview.setFitWidth(339);
                        this.add_imageBlog.setLayoutX(102);
                        this.add_imageBlog.setLayoutY(471);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                System.out.println("Image saved successfully.");
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
}
