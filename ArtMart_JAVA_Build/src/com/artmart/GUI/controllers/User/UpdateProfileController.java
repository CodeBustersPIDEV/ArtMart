package com.artmart.GUI.controllers.User;

import static com.artmart.dao.UserDao.hashPassword;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UpdateProfileController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private DatePicker birthdayField;
    @FXML
    private PasswordField pwdField;
    @FXML
    private TextField Phone_nbrField;
    @FXML
    private TextField bioField;
    @FXML
    private ImageView ProfilePic;
    @FXML
    private Label Label;

    @FXML
    private Button updateBtn;
    private String imageUrl = "";
    UserService user_ser = new UserService();
    User user = new User();
    Artist artist = new Artist();
    private final FileChooser fileChooser = new FileChooser();
    String boundary = "---------------------------12345";
    private Session session = new Session();

    int UserID = session.getUserID("1");

    boolean test2, test1, a;
    int userID;
    Admin admin = new Admin();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUpdate(int id) {
        userID = id;
        user = user_ser.getUser(userID);
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        Phone_nbrField.setText(String.valueOf(user.getPhone_nbr()));
        usernameField.setText(user.getUsername());
        birthdayField.setValue(user.getBirthday().toLocalDate());
        // pwdField.setText(user.getPwd());

        try {
            Image newImage = new Image(user.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }

        if (user.getRole().equals("artist")) {
            bioField.setVisible(true);
            Label.setText("Bio");
            Artist art = user_ser.getArtist(user.getUser_id());
            bioField.setText(art.getBio());

        } else if (user.getRole().equals("admin")) {
            bioField.setVisible(true);
            Label.setText("Department");
            Admin ad = user_ser.getAdmin(user.getUser_id());
            bioField.setText(ad.getDepartment());
        } else {
            bioField.setVisible(false);
        }
    }

    public void Warning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    public void OnUpdate(ActionEvent event) {
        String password;
        String name = nameField.getText();
        String email = emailField.getText();
        Date birthday = java.sql.Date.valueOf(birthdayField.getValue());
        int phoneNumber = Integer.valueOf(Phone_nbrField.getText());
        String username = usernameField.getText();
        if (pwdField.getText().isEmpty()) {
            password = user.getPwd();
            System.out.println(password);
        } else {
            String pwdPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
            password = hashPassword(pwdField.getText());
            if (!password.matches(pwdPattern)) {
                Warning("Password must contain at least one uppercase letter, one digit, and be at least 8 characters long");
            }
        }
        String bio = bioField.getText();
        String picture;
        String emailFormat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        LocalDate currentDate = LocalDate.now();
        if (imageUrl.equals("")) {
            picture = user.getPicture();
            System.out.println(picture);
        } else {
            picture = imageUrl;
        }
        if (!email.matches(emailFormat)) {
            Warning("The email must be valid");
        }

        if (birthdayField.getValue().isAfter(currentDate)) {
            Warning("The birthday date must not exceed today's date");

        }
        if (email.matches(emailFormat) && !birthdayField.getValue().isAfter(currentDate)) {
            User u = new User(phoneNumber, name, email, username, password, birthday, picture);
            System.out.println(password);
            if (user.getRole().equals("artist")) {
                artist = new Artist(u);
                artist.setBio(bio);
                a = user_ser.updateAccountAr(userID, artist);
            } else if (user.getRole().equals("admin")) {
                admin = new Admin(u);
                admin.setDepartment(bio);
                a = user_ser.updateAccountA(userID, admin);
            } else if (user.getRole().equals("client")) {
                Client client = new Client(u);
                a = user_ser.updateAccountC(userID, client);
            }
            if (a) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account updated");
                alert.showAndWait();
                if (user.getRole().equals("artist")) {
                   
                        Stage stage = (Stage) updateBtn.getScene().getWindow();
                        stage.close();
                        
                } else if (user.getRole().equals("admin")) {
                  
                        Stage stage = (Stage) updateBtn.getScene().getWindow();
                        stage.close();
                       
                } else if (user.getRole().equals("client")) {
                
                        Stage stage = (Stage) updateBtn.getScene().getWindow();
                        stage.close();
                        
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Oops!!Can not update account");
                alert.showAndWait();
            }

        }
    }

    @FXML
    public void OnDelete(ActionEvent event
    ) {
        if (user.getRole().equals("artist")) {
            test1 = user_ser.deleteAccountAr(user.getUser_id());
        } else if (user.getRole().equals("client")) {
            test2 = user_ser.deleteAccountC(user.getUser_id());
        }
        if (test1 || test2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Account deleted");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Oops!!Can not delete account");
            alert.showAndWait();
        }

    }

    @FXML
    public void OnUpload(ActionEvent event) throws MalformedURLException, ProtocolException, UnsupportedEncodingException, UnsupportedEncodingException, IOException {

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
            imageUrl = "http://localhost/PIDEV/BlogUploads/" + file.getName();
            int responseCode = connection.getResponseCode();
            Image Image = new Image(imageUrl);
            System.out.println(imageUrl);
            ProfilePic.setImage(Image);
            user.setPicture(imageUrl);
            user_ser.updateAccountU(userID, user);
            System.out.println("Response code: " + responseCode);
        }

    }

}
