package com.artmart.interfaces;

import com.artmart.models.Order;
import java.util.List;

public interface IOrderServiceDao {

    int createOrder(Order order);

    Order readOrder(int id);

    List<Order> getAllOrders();
    
    List<Order> getAllOrdersById(int id);

    boolean updateOrder(Order order);

    boolean deleteOrder(int id);

}
