package com.artmart.GUI.controllers.Event.Artist.Event;

import com.artmart.models.Event;
import com.artmart.services.EventService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ViewEventController implements Initializable {

    private Event event = new Event();
    private final EventService es = new EventService();
    private int eventID;
    private Text description;
    

    @FXML
    private Button btnDeleteEvent;
    @FXML
    private Button btnEditEvent;
    @FXML
    private Button btnReturn;
    private Text txtEventName;
    @FXML
    private Text txtEventType;
    @FXML
    private Text txtEventLocation;
    @FXML
    private Text txtEventStartDate;
    @FXML
    private Text txtEventEndDate;
    @FXML
    private Text txtEventCapacity;
    @FXML
    private Text txtEventEntryFee;
    @FXML
    private TextFlow tfEventDescription;
    @FXML
    private Text txtEventStatus;
    @FXML
    private ImageView imageView;
    private Image img;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.tfEventDescription.setStyle("-fx-border-color: #ff5946; -fx-border-width: 1px;");
        this.tfEventDescription.setPrefWidth(270);

    }    
    
    public void setUpEventData(Event event) {
        this.event = event;
        this.txtEventName.setText(event.getName());
        this.txtEventName.setTextAlignment(TextAlignment.CENTER);
        this.txtEventLocation.setText(event.getLocation());
        this.txtEventStartDate.setText(event.getStartDate().toString());
        this.txtEventEndDate.setText(event.getEndDate().toString());
        this.description = new Text(event.getDescription());
        this.description.setFill(Color.web("#5d53a8"));
        this.description.setStyle("-fx-font-family: 'System'; -fx-font-weight: bold; -fx-font-size: 14px;");
        this.tfEventDescription.getChildren().add(description);
        this.txtEventType.setText(event.getType());
        this.txtEventCapacity.setText(String.valueOf(event.getCapacity()));
        this.txtEventEntryFee.setText(String.valueOf(event.getEntryFee()));
        this.txtEventStatus.setText(event.getStatus());
        if (this.event.getImage() != null){
            File file = new File(this.event.getImage());
        //if (file.exists()) {
            this.img = new Image(file.toURI().toString());        
            this.imageView.setImage(this.img);
        } else {
            File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
            this.img = new Image(file.toURI().toString()); 
            this.imageView.setImage(this.img);
        }
//        File file = new File(this.event.getImage());
//            this.img = new Image(file.toURI().toString());
//        } else {
//            File file = new File("src/com/artmart/assets/BlogAssets/default-product.png");
//            this.img = new Image(file.toURI().toString());
//        }
        //this.eventID = event.getUserID();
    }

    @FXML
    private void onBtnDeleteEvent(ActionEvent event) {
        boolean result = this.es.deleteEvent(this.event.getEventID());
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Event");
                alert.setHeaderText(null);
                alert.setContentText("A new event has been updated successfully!");
                alert.showAndWait();
                alert.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Product");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update event!");
                alert.showAndWait();
            }
        this.onBtnReturn(event);
    }

    @FXML
    private void onBtnEditEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/edit_event.fxml"));
            Parent root = loader.load();
            EditEventController controller = loader.getController();
            controller.setUpEventData(this.event);
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
    private void onBtnReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
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
}
