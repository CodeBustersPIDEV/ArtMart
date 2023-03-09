package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CategoriesService;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ArtistReadyProductsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private TextField search;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    private List<ReadyProduct> readyProductslist;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private VBox vBoxCat;
    private List<Categories> categorieslist;
    private final CategoriesService CategoriesService = new CategoriesService();
    private CategoryCardController categoryCardController;

    @FXML
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");
    @FXML
    private Button addCategory;
    @FXML
    private Button refresh;
    
    @FXML
    private Button goToBlogs;
    @FXML
    private Button btnGoToEvents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.session = (Session) user.get(user.keySet().toArray()[0]);
            this.connectedUser = this.userService.getUser(this.session.getUserId());
            this.username.setText(this.connectedUser.getName());
            this.displayList();
            this.makeList();
            Map<String, String> profileActions = new HashMap<>();
            profileActions.put("", "");
            profileActions.put("Logout", "logout");
            profileActions.put("Profile", "profile");
            profileChoiceBox.getItems().addAll(profileActions.keySet());
            profileChoiceBox.setOnAction(event -> {
                String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
                String selectedId = profileActions.get(selectedItem);
                if ("profile".equals(selectedId)) {

                    profileChoiceBox.setValue("");
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
                } else if ("Logout".equals(selectedItem)) {
                    session.logOut("1");
                    Stage stage = (Stage) profileChoiceBox.getScene().getWindow();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayList() throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProducts(this.connectedUser.getUser_id());
        if (this.readyProductslist.isEmpty()) {
            Label emptyLabel = new Label("No products found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            this.readyProductslist.forEach(rp -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                    Parent root = loader.load();
                    ArtistReadyProductCardController controller = loader.getController();
                    controller.setReadyProduct(rp, this);
                    root.setId("" + rp.getReadyProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                } catch (SQLException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public void makeList() throws SQLException {
        this.vBoxCat.getChildren().clear();
        this.categorieslist = this.CategoriesService.getAllCategories();
        if (this.categorieslist.isEmpty()) {
            Label emptyLabel = new Label("No categories found.");
            this.vBox.getChildren().add(emptyLabel);
        } else {
            this.categorieslist.forEach(category -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/CategoryCard.fxml"));
                    Node categoryCard = loader.load();
                    CategoryCardController controller = loader.getController();
                    controller.setCategories(category, this);
                    categoryCard.setId("" + category.getCategories_ID());
                    Label categoryLabel = controller.getCategoryLabel();
                    categoryLabel.setOnMouseClicked(event -> {
                        String categoryName = controller.getCategoryName();
                        try {
                            sortByCategoryName(categoryName, category.getCategories_ID());
                        } catch (SQLException ex) {
                            Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    this.vBoxCat.getChildren().add(categoryCard);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });
        }
    }

    @FXML
    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addProduct.getScene().getWindow();
            stage.close();
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

    @FXML
    public void onAddCategory(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddCategory.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onSearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(rP -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rP, this);
                root.setId("" + rP.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void sortByCategoryName(String categoryName, int uID) throws SQLException {
        this.vBox.getChildren().clear();
        this.readyProductslist = this.readyProductService.getAllReadyProductsByCategoryName(categoryName, this.connectedUser.getUser_id());
        this.readyProductslist.forEach(rp -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductCard.fxml"));
                Parent root = loader.load();
                ArtistReadyProductCardController controller = loader.getController();
                controller.setReadyProduct(rp, this);
                root.setId("" + rp.getReadyProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

   

    @FXML
    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
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
    private void goToBlogs(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Blog/Blog.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onGoToEvents(ActionEvent event) {
        try {
            Stage stage = (Stage) btnGoToEvents.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/home_artist.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }
    @FXML
    private void custom(ActionEvent event) {
    try {
        Stage stage = (Stage) searchBtn.getScene().getWindow();
        stage.close();
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCustom.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Custom Product Managment");
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}    

