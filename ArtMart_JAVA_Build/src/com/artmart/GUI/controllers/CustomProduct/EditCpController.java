package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.ProductDao;
import com.artmart.models.Categories;
import com.artmart.models.CustomProduct;
import com.artmart.models.HasCategory;
import com.artmart.models.HasTag;
import com.artmart.models.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private CustomProduct cp;
    private Product viewProduct = new Product();
    private CustomproductslistController controller = new CustomproductslistController();
    private CustomProductCardController x = new CustomProductCardController();
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

    public void setUpData(String b_ID) throws SQLException {
        int id = Integer.parseInt(b_ID);
        CustomProduct viewBlog = customProductDao.getCustomProductById(id);
        this.nameField.setText(viewBlog.getName());
        this.descField.setText(viewBlog.getDescription());        
        this.dimField.setText(viewBlog.getDimensions());
        this.weightField.setText(Float.toString(viewBlog.getWeight()));
        this.materialField.setText(viewBlog.getMaterial());
        this.imageField.setText(viewBlog.getImage());
        this.categoryComboBox.getSelectionModel().select(viewBlog.getCategoryId());

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
    private void edit(ActionEvent event) throws SQLException, IOException, AddressException, MessagingException {
        // Get the user inputs
        String name = nameField.getText();
        String desc = descField.getText();
        String dim = dimField.getText();
        String weightText = weightField.getText();
        String material = materialField.getText();
        Categories category = categoryComboBox.getValue();
        String imagePath = imageField.getText();

        // Validate the user inputs
        if (name.isEmpty() || desc.isEmpty() || dim.isEmpty() || weightText.isEmpty() || material.isEmpty() || category == null || imagePath.isEmpty()) {
            // Display an error message if any of the fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
            return;
        }

        // Validate the weight input
        float weight = 0.0f;
        try {
            weight = Float.parseFloat(weightText);
        } catch (NumberFormatException e) {
            // Display an error message if the weight is not a valid float
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid weight.");
            alert.showAndWait();
            return;
        }

        // Get the selected image file path
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }

        // create a new product object with the updated values
        Product u = new Product(category.getCategories_ID(), name, desc, dim, weight, material, imagePath);
        // update the product using the ID of the custom product
        boolean a = productDao.updateProduct(this.customProductId, u);
        if (a) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Product updated with category: " + category.getName()); // display the name of the category in the message
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
    FileChooser fileChooser = new FileChooser();
    private File selectedImageFile;
    private boolean testImg = false;
    private final String phpUrl = "http://localhost/PIDEV/upload.php";
    String boundary = "---------------------------12345";

    @FXML
    private void chooseImage(ActionEvent event) throws IOException {

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.fileChooser.setTitle("Select an image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = this.fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            this.testImg = true;
//            Path sourcePath = file.toPath();
            byte[] imageData = Files.readAllBytes(file.toPath());

            URL url = new URL(phpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
            outputStream.write(imageData);
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.flush();
            outputStream.close();

            // Read the response from the PHP script
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            Path destinationPath = Paths.get("C:/xampp/htdocs/PIDEV/BlogUploads/" + file.getName());

            this.imageField.setText(destinationPath.toString());

            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Image Upload");
                alert.setHeaderText(null);
                alert.setContentText("Image uploaded successfully.");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An Error occured");
                alert.showAndWait();
            }
        }
    }

}
