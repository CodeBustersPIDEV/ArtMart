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
import java.time.LocalDate;
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
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class Add_eventController implements Initializable {
    
    private EventService es;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBoxType.getItems().addAll("Auction", "Art fair", "Open Gallery", "Exhibition");
        txtAreaDescription.setWrapText(true);
        txtAreaDescription.setPrefWidth(270);
        
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

    @FXML
    private  void onAddEvent(ActionEvent event) {
        name = txtName.getText();
        location = txtLocation.getText();
        String typeText = (String) comboBoxType.getValue(); 
        description = txtAreaDescription.getText();
        String entryFeeText = txtEntryFee.getText();
        String capacityText =txtCapacity.getText();
        LocalDate startDateValue = dpStartDate.getValue();
        String startDateText = startDateValue != null ? String.valueOf(startDateValue) : null;
        LocalDate endDateValue = dpEndDate.getValue();
        String endDateText = endDateValue != null ? String.valueOf(endDateValue) : null;

        if(name.isEmpty() 
        || location.isEmpty() 
        || typeText == null || typeText.isEmpty()
        || description.isEmpty() 
        || entryFeeText.isEmpty()
        || capacityText.isEmpty() 
        || startDateText == null || startDateText.isEmpty()
        || endDateText == null || endDateText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();
        }else {
            type = (String) comboBoxType.getValue();
            entryFee = Float.parseFloat(txtEntryFee.getText());
            capacity = Integer.parseInt(txtCapacity.getText());
            startDate = Date.valueOf(dpStartDate.getValue());
            endDate = Date.valueOf(dpEndDate.getValue());  
            Event ev = new Event(name, location, type, description, entryFee, capacity, startDate, endDate, userID);

            es = new EventService();

            int result = es.createEvent(ev);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("A new event has been added successfully!");
                alert.showAndWait();
                //userID++;
                alert.close();
                //returnToEventHomepage(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add event!");
                alert.showAndWait();
            }
        }
    }

}
