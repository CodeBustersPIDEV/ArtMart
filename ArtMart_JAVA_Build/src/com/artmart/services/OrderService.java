package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IOrderService;

public class OrderService implements IOrderService{
    SQLConnection connection = SQLConnection.getInstance();
}
