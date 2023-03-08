/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.UserDao;
import com.artmart.models.Apply;
import com.artmart.models.CustomProduct;
import com.artmart.models.Session;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
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

    private CustomProduct p = new CustomProduct();
    private CategoriesDao s = new CategoriesDao();
    private CustomProductDao cPDao = new CustomProductDao();
    public static final String ACCOUNT_SID = "AC85fdc289caf6aa747109220798d39394";
    public static final String AUTH_TOKEN = "798a6da8a44c9ab785ba50336b882e31";
    private ArtistCustomController controller = new ArtistCustomController();

    @FXML
    private Text owner;
    @FXML
    private Button applyBtn;

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
        this.pid.setText(Integer.toString(p.getCustomProductId()));

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
        this.WaightTxt.setText("" + p.getWeight());
        
        
        
        ApplyDao applyDao = new ApplyDao();
    int artistId = Session.getCurrentUserId(Session.getInstance().getSessionId());
    int customProductId = Integer.parseInt(pid.getText());
    if (applyDao.checkIfApplyExists(customProductId)) {
        applyBtn.setDisable(true);
    }
    }

    @FXML
    private void goapply(ActionEvent event) throws SQLException, IOException, AWTException {

        // Get the current user's ID from the session
        int artistId = Session.getCurrentUserId(Session.getInstance().getSessionId());

        // Get the ID of the custom product that the user is applying for
        int customProductId = Integer.parseInt(pid.getText());
        System.out.println(artistId);
        System.out.println(customProductId);

        // Create a new Apply object with the necessary information
        Apply apply = new Apply(customProductId, artistId, "Pending");

        // Create the Apply in the database using the ApplyDao
        ApplyDao applyDao = new ApplyDao();
        int applyId = applyDao.createApply(apply);

        // Display a success message to the user
        System.out.println("Apply created with ID: " + applyId);

        // Disable the "Apply" button so it cannot be pressed again
        applyBtn.setDisable(true);

        SystemTray tray = SystemTray.getSystemTray();

        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Application");

        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("Application");
        tray.add(trayIcon);

        trayIcon.displayMessage("Application done ", "Application", TrayIcon.MessageType.INFO);

    }

}
