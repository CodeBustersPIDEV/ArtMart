package com.artmart.GUI.controllers.Order;

import com.artmart.models.PaymentOption;
import com.artmart.models.ShippingOption;
import com.artmart.services.OrderService;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;
import org.json.JSONObject;

public class OrderCheckOutController implements Initializable {

    @FXML
    private ComboBox<String> paymentOptionsList;
    @FXML
    private ComboBox<String> shippingOptionsList;
    @FXML
    private TextArea shippingAddress;

    private List<PaymentOption> paymentList;
    private final OrderService orderService = new OrderService();
    private List<ShippingOption> shippmentList;
    private OrderGUIController orderGUIController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.shippmentList = this.orderService.getShippingOptions();
        ObservableList<String> shipItems = FXCollections.observableArrayList(
                this.shippmentList.stream().map(ShippingOption::getName).collect(Collectors.toList())
        );
        this.paymentList = this.orderService.getPaymentOptions();
        ObservableList<String> payItems = FXCollections.observableArrayList(
                this.paymentList.stream().map(PaymentOption::getName).collect(Collectors.toList())
        );
        this.paymentOptionsList.setItems(payItems);
        this.shippingOptionsList.setItems(shipItems);
        this.paymentOptionsList.getSelectionModel().selectFirst();
        this.shippingOptionsList.getSelectionModel().selectFirst();
    }

    @FXML
    private void OnPayAction(ActionEvent event) throws IOException {
        try {
            URL url = new URL("https://developers.flouci.com/api/generate_payment");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String payload = "{\n"
                    + "    \"app_token\": \"b9e12676-1459-41a1-b608-48d7c2cedcb0\",\n"
                    + "    \"app_secret\": \"9947737a-ee91-4119-b773-fc3a190d13af\",\n"
                    + "    \"accept_card\": \"true\",\n"
                    + "    \"amount\": \"100000\",\n"
                    + "    \"success_link\": \"http://localhost/artmart/success.html\",\n"
                    + "    \"fail_link\": \"http://localhost/artmart/fail.html\",\n"
                    + "    \"session_timeout_secs\": 1200,\n"
                    + "    \"developer_tracking_id\": \"<your_internal_tracking_id>\"\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String link = null;
            String paymentId = null;
            String output;
            while ((output = br.readLine()) != null) {
                JSONObject jsonResponse = new JSONObject(output);
                JSONObject result = jsonResponse.getJSONObject("result");
                link = result.getString("link");
                paymentId = result.getString("payment_id");
                Desktop.getDesktop().browse(new URI(link));
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/PaymentVerification.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            PaymentVerificationController controller = loader.getController();
            controller.setup(this, paymentId);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            conn.disconnect();
        } catch (IOException e) {
            System.out.println(e.getCause());
        } catch (URISyntaxException ex) {
            Logger.getLogger(OrderCheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void successfulPayment() {
        this.orderGUIController.successfulPayment(
                this.shippmentList.get(this.shippingOptionsList.getSelectionModel().getSelectedIndex()).getId(),
                this.shippingAddress.getText(),
                this.paymentList.get(this.paymentOptionsList.getSelectionModel().getSelectedIndex()).getId()
        );
        this.closeUI();
    }

    public void link(OrderGUIController controller) {
        this.orderGUIController = controller;
    }

    private void closeUI() {
        Stage thisPage = (Stage) shippingAddress.getScene().getWindow();
        thisPage.close();
    }

}
