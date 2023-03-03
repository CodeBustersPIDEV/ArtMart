package com.artmart.GUI.controllers.CustomProduct;

import static com.artmart.GUI.controllers.CustomProduct.ApplyCardArtistController.ACCOUNT_SID;
import static com.artmart.GUI.controllers.CustomProduct.ApplyCardArtistController.AUTH_TOKEN;
import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import com.artmart.models.Product;
import com.artmart.models.Session;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ApplyCardClientController implements Initializable {
    @FXML
    private Text Artisttxt;
    @FXML
    private Text CPtxt;
    @FXML
    private Text statustxt;
    @FXML
    private Text pid;

    private Apply apply;
    private ApplyDao applyDao;
    
 public static final String ACCOUNT_SID = "AC85fdc289caf6aa747109220798d39394";
  public static final String AUTH_TOKEN = "df3d004d8411369066d20af591ac52a0";
      private Session session = new Session();
  HashMap user = (HashMap) Session.getActiveSessions();

     @Override
    public void initialize(URL location, ResourceBundle resources)  {
        applyDao = new ApplyDao();
    }

    public void setApply(Apply apply) throws SQLException {
        this.apply = apply;
   String artistName = applyDao.getartisttNameById(apply.getArtist_ID());
    String cpn = applyDao.getCustomProductNameById(apply.getCustomproduct_ID());
        Artisttxt.setText(artistName);
        CPtxt.setText(cpn);
        statustxt.setText(apply.getStatus());
        pid.setText(String.valueOf(apply.getApply_ID()));
    }

    @FXML
    private void accept() {
        try {
            apply.setStatus("in progress");
            applyDao.updateApply(apply.getApply_ID(), apply);
            statustxt.setText(apply.getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
          Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
  Session session = Session.getInstance();
          
String phoneNumber = Session.getSession(session.getSessionId()).getPhoneNumber();

    System.out.println("+216"+phoneNumber);

        
     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
  new com.twilio.type.PhoneNumber("whatsapp:+216"+phoneNumber),
      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
      "your application is approved contact the client in his whatsapp : !"+phoneNumber)

    .create();
          System.out.println(message.getSid());    
            
        
    }

    @FXML
private void reject() {
    try {
        apply.setStatus("Rejected");
        applyDao.updateApply(apply.getApply_ID(), apply);
           statustxt.setText(apply.getStatus());
       
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


  
}
