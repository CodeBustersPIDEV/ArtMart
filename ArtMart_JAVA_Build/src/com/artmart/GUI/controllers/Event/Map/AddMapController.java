package com.artmart.GUI.controllers.Event.Map;

import com.artmart.GUI.controllers.Event.Artist.Event.AddEventController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddMapController implements Initializable {
    
    public static double lon;
    public static double lat;
    
    private WebEngine webengine ;

    @FXML
    private WebView webview;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webengine = webview.getEngine();
        url = getClass().getResource("/com/artmart/GUI/map/index.html");
        webengine.load(url.toString());
    }    

    @FXML
    private void tt(ActionEvent event) {
        Document doc = webengine.getDocument();
        Element lat = doc.getElementById("LAT");
        Element log = doc.getElementById("LOG");
        System.out.println(lat.getTextContent());
        System.out.println(log.getTextContent());
        controller.setCords(lat.getTextContent(), log.getTextContent());
    }
    AddEventController controller;
    public void setupController(AddEventController controller){
        this.controller = controller;
    }
// JavaScript interface object
    private class JavaApp {

        public void exit() {
            Platform.exit();
        }

    }
}
