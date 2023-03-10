/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.Product.EditReadyProductController;
import com.artmart.GUI.controllers.Product.ReadyproductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Apply;
import com.artmart.models.Categories;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CategoriesService;
import com.artmart.services.ProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AdminBoardController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<CustomProduct> readyProductsTableView;
    @FXML
    private TableColumn<CustomProduct, String> productNameColumn;
    @FXML
    private TableColumn<CustomProduct, Integer> productUserColumn;
    @FXML
    private TableColumn<CustomProduct, Void> operationColumn;
    @FXML
    private TableView<Categories> categoriesTableView;
    @FXML
    private TableColumn<Categories, String> catNameColumn;
    @FXML
    private TableColumn<Categories, Void> operationColumn3;
       private final CustomProductDao readyProductService = new CustomProductDao();
      private final ProductService productService = new ProductService();
    private final CategoriesService categoriesService = new CategoriesService();
      HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    private List<CustomProduct> readyProductsList;
        private List<Apply> appliesList;
    private List<Product> productsList;
    private List<ProductReview> productReviewsList;
    private List<Categories> categoriesList;
    int UserID = session.getUserID("1");
    @FXML
    private Button addCategory;
    @FXML
    private Button addCategory11;
    @FXML
    private Button refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());

        // Create a map of display names to IDs
        Map<String, String> profileActions = new HashMap<>();
        profileActions.put("", "");
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

                profileChoiceBox.setValue("");
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
    //            this.productsList = this.productService.getProductById(UserID)
            this.readyProductsList = this.readyProductService.getAllCustomProducts();
        } catch (SQLException ex) {
            Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<CustomProduct> items = FXCollections.observableArrayList(
                this.readyProductsList.stream().collect(Collectors.toList())
        );
        
        this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        this.productUserColumn.setCellValueFactory(new PropertyValueFactory<>("clientID"));
    


        this.readyProductsTableView.setItems(items);

        this.operationColumn.setCellFactory(param -> new TableCell<CustomProduct, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    try {
                        CustomProduct prod = getTableView().getItems().get(getIndex());
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Editcp.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        EditCpController controller = loader.getController();
                        controller.setUpData(String.valueOf(prod.getCustomProductId()));
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    } catch (SQLException ex) {
                        Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

                deleteButton.setOnAction(event -> {
                    CustomProduct prod = getTableView().getItems().get(getIndex());
                    try {
                        int isDeleted = readyProductService.deleteCustomProduct(prod.getCustomProductId());
                    } catch (SQLException ex) {
                        Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        readyProductsList = readyProductService.getAllCustomProducts();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ObservableList<CustomProduct> items = FXCollections.observableArrayList(
                            readyProductsList.stream().collect(Collectors.toList())
                    );
                    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productUserColumn.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
                
              

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
            this.categoriesList = this.categoriesService.getAllCategories();
        } catch (SQLException ex) {
            Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        categoriesList = categoriesService.getAllCategories();
                    } catch (SQLException ex) {
                        Logger.getLogger(com.artmart.GUI.controllers.Product.AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }
    
     @FXML
    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
   

    @FXML
    private void onAddCat(ActionEvent event) {
        
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/AddCategory.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
       
    }
    
    
     @FXML
    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/AdminBoard.fxml"));
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

    @FXML
    private void stat(ActionEvent event) {
         try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ProductStatistics.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void app(ActionEvent event) {
          try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyalllist.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void GoToShoppingCart(ActionEvent event) {
    }
}
