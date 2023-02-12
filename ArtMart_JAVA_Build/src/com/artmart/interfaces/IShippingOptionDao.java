package com.artmart.interfaces;
import com.artmart.models.*;
import java.util.List;

public interface IShippingOptionDao {
    
    int createShippingOption(ShippingOption shippingOption);
    ShippingOption getShippingOption(int id);
    List<ShippingOption> getShippingOptions();
    boolean updateShippingOption(ShippingOption shippingOption);
    boolean deleteShippingOption(int id);
    
}
