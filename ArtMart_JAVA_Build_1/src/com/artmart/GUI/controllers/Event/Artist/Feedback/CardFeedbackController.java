package com.artmart.GUI.controllers.Event.Artist.Feedback;

import com.artmart.models.Feedback;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class CardFeedbackController implements Initializable {

    @FXML
    private Text txtRating;
    @FXML
    private TextArea txtAreaComment;
    @FXML
    private Text txtUserName;
    @FXML
    private Text txtDatePosted;

    private Feedback feedback = new Feedback();
    private ListFeedbackController listFeedbackController = new ListFeedbackController();
    private UserService us = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void setUpFeedbackData(Feedback feedback, ListFeedbackController controller) {
        this.feedback = feedback;
        this.listFeedbackController = controller;
        this.txtUserName.setText(us.getUser(feedback.getUserID()).getUsername());
        this.txtDatePosted.setText(String.valueOf(feedback.getDate()));
        this.txtRating.setText(String.valueOf(feedback.getRating()));
        this.txtAreaComment.setText(feedback.getComment());
    }
    
}
