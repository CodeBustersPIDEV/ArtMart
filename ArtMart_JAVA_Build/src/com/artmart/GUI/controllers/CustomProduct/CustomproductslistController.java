package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.models.CustomProduct;
import com.artmart.models.Product;
import com.artmart.services.CustomProductService;
import com.artmart.services.ProductService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class CustomproductslistController implements Initializable {

    private final ProductService productService = new ProductService();
    private final CustomProductService customProductService = new CustomProductService();

    @FXML
    private VBox vBox;

    private List<CustomProduct> customProductslist;

    @FXML
    private Button searchb;

    @FXML
    private TextField search;
    @FXML
    private RadioButton sweight;
    @FXML
    private RadioButton sname;
    @FXML
    private Button statisticb;
    @FXML
    private Text total;
    @FXML
    private Text totalp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // this.customProductService.createCustomProduct(new Product(1, "amir",
            // "soltani", "Test",2, "Test", "Test"));
            this.makeList();
            calculateTotalWeight();
            calculateProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void calculateTotalWeight() throws SQLException {
        float totalWeight = 0;
        for (CustomProduct customProduct : customProductslist) {
            totalWeight += customProduct.getWeight();
        }
        total.setText(String.format("%.2f", totalWeight));
    }

    void calculateProduct() throws SQLException {
        int totalProducts = customProductslist.size();
        totalp.setText(String.format("%d", totalProducts));
    }

    public void makeList() throws SQLException {
        this.vBox.getChildren().clear();
        this.customProductslist = this.customProductService.getAllCustomProducts();
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void onsearch(ActionEvent event) throws SQLException {
        String keyword = search.getText();
        List<CustomProduct> matchingProducts = customProductService.searchCustomProductByName(keyword);
        this.vBox.getChildren().clear();
        matchingProducts.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void byweight(ActionEvent event) {

        this.sname.setSelected(false);
        this.sweight.setSelected(true);
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getWeight));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void byname(ActionEvent event) {

        this.sname.setSelected(true);
        this.sweight.setSelected(false);
        this.vBox.getChildren().clear();
        this.customProductslist.sort(Comparator.comparing(CustomProduct::getName));
        this.customProductslist.forEach(CProduct -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                Parent root = loader.load();
                CustomProductCardController controller = loader.getController();
                controller.setCustomProduct(CProduct, this);
                root.setId("" + CProduct.getCustomProductId());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            } catch (SQLException ex) {
                Logger.getLogger(CustomproductslistController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void statistic(ActionEvent event) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/com/artmart/GUI/views/CustomProduct/WeightStatisticsUI.fxml"));

            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Custom Product Managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @FXML
    private void onexit(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/artmart/GUI/views/CustomProduct/Custom Product.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
