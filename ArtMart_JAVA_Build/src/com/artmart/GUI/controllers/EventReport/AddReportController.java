/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.EventReport;

import com.artmart.models.Activity;
import com.artmart.models.Event;
import com.artmart.models.EventReport;
import com.artmart.models.Session;
import com.artmart.services.EventReportService;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class AddReportController implements Initializable {

    private final EventReportService ers = new EventReportService();
    private final EventService es = new EventService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
    
    @FXML
    private TextField txtAttendance;
    @FXML
    private ComboBox comboBoxEvent = new ComboBox();
    @FXML
    private Button btnAddReport;
    @FXML
    private Button btnCancelReport;
    private String eventText;
    private String attendanceText;
    private String eventName;
    private int attendance;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<Event> eventList = es.getAllEventsByID(userID);
        for(Event event : eventList) {
            this.comboBoxEvent.getItems().add(event.getName());
        }
    }    

    @FXML
    private void onBtnAddReport(ActionEvent event) {
        
        // get the values for creating a new activity

        // convert values for input check
        this.eventText = (String) this.comboBoxEvent.getValue(); 
        this.attendanceText = this.txtAttendance.getText();

        // input check
        if(this.attendanceText.isEmpty() 
        || this.eventText == null || this.eventText.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();
        }else {

            this.eventName = (String) this.comboBoxEvent.getValue();
            try {
                this.attendance = Integer.parseInt(this.txtAttendance.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid format");
                alert.setHeaderText(null);
                alert.setContentText("Invalid format for attendance!");
                alert.showAndWait();
                return;
            }
            System.out.println(attendance + this.es.getEventByName(eventName).getEventID());
            EventReport report = new EventReport(
                this.es.getEventByName(eventName).getEventID(), 
                this.attendance
            );

            int result = ers.createEventReport(report);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Report");
                alert.setHeaderText(null);
                alert.setContentText("A new activity has been added successfully!");
                alert.showAndWait();
                alert.close();
                onBtnCancelReport(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add report!");
                alert.showAndWait();
            }
        }            
    }

    @FXML
    private void onBtnCancelReport(ActionEvent event) {
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
