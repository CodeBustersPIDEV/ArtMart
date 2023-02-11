package com.artmart.interfaces;
import com.artmart.models.User;
import com.artmart.models.Client;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
public interface IUserService {
    User createAccountU(User user);
    User viewAccountDetailsU(int user_id);
    boolean deleteAccountU(int userId);
    User updateAccountU(int user_id, User user);
    
     Client createAccountC(Client client);
    Client viewAccountDetailsC(int client_id);
    boolean deleteAccountC(int client_d);
    Client updateAccountC(int client_id,Client client);
    
    Artist createAccountAr(Artist artist);
    Artist viewAccountDetailsAr(int artist_id);
    boolean deleteAccountAr(int artist_d);
    Artist updateAccountAr(int atist_id, Artist artist);
    
     Admin createAccountA(Admin admin);
    Admin viewAccountDetailsA(int admin_id);
    void viewListOfUsersA();
    boolean deleteAccountA(int admin_id);
    Admin updateAccountA(int admin_id, Admin admin);
}
