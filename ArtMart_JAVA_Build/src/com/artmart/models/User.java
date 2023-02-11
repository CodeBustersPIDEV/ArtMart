/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.models;

import java.util.Date;

/**
 *
 * @author 21697
 */
public class User {
    private int user_id,phone_nbr;
    private String name,email,usename,pwd;
    private Date birthday,date_creation;
    
    public User() {
    }
    
     public User(int user_id, int phone_nbr, String name, String email, String usename, String pwd, Date birthday, Date date_creation) {
        this.user_id = user_id;
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.usename = usename;
        this.pwd = pwd;
        this.birthday = birthday;
        this.date_creation = date_creation;
    }

    public User(int phone_nbr, String name, String email, String usename, String pwd, Date birthday, Date date_creation) {
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.usename = usename;
        this.pwd = pwd;
        this.birthday = birthday;
        this.date_creation = date_creation;
    }
    
    
    public int getUser_id() {
        return user_id;
    }

    public int getPhone_nbr() {
        return phone_nbr;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsename() {
        return usename;
    }

    public String getPwd() {
        return pwd;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getDate_creation() {
        return date_creation;
    }

   

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPhone_nbr(int phone_nbr) {
        this.phone_nbr = phone_nbr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

   
    
    
    
}
