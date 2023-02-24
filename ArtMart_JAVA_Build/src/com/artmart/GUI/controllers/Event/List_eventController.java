/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.Event;

import com.artmart.models.Event;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class List_eventController implements Initializable {

    private final EventService es = new EventService();
    private List<Event> eventList;
    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPaneEventList;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            this.makeList();
        }catch(SQLException e){}
    }     

    public void makeList() throws SQLException {
        this.eventList = this.es.getAllEvents();
        this.vBox.getChildren().clear();
        this.eventList.forEach(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/card_event.fxml"));
                Parent root = loader.load();
                Card_eventController controller = loader.getController();
                controller.setUpEventData(event, this);
                root.setId(""+event.getEventID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
        // Wrap the VBox in a ScrollPane to make it scrollable
        this.scrollPaneEventList = new ScrollPane(this.vBox);
        this.scrollPaneEventList.setFitToWidth(true);
        this.scrollPaneEventList.setFitToHeight(true);
        this.scrollPaneEventList.setContent(this.vBox);
    }


    @FXML
    private void returnToEventHomepage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/event_home.fxml"));
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
    private void onBtnSearch(ActionEvent ev) {
        String keyword = this.txtSearch.getText();
        List<Event> matchingEvents = this.es.searchEventByName(keyword);
        this.vBox.getChildren().clear();
        matchingEvents.forEach(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/card_event.fxml"));
                Parent root = loader.load();
                Card_eventController controller = loader.getController();
                controller.setUpEventData(event,this);
                root.setId("" + event.getEventID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }

    @FXML
    private void onBtnCancel(ActionEvent event) throws SQLException {
        this.txtSearch.setText("");
        this.makeList();
    }
    
}
