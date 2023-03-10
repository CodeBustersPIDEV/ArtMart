package com.artmart.GUI.controllers.Event.Artist.Activity;

import com.artmart.GUI.controllers.Event.Artist.Event.EditEventController;
import com.artmart.models.Activity;
import com.artmart.services.ActivityService;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardActivityController implements Initializable {
    
    private final EventService es = new EventService();
    private Activity activity = new Activity();
    private ActivityService as = new ActivityService();
    private ListActivityController listActivityController = new ListActivityController();

    @FXML
    private Text txtActivityTitle;
    @FXML
    private Text txtActivityHost;
    @FXML
    private Text txtActivityDate;
    @FXML
    private Text txtEvent;
    @FXML
    private Button btnDeleteActivity;
    @FXML
    private Button btnEditActivity;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setUpActivityData(Activity activity, ListActivityController controller){
        this.activity = activity;
        this.listActivityController = controller;
        this.txtActivityTitle.setText(activity.getTitle());
        this.txtActivityHost.setText(activity.getHost());
        this.txtActivityDate.setText(activity.getDate().toString());
        this.txtEvent.setText(es.getEvent(activity.getEventID()).getName());
    }   
    

//
//    @FXML
//    private void onBtnDeleteActivity(ActionEvent event) {
//        try {
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/edit_event.fxml"));
//            Parent root = loader.load();
//            Edit_eventController controller = loader.getController();
//            controller.setUpEventData(this.activity);
//            Scene scene = new Scene(root);
//            stage.setResizable(false);
//            stage.setTitle("");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            System.out.print(e.getMessage());
//        }        
//    }
//
    @FXML
    private void onBtnEditActivity(ActionEvent event) {
        try {
            Stage stage = (Stage) btnEditActivity.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/edit_activity.fxml"));
            Parent root = loader.load();
            EditActivityController controller = loader.getController();
            controller.setUpActivityData(this.activity);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }        
    }

    @FXML
    private void onBtnDeleteActivity(ActionEvent event) {
    }

    
}
