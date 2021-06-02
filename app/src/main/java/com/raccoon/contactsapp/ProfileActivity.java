package com.raccoon.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ProfileActivity extends AppCompatActivity {

    private EditText profileName,profileNumber;
    private Button callContact,saveChanges,deleteButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent li=new Intent(ProfileActivity.this,ContactListActivity.class);
        li.putExtra("User_No",getIntent().getStringExtra("User_No"));
        ProfileActivity.this.startActivity(li);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Datahandler instance = Datahandler.getInstance(getApplicationContext());

        profileName=findViewById(R.id.contact_name_edit);
        profileNumber=findViewById(R.id.contact_no_edit);

        Intent ig=getIntent();
        String userNo=ig.getStringExtra("User_No");

        profileName.setText(ig.getStringExtra("ContactName"));
        profileNumber.setText(ig.getStringExtra("ContactNumber"));

        callContact=findViewById(R.id.call_button);
        saveChanges=findViewById(R.id.save_button);
        deleteButton=findViewById(R.id.delete_button);



        callContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+profileNumber.getText()));

                if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(ProfileActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }
                else
                {
                    startActivity(callIntent);
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact updatedContact=new Contact(profileName.getText().toString(),profileNumber.getText().toString(),userNo);
                instance.updateContact(updatedContact,ig.getStringExtra("User_No"),profileNumber.getText().toString());
                Toast.makeText(ProfileActivity.this, "Contact Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.deleteContact(profileNumber.getText().toString());
                Toast.makeText(getApplicationContext(),"Contact Deleted",0).show();

                Intent li=new Intent(ProfileActivity.this,ContactListActivity.class);
                li.putExtra("User_No",userNo);
                ProfileActivity.this.startActivity(li);
                finish();
            }
        });
    }

}
