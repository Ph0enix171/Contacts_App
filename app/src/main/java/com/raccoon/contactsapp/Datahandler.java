package com.raccoon.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Datahandler extends SQLiteOpenHelper {

    //singleton object
    private static Datahandler sInstance;
    public static synchronized Datahandler getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new Datahandler(context.getApplicationContext());
        }
        return sInstance;
    }

    //constructor
    public Datahandler(Context context)
    {
        super(context, Parameters.DB_NAME,null,Parameters.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create user table
        String tableUSer="CREATE TABLE "+Parameters.USER_TABLE+"("+Parameters.KEY_USERNAME+" TEXT,"+Parameters.KEY_PASSWORD+
                " TEXT,"+Parameters.KEY_PHONE +" TEXT PRIMARY KEY);";
        Log.d("damtabase",tableUSer);
        sqLiteDatabase.execSQL(tableUSer);
        //create contacts table
        String tableContacts="CREATE TABLE "+Parameters.CONTACTS_TABLE+"("+Parameters.KEY_CONTACT_NAME +" TEXT,"+Parameters.KEY_CONTACT_NO+
                " TEXT PRIMARY KEY,"+Parameters.KEY_USER_NO+" TEXT,"
                +"FOREIGN KEY("+Parameters.KEY_USER_NO+") REFERENCES "+Parameters.USER_TABLE+"("+Parameters.KEY_PHONE +")"+");";
        Log.d("damtabase",tableContacts);
        sqLiteDatabase.execSQL(tableContacts);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //user create
    public Boolean createUser(User user,Context ct)
    {

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String searchQuery="SELECT EXISTS(SELECT 1 FROM "+Parameters.USER_TABLE+" WHERE "+Parameters.KEY_PHONE+"=?)";

        Cursor exists =sqLiteDatabase.rawQuery(searchQuery,new String[]{user.getPhone_No()});
        int exist=0;
        if( exists != null && exists.getColumnCount()>0){
            exists.moveToFirst();
            exist=exists.getInt(exists.getColumnCount()-1);
        }

        if(exist==1)
        {
            Toast.makeText(ct,"This number is already registered",1).show();
            sqLiteDatabase.close();
            return false;
        }
        else {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues userValues = new ContentValues();

            userValues.put(Parameters.KEY_USERNAME, user.getUsername());
            userValues.put(Parameters.KEY_PASSWORD, user.getPassword());
            userValues.put(Parameters.KEY_PHONE, user.getPhone_No());

            sqLiteDatabase.insert(Parameters.USER_TABLE, null, userValues);
            sqLiteDatabase.close();
            return true;
        }
    }

    //user login
    public Boolean logIn(String contactNo,String passWord, Context ct)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String searchQuery="SELECT EXISTS(SELECT 1 FROM "+Parameters.USER_TABLE+" WHERE "+Parameters.KEY_PHONE+"=?)";

        Cursor exists =sqLiteDatabase.rawQuery(searchQuery,new String[]{contactNo});
        int exist=0;

        if( exists != null && exists.getColumnCount()>0){
            exists.moveToFirst();
            exist=exists.getInt(exists.getColumnCount()-1);
        }

        if(exist==1)
        {
            String pass="SELECT "+Parameters.KEY_PASSWORD+" FROM "+Parameters.USER_TABLE+" WHERE "+Parameters.KEY_PHONE+"=?";
            Cursor pw=sqLiteDatabase.rawQuery(pass,new String[]{contactNo});

            String pWord="";
            if( pw != null && pw.getColumnCount()>0){
                pw.moveToFirst();
                pWord=pw.getString(0);
            }
            if(passWord.equals(pw.getString(0)))
            {
                Intent listActivity=new Intent(ct,ContactListActivity.class);
                listActivity.putExtra("User_No",contactNo);
                ct.startActivity(listActivity);
                return true;
            }
            else
                Toast.makeText(ct, "Wrong Password"+pWord,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(ct,"User not found. Please register",Toast.LENGTH_LONG).show();
        }
        return false;

    }

    //new contact
    public void newContact(Contact contact)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contactValues=new ContentValues();

        contactValues.put(Parameters.KEY_CONTACT_NAME,contact.getName());
        contactValues.put(Parameters.KEY_CONTACT_NO,contact.getNumber());
        contactValues.put(Parameters.KEY_USER_NO,contact.getUser_No());

        sqLiteDatabase.insert(Parameters.CONTACTS_TABLE,null,contactValues);
        sqLiteDatabase.close();
    }

    //get contact list
    public List<Contact> getAllContacts(String userNo)
    {
        List<Contact> ContactList=new ArrayList<Contact>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String get="SELECT * FROM "+Parameters.CONTACTS_TABLE+" WHERE "+Parameters.KEY_USER_NO+"=?";
        Cursor cursor=sqLiteDatabase.rawQuery(get,new String[]{userNo});

        if(cursor.moveToNext())
        {
            do{
                Contact contact=new Contact();
                contact.setName(cursor.getString(0));
                contact.setNumber(cursor.getString(1));
                contact.setUser_No(cursor.getString(2));
                ContactList.add(contact);
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return ContactList;
    }

    //update contact
    public void updateContact(Contact contact,String uid,String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues val=new ContentValues();

        val.put(Parameters.KEY_CONTACT_NAME,contact.getName());
        val.put(Parameters.KEY_CONTACT_NO,contact.getNumber());
        sqLiteDatabase.update(Parameters.CONTACTS_TABLE,val,Parameters.KEY_USER_NO+"=? AND "+Parameters.KEY_CONTACT_NO+"=?",
                new String[]{uid,id});
        sqLiteDatabase.close();
    }

    //delete contact
    public void deleteContact(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(Parameters.CONTACTS_TABLE,Parameters.KEY_CONTACT_NO+"=?",new String[]{id});
        sqLiteDatabase.close();
    }

    //no. of contacts fix bug
    public int getCount()
    {
        String query="SELECT * FROM "+Parameters.CONTACTS_TABLE+" WHERE "+Parameters.KEY_USER_NO+"=?";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cs=sqLiteDatabase.rawQuery(query,new String[]{ContactListActivity.userNo});
        int exist=0;
        if( cs != null && cs.getCount()>0){
            cs.moveToFirst();
            exist=cs.getCount();
        }
        return exist;
    }

}
