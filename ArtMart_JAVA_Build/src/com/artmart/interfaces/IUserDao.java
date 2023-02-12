/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.User;

/**
 *
 * @author 21697
 */
public interface IUserDao {
    int createAccountU(User user);
    User getUser(int user_id);
    boolean deleteAccountU(int userId);
    User updateAccountU(int user_id, User user);
}
