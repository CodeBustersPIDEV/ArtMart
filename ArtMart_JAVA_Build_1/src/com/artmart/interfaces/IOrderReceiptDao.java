package com.artmart.interfaces;

import com.artmart.models.Receipt;
import java.util.List;

public interface IOrderReceiptDao {

    int createReceipt(Receipt receipt);

    Receipt getReceipt(int id);

    List<Receipt> getReceipts();

    boolean updateReceipt(Receipt receipt);

    boolean deleteReceipt(int id);
}
