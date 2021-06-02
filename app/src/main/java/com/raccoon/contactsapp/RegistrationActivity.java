package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity {

    //fields
    private Button doneButton;
    private TextInputEditText userName,passWord,conPassword,phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Datahandler instance = Datahandler.getInstance(getApplicationContext());//singleton database
        Preferences preferences=Preferences.getInstance(getApplicationContext());//singleton preferences

        userName=findViewById(R.id.user_registration);
        passWord=findViewById(R.id.password_registration);
        conPassword=findViewById(R.id.password_con_registration);
        phoneNo=findViewById(R.id.phone_registration);

        doneButton=findViewById(R.id.done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().isEmpty()||passWord.getText().toString().isEmpty()||conPassword.getText().toString().isEmpty()||
                        phoneNo.getText().toString().isEmpty())
                    Toast.makeText(RegistrationActivity.this,"Missing Fields",0).show();
                else if(!(passWord.getText().toString().equals(conPassword.getText().toString())))//passwords do not match
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                else//new user created
                {
                    User us=new User(userName.getText().toString(),passWord.getText().toString(),phoneNo.getText().toString());

                    Boolean reg=instance.createUser(us,RegistrationActivity.this);

                    if(reg) {
                        Toast.makeText(RegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                        preferences.loggedIn(phoneNo.getText().toString());

                        Intent il = new Intent(RegistrationActivity.this, ContactListActivity.class);

                        il.putExtra("User_No", phoneNo.getText().toString());
                        startActivity(il);
                        finish();
                    }

                }
            }
        });
    }
}