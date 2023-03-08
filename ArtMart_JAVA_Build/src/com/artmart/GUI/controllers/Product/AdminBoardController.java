/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Product;

import com.artmart.GUI.controllers.User.SignUpController;
import com.artmart.dao.UserDao;
import com.artmart.models.ReadyProduct;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.DatabaseUtilsService;
import com.artmart.services.ReadyProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rymae
 */
public class AdminBoardController implements Initializable {

    private final ReadyProductService readyProductService = new ReadyProductService();

    @FXML
    private TabPane tabPane;
    private DatabaseUtilsService databaseUtilsService;
//    @FXML
//    private VBox vBox;
//    @FXML
//    private TextField search;
//    @FXML
//    private Button searchBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button addProduct;
    @FXML
    private ChoiceBox<String> profileChoiceBox;

    @FXML
    private Button profileButton;

    @FXML
    private Label profileLabel;
    @FXML
    private Label username;
    HashMap user = (HashMap) Session.getActiveSessions();
    private Session session = new Session();
    private User connectedUser = new User();
    private final UserDao userService = new UserDao();
    SignUpController profile = new SignUpController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.session = (Session) user.get(user.keySet().toArray()[0]);
        this.connectedUser = this.userService.getUser(this.session.getUserId());
        this.username.setText(this.connectedUser.getName());

        // Get the list of table names from the database and create a tab for each table
        List<String> tableNames = null;
        try {
            tableNames = databaseUtilsService.getTableNames();
        } catch (SQLException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String tableName : tableNames) {
            Tab tab = new Tab(tableName);
            tabPane.getTabs().add(tab);

            // Create a TableView for each table and add it to the tab
            TableView tableView = new TableView();
            tab.setContent(tableView);

            // Get the column names for the current table and add them to the TableView
            List<String> columnNames = null;
            try {
                columnNames = databaseUtilsService.getTableColumnNames(tableName);
            } catch (SQLException ex) {
                Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String columnName : columnNames) {
                TableColumn tableColumn = new TableColumn(columnName);
                tableView.getColumns().add(tableColumn);
            }

            // create TableColumn objects for each column
            List<TableColumn<List<Object>, Object>> columns = new ArrayList<>();
            for (int i = 0; i < columnNames.size(); i++) {
                final int columnIndex = i;
                TableColumn<List<Object>, Object> column = new TableColumn<>(columnNames.get(i));
                column.setCellValueFactory(cellData -> {
                    List<Object> row = cellData.getValue();
                    return new SimpleObjectProperty<>(row.get(columnIndex));
                });
                columns.add(column);
            }

            // get the table data and set it to the TableView
            List<List<Object>> tableData = null;
            try {
                tableData = databaseUtilsService.getTableData(tableName);
            } catch (SQLException ex) {
                Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ObservableList<List<Object>> observableData = FXCollections.observableArrayList(tableData);
            tableView.setItems(observableData);
            tableView.getColumns().addAll(columns);
        }

        // Create a map of display names to IDs
        Map<String, String> profileActions = new HashMap<>();
        profileActions.put("Logout", "logout");
        profileActions.put("Profile", "profile");

        // Populate the choice box with display names
        profileChoiceBox.getItems().addAll(profileActions.keySet());

        // Add an event listener to handle the selected item's ID
        profileChoiceBox.setOnAction(event -> {
            String selectedItem = profileChoiceBox.getSelectionModel().getSelectedItem();
            String selectedId = profileActions.get(selectedItem);
            // Handle the action based on the selected ID
            if ("profile".equals(selectedId)) {
                profile.goToProfile(event, "ProfileClient");
            } else if ("logout".equals(selectedId)) {
                session.logOut("1");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/User/login.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(ReadyproductsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void onAdd(ActionEvent event) {
        try {
            Stage stage = (Stage) addProduct.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Product/AddReadyProduct.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Add Ready Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void onBack(ActionEvent event) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/Product.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

//    @FXML
//    private void onSearch(ActionEvent event) throws SQLException {
//        String keyword = search.getText();
//        List<ReadyProduct> matchingProducts = readyProductService.searchReadyProductByName(keyword);
//        this.vBox.getChildren().clear();
//        matchingProducts.forEach(rP -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/ReadyProductCard.fxml"));
//                Parent root = loader.load();
//                AdminCardController controller = loader.getController();
//                controller.setReadyProduct(rP, this);
//                root.setId("" + rP.getReadyProductId());
//                this.vBox.getChildren().add(root);
//            } catch (IOException e) {
//                System.out.print(e.getCause());
//            } catch (SQLException ex) {
//                Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//    }
    public void refreshScene(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Product/AdminBoard.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
