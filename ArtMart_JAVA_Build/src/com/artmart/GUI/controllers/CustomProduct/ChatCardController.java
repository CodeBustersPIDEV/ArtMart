/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.models.Chat;
import com.artmart.services.ChatService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ChatCardController implements Initializable {

    @FXML
    private Text client_id;
    @FXML
    private Text custom_product_ID;
    @FXML
    private Text artist_ID;
    @FXML
    private Text history;
  private Chat p=new Chat();
  private ChatslistController controller=new ChatslistController();
   private final ChatService chatService = new ChatService(); 
    @FXML
    private Button OnDelete;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setChat(Chat param,ChatslistController controller){
    this.p=param;
    this.controller = controller;
    this.client_id.setText(Integer.toString(p.getClient_ID()));
    this.custom_product_ID.setText(Integer.toString(p.getCustom_product_ID()));
     this.artist_ID.setText(Integer.toString(p.getArtist_ID()));
    this.history.setText(p.getHistory());
}
    @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.chatService.deleteChat(this.p.getChat_ID());
        this.controller.makeList();
    }

}
