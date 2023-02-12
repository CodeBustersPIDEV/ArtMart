package com.artmart.interfaces;

import com.artmart.models.OrderUpdate;
import java.util.List;

public interface IOrderUpdateDao {
    
    int createOrderUpdate(OrderUpdate orderUpdate);
    OrderUpdate getOrderUpdate(int id);
    List<OrderUpdate> getOrderUpdates();
    boolean updateOrderUpdate(OrderUpdate orderUpdate);
    boolean deleteOrderUpdate(int id);
}
