/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event;

import com.artmart.models.Event;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class Edit_eventController implements Initializable {
    
    private Event event = new Event();
    private final EventService es = new EventService();;
    private int eventID;
    
    private String name;
    private String location;
    private String type;
    private String description;
    private double entryFee;
    private int capacity;
    private Date startDate;
    private Date endDate;
    private int userID = 1;
    
    private String typeText; 
    private String entryFeeText;
    private String capacityText;
    private String startDateText;
    private String endDateText;
    
    @FXML
    private TextField txtEventLocation;
    @FXML
    private TextField txtEventName;
    @FXML
    private ComboBox comboBoxEventType = new ComboBox();;
    @FXML
    private TextField txtEventCapacity;
    @FXML
    private TextField txtEventEntryFee;
    @FXML
    private TextArea txtAreaEventDescription;
    @FXML
    private DatePicker dpEventEndDate;
    @FXML
    private DatePicker dpEventStartDate;
    @FXML
    private Button btnCancelEvent;
    @FXML
    private Button btnConfirm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.comboBoxEventType.getItems().addAll("Auction", "Art fair", "Open Gallery", "Exhibition");
        this.txtAreaEventDescription.setWrapText(true);
    }    
    
    public void setUpEventData(Event event) {
        this.event = event;
        this.txtEventName.setText(event.getName());
        this.txtEventLocation.setText(event.getLocation());
        this.dpEventStartDate.setValue(event.getStartDate().toLocalDate());
        this.dpEventEndDate.setValue(event.getEndDate().toLocalDate());
        this.txtAreaEventDescription.setText(event.getDescription());
        this.comboBoxEventType.setValue(event.getType());
        this.txtEventCapacity.setText(String.valueOf(event.getCapacity()));
        this.txtEventEntryFee.setText(String.valueOf(event.getEntryFee()));
        this.eventID = event.getEventID();
        System.out.println("event id " + this.eventID);
    }


    @FXML
    private void onBtnConfirm(ActionEvent event) {

        // get the values for updating the event
        this.name = this.txtEventName.getText();
        this.location = this.txtEventLocation.getText();
        this.description = this.txtAreaEventDescription.getText();
        this.type = (String) this.comboBoxEventType.getValue();
        this.entryFee = Float.parseFloat(this.txtEventEntryFee.getText());
        this.capacity = Integer.parseInt(this.txtEventCapacity.getText());
        this.startDate = Date.valueOf(this.dpEventStartDate.getValue());
        this.endDate = Date.valueOf(this.dpEventEndDate.getValue());  
        
        // convert values for input check
        this.typeText = (String) this.comboBoxEventType.getValue(); 
        this.entryFeeText = this.txtEventEntryFee.getText();
        this.capacityText = this.txtEventCapacity.getText();
        this.startDateText = this.startDate != null ? String.valueOf(this.startDate) : null;
        this.endDateText = this.endDate != null ? String.valueOf(this.endDate) : null;    
        
        // input check
        if(this.name.isEmpty() 
        || this.location.isEmpty() 
        || this.typeText == null || this.typeText.isEmpty()
        || this.description.isEmpty() 
        || this.entryFeeText.isEmpty()
        || this.capacityText.isEmpty() 
        || this.startDateText == null || this.startDateText.isEmpty()
        || this.endDateText == null || this.endDateText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();
        }else {
            Event ev = new Event(
                this.name, 
                this.location, 
                this.type, 
                this.description, 
                this.entryFee, 
                this.capacity, 
                this.startDate, 
                this.endDate
            );

            boolean result = this.es.updateEvent(this.eventID, ev);
            System.out.println(result);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Event");
                alert.setHeaderText(null);
                alert.setContentText("A new event has been updated successfully!");
                alert.showAndWait();
                //userID++;
                alert.close();
                this.onBtnCancelEvent(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Product");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update event!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void onBtnCancelEvent(ActionEvent event) {
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
