package com.artmart.interfaces;

import com.artmart.models.*;
import com.artmart.utils.OrderCurrentStatus;
import java.util.List;

public interface IOrderStatusDao {

    int createOrderStatus(OrderStatus orderStatus);

    OrderStatus getOrderStatus(int id);
    
    OrderStatus getOrderStatusByOrderId(int id);

    List<OrderStatus> getOrderStatuses();

    boolean updateOrderStatus(int id,OrderCurrentStatus status);

    boolean deleteOrderStatus(int id);

}
