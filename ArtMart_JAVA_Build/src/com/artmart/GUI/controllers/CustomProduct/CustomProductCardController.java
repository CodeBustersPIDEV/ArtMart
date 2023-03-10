package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CategoriesDao;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private CustomProduct p = new CustomProduct();
    private CategoriesDao s = new CategoriesDao();
    private CustomProductDao cPDao = new CustomProductDao();

    private CustomproductslistController controller = new CustomproductslistController();
    @FXML
    private Button deleteBtn;
    @FXML
    private Text CID;
    @FXML
    private Text dim;
    @FXML
    private Text mat;

    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCustomProduct(CustomProduct param, CustomproductslistController controller) throws SQLException {
        this.p = param;
        this.controller = controller;
        this.pid.setText(Integer.toString(param.getCustomProductId()));
        // Retrieve the category name from the database using the CategoryDao
        String categoryName = s.getCategoryNameById(p.getCategoryId());
        this.CID.setText(categoryName);
        this.dim.setText(p.getDimensions());
        this.mat.setText(p.getMaterial());
        Image image = new Image(p.getImage());
        System.out.println(p.getImage());
        this.img.setImage(image);

        this.nameTxt.setText(p.getName());
        this.descTxt.setText(p.getDescription());
        this.WaightTxt.setText("" + p.getWeight());
    }

    @FXML
    private void OnDelete(ActionEvent event) throws SQLException {
        this.cPDao.deleteCustomProduct(this.p.getCustomProductId());
        this.controller.makeList();
        this.controller.calculateTotalWeight();
        this.controller.calculateProduct();
    }

    @FXML
    private void goupdate(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/artmart/GUI/views/CustomProduct/EditCp.fxml"));
            Parent root = loader.load();
            EditCpController editController = loader.getController();
            editController.setUpData(this.pid.getText());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Custom Product");
            Stage currentStage = (Stage) deleteBtn.getScene().getWindow();
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
