package com.raccoon.contactsapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static Preferences loggedIn;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static synchronized Preferences getInstance(Context context)
    {
        if(loggedIn==null)
            loggedIn=new Preferences(context.getApplicationContext());
        return loggedIn;
    }

    public Preferences(Context context)
    {

        pref = context.getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        editor.putBoolean("loggedIn",false);

    }

    public void loggedIn(String userNo)
    {
        editor.putBoolean("loggedIn",true);
        editor.putString("userNo",userNo);
        editor.commit();
    }
    public void loggedOut()
    {
        editor.putBoolean("loggedIn",false);
        editor.commit();
    }

    public Boolean getStatus()
    {
        return pref.getBoolean("loggedIn",false);

    }
    public String getUserNo()
    {
       return pref.getString("userNo","");
    }
}
