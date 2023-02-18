package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.services.OrderService;
import com.artmart.services.ProductService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class OrderCardController implements Initializable {
    
    private final ProductService productDao=new ProductService();
    
    private final OrderService orderService=new OrderService();
    
    private Order order;
    @FXML
    private Text productNameValue;
    @FXML
    private Text quantityValue;
    @FXML
    private Text AddressValue;
    @FXML
    private Text orderValue;
    @FXML
    private Text costValue;
    
    @FXML
    private Text statusValue;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void setOrder(Order order){
        this.order = order;
        try{
        this.productNameValue.setText(this.productDao.getProductById(this.order.getProductId()).getName());
        this.quantityValue.setText(String.valueOf(this.order.getQuantity()));
        this.statusValue.setText(this.orderService.getOrderStatusByOrderId(order.getId()).getStatus());
        this.AddressValue.setText(String.valueOf(this.order.getShippingAddress()));
        this.orderValue.setText(String.valueOf(this.order.getOrderDate()));
        this.costValue.setText(String.valueOf(this.order.getTotalCost()));
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }
    
}
