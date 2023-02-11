package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IUserService;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;

public class UserService implements IUserService{
    SQLConnection connection = SQLConnection.getInstance();

    @Override
    public User createAccountU(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public User viewAccountDetailsU(int user_id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean deleteAccountU(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public User updateAccountU(int user_id, User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client createAccountC(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client viewAccountDetailsC(int client_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountC(int client_d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client updateAccountC(int client_id, Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist createAccountAr(Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist viewAccountDetailsAr(int artist_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountAr(int artist_d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist updateAccountAr(int atist_id, Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin createAccountA(Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin viewAccountDetailsA(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void viewListOfUsersA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountA(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin updateAccountA(int admin_id, Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
