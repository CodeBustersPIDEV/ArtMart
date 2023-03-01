/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import com.artmart.services.UserService;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author 21697
 */
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
        if (pwdField.getText().equals("")) {
            password = user.getPwd();
        } else {
            password = pwdField.getText();
        }
        String bio = bioField.getText();
        String picture;
        String emailFormat = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String pwdPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        LocalDate currentDate = LocalDate.now();
        if (imageUrl.equals("")) {
            picture = user.getPicture();
            System.out.println(picture);
            //out.println(picture);
        } else {
            picture = imageUrl;
        }
        if (!email.matches(emailFormat)) {
            Warning("The email must be valid");
        }
        if (!password.matches(pwdPattern)) {
            Warning("Password must contain at least one uppercase letter, one digit, and be at least 8 characters long");
        }
        if (birthdayField.getValue().isAfter(currentDate)) {
            Warning("The birthday date must not exceed today's date");

        }
        if (email.matches(emailFormat) && password.matches(pwdPattern) && !birthdayField.getValue().isAfter(currentDate)) {
            User u = new User(phoneNumber, name, email, username, password, birthday, picture);

            if (user.getRole().equals("artist")) {
                artist = new Artist(u);
                artist.setBio(bio);
                a = user_ser.updateAccountAr(userID, artist);
            } else if (user.getRole().equals("admin")) {
                admin = new Admin(u);
                admin.setDepartment(bio);
                a = user_ser.updateAccountA(userID, admin);
            } else if (user.getRole().equals("client")) {
                //System.out.println(u.getPicture());
                Client client = new Client(u);
                a = user_ser.updateAccountC(userID, client);
            }
            if (a) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account updated");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Oops!!Can not update account");
                alert.showAndWait();
            }
            try {
                Stage stage = (Stage) updateBtn.getScene().getWindow();
                stage.close();
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/SignUp.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("User Managment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.print(e.getMessage());
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
    public void OnUpload(ActionEvent event) throws MalformedURLException, ProtocolException, UnsupportedEncodingException, UnsupportedEncodingException {

        String serverUrl = "http://localhost/upload_script.php";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image file to upload");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();

                HttpURLConnection connection = (HttpURLConnection) new URL(serverUrl).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "*****");

                String boundary = "*****";

                String fileName = file.getName();
                String mimeType = "image/jpeg";
                String fieldName = "image";

                StringBuilder data = new StringBuilder();
                data.append("--").append(boundary).append("\r\n");
                data.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(fileName).append("\"\r\n");
                data.append("Content-Type: ").append(mimeType).append("\r\n\r\n");

                byte[] headerBytes = data.toString().getBytes("UTF-8");
                byte[] footerBytes = ("\r\n--" + boundary + "--\r\n").getBytes("UTF-8");

                connection.setRequestProperty("Content-Length", String.valueOf(headerBytes.length + file.length() + footerBytes.length));

                connection.connect();

                connection.getOutputStream().write(headerBytes);

                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    connection.getOutputStream().write(buffer, 0, bytesRead);
                }

                connection.getOutputStream().write(footerBytes);

                connection.getOutputStream().flush();
                connection.getOutputStream().close();
                imageUrl = "http://localhost/images/" + fileName;
                int responseCode = connection.getResponseCode();
                //user.setPicture("http://localhost/images/"+fileName);
                Image Image = new Image(imageUrl);
                ProfilePic.setImage(Image);
                user_ser.updateAccountU(userID, user);
                System.out.println("Response code: " + responseCode);

            } catch (IOException ex) {
                Logger.getLogger(UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
