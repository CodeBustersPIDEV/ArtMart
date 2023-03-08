package com.artmart.interfaces;

import com.artmart.models.ReadyProduct;
import java.sql.SQLException;
import java.util.List;

public interface IReadyProductDao {

    List<ReadyProduct> getAllReadyProducts(int uID) throws SQLException;
    
    List<ReadyProduct> getAllReadyProducts() throws SQLException;

    ReadyProduct getReadyProductById(int id, int uID) throws SQLException;

    int createReadyProduct(ReadyProduct readyProduct) throws SQLException;

    boolean updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException;

    int deleteReadyProduct(int id) throws SQLException;
}
