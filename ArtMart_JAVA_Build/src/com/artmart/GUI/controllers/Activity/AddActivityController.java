/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Activity;

import com.artmart.models.Activity;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.ActivityService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class AddActivityController implements Initializable {
    
    private final ActivityService as = new ActivityService();
    private final EventService es = new EventService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
        
    private String host;
    private String title;
    private String eventName;
    private Date date;
    
    private String eventText;
    private String dateText;

    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtTitle;
    @FXML
    private DatePicker dpDate;
    @FXML
    private ComboBox comboBoxEvent = new ComboBox();
    @FXML
    private Button btnAddActivity;
    @FXML
    private Button btnCancelActivity;


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
    private void onBtnAddActivity(ActionEvent event) {
        
        // get the values for creating a new activity
        this.host = txtHost.getText();
        this.title = txtTitle.getText();

        // convert values for input check
        this.eventText = (String) this.comboBoxEvent.getValue(); 
        this.dateText = this.dpDate.getValue() != null ? String.valueOf(this.dpDate.getValue()) : null;
          
//        System.out.println(eventID);
//        System.out.println(startDateText);
//        System.out.println(endDateText);
//        System.out.println(title);
//        System.out.println(host);

        // input check
        if(this.title.isEmpty() 
        || this.host.isEmpty() 
        || this.eventText == null || this.eventText.isEmpty()
        || this.dateText == null || this.dateText.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();
        }else {

            this.eventName = (String) this.comboBoxEvent.getValue();
            try {
                this.date = Date.valueOf(this.dpDate.getValue());
            } catch (DateTimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid format");
                alert.setHeaderText(null);
                alert.setContentText("Invalid format for start date or end date!");
                alert.showAndWait();
                return;                
            }
            
            Activity activity = new Activity(
                this.es.getEventByName(eventName).getEventID(), 
                this.date, 
                this.title, 
                this.host
            );

            int result = as.createActivity(activity);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Activity");
                alert.setHeaderText(null);
                alert.setContentText("A new activity has been added successfully!");
                alert.showAndWait();
                alert.close();
                onBtnCancelActivity(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Event");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add activity!");
                alert.showAndWait();
            }
        }        
    }

    @FXML
    private void onBtnCancelActivity(ActionEvent event) {
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
