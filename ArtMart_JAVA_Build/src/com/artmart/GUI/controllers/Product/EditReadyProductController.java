/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ProductDao;
import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.ProductService;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class EditReadyProductController implements Initializable {

    @FXML
    private Label prodID;
    @FXML
    private Label userID;
    @FXML
    private TextField nameF;
    @FXML
    private TextArea descriptionF;
    @FXML
    private TextField dimensionsF;
    @FXML
    private TextField weightF;
    @FXML
    private TextField priceF;
    @FXML
    private TextField materialF;
    @FXML
    private ComboBox<Categories> categoryF;
    @FXML
    private TextField imageField;
    @FXML
    private Button edit;
    @FXML
    private Button backBtn;
    @FXML
    private Button uploadImage;
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    private File selectedImageFile;
    private ReadyproductsListController controller = new ReadyproductsListController();
    private final ProductDao productDao = new ProductDao();
    private final ReadyProductDao rp = new ReadyProductDao();
    private final CategoriesDao categoriesDao = new CategoriesDao();
    private int readyProductId;
    private ReadyProduct viewProd = new ReadyProduct();
    private int id;
    UserService user_ser = new UserService();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    int UserID = session.getUserID("1");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());
        populateComboBox();
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

    private void populateComboBox() {
        try {
            List<Categories> categories = categoriesDao.getAllCategories();
            categoryF.getItems().addAll(categories);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to get categories from database");
            alert.showAndWait();
        }
    }

    public void setUpData(String pid) {
        try {
            this.prodID.setText(pid);
            this.readyProductId = Integer.valueOf(pid);
            this.userID.setText(this.connectedUser.getName());
            this.id = Integer.parseInt(this.prodID.getText());
            ReadyProductService readyProductService = new ReadyProductService();
            int productId = readyProductService.getReadyProductId(id);
            if (productId == 0) {
                throw new SQLException("Failed to get product ID from database");
            }
            ProductService productService = new ProductService();
            Product product = productService.getProductById(productId);
            this.viewProd = rp.getReadyProductById(productId);
            int price = this.viewProd.getPrice();
            this.viewProd = convertToReadyProduct(product);
            this.nameF.setText(this.viewProd.getName());
            this.descriptionF.setText(this.viewProd.getDescription());
            this.dimensionsF.setText(this.viewProd.getDimensions());
            this.weightF.setText(Float.toString(this.viewProd.getWeight()));
            this.materialF.setText(this.viewProd.getMaterial());
            this.imageField.setText(this.viewProd.getImage());
            this.priceF.setText("" + price);
            int categoryId = this.viewProd.getCategoryId();
            System.out.println(this.viewProd);
            this.categoryF.getSelectionModel().select(categoriesDao.getCategoriesById(categoryId));
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to get data from database");
            alert.showAndWait();
        }
    }

    public static ReadyProduct convertToReadyProduct(Product product) {
        ReadyProduct readyProduct = new ReadyProduct();
        readyProduct.setProductId(product.getProductId());
        readyProduct.setName(product.getName());
        readyProduct.setDescription(product.getDescription());
        readyProduct.setDimensions(product.getDimensions());
        readyProduct.setWeight(product.getWeight());
        readyProduct.setCategoryId(product.getCategoryId());
        readyProduct.setMaterial(product.getMaterial());
        readyProduct.setImage(product.getImage());
        return readyProduct;
    }

    @FXML
    private void onEdit(ActionEvent event) throws SQLException, IOException {
        String name = nameF.getText();
        String description = descriptionF.getText();
        String dimensions = dimensionsF.getText();
        float weight = Float.parseFloat(weightF.getText());
        int price = Integer.parseInt(priceF.getText());
        String material = materialF.getText();
        Categories category = categoryF.getValue();
        String imagePath = imageField.getText();
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }
        ReadyProduct u = new ReadyProduct(this.viewProd.getProductId(),category.getCategories_ID(), name, description, dimensions, weight, material, imagePath, price);
        boolean a = productDao.updateProduct(u.getProductId(), u);
        boolean b = this.readyProductDao.updateReadyProduct(readyProductId, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product updated !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops! Can not update product");
            alert.showAndWait();
        }
    }

    private ReadyProductDao readyProductDao = new ReadyProductDao();
    private ReadyProduct pr;

    public void setProductId(int productId) throws SQLException {
        this.readyProductId = productId;
        this.pr = readyProductDao.getReadyProductById(productId);
    }

    @FXML
    private void onUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            imageField.setText(selectedImageFile.getAbsolutePath());
        }
    }

    @FXML
    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
