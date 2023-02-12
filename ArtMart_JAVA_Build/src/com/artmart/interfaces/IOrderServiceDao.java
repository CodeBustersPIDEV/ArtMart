package com.artmart.interfaces;

import com.artmart.models.Order;
import java.util.List;

public interface IOrderServiceDao {
        
    //OrderDao
    Order createOrder(Order order);
    Order readOrder(int id);
    List<Order> getAllOrders();
    boolean updateOrder(Order order);
    boolean deleteOrder(int id);
    
}
