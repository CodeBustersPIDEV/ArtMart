/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.ReadyProduct;
import java.util.List;

/**
 *
 * @author rymae
 */
public interface IReadyProductService {

    ReadyProduct getReadyProductById(int id);

    List<ReadyProduct> getAllCustomProducts();

    int createReadyProduct(ReadyProduct readyProduct);

    boolean updateReadyProduct(ReadyProduct readyProduct);

    boolean deleteReadyProduct(int id);
}
