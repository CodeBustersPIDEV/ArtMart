package com.artmart.models;

import java.sql.Date;

public class User {

    private int user_id, phone_nbr;
    private String name, email, username, pwd, role, picture;
    private Date birthday, date_creation;
    boolean blocked;

    public User() {
    }

    public User(int user_id, int phone_nbr, String name, String email, String username, String pwd, Date birthday, Date date_creation, String role, String picture) {
        this.user_id = user_id;
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwd = pwd;
        this.birthday = birthday;
        this.date_creation = date_creation;
        this.role = role;
        this.picture = picture;

    }

    public User(int phone_nbr, String name, String email, String username, String pwd, String role, String picture, Date birthday) {
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwd = pwd;
        this.role = role;
        this.picture = picture;
        this.birthday = birthday;
    }
    
    
     public User(int phone_nbr, String name, String email, String username, String pwd, Date birthday, String picture) {
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwd = pwd;
        this.picture = picture;
        this.birthday = birthday;
    }

    public User(int phone_nbr, String name, String email, String username, String pwd, Date birthday) {
        this.phone_nbr = phone_nbr;
        this.name = name;
        this.email = email;
        this.username = username;
        this.pwd = pwd;
        this.birthday = birthday;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getUsername() {
        return username;
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

    public String getPicture() {
        return picture;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public void setUsername(String usename) {
        this.username = usename;
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

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", phone_nbr=" + phone_nbr + ", name=" + name + ", email=" + email + ", username=" + username + ", pwd=" + pwd + ", role=" + role + ", birthday=" + birthday + ", date_creation=" + date_creation + '}';
    }

}
