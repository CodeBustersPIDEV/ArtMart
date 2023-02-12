package com.artmart.interfaces;

import com.artmart.models.OrderRefund;
import com.artmart.models.Wishlist;
import java.util.List;

public interface IOrderRefundDao {
    
    //OrderRefundDao 
    int createOrderRefund(OrderRefund orderRefund);
    OrderRefund getOrderRefund(int id);
    List<OrderRefund> getOrderRefunds();
    boolean updateOrderRefund(OrderRefund orderRefund);
    boolean deleteOrderRefund(int id);
    
}
