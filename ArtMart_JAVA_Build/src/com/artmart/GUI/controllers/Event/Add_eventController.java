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
public class Add_eventController implements Initializable {
    
    private final EventService es = new EventService();
    //private int eventID;
    private String name;
    private String location;
    private String type;
    private String description;
    private double entryFee;
    private int capacity;
    private Date startDate;
    private Date endDate;
    //private int userID;
    private int userID = 1;

    
    private String typeText; 
    private String entryFeeText;
    private String capacityText;
    private String startDateText;
    private String endDateText;

    @FXML
    private Button btnAddEvent;
    @FXML
    private Button btnCancelEvent;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox comboBoxType = new ComboBox();;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // add event types in comboBox
        this.comboBoxType.getItems().addAll("Auction", "Art fair", "Open Gallery", "Exhibition");
        this.txtAreaDescription.setWrapText(true);
    }    

    @FXML
    private  void onAddEvent(ActionEvent event) {
        
        // get the values for creating a new event
        this.name = this.txtName.getText();
        this.location = this.txtLocation.getText();
        this.description = this.txtAreaDescription.getText();
        this.type = (String) this.comboBoxType.getValue();
        this.entryFee = Float.parseFloat(this.txtEntryFee.getText());
        this.capacity = Integer.parseInt(this.txtCapacity.getText());
        this.startDate = Date.valueOf(this.dpStartDate.getValue());
        this.endDate = Date.valueOf(this.dpEndDate.getValue());  
        
        // convert values for input check
        this.typeText = (String) this.comboBoxType.getValue(); 
        this.entryFeeText = this.txtEntryFee.getText();
        this.capacityText = this.txtCapacity.getText();
        this.startDateText = this.startDate != null ? String.valueOf(this.startDate) : null;
        this.endDateText = this.endDate != null ? String.valueOf(this.endDate) : null;        
        
        // input check
        if(name.isEmpty() 
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
                this.endDate, 
                this.userID
            );

            int result = es.createEvent(ev);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("A new event has been added successfully!");
                alert.showAndWait();
                //userID++;
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/event_home.fxml"));
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
