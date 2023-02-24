/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Activity;

import com.artmart.models.Activity;
import com.artmart.services.ActivityService;
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
    
    private int eventID = 1;
    
    private String host;
    private String title;
    private Date endDate;
    private Date startDate;
    
    private String startDateText;
    private String endDateText;

    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtTitle;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private DatePicker dpStartDate;
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
    }    

    @FXML
    private void onBtnAddActivity(ActionEvent event) {
        
        // get the values for creating a new activity
        host = txtHost.getText();
        title = txtTitle.getText();
        startDate = Date.valueOf(dpStartDate.getValue());
        endDate = Date.valueOf(dpEndDate.getValue()); 
        
        // convert values for input check
        startDateText = startDate != null ? String.valueOf(startDate) : null;
        endDateText = endDate != null ? String.valueOf(endDate) : null;  
        
        // input check
        if(title.isEmpty() 
        || host.isEmpty() 
        || startDateText == null || startDateText.isEmpty()
        || endDateText == null || endDateText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add event! \nComplete missing information.");
            alert.showAndWait();
        }else {
//            System.out.println(eventID);
//            System.out.println(startDate);
//            System.out.println(endDate);
//            System.out.println(title);
//            System.out.println(host);
            
            Activity activity = new Activity(
                this.eventID, 
                this.startDate, 
                this.endDate, 
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
                //userID++;
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
