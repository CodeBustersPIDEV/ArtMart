package com.artmart.interfaces;
import com.artmart.models.User;
import com.artmart.models.Client;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import java.util.List;
public interface IUserService {
    //user DAO
    int createAccountU(User user);
    User getUser(int user_id);
    boolean deleteAccountU(int userId);
    boolean updateAccountU(int user_id, User user);
    //client DAO
     Client createAccountC(Client client);
     Client getClient(int client_id);
    boolean deleteAccountC(int client_d);
    boolean updateAccountC(int client_id,Client client);
     //artist DAO
    Artist createAccountAr(Artist artist);
      Artist getArtist(int artist_id);
    boolean deleteAccountAr(int artist_d);
    boolean updateAccountAr(int atist_id, Artist artist);
     //admin DAO
     Admin createAccountA(Admin admin);
    Admin getAdmin(int admin_id);
    List<User> viewListOfUsersA();
    boolean deleteAccountA(int admin_id);
    boolean updateAccountA(int admin_id, Admin admin);
}
