/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.dao;

import com.artmart.interfaces.IClientDao;
import com.artmart.models.Client;
import java.sql.Connection;

/**
 *
 * @author 21697
 */
public class ClientDao implements IClientDao{
private Connection connection;

    public ClientDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client createAccountC(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client getClient(int client_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountC(int client_d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateAccountC(int client_id, Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
