/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.ApplyDao;
import com.artmart.models.Apply;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class ApplyallController implements Initializable {

    private Apply apply;
    private ApplyDao applyDao;
    @FXML
    private Text pid;
    @FXML
    private Text Artisttxt;
    @FXML
    private Text CPtxt;
    @FXML
    private Text statustxt;
    private ApplyalllistController controller = new ApplyalllistController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyDao = new ApplyDao();
    }

    public void setApply(Apply apply,ApplyalllistController wiw) throws SQLException {
        this.apply = apply;
        this.controller = wiw;
        String artistName = applyDao.getartisttNameById(apply.getArtist_ID());
        String cpn = applyDao.getCustomProductNameById(apply.getCustomproduct_ID());
        Artisttxt.setText(artistName);
        CPtxt.setText(cpn);
        statustxt.setText(apply.getStatus());
        pid.setText(String.valueOf(apply.getApply_ID()));
    }

    @FXML
    private void delete(ActionEvent event) throws SQLException {
        applyDao.deleteApply(this.apply.getApply_ID());
        this.controller.displayApplies();
    }
}
