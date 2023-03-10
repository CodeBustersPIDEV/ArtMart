package com.artmart.GUI.controllers.Event.Artist.Participation;

import com.artmart.models.Participation;
import com.artmart.services.EventService;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class CardParticipationController implements Initializable {

    @FXML
    private Text txtUsername;
    @FXML
    private Text txtRole;
    @FXML
    private Text txtRegDate;
    @FXML
    private Text txtEvent;


    private Participation participation = new Participation();
    private ListParticipationController listParticipationController = new ListParticipationController();
    
    private UserService us = new UserService();
    private EventService es = new EventService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUpParticipationData(Participation participation, ListParticipationController controller){
        this.participation = participation;
        this.listParticipationController = controller;
        this.txtUsername.setText(us.getUser(participation.getUserID()).getUsername());
        this.txtRole.setText(us.getUser(participation.getUserID()).getRole());
        this.txtRegDate.setText(participation.getRegistrationDate().toString());
        this.txtEvent.setText(es.getEvent(participation.getEventlD()).getName());
    }  

}
