package com.artmart.services;

import com.artmart.interfaces.IUserService;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import java.util.List;
import com.artmart.dao.*;

public class UserService implements IUserService {

    private final UserDao userDao = new UserDao();
    private final ArtistDao artistDao = new ArtistDao();
    private final AdminDao adminDao = new AdminDao();
    private final ClientDao clientDao = new ClientDao();

    @Override
    public int createAccountC(Client client) {
        return this.clientDao.createAccountC(client);
    }

    @Override
    public Client getClient(int user_id) {
        return this.clientDao.getClient(user_id);
    }

    @Override
    public boolean deleteAccountC(int user_id) {
        return this.clientDao.deleteAccountC(user_id);
    }

    @Override
    public boolean updateAccountC(int user_id, Client client) {
        return this.clientDao.updateAccountC(user_id, client);
    }

    @Override
    public int createAccountAr(Artist artist) {
        return this.artistDao.createAccountAr(artist);
    }

    @Override
    public Artist getArtist(int user_id) {
        return this.artistDao.getArtist(user_id);
    }

    @Override
    public boolean deleteAccountAr(int artist_d) {
        return this.artistDao.deleteAccountAr(artist_d);
    }

    @Override
    public boolean updateAccountAr(int user_id, Artist artist) {
        return this.artistDao.updateAccountAr(user_id, artist);
    }

    @Override
    public int createAccountA(Admin admin) {
        return this.adminDao.createAccountA(admin);
    }

    @Override
    public Admin getAdmin(int user_id) {
        return this.adminDao.getAdmin(user_id);
    }

    @Override
    public List<User> viewListOfUsers() {
        return this.userDao.viewListOfUsers();
    }

    @Override
    public boolean deleteAccountA(int user_id) {
        return this.adminDao.deleteAccountA(user_id);
    }

    @Override
    public boolean updateAccountA(int user_id, Admin admin) {
        return this.adminDao.updateAccountA(user_id, admin);
    }

}
