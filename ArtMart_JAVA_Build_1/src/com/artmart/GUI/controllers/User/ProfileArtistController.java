package com.artmart.GUI.controllers.User;

import static com.artmart.utils.GenerateQRCode.generateQRcode;
import com.artmart.models.Artist;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.UserService;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class ProfileArtistController implements Initializable {

    @FXML
    private ImageView ProfilePic;
    @FXML
    private ImageView QrCode;
    @FXML
    private Text usernameProfile;
    @FXML
    private Label nameProfile;
    @FXML
    private Label emailProfile;
    @FXML
    private Label nbrArtProfile;
    @FXML
    private Label bioProfile;
    @FXML
    private Label birthdayProfile;
    @FXML
    private Button editProfileBtn;
   
    @FXML
    private Label phoneProfile;
    @FXML
    private Button logout;

    private Session session = new Session();
    int UserID = session.getUserID("1");
    private Artist artist = new Artist();
    UserService user_ser = new UserService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setProfile(int id) {
       
        User test = user_ser.getUser(UserID);
        if (test.getRole().equals("admin")) {
            logout.setVisible(false);
        }
        artist = user_ser.getArtist(id);
        nameProfile.setText(artist.getName());
        usernameProfile.setText(artist.getUsername());
        emailProfile.setText(artist.getEmail());
        bioProfile.setText(artist.getBio());
        phoneProfile.setText(String.valueOf(artist.getPhone_nbr()));
        nbrArtProfile.setText(String.valueOf(artist.getNbr_artwork()));
        birthdayProfile.setText(artist.getBirthday().toString());
        try {
            Image newImage = new Image(artist.getPicture());
            //   System.out.println(artist.getPicture());
            ProfilePic.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
    }

    @FXML
    public void OnLogOut(ActionEvent event) {
        session.logOut("1");
        Stage stage = (Stage) logout.getScene().getWindow();
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

    

    @FXML

    public void OnUpdateBtn(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/updateProfile.fxml"));
            Parent root = loader.load();
            UpdateProfileController controller = loader.getController();
            controller.setUpdate(artist.getUser_id());
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    public void OnQrCode(ActionEvent event) throws IOException, WriterException {
        //data that we want to store in the QR code  
        String str = String.valueOf(artist.getArtist_id());
//path where we want to get QR Code  
        String path = "C:\\Users\\21697\\OneDrive\\Documents\\GitHub\\ArtMart\\QRCode\\Quote" + artist.getArtist_id() + ".png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability  
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//invoking the user-defined method that creates the QR code  
        generateQRcode(str, path, charset, hashMap, 200, 200);
        System.out.println("QR Code created successfully.");
        try {
            Image newImage = new Image("file:/C:/Users/21697/OneDrive/Documents/GitHub/ArtMart/QRCode/Quote" + artist.getArtist_id() + ".png");
            //   System.out.println(artist.getPicture());
            QrCode.setImage(newImage);
        } catch (Exception e) {
            System.out.println("Error setting image: " + e.getMessage());
        }
    }
}
