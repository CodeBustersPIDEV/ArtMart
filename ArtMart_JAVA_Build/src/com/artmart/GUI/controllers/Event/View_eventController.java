/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event;

import com.artmart.models.Event;
import com.artmart.services.EventService;
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
public class View_eventController implements Initializable {

    private Event event = new Event();
    private EventService es =new EventService();
       //private View_eventController viewEventController = new View_eventController;
    private int eventID;
    Text description;
    

    @FXML
    private Button btnDeleteEvent;
    @FXML
    private Button btnEditEvent;
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
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfEventDescription.setStyle("-fx-border-color: #1752b8; -fx-border-width: 1px;");
        tfEventDescription.setPrefWidth(270);
//        tfEventDescription.getChildren().s

    }    
    
    public void setUpEventData(Event event) {
        this.event = event;
        this.txtEventName.setText(event.getName());
        txtEventName.setTextAlignment(TextAlignment.CENTER);
        this.txtEventLocation.setText(event.getLocation());
        this.txtEventStartDate.setText(event.getStartDate().toString());
        this.txtEventEndDate.setText(event.getEndDate().toString());
//        this.txtEventDescription.setText(event.getDescription());
//        this.fpEventDescription.getChildren().add(new Text(event.getDescription()));
        description = new Text(event.getDescription());
        description.setFill(Color.web("#1752b8"));
        description.setStyle("-fx-font-family: 'Agency FB'; -fx-font-weight: bold; -fx-font-size: 16px;");
        this.tfEventDescription.getChildren().add(description);
        this.txtEventType.setText(event.getType());
        this.txtEventCapacity.setText(String.valueOf(event.getCapacity()));
        this.txtEventEntryFee.setText(String.valueOf(event.getEntryFee()));
        this.eventID = event.getUserID();
    }


    @FXML
    private void onBtnDeleteEvent(ActionEvent event) {
        this.es.deleteEvent(this.event.getEventID());
        onBtnReturn(event);
    }



    @FXML
    private void onBtnReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/list_event.fxml"));
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
