package com.artmart.dao;

import com.artmart.interfaces.IAdminDao;
import com.artmart.models.Admin;
import com.artmart.models.User;
import java.sql.Connection;
import java.util.List;

public class AdminDao implements IAdminDao{
private Connection connection;

    public AdminDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Admin createAccountA(Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin getAdmin(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> viewListOfUsersA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAccountA(int admin_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin updateAccountA(int admin_id, Admin admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
