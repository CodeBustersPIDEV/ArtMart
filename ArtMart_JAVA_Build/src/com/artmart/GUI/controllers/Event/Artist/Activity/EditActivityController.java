package com.artmart.GUI.controllers.Event.Artist.Activity;

import com.artmart.models.Activity;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.ActivityService;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditActivityController implements Initializable {

    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtTitle;
    @FXML
    private Button btnCancelActivity;
    @FXML
    private DatePicker dpDate;
    @FXML
    private ComboBox<String> comboBoxEvent = new ComboBox();
    @FXML
    private Button btnEditActivity;
        
    private final ActivityService as = new ActivityService();
    private final EventService es = new EventService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
    
    private Activity activity = new Activity();
    private int activityID;

    private String host;
    private String title;
    private int eventID;
    private Date date;
    
    private String eventText;
    private String dateText;
    private String eventName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Event> eventList = es.getMyEvents(userID);
        for(Event event : eventList) {
            this.comboBoxEvent.getItems().add(event.getName());
        }    }    

    void setUpActivityData(Activity activity) {
        this.activity = activity;
        this.txtTitle.setText(activity.getTitle());
        this.txtHost.setText(activity.getHost());
        this.dpDate.setValue(activity.getDate().toLocalDate());
        this.comboBoxEvent.setValue(es.getEvent(activity.getEventID()).getName());
        this.activityID = activity.getActivityID();
        System.out.println(this.activityID);
    }

    @FXML
    private void onBtnEditActivity(ActionEvent event) {
        
        // get the values for creating a new activity
        this.host = txtHost.getText();
        this.title = txtTitle.getText();

        // convert values for input check
        this.eventText = (String) this.comboBoxEvent.getValue(); 
        this.dateText = this.dpDate.getValue() != null ? String.valueOf(this.dpDate.getValue()) : null;

        // input check
        if(this.title.isEmpty() 
        || this.host.isEmpty() 
        || this.eventText == null || this.eventText.isEmpty()
        || this.dateText == null || this.dateText.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update activity! \nComplete missing information.");
            alert.showAndWait();
        }else {

            this.eventName = (String) this.comboBoxEvent.getValue();
            try {
                this.date = Date.valueOf(this.dpDate.getValue());
            } catch (DateTimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid format");
                alert.setHeaderText(null);
                alert.setContentText("Invalid format for date!");
                alert.showAndWait();
                return;                
            }
            Activity act = new Activity(
                this.as.getActivity(activityID).getEventID(),
                this.date, 
                this.title, 
                this.host
            );
            boolean result = this.as.updateActivity(this.activityID, act);
            
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Activity");
                alert.setHeaderText(null);
                alert.setContentText("The activity has been updated successfully!");
                alert.showAndWait();
                alert.close();
                onBtnCancelActivity(event);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update activity");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update activity!");
                alert.showAndWait();
            }
        }        
    }

    @FXML
    private void onBtnCancelActivity(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCancelActivity.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/list_activity.fxml"));
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
