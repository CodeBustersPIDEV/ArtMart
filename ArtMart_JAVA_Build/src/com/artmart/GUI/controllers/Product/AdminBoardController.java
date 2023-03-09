/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.CustomProduct.EditCategoryController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CategoriesService;
import com.artmart.services.DatabaseUtilsService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AdminBoardController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();
    private final CategoriesService categoriesService = new CategoriesService();

    @FXML
    private TableView<ReadyProduct> readyProductsTableView;
    @FXML
    private TableView<ProductReview> productReviewsTableView;
    @FXML
    private TableView<Categories> categoriesTableView;
    @FXML
    private TableColumn<ReadyProduct, String> productNameColumn;
    @FXML
    private TableColumn<ReadyProduct, Integer> productUserColumn;
    @FXML
    private TableColumn<ReadyProduct, Integer> priceColumn;
    @FXML
    private TableColumn<ReadyProduct, String> categoryColumn;
    @FXML
    private TableColumn<ReadyProduct, Void> operationColumn;
    @FXML
    private TableColumn<ProductReview, Integer> reviewUserColumn;
    @FXML
    private TableColumn<ProductReview, String> titleColumn;
    @FXML
    private TableColumn<ProductReview, String> textColumn;
    @FXML
    private TableColumn<ProductReview, Float> ratingColumn;
    @FXML
    private TableColumn<ProductReview, String> dateColumn;
    @FXML
    private TableColumn<ProductReview, Void> operationColumn2;
    @FXML
    private TableColumn<Categories, String> catNameColumn;
    @FXML
    private TableColumn<Categories, Void> operationColumn3;
//    @FXML
//    private TextField search;
//    @FXML
//    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    @FXML
    private Button addCategory;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button profileButton;

    @FXML
    private Label profileLabel;
    @FXML
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    private List<ReadyProduct> readyProductsList;
    private List<ProductReview> productReviewsList;
    private List<Categories> categoriesList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());

        // Create a map of display names to IDs
        Map<String, String> profileActions = new HashMap<>();
        profileActions.put("Logout", "logout");
        profileActions.put("Profile", "profile");

        // Populate the choice box with display names
        profileChoiceBox.getItems().addAll(profileActions.keySet());

        // Add an event listener to handle the selected item's ID
        profileChoiceBox.setOnAction(event -> {
            String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
            String selectedId = profileActions.get(selectedItem);
            // Handle the action based on the selected ID
            if ("profile".equals(selectedId)) {
                profile.goToProfile(event, "ProfileClient");
            } else if ("logout".equals(selectedId)) {
                session.logOut("1");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        try {
            this.readyProductsList = this.readyProductService.getAllReadyProducts();
        } catch (SQLException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<ReadyProduct> items = FXCollections.observableArrayList(
                this.readyProductsList.stream().collect(Collectors.toList())
        );

        this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productUserColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category_ID"));

        this.readyProductsTableView.setItems(items);

        this.operationColumn.setCellFactory(param -> new TableCell<ReadyProduct, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    try {
                        ReadyProduct prod = getTableView().getItems().get(getIndex());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/EditReadyProduct.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        EditReadyProductController controller = loader.getController();
                        controller.setUpData(String.valueOf(prod.getReadyProductId()));
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    }

                });

                deleteButton.setOnAction(event -> {
                    ReadyProduct prod = getTableView().getItems().get(getIndex());
                    try {
                        int isDeleted = readyProductService.deleteReadyProduct(prod.getReadyProductId());
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        readyProductsList = readyProductService.getAllReadyProducts();
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ObservableList<ReadyProduct> items = FXCollections.observableArrayList(
                            readyProductsList.stream().collect(Collectors.toList())
                    );
                    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productUserColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
                    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category_ID"));

                    readyProductsTableView.setItems(items);

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

        try {
            this.productReviewsList = this.readyProductService.getAllProductReviews();
        } catch (SQLException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<ProductReview> items2 = FXCollections.observableArrayList(
                this.productReviewsList.stream().collect(Collectors.toList())
        );

        this.reviewUserColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        this.ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        this.productReviewsTableView.setItems(items2);

        this.operationColumn2.setCellFactory(param -> new TableCell<ProductReview, Void>() {
            private final Button deleteButton2 = new Button("Delete");

            {
                deleteButton2.setOnAction(event -> {
                    ProductReview prod = getTableView().getItems().get(getIndex());
                    try {
                        int isDeleted = readyProductService.deleteProductReview(prod.getReviewId());
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        productReviewsList = readyProductService.getAllProductReviews();
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ObservableList<ProductReview> items = FXCollections.observableArrayList(
                            productReviewsList.stream().collect(Collectors.toList())
                    );
                    reviewUserColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
                    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                    textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
                    ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
                    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

                    productReviewsTableView.setItems(items2);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(deleteButton2);
                    buttons.setSpacing(20);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
        try {
            this.categoriesList = this.categoriesService.getAllCategories();
        } catch (SQLException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Categories> items3 = FXCollections.observableArrayList(
                this.categoriesList.stream().collect(Collectors.toList())
        );

        this.catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.categoriesTableView.setItems(items3);

        this.operationColumn3.setCellFactory(param -> new TableCell<Categories, Void>() {
            private final Button editButton3 = new Button("Edit");
            private final Button deleteButton3 = new Button("Delete");

            {
                editButton3.setOnAction(event -> {
                    try {
                        Categories cat = getTableView().getItems().get(getIndex());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/EditCategory.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    }

                });

                deleteButton3.setOnAction(event -> {
                    Categories cat = getTableView().getItems().get(getIndex());
                    try {
                        int isDeleted = categoriesService.deleteCategories(cat.getCategories_ID());
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        categoriesList = categoriesService.getAllCategories();
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ObservableList<Categories> items = FXCollections.observableArrayList(
                            categoriesList.stream().collect(Collectors.toList())
                    );
                    catNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

                    categoriesTableView.setItems(items3);

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(editButton3, deleteButton3);
                    buttons.setSpacing(20);
                    buttons.setAlignment(Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
    }

    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addProduct.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddReadyProduct.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public void onAddCat(ActionEvent event) {
        try {
            Stage stage = (Stage) addCategory.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddCategory.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Category");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

//    @FXML
//    private void onSearch(ActionEvent event) throws SQLException {
//        String keyword = search.getText();
//        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
//        this.vBox.getChildren().clear();
//        matchingProducts.forEach(rP -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
//                Parent root = loader.load();
//                AdminCardController controller = loader.getController();
//                controller.setReadyProduct(rP, this);
//                root.setId("" + rP.getReadyProductId());
//                this.vBox.getChildren().add(root);
//            } catch (IOException e) {
//                System.out.print(e.getCause());
//            } catch (SQLException ex) {
//                Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//    }
    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/AdminBoard.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
