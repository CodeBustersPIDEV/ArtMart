package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ProductReviewDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.ProductReview;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.OrderService;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ReviewsListController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private VBox vBox;
    @FXML
    private Button backBtn;
    private List<ProductReview> productReviewsList;
    ReadyProduct viewProd = new ReadyProduct();
    int pageId;
    @FXML
    private Label pid;
    @FXML
    private Text name;
    @FXML
    private Text nb;
    @FXML
    private Label connUsername;
    @FXML
    private Label username;
    @FXML
    private Label category;
    @FXML
    private Text price;
    @FXML
    private Button addReview;
    @FXML
    private Button order;
    @FXML
    private ImageView imagePreview;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");
    UserService user_ser = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.connUsername.setText(this.connectedUser.getName());

        String role = this.connectedUser.getRole();
        System.out.println(role);
        if (role.equals("artist")) {
            addReview.setVisible(false);
            order.setVisible(false);
        }

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
                User u = user_ser.getUser(UserID);
                if (u.getRole().equals("admin")) {
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
                } else if (u.getRole().equals("artist")) {
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
                } else if (u.getRole().equals("client")) {
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
    }

    public void setProductId(int pid) throws SQLException {
        pageId = pid;
        this.displayList();
    }

    public void setReadyProduct(ReadyProduct param) throws SQLException {
        this.viewProd = param;
        this.pid.setText("" + pageId);
        CategoriesDao c = new CategoriesDao();
        Categories cat = c.getCategoriesById(this.viewProd.getCategoryId());
        String catName = cat.getName();
        this.category.setText(catName);
        this.price.setText(this.viewProd.getPrice() + "");
        ProductReviewDao revCount = new ProductReviewDao();
        int count = revCount.getProductReviewCountByProdId(pageId);
        this.nb.setText("" + count);

        UserDao u = new UserDao();
        int uID = this.viewProd.getUserId();
        User user = u.getUser(uID);
        String userN = user.getName();
        this.username.setText("" + userN);

        // Load the image from the file path stored in ReadyProduct object's image field
        Image image = new Image(this.viewProd.getImage());
        this.imagePreview.setImage(image);

        this.name.setText(viewProd.getName());
    }

    public void displayList() throws SQLException {
        this.vBox.getChildren().clear();
        this.productReviewsList = this.readyProductService.getAllProductReviewsByProdId(pageId);
        this.productReviewsList.forEach(pr -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReviewCard.fxml"));
                Parent root = loader.load();
                ReviewCardController controller = loader.getController();
                controller.setProductReview(pr);
                root.setId("" + pr.getReviewId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }

    @FXML
    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addReview.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddReview.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Product Review");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/readyproductslist.fxml"));
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
    public void onOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Place an Order");
        alert.setHeaderText(null);

        // Create custom layout
        Label quantityLabel = new Label("Quantity:");
        TextField quantityTextField = new TextField();
        VBox vbox = new VBox(quantityLabel, quantityTextField);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Set custom layout to dialog pane
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                int quantity = Integer.parseInt(quantityTextField.getText());
                OrderService orderService = new OrderService();
                orderService.addToShoppingCart(viewProd, quantity, viewProd.getPrice());
            }
        });
    }

}
