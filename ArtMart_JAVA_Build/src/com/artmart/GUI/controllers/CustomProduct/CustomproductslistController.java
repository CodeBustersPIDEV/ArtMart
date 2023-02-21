    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.artmart.GUI.controllers.CustomProduct;

    import com.artmart.models.CustomProduct;
    import com.artmart.models.Product;
    import com.artmart.services.CustomProductService;
    import com.artmart.services.ProductService;
    import java.io.IOException;
    import java.net.URL;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.ResourceBundle;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.layout.VBox;

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

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            try{
         //   this.customProductService.createCustomProduct(new Product(1, "amir", "soltani", "Test",2, "Test", "Test"));
            this.makeList();
            }catch(SQLException e){}
        }    
        public void makeList() throws SQLException{
            this.vBox.getChildren().clear();
            this.customProductslist = this.customProductService.getAllCustomProducts();
            this.customProductslist.forEach(CProduct -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/CustomProduct/CustomProductCard.fxml"));
                    Parent root = loader.load();
                    CustomProductCardController controller = loader.getController();
                    controller.setCustomProduct(CProduct,this);
                    root.setId(""+CProduct.getCustomProductId());
                    this.vBox.getChildren().add(root);
                } catch (IOException e) {
                    System.out.print(e.getCause());
                }
            });
        }

    }