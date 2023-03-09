/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.services.CustomProductService;
import com.artmart.models.Product;
import com.artmart.models.Session;
import com.artmart.models.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class AddCustomController implements Initializable {

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
    private TextField imageField;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<Categories> categoryComboBox2;
     private final CategoriesDao categoriesDao = new CategoriesDao();
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();

    @FXML
    private Button imageButton;
    @FXML
    private ImageView img;
    private Image image;
  private boolean testImg = false;
     private final String phpUrl = "http://localhost/PIDEV/upload.php";
    String boundary = "---------------------------12345";
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<?> profileChoiceBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          File file = new File("src/com/artmart/GUI/controllers/CustomProduct/artmart.PNG");
       this.image = new Image(file.toURI().toString());
       this.img.setImage(image);
        try {
            
            // Get all categories from the database
            List<Categories> categories = categoriesDao.getAllCategories();

            // Add categories to the ComboBox
            categoryComboBox2.getItems().addAll(categories);

            // Set the ComboBox to display category names
            categoryComboBox2.setCellFactory(new Callback<ListView<Categories>, ListCell<Categories>>() {
                @Override
                public ListCell<Categories> call(ListView<Categories> param) {
                    return new ListCell<Categories>() {
                        @Override
                        protected void updateItem(Categories item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                            } else {
                                setText(item.getName());
                            }
                        }
                    };
                }
            });

            // Set the ComboBox to use the category name as the selected value
            categoryComboBox2.setConverter(new StringConverter<Categories>() {
                @Override
                public String toString(Categories category) {
                    return category == null ? null : category.getName();
                }

                @Override
                public Categories fromString(String categoryName) {
                    return categories.stream()
                            .filter(category -> category.getName().equals(categoryName))
                            .findFirst()
                            .orElse(null);
                }
            });

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

     
 @FXML
private void handleAddButtonAction(ActionEvent event) throws SQLException {
Session session = Session.getInstance();
int clientId = session.getCurrentUserId(session.getSessionId());




    String name = nameField.getText();
    String desc = descField.getText();
    String dim = dimField.getText();
    float weight = 0;
    String weightText = weightField.getText();
    String material = materialField.getText();
    String imagePath = imageField.getText();

    // Get the selected category from the combo box
    Categories selectedCategory = (Categories) categoryComboBox2.getSelectionModel().getSelectedItem();

    // Check if all fields are filled
    if (name.isEmpty() || desc.isEmpty() || dim.isEmpty() || weightText.isEmpty() || material.isEmpty() || imagePath.isEmpty() || selectedCategory == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all fields!");
        alert.showAndWait();
        return;
    }

    try {
        weight = Float.parseFloat(weightText);
    } catch (NumberFormatException ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Invalid weight value!");
        alert.showAndWait();
        return;
    }

    Product baseProduct = new Product(selectedCategory.getCategories_ID(), name, desc, dim, weight, material, imagePath);

    CustomProductService customProductService = new CustomProductService();
   
    int result = customProductService.createCustomProduct(baseProduct,clientId);
    if (result > 0) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Custom product has been added successfully!");
        alert.showAndWait();
    } else {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Add Custom Product");
        alert.setHeaderText(null);
        alert.setContentText("Failed to add custom product!");
        alert.showAndWait();
    }
}


 FileChooser fileChooser = new FileChooser();
    @FXML
    private void handleSelectImageAction(ActionEvent event) throws IOException {
 
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
         
                }
            catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An Error occured");
                alert.showAndWait();
            }
}}

    @FXML
    private void back(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/Customproductslist.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    }
    }