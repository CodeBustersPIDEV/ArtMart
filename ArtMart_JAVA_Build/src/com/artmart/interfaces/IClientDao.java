/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Client;

/**
 *
 * @author 21697
 */
public interface IClientDao {
    int createAccountC(Client client);
     Client getClient(int client_id);
    boolean deleteAccountC(int client_d);
    boolean updateAccountC(int client_id,Client client);
}
