package com.artmart.GUI.controllers.Event.Artist.Event;

import com.artmart.GUI.controllers.Event.User.Event.UserCardEventController;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListEventController implements Initializable {

    private final EventService es = new EventService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
    private List<Event> eventList;
    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scrollPaneEventList;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox comboBox = new ComboBox();
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAddEvent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            this.comboBox.getItems().addAll("My events", "Other events");
            this.makeMyList();
        }catch(SQLException e){}
    }     

    public void makeMyList() throws SQLException {
        
        this.eventList = this.es.getMyEvents(userID);
        this.vBox.getChildren().clear();
        
        this.eventList.forEach(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/card_event.fxml"));
                Parent root = loader.load();
                CardEventController controller = loader.getController();
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

    public void makeUserList() throws SQLException {
        
        this.eventList = this.es.getOtherEvents(userID);
        this.vBox.getChildren().clear();
        
        this.eventList.forEach(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/User/Event/user_card_event.fxml"));
                Parent root = loader.load();
                UserCardEventController controller = loader.getController();
                //CardEventController controller = loader.getController();
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
    private void onBtnSearch(ActionEvent ev) {
        String keyword = this.txtSearch.getText();
        List<Event> matchingEvents = new ArrayList<>();
        if (comboBox.getValue() == null || comboBox.getValue().equals("My events")) {
            matchingEvents = this.es.searchMyEventByName(keyword, userID);
            this.vBox.getChildren().clear();
            matchingEvents.forEach(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/card_event.fxml"));
                    Parent root = loader.load();
                    CardEventController controller = loader.getController();
                    controller.setUpEventData(event, this);
                    root.setId("" + event.getEventID());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });
        }else {
            matchingEvents = this.es.searchOtherEventByName(keyword, userID);
            this.vBox.getChildren().clear();
            matchingEvents.forEach(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/User/Event/user_card_event.fxml"));
                    Parent root = loader.load();
                    UserCardEventController controller = loader.getController();
                    controller.setUpEventData(event, this);
                    root.setId("" + event.getEventID());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });
        }
    }

    @FXML
    private void onTxtSearch(KeyEvent ev) {
        if (ev.getCode() == KeyCode.ENTER) {
            String keyword = this.txtSearch.getText();
            List<Event> matchingEvents = new ArrayList<>();
            if (comboBox.getValue() == null || comboBox.getValue().equals("My events")) {
                matchingEvents = this.es.searchMyEventByName(keyword, userID);
                this.vBox.getChildren().clear();
                matchingEvents.forEach(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Event/card_event.fxml"));
                        Parent root = loader.load();
                        CardEventController controller = loader.getController();
                        controller.setUpEventData(event, this);
                        root.setId("" + event.getEventID());
                        this.vBox.getChildren().add(root);
                    } catch (IOException e) {
                        System.out.print(e.getCause());
                    }
                });
            }else {
                matchingEvents = this.es.searchOtherEventByName(keyword, userID);
                this.vBox.getChildren().clear();
                matchingEvents.forEach(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/User/Event/user_card_event.fxml"));
                        Parent root = loader.load();
                        UserCardEventController controller = loader.getController();
                        controller.setUpEventData(event, this);
                        root.setId("" + event.getEventID());
                        this.vBox.getChildren().add(root);
                    } catch (IOException e) {
                        System.out.print(e.getCause());
                    }
                });
            }
        }
    }

    @FXML
    private void onBtnCancel(ActionEvent event) throws SQLException {
        this.txtSearch.setText("");
        if (comboBox.getValue() == null || comboBox.getValue().equals("My events")) {
            this.makeMyList();
        } else {
            this.makeUserList();
        }
        //this.comboBox.setValue("My events");
    }

    @FXML
    private void onComboBox(ActionEvent event) throws SQLException {
        if (comboBox.getValue() == null || comboBox.getValue().equals("My events")) {
            this.makeMyList();
        }
        if (comboBox.getValue().equals("Other events")) {
            this.makeUserList();
        }
    }

    @FXML
    private void returnToEventHomepage(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();            
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
    private void onBtnAddEvent(ActionEvent event) {
        try {
            Stage stage = (Stage) btnAddEvent.getScene().getWindow();
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
}
