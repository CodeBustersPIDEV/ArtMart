package com.artmart.GUI.controllers.Order;

import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditShippingOptionsController implements Initializable {

    private final OrderService orderService = new OrderService();
    private List<ShippingOption> shippmentList;
    private ShippingOption selectOption = new ShippingOption();
    @FXML
    private TableView<ShippingOption> shippmentTableView;
    @FXML
    private TableColumn<ShippingOption, Integer> shippingId_Col;
    @FXML
    private TableColumn<ShippingOption, String> name_Col;
    @FXML
    private TableColumn<ShippingOption, String> carrier_Col;
    @FXML
    private TableColumn<ShippingOption, String> shippingSpd_Col;
    @FXML
    private TextArea regionUpdateField;
    @FXML
    private TextField feeUpdateField;
    @FXML
    private TextField speedUpdateField;
    @FXML
    private TextField carrierUpdateField;
    @FXML
    private TextField nameUpdateField;
    @FXML
    private Button SaveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    private String mode = "Add";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.refreshList();

    }

    public void enableFields(String mode) {
        this.mode = mode;
        this.SaveBtn.setDisable(false);
        this.cancelBtn.setDisable(false);
        this.regionUpdateField.setDisable(false);
        this.feeUpdateField.setDisable(false);
        this.speedUpdateField.setDisable(false);
        this.carrierUpdateField.setDisable(false);
        this.nameUpdateField.setDisable(false);
        if (this.mode.equals("Edit")) {
            this.deleteBtn.setDisable(false);
            this.regionUpdateField.setText(this.selectOption.getAvailableRegions());
            this.feeUpdateField.setText(this.selectOption.getShippingFee() + "");
            this.speedUpdateField.setText(this.selectOption.getShippingSpeed());
            this.carrierUpdateField.setText(this.selectOption.getCarrier());
            this.nameUpdateField.setText(this.selectOption.getName());
        } else {
            this.ClearFields();
        }
    }

    public void ClearFields() {
        this.regionUpdateField.setText("");
        this.feeUpdateField.setText("0.0");
        this.speedUpdateField.setText("");
        this.carrierUpdateField.setText("");
        this.nameUpdateField.setText("");
    }

    public void disableFields() {
        this.SaveBtn.setDisable(true);
        this.cancelBtn.setDisable(true);
        this.deleteBtn.setDisable(true);
        this.regionUpdateField.setDisable(true);
        this.nameUpdateField.setDisable(true);
        this.feeUpdateField.setDisable(true);
        this.speedUpdateField.setDisable(true);
        this.carrierUpdateField.setDisable(true);
        this.ClearFields();
    }

    @FXML
    private void OnSaveBtn(ActionEvent event) {
        this.selectOption.setAvailableRegions(this.regionUpdateField.getText());
        this.selectOption.setCarrier(this.carrierUpdateField.getText());
        this.selectOption.setName(this.nameUpdateField.getText());
        this.selectOption.setShippingFee(Double.valueOf(this.feeUpdateField.getText()));
        this.selectOption.setShippingSpeed(this.speedUpdateField.getText());
        if (this.mode.equals("Edit")) {
            this.orderService.updateShippingOption(this.selectOption);
        } else {
            this.orderService.createShippingOption(this.selectOption);
        }
        this.refreshList();
        this.disableFields();
    }

    @FXML
    private void OnCancelButton(ActionEvent event) {
        this.disableFields();
    }

    private void refreshList() {
        this.disableFields();
        this.shippmentList = this.orderService.getShippingOptions();
        ObservableList<ShippingOption> items = FXCollections.observableArrayList(
                this.shippmentList.stream().collect(Collectors.toList())
        );
        shippingId_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        carrier_Col.setCellValueFactory(new PropertyValueFactory<>("carrier"));
        shippingSpd_Col.setCellValueFactory(new PropertyValueFactory<>("shippingSpeed"));
        shippmentTableView.setItems(items);
        this.shippmentTableView.setRowFactory(tv -> {
            TableRow<ShippingOption> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    this.selectOption = row.getItem();
                    this.enableFields("Edit");
                }
            });
            return row;
        });
    }

    @FXML
    private void OnDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete " + this.selectOption.getName() + " Shipping Option");
        alert.setHeaderText("Are you sure you want to delete " + this.selectOption.getName() + " Shipping Option ?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.orderService.deleteShippingOption(selectOption.getId());
            this.refreshList();
            this.disableFields();

        }
    }

    @FXML
    private void OnAddBtn(ActionEvent event) {
        this.enableFields("Add");
    }
}
