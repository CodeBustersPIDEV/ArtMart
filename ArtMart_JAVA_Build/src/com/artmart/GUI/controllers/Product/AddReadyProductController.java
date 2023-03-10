package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Categories;
import com.artmart.models.Product;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.OrderService;
import com.artmart.services.ReadyProductService;
import com.artmart.services.UserService;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AddReadyProductController implements Initializable {

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
    private TextField userF;
    @FXML
    private TextField materialF;
    @FXML
    private ComboBox<Categories> categoryF;
    @FXML
    private TextField imageField;
    @FXML
    private Button add;
    private final CategoriesDao categoriesDao = new CategoriesDao();
    @FXML
    private Button backBtn;
    @FXML
    private Button uploadImage;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button profileButton;

    @FXML
    private Label profileLabel;
    @FXML
    private Label username;

    ReadyProductService readyProductService = new ReadyProductService();
    UserService user_ser = new UserService();

    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();
    private final String phpUrl = "http://localhost:88/PIDEV/upload.php";
    String boundary = "---------------------------12345";
    private boolean testImg = false;
    private final FileChooser fileChooser = new FileChooser();
    int UserID = session.getUserID("1");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.session = (Session) user.get(user.keySet().toArray()[0]);
            this.connectedUser = this.userService.getUser(this.session.getUserId());
            this.username.setText(this.connectedUser.getName());
            // Create a map of display names to IDs
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
            // Get all categories from the database
            List<Categories> categories = categoriesDao.getAllCategories();

            // Add categories to the ComboBox
            categoryF.getItems().addAll(categories);

            // Set the ComboBox to display category names
            categoryF.setCellFactory(new Callback<ListView<Categories>, ListCell<Categories>>() {
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

            categoryF.setConverter(new StringConverter<Categories>() {
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
    private void onAddNew(ActionEvent event) throws SQLException, IOException, MessagingException {
        String name = nameF.getText();
        String description = descriptionF.getText();
        String dimensions = dimensionsF.getText();
        float weight = Float.parseFloat(weightF.getText());
        String material = materialF.getText();
        String imagePath = imageField.getText();
        int price = Integer.parseInt(priceF.getText());
        int us = this.connectedUser.getUser_id();

        // Get the selected category from the combo box
        Categories selectedCategory = (Categories) categoryF.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Please select a category!");
            alert.showAndWait();
            return;
        }

        Product basePr = new Product(selectedCategory.getCategories_ID(), name, description, dimensions, weight, material, imagePath);

        ReadyProduct readyPr = new ReadyProduct(basePr, price, us);
        System.out.println(readyPr);
        int result = readyProductService.createReadyProduct(readyPr);
        if (result > 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Ready product has been added successfully!");
            alert.showAndWait();

            //test email
            String productName = nameF.getText();
            User u = user_ser.getUser(this.session.getUserId());
            String email = this.connectedUser.getEmail();
            System.out.println("test begin");
            readyProductService.sendEmail(email, "New product added", readyProductService.generateVerificationEmail(u.getName(), productName));
            System.out.println("test reached");

            // Close the current window
            Stage stage = (Stage) add.getScene().getWindow();
            stage.close();

            // Load and display the ArtistReadyProductsList interface
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ArtistReadyProductsList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } else {
            // Display an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Add Ready Product");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add ready product!");
            alert.showAndWait();
        }

    }

    @FXML
    private void onUpload(ActionEvent event) throws IOException {
        String serverUrl = "http://localhost/upload_script.php";

        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.fileChooser.setTitle("Select an image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = this.fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
//            Path sourcePath = file.toPath();
            byte[] imageData = Files.readAllBytes(file.toPath());

            URL url = new URL(serverUrl);
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

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            String imageUrl = "http://localhost/PIDEV/BlogUploads/" + file.getName();

            this.imageField.setText(imageUrl);

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

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
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
