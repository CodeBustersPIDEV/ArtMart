package com.artmart.GUI.controllers.Event.Artist.Activity;

import com.artmart.models.Activity;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.ActivityService;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListActivityController implements Initializable {
    
    private final EventService es = new EventService();
    private final ActivityService as = new ActivityService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
    
    private List<Activity> activityList;
    
    @FXML
    private VBox vBox;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private ScrollPane scrollPaneActivityList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            this.makeList();
        }catch(SQLException e){}
    }    

    public void makeList() throws SQLException {
        this.activityList = this.as.getAllActivitiesByID(userID);

        this.vBox.getChildren().clear();
        this.activityList.forEach(activity -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/card_activity.fxml"));
                Parent root = loader.load();
                CardActivityController controller = loader.getController();
                controller.setUpActivityData(activity, this);
                root.setId(""+activity.getActivityID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
        // Wrap the VBox in a ScrollPane to make it scrollable
        this.scrollPaneActivityList = new ScrollPane(this.vBox);
        this.scrollPaneActivityList.setFitToWidth(true);
        this.scrollPaneActivityList.setFitToHeight(true);
        this.scrollPaneActivityList.setContent(this.vBox);
    }
    

    @FXML
    private void onBtnSearch(ActionEvent event) {
        String keyword = this.txtSearch.getText();
        List<Activity> matchingActivities = this.as.searchActivityByTitle(keyword, userID);
        this.vBox.getChildren().clear();
        matchingActivities.forEach(activity -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/card_activity.fxml"));
                Parent root = loader.load();
                CardActivityController controller = loader.getController();
                controller.setUpActivityData(activity,this);
                root.setId("" + activity.getActivityID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }

    @FXML
    private void onBtnReturn(ActionEvent event) {
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

    @FXML
    private void onBtnCancel(ActionEvent event) throws SQLException {
        this.txtSearch.setText("");
        this.makeList();
    }

    @FXML
    private void onTxtSearch(KeyEvent ev) {
        if (ev.getCode() == KeyCode.ENTER) {
            String keyword = this.txtSearch.getText();
            List<Activity> matchingActivities = this.as.searchActivityByTitle(keyword, userID);
            this.vBox.getChildren().clear();
            matchingActivities.forEach(activity -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/card_activity.fxml"));
                    Parent root = loader.load();
                    CardActivityController controller = loader.getController();
                    controller.setUpActivityData(activity, this);
                    root.setId("" + activity.getActivityID());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });            
        }
 
    }
    
}
