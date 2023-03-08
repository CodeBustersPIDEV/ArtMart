/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event.User.Event;

import com.artmart.GUI.controllers.Event.Artist.Event.ListEventController;
import com.artmart.models.Event;
import com.artmart.models.Participation;
import com.artmart.models.Session;
import com.artmart.services.EventService;
import com.artmart.services.ParticipationService;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserCardEventController implements Initializable {
    
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());    
    
    private Event event = new Event();
    private EventService es = new EventService();
    private ParticipationService ps = new ParticipationService();
    private ListEventController listEventController = new ListEventController();
    
    private UserViewEventController userViewEventController = new UserViewEventController();
    private Participation participation = new Participation();

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
    private Text txtEventTitle1;
    @FXML
    private Text txtEventLocation1;
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

    @FXML
    private void onBtnViewEvent(ActionEvent event) {
        try {
            /*
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setTitle("Blog Managment");
                stage.setScene(scene);
                stage.show();
            */
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/User/Event/user_view_event.fxml"));
            Parent root = loader.load();
            UserViewEventController controller = loader.getController();
            controller.setUpEventData(this.event);
            
            System.out.println("userID " + this.userID + " eventID " + this.event.getEventID());
            System.out.println("participation = " + this.ps.getParticipationByID(this.userID, this.event.getEventID()));
            
            controller.setUpParticipationData(this.ps.getParticipationByID(this.userID, this.event.getEventID()));
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
