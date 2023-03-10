package com.artmart.interfaces;

import com.artmart.models.Admin;
public interface IAdminDao {

    int createAccountA(Admin admin);

    Admin getAdmin(int admin_id);

    boolean deleteAccountA(int admin_id);

    boolean updateAccountA(int admin_id, Admin admin);
}
