package com.artmart.GUI.controllers.Event.User.Feedback;

import com.artmart.models.Event;
import com.artmart.models.Feedback;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class UserCardFeedbackController implements Initializable {

    @FXML
    private Text txtUserName;
    @FXML
    private Text txtDatePosted;
    @FXML
    private Text txtRating;
    @FXML
    private TextArea txtAreaComment;
    private Feedback feedback = new Feedback();
    private UserListFeedbackController listFeedbackController = new UserListFeedbackController();
    
    private UserService us = new UserService();
    private Event event = new Event();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setUpEventData(Feedback feedback, UserListFeedbackController controller) {
        this.feedback = feedback;
        this.listFeedbackController = controller;
        this.txtUserName.setText(us.getUser(feedback.getUserID()).getUsername());
        this.txtDatePosted.setText(String.valueOf(feedback.getDate()));
        this.txtRating.setText(String.valueOf(feedback.getRating()));
        this.txtAreaComment.setText(feedback.getComment());
    }

//    public void setUpEventData(Event event) {
//        this.event = event;
//    }
    
}
