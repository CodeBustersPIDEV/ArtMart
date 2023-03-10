package com.artmart.GUI.controllers.Event.Artist.Event;

import com.artmart.GUI.controllers.Event.Map.AddMapController;
import com.artmart.GUI.controllers.Event.User.Event.UserViewEventController;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.EventService;
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
import java.sql.Date;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddEventController implements Initializable {

    private final EventService es = new EventService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());

    private String name;
    private String location;
    private String type;
    private String description;
    private double entryFee;
    private int capacity;
    private Date startDate;
    private Date endDate;
    private String img;
    private Image image;

    private String typeText;
    private String entryFeeText;
    private String capacityText;
    private String startDateText;
    private String endDateText;
    private final FileChooser fileChooser = new FileChooser();
    private final String phpUrl = "http://localhost/PIDEV/upload.php";
    String boundary = "---------------------------12345";

    @FXML
    private Button btnAddEvent;
    @FXML
    private Button btnCancelEvent;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox comboBoxType = new ComboBox();
    @FXML
    private TextField txtCapacity;
    @FXML
    private TextField txtEntryFee;
    @FXML
    private TextArea txtAreaDescription;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private TextField txtImage;
    @FXML
    private ComboBox comboBoxEventStatus = new ComboBox();
    @FXML
    private Button btnAddImage;
    @FXML
    private Button btnMap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // add event types in comboBox
        this.comboBoxType.getItems().addAll("Auction", "Art fair", "Open Gallery", "Exhibition");
        this.comboBoxEventStatus.getItems().addAll("Scheduled", "Started", "Finished", "Cancelled");
        this.txtAreaDescription.setWrapText(true);

    }

    @FXML
    private void onAddEvent(ActionEvent event) {

        // get the values for creating a new event
        this.name = this.txtName.getText();
        this.location = this.txtLocation.getText();
        this.description = this.txtAreaDescription.getText();

        // convert values for input check
        this.typeText = (String) this.comboBoxType.getValue();
        this.entryFeeText = this.txtEntryFee.getText();
        this.capacityText = this.txtCapacity.getText();
        this.startDateText = this.dpStartDate.getValue() != null ? String.valueOf(this.dpStartDate.getValue()) : null;
        this.endDateText = this.dpEndDate.getValue() != null ? String.valueOf(this.dpEndDate.getValue()) : null;

        // input check
        if (this.name.isEmpty()
                || this.location.isEmpty()
                || this.description.isEmpty()
                || this.typeText == null || this.typeText.isEmpty()
                || this.entryFeeText.isEmpty()
                || this.capacityText.isEmpty()
                || this.startDateText == null || this.startDateText.isEmpty()
                || this.endDateText == null || this.endDateText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();

        } else {

            this.type = (String) this.comboBoxType.getValue();
            try {
                this.startDate = Date.valueOf(this.dpStartDate.getValue());
                this.endDate = Date.valueOf(this.dpEndDate.getValue());
                startDate.before(endDate);
                if (!startDate.before(endDate)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid dates");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to add event! \nStart date must me before end date!");
                    alert.showAndWait();
                    return;
                }
            } catch (DateTimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid format");
                alert.setHeaderText(null);
                alert.setContentText("Invalid format for start date or end date!");
                alert.showAndWait();
                return;
            }
            try {
                this.entryFee = Float.parseFloat(this.txtEntryFee.getText());
                this.capacity = Integer.parseInt(this.txtCapacity.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid format");
                alert.setHeaderText(null);
                alert.setContentText("Invalid format for entry fee or capacity!");
                alert.showAndWait();
                return;
            }
            Event ev = new Event(
                    this.userID,
                    this.name,
                    this.location,
                    this.type,
                    this.description,
                    this.entryFee,
                    this.capacity,
                    this.startDate,
                    this.endDate,
                    this.img
            );

            int result = es.createEvent(ev);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("A new event has been added successfully!");
                alert.showAndWait();
                alert.close();
                this.returnToEventHomepage(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add event!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void returnToEventHomepage(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCancelEvent.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/list_event.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onBtnAddImage(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.fileChooser.setTitle("Select an image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = this.fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
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
            this.img = destinationPath.toString();
            this.txtImage.setText(this.img);
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

//    private void onMap(MouseEvent event) {
//        
//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Map/add_map.fxml"));//com/artmart/GUI/views/Event/Map/add_map.fxml
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }   
    @FXML
    private void onBtnMap(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Map/add_map.fxml"));
            Parent parent = loader.load();
            AddMapController controller = loader.getController();
            controller.setupController(this);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void setCords(String x, String z) {
        txtLocation.setText(z+" "+x);
    }
}
/*
            try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/Gui/testMap.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
 */
