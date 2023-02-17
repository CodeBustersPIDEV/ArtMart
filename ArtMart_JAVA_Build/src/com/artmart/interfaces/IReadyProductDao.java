package com.artmart.interfaces;

import com.artmart.models.ReadyProduct;
import java.sql.SQLException;
import java.util.List;

public interface IReadyProductDao {

    List<ReadyProduct> getAllReadyProducts() throws SQLException;

    ReadyProduct getReadyProductById(int id) throws SQLException;

    int createReadyProduct(ReadyProduct readyProduct) throws SQLException;

    int updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException;

    int deleteReadyProduct(int id) throws SQLException;
}
