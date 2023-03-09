package com.artmart.GUI.controllers.Event.Artist.EventReport;

import com.artmart.GUI.controllers.Event.Artist.Event.CardEventController;
import com.artmart.models.Event;
import com.artmart.models.EventReport;
import com.artmart.models.Session;
import com.artmart.services.EventReportService;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListReportController implements Initializable {

    private final EventReportService ers = new EventReportService();
    private final HashMap user = (HashMap) Session.getActiveSessions();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());
    private List<Event> eventList;    
    private List<EventReport> reportList;

    @FXML
    private ScrollPane scrollPaneEventList;
    @FXML
    private VBox vBox;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            this.makeList();
        }catch(SQLException e){}
    }     

    public void makeList() throws SQLException {
        this.reportList = this.ers.getAllReportsByID(userID);
        this.vBox.getChildren().clear();
        this.reportList.forEach(report -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/EventReport/card_report.fxml"));
                Parent root = loader.load();
                CardReportController controller = loader.getController();
                controller.setUpEventData(report, this);
                root.setId(""+report.getEventID());
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
    private void onBtnSearch(ActionEvent event) {
    }

    @FXML
    private void onTxtSearch(KeyEvent event) {
    }

    @FXML
    private void onBtnCancel(ActionEvent event) {
    }
    
}
