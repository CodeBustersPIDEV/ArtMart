/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.models.Chat;
import com.artmart.services.ChatService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ChatslistController implements Initializable {

    @FXML
    private VBox vBox;
        private List<Chat> chatslist;
 private final ChatService chatService = new ChatService(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           try {
            this.makeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeList() throws SQLException {
        this.vBox.getChildren().clear();
        this.chatslist = this.chatService.getAllChats();
        this.chatslist.forEach(Chat -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/ChatCard.fxml"));
                Parent root = loader.load();
                ChatCardController controller = loader.getController();
                controller.setChat(Chat,this);
                root.setId("" + Chat.getChat_ID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }
    
  
    }    
    
