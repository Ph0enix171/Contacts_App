package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddContactActivity extends AppCompatActivity {

    //fields
    private TextInputEditText name,number;
    private Button doneButton;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent li=new Intent(AddContactActivity.this,ContactListActivity.class);
        li.putExtra("User_No",getIntent().getStringExtra("User_No"));
        AddContactActivity.this.startActivity(li);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //intent
        Intent ai=getIntent();

        name=findViewById(R.id.name_add);
        number=findViewById(R.id.number_add);

        Datahandler instance = Datahandler.getInstance(getApplicationContext());//singleton database

        String nameAdd,numberAdd;//new contact details

        String userNo=ai.getStringExtra("User_No");//foreign key

        doneButton=findViewById(R.id.done_button);

        //done button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().isEmpty()||number.getText().toString().isEmpty())
                    Toast.makeText(AddContactActivity.this,"Missing Fields",1).show();
                else
                {
                    Contact ct=new Contact(name.getText().toString(),number.getText().toString(),userNo);
                    instance.newContact(ct);
                    Toast.makeText(AddContactActivity.this,"Contact Added",1).show();

                    Intent li=new Intent(AddContactActivity.this,ContactListActivity.class);
                    li.putExtra("User_No",userNo);
                    AddContactActivity.this.startActivity(li);
                    finish();
                }
            }
        });


    }
}