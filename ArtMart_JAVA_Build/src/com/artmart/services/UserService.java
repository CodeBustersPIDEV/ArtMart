package com.artmart.services;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IUserService;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import java.util.List;
import com.artmart.dao.*;
import java.sql.Connection;
import java.sql.SQLException;


public class UserService implements IUserService{
private Connection connection;
private UserDao userDao;
private ArtistDao artistDao;
private AdminDao adminDao;
private ClientDao clientDao;

    public UserService() {
        try{
            
        this.connection = SQLConnection.getInstance().getConnection();
        this.userDao = new UserDao(this.connection);
        this.artistDao = new ArtistDao(this.connection);
        this.adminDao = new AdminDao(this.connection);
        this.clientDao = new ClientDao(this.connection);
        
        }catch(SQLException e){
            System.err.print(e.getMessage());
        }
    }

    @Override
    public int createAccountU(User user) {
    return this.userDao.createAccountU(user);
    }

    @Override
    public User getUser(int user_id) {
return this.userDao.getUser(user_id);
    }

    @Override
    public boolean deleteAccountU(int user_id) {
return this.userDao.deleteAccountU(user_id);
    }

    @Override
    public boolean updateAccountU(int user_id, User user) {
return this.userDao.updateAccountU(user_id, user);
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

    @Override
    public Artist createAccountAr(Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Artist getArtist(int artist_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountAr(int artist_d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateAccountAr(int atist_id, Artist artist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin createAccountA(Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin getAdmin(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> viewListOfUsersA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountA(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateAccountA(int admin_id, Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    }

    


