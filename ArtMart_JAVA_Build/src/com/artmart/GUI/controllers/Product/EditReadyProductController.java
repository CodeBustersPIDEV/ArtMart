/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
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
    private TextField userID;
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
    private ReadyproductsListController controller = new ReadyproductsListController();
    private final ProductDao productDao = new ProductDao();
    private final CategoriesDao categoriesDao = new CategoriesDao();
    // variable to hold the ID of the ready product
    private int readyProductId;
    @FXML
    private Button backBtn;
    @FXML
    private Button uploadImage;
    @FXML
    private Label username;

    private File selectedImageFile;

    private ReadyProduct viewProd = new ReadyProduct();
    private int id;

    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());
        populateComboBox();
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
            this.userID.setText(this.connectedUser.getName());
            this.id = Integer.parseInt(this.prodID.getText());
            ReadyProductService readyProductService = new ReadyProductService();

            int productId = readyProductService.getReadyProductId(id);
            if (productId == 0) {
                throw new SQLException("Failed to get product ID from database");
            }
            ProductService productService = new ProductService();
            Product product = productService.getProductById(productId);

            this.viewProd = convertToReadyProduct(product);

            this.nameF.setText(this.viewProd.getName());
            this.descriptionF.setText(this.viewProd.getDescription());
            this.dimensionsF.setText(this.viewProd.getDimensions());
            this.weightF.setText(Float.toString(this.viewProd.getWeight()));
            this.priceF.setText(Float.toString(this.viewProd.getPrice()));
            this.materialF.setText(this.viewProd.getMaterial());
            this.imageField.setText(this.viewProd.getImage());
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
        String material = materialF.getText();
        Categories category = categoryF.getValue();
        String categoryName = category.getName(); // get the name of the selected category
        int price = Integer.parseInt(priceF.getText());

        // Get the selected image file path
        String imagePath = imageField.getText();
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }

        // create a new product object with the updated values
        ReadyProduct u = new ReadyProduct(category.getCategories_ID(), name, description, dimensions, weight, material, imagePath, price);
        // update the product using the ID of the custom product
        boolean a = productDao.updateProduct(this.readyProductId, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product updated with category: " + categoryName); // display the name of the category in the message
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
        // Fetch the product from the database using the product ID
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
