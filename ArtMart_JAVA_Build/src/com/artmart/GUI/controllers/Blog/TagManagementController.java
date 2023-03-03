/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.models.Tag;
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
public class TagManagementController implements Initializable {

    @FXML
    private Button goBack;
    @FXML
    private TableView<Tag> tagTableView;
    @FXML
    private TableColumn<Tag, Integer> catID_Column;
    @FXML
    private TableColumn<Tag, String> catNameColumn;
    @FXML
    private TableColumn<Tag, Void> operationColumn;
    private final BlogService blogService = new BlogService();
    private List<Tag> tagList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tagList = this.blogService.getAllTags();
        ObservableList<Tag> items = FXCollections.observableArrayList(
                this.tagList.stream().collect(Collectors.toList())
        );
        this.catID_Column.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.tagTableView.setItems(items);

        this.operationColumn.setCellFactory(param -> new TableCell<Tag, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    try {
                        Tag tag = getTableView().getItems().get(getIndex());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/editTag.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        EditTagController controller = loader.getController();
                        controller.setUpData(String.valueOf(tag.getId()));
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    }

                });

                deleteButton.setOnAction(event -> {
                    Tag tag = getTableView().getItems().get(getIndex());
                    boolean isDeleted = blogService.deleteTag(tag.getId());
                    tagList = blogService.getAllTags();
                    ObservableList<Tag> items = FXCollections.observableArrayList(
                            tagList.stream().collect(Collectors.toList())
                    );
                    catID_Column.setCellValueFactory(new PropertyValueFactory<>("id"));

                    catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                    tagTableView.setItems(items);

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


