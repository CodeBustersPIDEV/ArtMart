/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event;

import com.artmart.models.Event;
import com.artmart.services.EventService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class Card_eventController implements Initializable {
    
    private Event event = new Event();
    private EventService es =new EventService();
    private List_eventController listEventController = new List_eventController();

    @FXML
    private Button btnViewEvent;
    @FXML
    private Text txtEventTitle;
    @FXML
    private Text txtEventLocation;
    @FXML
    private Text txtEventStartDate;
    @FXML
    private Text txtEventEndDate;
    @FXML
    private Text txtUser;
    @FXML
    private Button btnDeleteEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setUpEventData(Event param,List_eventController controller){
        this.event = param;
        this.listEventController = controller;
        this.txtEventTitle.setText(event.getName());
        this.txtEventLocation.setText(event.getLocation());
        this.txtEventStartDate.setText(event.getStartDate().toString());
        this.txtEventEndDate.setText(event.getEndDate().toString());
        //this.txtUser.setText(event.getUserID());
    }   


}
