package com.artmart.interfaces;

import com.artmart.models.Client;

public interface IClientDao {

    int createAccountC(Client client);

    Client getClient(int client_id);

    boolean deleteAccountC(int client_d);

    boolean updateAccountC(int client_id, Client client);
}
