package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.GUI.controllers.Product.ArtistReadyProductCardController;
import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.Product.CategoryCardController;
import com.artmart.GUI.controllers.Product.ReadyproductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import com.artmart.models.Categories;
import com.artmart.models.CustomProduct;
import com.artmart.models.HasCategory;
import com.artmart.models.HasTag;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CategoriesService;
import com.artmart.services.CustomProductService;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class CustomproductslistController implements Initializable {

    private final ProductService productService = new ProductService();
    private final CustomProductService customProductService = new CustomProductService();

    @FXML
    private VBox vBox;

    private List<CustomProduct> customProductslist;
    private List<ReadyProduct> readyProductslist;
    private final ReadyProductService readyProductService = new ReadyProductService();
    private User connectedUser = new User();
    @FXML
    private Button searchb;

    @FXML
    private TextField search;
    @FXML
    private RadioButton sweight;
    @FXML
    private RadioButton sname;
    @FXML
    private Button statisticb;
    @FXML
    private Text total;
    @FXML
    private Text totalp;
    HashMap user = (HashMap) Session.getActiveSessions();
    ApplyDao x = new ApplyDao();
    @FXML
    private RadioButton def;

    private VBox vBoxCat;
    private List<Categories> categorieslist;
    private final CategoriesService CategoriesService = new CategoriesService();
    private CategoryCardController categoryCardController;
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private javafx.scene.control.Label username;
    private Session session = new Session();
    int UserID = session.getUserID("1");
    UserService user_ser = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            User connectedUser = user_ser.getUser(UserID);
            username.setText(connectedUser.getUsername());
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

                } else if ("logout".equals(selectedId)) {
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
            this.makeList();
            calculateTotalWeight();
            calculateProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void calculateTotalWeight() throws SQLException {
        float totalWeight = 0;
        for (CustomProduct customProduct : customProductslist) {
            totalWeight += customProduct.getWeight();
        }
        total.setText(String.format("%.2f", totalWeight));
    }

    void calculateProduct() throws SQLException {
        int totalProducts = customProductslist.size();
        totalp.setText(String.format("%d", totalProducts));
    }

    public void makeList() throws SQLException {
        this.vBox.getChildren().clear();
        Session session = Session.getInstance();
        int clientId = session.getCurrentUserId(session.getSessionId());
        this.customProductslist = this.customProductService.getCustomProductsByClientId(clientId);
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void onsearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<CustomProduct> matchingProducts = customProductService.searchCustomProductByName1(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void byweight(ActionEvent event) {

        this.sname.setSelected(false);
        this.sweight.setSelected(true);
        this.def.setSelected(false);
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getWeight));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void byname(ActionEvent event) {

        this.sname.setSelected(true);
        this.sweight.setSelected(false);
        this.def.setSelected(false);
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getName));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void statistic(ActionEvent event) {

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
    private void onexit(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void apply(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyList.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void def(ActionEvent event) throws SQLException {
        this.sname.setSelected(false);
        this.sweight.setSelected(false);
        this.def.setSelected(true);
        this.makeList();
    }

    @FXML
    private void goToInstagram(ActionEvent event) {
        String url = "https://www.instagram.com/soltani_amir_/";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gofb(ActionEvent event) {
        String url = "https://www.facebook.com/amir.soltani.503/";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goR(ActionEvent event) {
        String url = "https://www.reddit.com/search/?q=art";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void add(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/addCustom.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
            Stage currentStage = (Stage) statisticb.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private OrderService orderSerivce= new OrderService();
    @FXML
    private void GoToShoppingCart(ActionEvent event) {
        this.orderSerivce.goToCheckOut();
    }

}
