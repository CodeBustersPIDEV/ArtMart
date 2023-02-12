package com.artmart.interfaces;
import com.artmart.models.*;
import java.util.List;

public interface IOrderStatusDao {
    
    //OrderStatusDao 
    int createOrderStatus(OrderStatus orderStatus);
    OrderStatus getOrderStatus(int id);
    List<OrderStatus> getOrderStatuses();
    boolean updateOrderStatus(OrderStatus orderStatus);
    boolean deleteOrderStatus(int id);
    
}
