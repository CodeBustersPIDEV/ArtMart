/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.interfaces;

import com.artmart.models.Admin;
import com.artmart.models.User;
import java.util.List;

/**
 *
 * @author 21697
 */
public interface IAdminDao {
     int createAccountA(Admin admin);
    Admin getAdmin(int admin_id);
    List<User> viewListOfUsersA();
    boolean deleteAccountA(int admin_id);
    boolean updateAccountA(int admin_id, Admin admin);
}
