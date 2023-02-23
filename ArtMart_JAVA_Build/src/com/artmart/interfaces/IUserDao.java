package com.artmart.interfaces;

import com.artmart.models.User;
import java.util.List;

public interface IUserDao {

    int createAccountU(User user);

    User getUser(int user_id);

    boolean deleteAccountU(int userId);

    List<User> viewListOfUsers();

    boolean updateAccountU(int user_id, User user);

    public boolean authenticate(String username, String password);
    
}
