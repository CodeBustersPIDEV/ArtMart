package com.artmart.interfaces;

import com.artmart.models.*;
import java.util.List;

public interface IOrderStatusDao {

    int createOrderStatus(OrderStatus orderStatus);

    OrderStatus getOrderStatus(int id);
    
    OrderStatus getOrderStatusByOrderId(int id);

    List<OrderStatus> getOrderStatuses();

    boolean updateOrderStatus(OrderStatus orderStatus);

    boolean deleteOrderStatus(int id);

}
