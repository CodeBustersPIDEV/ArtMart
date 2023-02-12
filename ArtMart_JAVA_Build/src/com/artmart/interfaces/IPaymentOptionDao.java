package com.artmart.interfaces;
import com.artmart.models.*;
import java.util.List;

public interface IPaymentOptionDao {
    
    int createPaymentOption(PaymentOption paymentOption);
    PaymentOption getPaymentOption(int id);
    List<PaymentOption> getPaymentOptions();
    boolean updatePaymentOption(PaymentOption paymentOption);
    boolean deletePaymentOption(int id);
    
}
