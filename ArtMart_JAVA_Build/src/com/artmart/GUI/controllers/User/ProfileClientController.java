/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.User;

import com.artmart.models.Client;
import com.artmart.services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21697
 */
public class ProfileClientController implements Initializable {

    @FXML
    private Label nameProfile;

    @FXML
    private Label emailProfile;

    @FXML
    private Label phoneProfile;

    @FXML
    private Label usernameProfile;

    @FXML
    private Label birthdayProfile;
    @FXML
    private Label nbrOrderProfile;
    @FXML
    private Label nbrCusDemProfile;

    @FXML
    private Button backBtn;
    UserService user_ser = new UserService();
    private Client client = new Client();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
               client=user_ser.getClient(6);
               nameProfile.setText(client.getName());
               usernameProfile.setText(client.getUsername());
               emailProfile.setText(client.getEmail());
               nbrOrderProfile.setText(String.valueOf(client.getNbr_orders()));
              phoneProfile.setText(String.valueOf(client.getPhone_nbr()));
               nbrCusDemProfile.setText(String.valueOf(client.getNbr_cus_demands()));
               birthdayProfile.setText(client.getBirthday().toString());    }

    public void OnBack(ActionEvent event) {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();
    }
}
