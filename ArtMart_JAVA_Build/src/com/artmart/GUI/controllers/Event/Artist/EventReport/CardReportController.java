package com.artmart.GUI.controllers.Event.Artist.EventReport;

import com.artmart.GUI.controllers.Event.Artist.Event.ListEventController;
import com.artmart.GUI.controllers.Event.Artist.Event.ViewEventController;
import com.artmart.models.Event;
import com.artmart.models.EventReport;
import com.artmart.services.EventReportService;
import com.artmart.services.EventService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardReportController implements Initializable {

    private ListReportController listReportController = new ListReportController();
    private EventService es = new EventService();
    private EventReportService rs = new EventReportService();
    private EventReport report = new EventReport();

    @FXML
    private Button btnViewReport;
    @FXML
    private Button btnDeleteReport;
    @FXML
    private Text txtEventTitle;
    @FXML
    private Text txtEventAttendance;
    @FXML
    private Text txtEventLocation1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUpEventData(EventReport report, ListReportController controller){
        this.report = report;
        this.listReportController = controller;
        this.txtEventTitle.setText(es.getEvent(report.getEventID()).getName());
//        this.txtEventTitle.setText(String.valueOf(report.getEventID()));
        this.txtEventAttendance.setText(String.valueOf(report.getAttendance()));

//        this.eventID = event.getUserID();
    }       

    @FXML
    private void onBtnViewReport(ActionEvent event) {
         try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/EventReport/view_report.fxml"));
            Parent root = loader.load();
            ViewReportController controller = loader.getController();
            controller.setUpEventData(this.report);
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
    private void onBtnDeleteReport(ActionEvent event) throws SQLException {
        this.rs.deleteEventReport(report.getEventReportID());
        this.listReportController.makeList();
    }
}
