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
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
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
    @FXML
    private FlowPane fpEventDescription;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfEventDescription.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        tfEventDescription.setPrefWidth(270);
    }    
    
    public void setUpEventData(Event event) {
        this.event = event;
        this.txtEventName.setText(event.getName());
        this.txtEventLocation.setText(event.getLocation());
        this.txtEventStartDate.setText(event.getStartDate().toString());
        this.txtEventEndDate.setText(event.getEndDate().toString());
//        this.txtEventDescription.setText(event.getDescription());
//        this.fpEventDescription.getChildren().add(new Text(event.getDescription()));
        this.tfEventDescription.getChildren().add(new Text(event.getDescription()));
        this.txtEventType.setText(event.getType());
        this.txtEventCapacity.setText(String.valueOf(event.getCapacity()));
        this.txtEventEntryFee.setText(String.valueOf(event.getEntryFee()));
        this.eventID = event.getUserID();
    }


    @FXML
    private void onbtnDeleteEvent(ActionEvent event) {
    }

    @FXML
    private void onBtnEditEvent(ActionEvent event) {
    }

}
