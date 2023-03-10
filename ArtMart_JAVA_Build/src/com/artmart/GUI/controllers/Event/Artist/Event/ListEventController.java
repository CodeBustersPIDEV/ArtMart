package com.artmart.GUI.controllers.Event.Artist.Event;

import com.artmart.GUI.controllers.Event.User.Event.UserCardEventController;
import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.models.Event;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.EventService;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private javafx.scene.control.Label username;
    UserService user_ser = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.comboBox.getItems().addAll("My events", "Other events");
            this.makeMyList();
        } catch (SQLException e) {
        }
        
         User connectedUser = user_ser.getUser(userID);
            username.setText(connectedUser.getUsername());
            Map<String, String> profileActions = new HashMap<>();

            profileActions.put("", "");
            profileActions.put("Logout", "logout");
            profileActions.put("Profile", "profile");
            // Populate the choice box with display names
            profileChoiceBox.getItems().addAll(profileActions.keySet());
            // Add an event listener to handle the selected item's ID
            profileChoiceBox.setOnAction(event -> {
                String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
                String selectedId = profileActions.get(selectedItem);
                // Handle the action based on the selected ID
                 if ("profile".equals(selectedId)) {

               profileChoiceBox.setValue("");
                User u = user_ser.getUser(userID);
                if (u.getRole().equals("admin")) {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileAdmin.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileAdminController controller = loader.getController();
                        controller.setProfile(userID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (u.getRole().equals("artist")) {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileArtist.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileArtistController controller = loader.getController();
                        controller.setProfile(userID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (u.getRole().equals("client")) {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/User/ProfileClient.fxml"));
                    try {
                        Parent root = loader.load();

                        ProfileClientController controller = loader.getController();
                        controller.setProfile(userID);
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ArtistReadyProductsListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                } else if ("logout".equals(selectedId)) {
                    session.logOut("1");
                    Stage stage = (Stage) profileChoiceBox.getScene().getWindow();
                    stage.close();
                    try {

                        stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                        Scene scene = new Scene(root);
                        stage.setResizable(false);
                        stage.setTitle("User Managment");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        System.out.print(e.getMessage());
                    }

                }
            });
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
                root.setId("" + event.getEventID());
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
                root.setId("" + event.getEventID());
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
        } else {
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
            } else {
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
