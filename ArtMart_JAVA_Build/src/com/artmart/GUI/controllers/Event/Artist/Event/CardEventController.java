package com.artmart.GUI.controllers.Event.Artist.Event;

import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardEventController implements Initializable {
    
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());    
    
    private Event event = new Event();
    private EventService es = new EventService();
    private ListEventController listEventController = new ListEventController();
//    private int eventID;

    @FXML
    private Text txtEventTitle;
    @FXML
    private Text txtEventLocation;
    @FXML
    private Text txtEventStartDate;
    @FXML
    private Text txtEventEndDate;
    @FXML
    private Button btnViewEvent;
    @FXML
    private Button btnEditEvent;
    @FXML
    private Button btnDeleteEvent;
    @FXML
    private Text txtEventStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setUpEventData(Event event,ListEventController controller){
        this.event = event;
        this.listEventController = controller;
        this.txtEventTitle.setText(event.getName());
        this.txtEventLocation.setText(event.getLocation());
        this.txtEventStartDate.setText(event.getStartDate().toString());
        this.txtEventEndDate.setText(event.getEndDate().toString());
        this.txtEventStatus.setText(event.getStatus());
        
//        this.eventID = event.getUserID();
    }   

    @FXML//event_home
    private void onBtnViewEvent(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) btnViewEvent.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/view_event.fxml"));
            Parent root = loader.load();
            ViewEventController controller = loader.getController();
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
    private void onBtnEditEvent(ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) btnEditEvent.getScene().getWindow();
            stage.close();
            stage = new Stage();            
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
    private void onBtnDeleteEvent(ActionEvent event) throws SQLException {
        boolean result = this.es.deleteEvent(this.event.getEventID());
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Event");
                alert.setHeaderText(null);
                alert.setContentText("The event has been deleted!");
                alert.showAndWait();
                alert.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Product");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete event!");
                alert.showAndWait();
            }
        this.listEventController.makeMyList();
    }
}
