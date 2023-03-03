/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.artmart.GUI.controllers.EventReport;

import com.artmart.models.Event;
import com.artmart.models.EventReport;
import com.artmart.services.EventService;
import com.artmart.services.PdfService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author GhassenZ
 */
public class ViewReportController implements Initializable {

    EventReport report = new EventReport();
    private Event event = new Event();
    private final EventService es = new EventService();
    
    @FXML
    private Button btnDeleteEvent;
    @FXML
    private Button btnPdf;
    @FXML
    private Button btnReturn;
    @FXML
    private Text txtEventName;
    @FXML
    private Text txtEventType;
    @FXML
    private Text txtEventLocation;
    @FXML
    private Text txtEventStartDate;
    @FXML
    private Text txtEventEndDate;
    @FXML
    private Text txtEventCapacity;
    @FXML
    private Text txtEventEntryFee;
    @FXML
    private TextFlow tfEventDescription;
    @FXML
    private Text txtAtt;
    @FXML
    private Text txtEventID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setUpEventData(EventReport report) {
        this.report = report;
        this.txtAtt.setText(String.valueOf(report.getAttendance()));
        this.txtEventID.setText(String.valueOf(report.getEventID()));
        //this.eventID = event.getUserID();
    }    

    @FXML
    private void onBtnDeleteEvent(ActionEvent event) {
    }

    @FXML
    private void onBtnPdf(ActionEvent event) throws IOException, NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException, SQLException {

        Event ev = es.getEvent(report.getEventID());
        PdfService pdf = new PdfService();
        
        try{
            pdf.generatePdf(""+ev.getName()+"", ev, ev.getEventID());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF");
            alert.setHeaderText(null);
            alert.setContentText("!!!PDF exported!!!");
            alert.showAndWait();
            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("!!!Choose an event!!!");
            alert.showAndWait();
        }
    }      

    @FXML
    private void onBtnReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/EventReport/list_report.fxml"));
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
