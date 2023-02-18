/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CustomProductDao;
import com.artmart.models.CustomProduct;
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
 * @author mahou
 */
public class CustomProductCardController implements Initializable {

    @FXML
    private Text nameTxt;
    @FXML
    private Text descTxt;
    @FXML
    private Text WaightTxt;
    
    private CustomProduct p=new CustomProduct();
    
    private CustomProductDao cPDao = new CustomProductDao();
    
    private CustomproductslistController controller=new CustomproductslistController();
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
        this.nameTxt.setText(p.getName());
        this.descTxt.setText(p.getDescription());
        this.WaightTxt.setText(""+p.getWeight());
    }

    @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.cPDao.deleteCustomProduct(this.p.getCustomProductId());
        this.controller.makeList();
    }
}
