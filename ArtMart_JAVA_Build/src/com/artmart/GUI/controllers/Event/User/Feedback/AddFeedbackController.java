/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event.User.Feedback;

import com.artmart.models.Event;
import com.artmart.models.Feedback;
import com.artmart.models.Session;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddFeedbackController implements Initializable {

    @FXML
    private TextArea txtAreaComment;
    @FXML
    private Spinner<Integer> spinnerRating;
    private String comment;
    private Integer rating;

    private Event event = new Event();
    private final EventService es = new EventService();

    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId()); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtAreaComment.setWrapText(true);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5);
        valueFactory.setValue(Integer.MAX_VALUE);
        spinnerRating.setValueFactory(valueFactory);
    }    

    public void setUpEventData(Event event) {
        this.event = event;
    }
    
    @FXML
    private void onPost(ActionEvent event) {
        
        // get the values for creating a new activity
        this.comment = this.txtAreaComment.getText();
        this.rating = this.spinnerRating.getValue();

        // input check
        if(this.comment.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add comment! \nComplete missing information.");
            alert.showAndWait();
        }else {
            
            Feedback feedback = new Feedback(
                this.event.getEventID(),
                this.userID,
                this.rating, 
                this.comment
            );
            System.out.println(feedback);
            int result = 0;
                    //as.createActivity(activity);

            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Activity");
                alert.setHeaderText(null);
                alert.setContentText("A new activity has been added successfully!");
                alert.showAndWait();
                alert.close();
                onCancel(event);
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
    private void onCancel(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/home_artist.fxml"));
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
