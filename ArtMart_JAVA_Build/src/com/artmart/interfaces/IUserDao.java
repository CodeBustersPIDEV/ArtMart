package com.artmart.interfaces;

import com.artmart.models.User;
import java.util.List;

public interface IUserDao {

    int createAccountU(User user);

    User getUser(int user_id);

    public boolean blockUser(int user_id, boolean state);

    int getUserIdByEmail(String email);

    boolean deleteAccountU(int userId);

    List<User> viewListOfUsers();

    boolean updateAccountU(int user_id, User user);

    int getUserIdByUsername(String username);

    public boolean authenticate(String username, String password);

    public void StoreToken(String token, String email);

    public String verifyToken(String email, String token);

}
