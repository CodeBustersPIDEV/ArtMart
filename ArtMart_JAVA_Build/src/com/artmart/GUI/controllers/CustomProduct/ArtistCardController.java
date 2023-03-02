/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.CustomProduct;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ArtistCardController implements Initializable {

    @FXML
    private Text pid;
    @FXML
    private Text nameTxt;
    @FXML
    private Text descTxt;
    @FXML
    private Text WaightTxt;
    @FXML
    private Text CID;
    @FXML
    private Text dim;
    @FXML
    private Text mat;
    @FXML
    private ImageView img;
        
    private CustomProduct p=new CustomProduct();
     private CategoriesDao s=new CategoriesDao();
    private CustomProductDao cPDao = new CustomProductDao();
    public static final String ACCOUNT_SID = "AC85fdc289caf6aa747109220798d39394";
  public static final String AUTH_TOKEN = "798a6da8a44c9ab785ba50336b882e31";
    private ArtistCustomController controller=new ArtistCustomController();
    @FXML
    private Text owner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   public void setCustomProduct(CustomProduct param, ArtistCustomController controller) throws SQLException {
    this.p = param;
    this.controller = controller;
    this.pid.setText(Integer.toString(p.getProductId()));
    // Retrieve the category name from the database using the CategoryDao
    String categoryName = s.getCategoryNameById(p.getCategoryId());
    this.CID.setText(categoryName);
    this.dim.setText(p.getDimensions());
    this.mat.setText(p.getMaterial());
    
    // Retrieve the client name from the database using the ClientService
    UserDao clientService = new UserDao();
    String clientName = clientService.getClientNameById(p.getClientID());
    this.owner.setText(clientName);

    // Load the image from the file path stored in CustomProduct object's image field
    Image image = new Image("file:" + p.getImage());
    this.img.setImage(image);

    this.nameTxt.setText(p.getName());
    this.descTxt.setText(p.getDescription());
    this.WaightTxt.setText(""+p.getWeight());
}

   private void goapply(ActionEvent event) throws SQLException, IOException {
//    // Get the client ID from the custom product
//    int clientID = p.getClientID();
//
//    // Get the artist ID from the session
//    int artistID = Session.getCurrentUserId(Session.getInstance().getSessionId());
//    System.out.println(artistID);
//    // Create a new Chat object with the client ID and artist ID
//    Chat chat = new Chat(clientID,p.getCustomProductId(), artistID);
//    System.out.println(clientID);
//    System.out.println();
//
//    // Save the Chat object to the database using the ChatDao
//    ChatDao chatDao = new ChatDao();
//    int chatID = chatDao.createChat(chat);
    
    // Redirect the user to the chat room with the chat ID
//    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/ChatRoom.fxml"));
//    Parent chatRoomParent = loader.load();
//    ChatRoomController chatRoomController = loader.getController();
//    chatRoomController.setChatID(chatID);
//    Scene chatRoomScene = new Scene(chatRoomParent);
//    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//    window.setScene(chatRoomScene);
//    window.show();
}

    
}
