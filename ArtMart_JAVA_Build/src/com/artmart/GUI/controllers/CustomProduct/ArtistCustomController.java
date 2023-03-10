package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.models.Artist;
import com.artmart.models.CustomProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.CustomProductService;
import com.artmart.services.ProductService;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ArtistCustomController implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private Button searchb;
    @FXML
    private TextField search;
    @FXML
    private RadioButton sweight;
    @FXML
    private RadioButton sname;
    @FXML
    private Text totalp;
    private final ProductService productService = new ProductService();
    private final CustomProductService customProductService = new CustomProductService();
    private List<CustomProduct> customProductslist;
    private Session session = new Session();
    int UserID = session.getUserID("1");
    UserService user_ser = new UserService();
    private Artist loggedInArtist;
    @FXML
    private Button apply;
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private RadioButton SDEF;

    public void setLoggedInArtist(Artist artist) {
        this.loggedInArtist = artist;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User connectedUser = user_ser.getUser(UserID);
        username.setText(connectedUser.getUsername());
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
        try {

            this.makeList();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistCustomController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            calculateProduct();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistCustomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void calculateProduct() throws SQLException {
        int totalProducts = customProductslist.size();
        totalp.setText(String.format("%d", totalProducts));
    }

    public void makeList() throws SQLException {
        this.vBox.getChildren().clear();
        this.customProductslist = this.customProductService.getAllCustomProducts();
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCard.fxml"));
                Parent root = loader.load();
                ArtistCardController controller = loader.getController();
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
        List<CustomProduct> matchingProducts = customProductService.searchCustomProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCard.fxml"));
                Parent root = loader.load();
                ArtistCardController controller = loader.getController();
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
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getWeight));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCard.fxml"));
                Parent root = loader.load();
                ArtistCardController controller = loader.getController();
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
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getName));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/ArtistCard.fxml"));
                Parent root = loader.load();
                ArtistCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(ArtistCustomController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void onexit(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void apply(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/artmart/GUI/views/CustomProduct/applyListartist.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
