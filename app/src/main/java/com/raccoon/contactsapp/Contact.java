package com.raccoon.contactsapp;

public class Contact {
    private String Name,Number,User_No;


    public Contact()
    {
        
    }
    public Contact(String Name,String Number,String User_no)
    {
        this.Name=Name;
        this.Number=Number;
        this.User_No=User_no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getUser_No() {
        return User_No;
    }

    public void setUser_No(String user_No) {
        User_No = user_No;
    }
}
