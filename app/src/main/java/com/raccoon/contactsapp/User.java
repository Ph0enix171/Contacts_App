package com.raccoon.contactsapp;

public class User {
    private String Username,Password,Phone_No;

    public User(String Username,String Password,String Phone_No)
    {
        this.Username=Username;
        this.Password=Password;
        this.Phone_No=Phone_No;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone_No) {
        Phone_No = phone_No;
    }
}
