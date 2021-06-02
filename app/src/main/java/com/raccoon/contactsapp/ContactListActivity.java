package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    //fields
    private Button logOut;
    private FloatingActionButton addContact;
    private TextView contactCount,noContact;
    private RecyclerView contactList;

    public static String userNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        //intent
        Intent arrival=getIntent();
        userNo=arrival.getStringExtra("User_No");

        Datahandler instance = Datahandler.getInstance(getApplicationContext());//singleton database
        Preferences preferences=Preferences.getInstance(getApplicationContext());//singleton preferences

        logOut=findViewById(R.id.log_out_button);
        addContact=findViewById(R.id.add_contact_fab);
        contactCount=findViewById(R.id.contact_count);
        contactList=findViewById(R.id.contact_list);
        noContact=findViewById(R.id.no_contacts_view);

        int noOfContacts=instance.getCount();

        if(noOfContacts==0)
        {
            contactCount.setText("0");
            contactList.setVisibility(View.GONE);
            noContact.setVisibility(View.VISIBLE);
        }
        else {
            contactList.setVisibility(View.VISIBLE);
            noContact.setVisibility(View.GONE);

            //set count
            contactCount.setText(Integer.toString(instance.getCount()));
            //set adapter
            RecyclerView contactListView = findViewById(R.id.contact_list);
            List<Contact> contactList = instance.getAllContacts(userNo);
            ContactsAdapter contactsAdapter = new ContactsAdapter(ContactListActivity.this, contactList);
            contactListView.setAdapter(contactsAdapter);
        }
            //logout button click
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();//logout message

                    preferences.loggedOut();

                    //login page is displayed
                    Intent mn = new Intent(ContactListActivity.this, LogInActivity.class);
                    ContactListActivity.this.startActivity(mn);
                    finish();
                }
            });


        //add button click
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ai=new Intent(ContactListActivity.this,AddContactActivity.class);
                ai.putExtra("User_No",userNo);
                ContactListActivity.this.startActivity(ai);
                finish();
            }
        });

    }
}