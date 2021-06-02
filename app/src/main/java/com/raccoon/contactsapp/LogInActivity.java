package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LogInActivity extends AppCompatActivity {


    //fields
    private TextInputEditText numberEdit,passwordEdit;
    private Button logIn,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Datahandler instance = Datahandler.getInstance(getApplicationContext());//singleton database
        Preferences preferences=Preferences.getInstance(getApplicationContext());//singleton preferences

        logIn=findViewById(R.id.log_in_button);
        register=findViewById(R.id.register_button);

        numberEdit=findViewById(R.id.number_text_in);
        passwordEdit=findViewById(R.id.password_text_in);

        //login button
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(numberEdit.getText().toString().isEmpty()||passwordEdit.getText().toString().isEmpty())
                    Toast.makeText(LogInActivity.this,"Missing Fields",0).show();
                else
                {
                    numberEdit = findViewById(R.id.number_text_in);
                    passwordEdit = findViewById(R.id.password_text_in);

                    Boolean logged=instance.logIn(numberEdit.getText().toString(), passwordEdit.getText().toString(), LogInActivity.this);

                    if(logged) {
                        preferences.loggedIn(numberEdit.getText().toString());
                        Intent il = new Intent(LogInActivity.this, ContactListActivity.class);

                        il.putExtra("User_No", numberEdit.getText().toString());
                        startActivity(il);
                        finish();
                    }
                }
            }
        });

        //register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir=new Intent(LogInActivity.this, RegistrationActivity.class);//registration activity
                startActivity(ir);
            }
        });
    }
}