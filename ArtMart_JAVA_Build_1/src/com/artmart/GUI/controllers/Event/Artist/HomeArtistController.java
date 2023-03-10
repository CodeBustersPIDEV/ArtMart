package com.artmart.GUI.controllers.Event.Artist;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeArtistController implements Initializable {

    @FXML
    private Button btnReturn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/MainView.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }    

    private void onBtnAddEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/add_event.fxml"));
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
    private void onBtnGetAllEvents(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/list_event.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    } 

    private void onBtnAddActivity(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Activity/add_activity.fxml"));
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
    private void onBtnGetAllActivities(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
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

    private void onBtnAddReport(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/EventReport/add_report.fxml"));
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
    private void onBtnGetReports(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/EventReport/list_report.fxml"));
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
    private void onViewParticipations(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Participation/list_participation.fxml"));
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
    private void onViewFeedbacks(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Feedback/list_feedback.fxml"));
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
