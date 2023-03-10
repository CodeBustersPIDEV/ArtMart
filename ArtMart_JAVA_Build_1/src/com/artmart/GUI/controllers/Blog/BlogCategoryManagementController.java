/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.BlogCategories;
import com.artmart.services.BlogService;
import java.io.IOException;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogCategoryManagementController implements Initializable {

    @FXML
    private Button goBack;
    @FXML
    private TableView<BlogCategories> categoryTableView;
    @FXML
    private TableColumn<BlogCategories, String> catNameColumn;
    @FXML
    private TableColumn<BlogCategories, Void> operationColumn;

    private final BlogService blogService = new BlogService();
    private List<BlogCategories> blogCategoriesList;
    @FXML
    private Button addCat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.blogCategoriesList = this.blogService.getAllBlogCategories();
        ObservableList<BlogCategories> items = FXCollections.observableArrayList(
                this.blogCategoriesList.stream().collect(Collectors.toList())
        );
        this.catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.categoryTableView.setItems(items);

        this.operationColumn.setCellFactory(param -> new TableCell<BlogCategories, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    try {
                        BlogCategories category = getTableView().getItems().get(getIndex());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/editBlogCategory.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        EditBlogCategoryController controller = loader.getController();
                        controller.setUpData(String.valueOf(category.getId()));
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    }

                });

                deleteButton.setOnAction(event -> {
                    BlogCategories category = getTableView().getItems().get(getIndex());
                    boolean isDeleted = blogService.deleteBlogCategory(category.getId());
                    blogCategoriesList = blogService.getAllBlogCategories();
                    ObservableList<BlogCategories> items = FXCollections.observableArrayList(
                            blogCategoriesList.stream().collect(Collectors.toList())
                    );
                    catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                    categoryTableView.setItems(items);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(editButton, deleteButton);
                    buttons.setSpacing(20);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
    }

    @FXML
    private void goBackToBlogMenu(ActionEvent event) {
        try {
            Stage stage1 = (Stage) goBack.getScene().getWindow();
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

    @FXML
    private void goToAddCat(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/addCategory.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
