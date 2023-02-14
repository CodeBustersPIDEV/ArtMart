/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.ReadyProduct;
import java.sql.SQLException;

/**
 *
 * @author rymae
 */
public interface IReadyProductDao {

    ReadyProduct getReadyProductById(int id) throws SQLException;

    int createReadyProduct(ReadyProduct readyProduct) throws SQLException;

    int updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException;

    int deleteReadyProduct(int id) throws SQLException;
}
