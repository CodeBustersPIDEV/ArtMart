/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CustomProductDao;
import com.artmart.models.CustomProduct;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mahou
 */
public class CustomProductCardController implements Initializable {
   @FXML
    private Text pid;
    @FXML
    private Text nameTxt;
    @FXML
    private Text descTxt;
    @FXML
    private Text WaightTxt;
    
    private CustomProduct p=new CustomProduct();
    
    private CustomProductDao cPDao = new CustomProductDao();
    
    private CustomproductslistController controller=new CustomproductslistController();
    @FXML
    private Button deleteBtn;
    @FXML
    private Text CID;
    @FXML
    private Text dim;
    @FXML
    private Text mat;
    @FXML
    private Text img;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCustomProduct(CustomProduct param,CustomproductslistController controller){
        this.p=param;
        this.controller = controller;
           this.pid.setText(Integer.toString(p.getProductId()));
       this.CID.setText(Integer.toString(p.getCategoryId()));
           this.dim.setText(p.getDimensions());
                    this.mat.setText(p.getMaterial());
                          this.img.setText(p.getImage());
        this.nameTxt.setText(p.getName());
        this.descTxt.setText(p.getDescription());
        this.WaightTxt.setText(""+p.getWeight());
    }

    @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.cPDao.deleteCustomProduct(this.p.getCustomProductId());
        this.controller.makeList();
    }
    public void setProductId(int pid) {
        this.pid.setText(Integer.toString(pid));
    }

    
   @FXML
private void goupdate(ActionEvent event) throws SQLException {
    try {
        // create a new instance of the FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        // set the location of the FXML file for the EditCpController
        loader.setLocation(getClass().getResource("/com/artmart/GUI/views/CustomProduct/EditCp.fxml"));
        // load the FXML file and get the root node of the scene graph
        Parent root = loader.load();
        // get a reference to the EditCpController
        EditCpController editController = loader.getController();
        // set the ID of the custom product in the EditCpController
        editController.setProductId(this.p.getProductId());
        // create a new scene with the root node
        Scene scene = new Scene(root);
        // create a new stage and set the scene
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit Custom Product");
        // close the current window
        Stage currentStage = (Stage) deleteBtn.getScene().getWindow();
        currentStage.close();
        // show the new window
        stage.show();
    } catch (IOException e) {
        System.out.print(e.getMessage());
    }
}

}
 
    


