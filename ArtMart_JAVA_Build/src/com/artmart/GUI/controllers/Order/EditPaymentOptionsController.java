package com.artmart.GUI.controllers.Order;

import com.artmart.models.PaymentOption;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditPaymentOptionsController implements Initializable {

    private final OrderService orderService = new OrderService();
    private List<PaymentOption> paymentList;
    private PaymentOption selectOption = new PaymentOption();
    @FXML
    private TableColumn<PaymentOption, String> name_Col;
    @FXML
    private Button SaveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextArea regionUpdateField;
    @FXML
    private TextField nameUpdateField;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private TableView<PaymentOption> paymentTableView;
    @FXML
    private TableColumn<PaymentOption, Integer> paymentId_Col;
    @FXML
    private TableColumn<PaymentOption, String> contries_Col;
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
        this.nameUpdateField.setDisable(false);
        if (this.mode.equals("Edit")) {
            this.deleteBtn.setDisable(false);
            this.nameUpdateField.setText(this.selectOption.getName());
            this.regionUpdateField.setText(this.selectOption.getAvailableCountries());
        } else {
            this.ClearFields();
        }
    }

    public void ClearFields() {
        this.regionUpdateField.setText("");
        this.nameUpdateField.setText("");
    }

    public void disableFields() {
        this.SaveBtn.setDisable(true);
        this.cancelBtn.setDisable(true);
        this.deleteBtn.setDisable(true);
        this.regionUpdateField.setDisable(true);
        this.nameUpdateField.setDisable(true);
        this.ClearFields();
    }

    @FXML
    private void OnSaveBtn(ActionEvent event) {
        this.selectOption.setName(this.nameUpdateField.getText());
        this.selectOption.setAvailableCountries(this.regionUpdateField.getText());
        if (this.mode.equals("Edit")) {
            this.orderService.updatePaymentOption(this.selectOption);
        } else {
            this.orderService.createPaymentOption(this.selectOption);
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
        this.paymentList = this.orderService.getPaymentOptions();
        ObservableList<PaymentOption> items = FXCollections.observableArrayList(
                this.paymentList.stream().collect(Collectors.toList())
        );
        paymentId_Col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        contries_Col.setCellValueFactory(new PropertyValueFactory<>("availableCountries"));
        paymentTableView.setItems(items);
        this.paymentTableView.setRowFactory(tv -> {
            TableRow<PaymentOption> row = new TableRow<>();
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
        alert.setHeaderText("Are you sure you want to delete " + this.selectOption.getName() + " Payment Option ?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.orderService.deletePaymentOption(selectOption.getId());
            this.refreshList();
            this.disableFields();

        }
    }

    @FXML
    private void OnAddBtn(ActionEvent event) {
        this.enableFields("Add");
    }

}
