/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event.User.Event;

import com.artmart.GUI.controllers.Event.Artist.Event.EditEventController;
import com.artmart.models.Event;
import com.artmart.models.Participation;
import com.artmart.models.Session;
import com.artmart.services.EventService;
import com.artmart.services.ParticipationService;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class UserViewEventController implements Initializable {
    
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());    
    
    private Event event = new Event();
    private Participation participation = new Participation();
    
    private final EventService es = new EventService();
    private final ParticipationService ps = new ParticipationService();
    private int eventID;
    private Text description;

    @FXML
    private Button btnReturn;
    @FXML
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
    private Text attendingStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.tfEventDescription.setStyle("-fx-border-color: #ff5946; -fx-border-width: 1px;");
        this.tfEventDescription.setPrefWidth(270);
        this.attendingStatus.setText("No ☹");
//        if (participation == null) {
//            this.attendingStatus.setText("No!");
//        }else {
//            this.attendingStatus.setText(participation.getAttendanceStatus());
//        }
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
        //this.eventID = event.getUserID();
    }
    
    public void setUpParticipationData(Participation participation) {
        this.participation = participation;
        if (this.participation != null) {
            this.attendingStatus.setText(participation.getAttendanceStatus());
            if (this.participation.getAttendanceStatus().equals("Yes")) {
                this.attendingStatus.setText("Yes 😄");
                this.attendingStatus.setFill(Color.web("#09a83e"));
            }else {
                this.attendingStatus.setText("No 😓");
            }
        }

    }

    @FXML
    private void onBtnAttend(ActionEvent event) {
            Participation participation = new Participation(
                this.userID,
                this.event.getEventID(),
                "Yes"
            );

            int result = ps.createParticipation(participation);

            if (result > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attend Event");
                alert.setHeaderText(null);
                alert.setContentText("You are now attending the event!");
                alert.showAndWait();
                alert.close();
               this.onBtnReturn(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attend Event");
                alert.setHeaderText(null);
                alert.setContentText("You are already attending the event!");
                alert.showAndWait();
            }   
    }

    @FXML
    private void onBtnCancel(ActionEvent event) {
        Participation p = this.ps.getParticipationByID(userID, this.event.getEventID());
        if(participation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cancel participation to event");
            alert.setHeaderText(null);
            alert.setContentText("Failed to cancel participation!");
            alert.showAndWait();
        }else {
            boolean result = this.ps.deleteParticipation(p.getParticipationlD());
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cancel participation to event");
                alert.setHeaderText(null);
                alert.setContentText("You cancelled your participation to the event successfully!");
                alert.showAndWait();
                alert.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cancel participation to event");
                alert.setHeaderText(null);
                alert.setContentText("Failed to cancel participation!");
                alert.showAndWait();
            }
        this.onBtnReturn(event);
        }
    }
    
    @FXML
    private void onBtnReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
