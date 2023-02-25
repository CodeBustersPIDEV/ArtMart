package com.artmart.interfaces;

import com.artmart.models.User;
import com.artmart.models.Client;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import java.util.List;

public interface IUserService {

    User getUser(int user_id);

    boolean updateAccountU(int user_id, User user);

    public boolean blockUser(int user_id, boolean state);

    int getUserIdByUsername(String username);

    int getUserIdByEmail(String email);

    int createAccountC(Client client);

    Client getClient(int user_id);

    boolean deleteAccountC(int user_id);

    boolean updateAccountC(int user_id, Client client);

    int createAccountAr(Artist artist);

    Artist getArtist(int user_id);

    boolean deleteAccountAr(int user_id);

    boolean updateAccountAr(int user_id, Artist artist);

    int createAccountA(Admin admin);

    Admin getAdmin(int user_id);

    List<User> viewListOfUsers();

    boolean deleteAccountA(int user_id);

    boolean updateAccountA(int user_id, Admin admin);

    public boolean authenticate(String username, String password);

}
