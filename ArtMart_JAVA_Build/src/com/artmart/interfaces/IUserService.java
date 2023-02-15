package com.artmart.interfaces;
import com.artmart.models.User;
import com.artmart.models.Client;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import java.util.List;
public interface IUserService {
   
    //client DAO
     int createAccountC(Client client);
     Client getClient(int user_id);
    boolean deleteAccountC(int user_id);
    boolean updateAccountC(int user_id,Client client);
     //artist DAO
    int createAccountAr(Artist artist);
      Artist getArtist(int user_id);
    boolean deleteAccountAr(int user_id);
    boolean updateAccountAr(int user_id, Artist artist);
     //admin DAO
     int createAccountA(Admin admin);
    Admin getAdmin(int user_id);
    List<User> viewListOfUsers();
    boolean deleteAccountA(int user_id);
    boolean updateAccountA(int user_id, Admin admin);
}
