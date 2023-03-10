package com.artmart.interfaces;

import com.artmart.models.OrderRefund;
import java.util.List;

public interface IOrderRefundDao {

    int createOrderRefund(OrderRefund orderRefund);

    OrderRefund getOrderRefund(int id);

    List<OrderRefund> getOrderRefunds();

    boolean updateOrderRefund(OrderRefund orderRefund);

    boolean deleteOrderRefund(int id);

}
