/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.models;

import java.sql.Date;

/**
 *
 * @author 21697
 */
public class Admin extends User {
    private int admin_id;
    private String department;


    public Admin(int admin_id) {
        this.admin_id = admin_id;
    }

    public Admin() {
        
    }

    public Admin(User user) {
        super(user.getPhone_nbr(), user.getName(), user.getEmail(), user.getUsername(), user.getPwd(), user.getBirthday(), user.getRole());
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
    
}
