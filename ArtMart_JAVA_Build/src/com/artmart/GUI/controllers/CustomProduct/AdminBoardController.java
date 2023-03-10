/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.CustomProduct;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AdminBoardController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    @FXML
    private TextField search;
    @FXML
    private Button searchBtn;
    @FXML
    private Label username;
    @FXML
    private ChoiceBox<?> profileChoiceBox;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<?> readyProductsTableView;
    @FXML
    private TableColumn<?, ?> productNameColumn;
    @FXML
    private TableColumn<?, ?> productUserColumn;
    @FXML
    private TableColumn<?, ?> priceColumn;
    @FXML
    private TableColumn<?, ?> categoryColumn;
    @FXML
    private TableColumn<?, ?> operationColumn;
    @FXML
    private TableView<?> categoriesTableView;
    @FXML
    private TableColumn<?, ?> catNameColumn;
    @FXML
    private TableColumn<?, ?> operationColumn3;
    @FXML
    private Button refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void onAdd(ActionEvent event) {
        
    }
    
    @FXML
    public void onBack(ActionEvent event) {
        
    }
    
    @FXML
    public void refreshScene(ActionEvent event) {
        
    }

    @FXML
    private void GoToShoppingCart(ActionEvent event) {
    }
}
