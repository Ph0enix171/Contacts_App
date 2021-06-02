package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    Handler handler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Preferences preferences=Preferences.getInstance(getApplicationContext());//singleton preferences

        if(preferences.getStatus())//logged in
        {
            intent=new Intent(MainActivity.this,ContactListActivity.class);
            intent.putExtra("User_No",preferences.getUserNo());
        }
        else//logged out
        {
            intent=new Intent(MainActivity.this,LogInActivity.class);
        }

        handler=new Handler();//loading screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.startActivity(intent);
                finish();
            }
        },2000);

    }
}