package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.ProductDao;
import com.artmart.models.Categories;
import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class EditCpController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField descField;
    @FXML
    private TextField dimField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField materialField;
    @FXML
    private ComboBox<Categories> categoryComboBox;
    @FXML
    private TextField imageField;
    @FXML
    private Button edit_cp;

    private Product viewProduct = new Product();
    private CustomproductslistController controller = new CustomproductslistController();
    private final ProductDao productDao = new ProductDao();
    private final CategoriesDao categoriesDao = new CategoriesDao();
    @FXML
    private Label prodid;

    // variable to hold the ID of the custom product
    private int customProductId;
    @FXML
    private Button backBtn;
    @FXML
    private Button chooseImageButton;
    @FXML
    private ImageView img;
    private Image image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          File file = new File("src/com/artmart/GUI/controllers/CustomProduct/artmart.PNG");
       this.image = new Image(file.toURI().toString());
       this.img.setImage(image);
        populateComboBox();
    }

    private void populateComboBox() {
        try {
            List<Categories> categories = categoriesDao.getAllCategories();
            categoryComboBox.getItems().addAll(categories);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to get categories from database");
            alert.showAndWait();
        }
    }

   @FXML
private void edit(ActionEvent event) throws SQLException, IOException {
    String name = nameField.getText();
    String desc = descField.getText();
    String dim = dimField.getText();
    float weight = Float.parseFloat(weightField.getText());
    String material = materialField.getText();
    Categories category = categoryComboBox.getValue();
    String categoryName = category.getName(); // get the name of the selected category
    
    // Get the selected image file path
    String imagePath = imageField.getText();
    if (selectedImageFile != null) {
        imagePath = selectedImageFile.getAbsolutePath();
    }
    if (name.isEmpty() || desc.isEmpty() || dim.isEmpty() || material.isEmpty()|| imagePath.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all fields.");
        alert.showAndWait();
        return;
    }
    // create a new product object with the updated values
    Product u = new Product(category.getCategories_ID(), name, desc, dim, weight, material, imagePath);
    // update the product using the ID of the custom product
    boolean a = productDao.updateProduct(this.customProductId, u);
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

     private CustomProductDao customProductDao = new CustomProductDao();
    private CustomProduct product;
public void setProductId(int productId) throws SQLException {
    this.customProductId = productId;
    // Fetch the product from the database using the product ID
    this.product = customProductDao.getCustomProductById(productId);
}

@FXML
private void goBack(ActionEvent event) throws IOException, SQLException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
    Parent root = loader.load();
    CustomproductslistController controller = loader.getController();
    controller.makeList();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

    private File selectedImageFile;

@FXML
private void chooseImage(ActionEvent event) {
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



}