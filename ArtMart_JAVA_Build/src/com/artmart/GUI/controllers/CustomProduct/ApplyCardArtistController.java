package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.models.Apply;
import com.artmart.models.CustomProduct;
import com.artmart.models.Session;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class ApplyCardArtistController implements Initializable {

    @FXML
    private Text pid;
    @FXML
    private Text Artisttxt;
    @FXML
    private Text CPtxt;
    @FXML
    private Text statustxt;
    private Apply apply;
    private CustomProduct z;
    private ApplyDao applyDao;
    public static final String ACCOUNT_SID = "AC85fdc289caf6aa747109220798d39394";
    public static final String AUTH_TOKEN = "df3d004d8411369066d20af591ac52a0";
    private Session session = new Session();
    private CustomProductDao x = new CustomProductDao();
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();

    @FXML
    private Button finish;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    private void finish(ActionEvent event) throws AWTException, SQLException {
        try {
            int cp = x.getCustomPId(this.apply.getCustomproduct_ID());
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Give Custom Product Price");
            dialog.setHeaderText("Enter The Amount of Money Required");
            dialog.setContentText("Amount :");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
            }
            finish.setDisable(true);
            apply.setStatus("done");

            applyDao.updateApply(apply.getApply_ID(), apply);
            statustxt.setText(apply.getStatus());

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Session session = Session.getInstance();
            String phoneNumber = Session.getSession(session.getSessionId()).getPhoneNumber();

            System.out.println("+216" + phoneNumber);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:+216" + phoneNumber),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    "your custom product is done !!")
                    .create();
            System.out.println(message.getSid());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SystemTray tray = SystemTray.getSystemTray();

        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Application");

        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("Application");
        tray.add(trayIcon);

        trayIcon.displayMessage("DONE ", "Application", TrayIcon.MessageType.INFO);

    }

}
