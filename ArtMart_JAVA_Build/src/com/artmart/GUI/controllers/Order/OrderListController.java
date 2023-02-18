package com.artmart.GUI.controllers.Order;

import com.artmart.models.Order;
import com.artmart.models.User;
import com.artmart.services.OrderService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class OrderListController implements Initializable {

    private User user;
    @FXML
    private VBox orderCard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderCard.setAlignment(Pos.CENTER);
        orderCard.setSpacing(5);
    }

    public void setUser(User user) {
        this.user = user;
        this.refreshList();
    }
    
    public void refreshList(){
        orderCard.getChildren().clear();
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.getOrdersById(this.user.getUser_id());
        orderList.forEach(order -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Order/OrderCard.fxml"));
                Parent root = loader.load();
                OrderCardController controller = loader.getController();
                controller.setupData(order,this);
                root.setId(""+order.getId());
                orderCard.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
    }
}
