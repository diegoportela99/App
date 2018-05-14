package me.regstudio.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diego on 24/03/2018.
 * Code of datahelper created by Krijan
 */

public class Data extends Activity{

    static boolean emailExist = false;
    String email, username, password;

    //Constructor
    public Data(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Data(String username, String password){
        this.username = username;
        this.password = password;
    }

    //create user and store the username, password and email in the database
    //returns true if it was successful
    protected boolean userCreate(String username, String password, String email)
    {
        this.username = username;
        this.password = password;

        if (email.length() < 1){
            // ~don't add the email to the database, because it doesn't exist
            emailExist = false;
        } else{
            // ~add email to the database, it does exist
            emailExist = true;
        }

        try{
            DatabaseHelper d = new DatabaseHelper(this);
            d.insertContact(this);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }



    }

    //checks to see if user is in database, returns true if user exists
    protected boolean checkUser(String username, String password)
    {

        return true;
    }


//    public void setUsername(String username)
//    {
//        this.username = username;
//    }
//
    public String getUsername()
    {
        return this.username;
    }
//
//    public void setEmail(String email)
//    {
//        this.email = email;
//    }

    public String getEmail()
    {
        return this.email;
    }

//    public void setPassword(String password)
//    {
//        this.password = password;
//    }

    public String getPassword()
    {
        return this.password;
    }


    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VESION = 1;
        private static final String DATABASE_NAME = "contacts.db";
        private static final String TABLE_NAME = "contacts";
        private static final String COLUMN_ID = "id";
        private static final String COLUMN_USERNAME = "USERNAME";
        private static final String COLUMN_EMAIL = "email";
        private static final String COLUMN_PASSWORD = "password";
        SQLiteDatabase db;
        private static final String TABLE_CREATE = "create table contacts (id integer primary key not null, " +
                "" + "username text not null, email text not null, password text not null);";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VESION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            this.db = db;

        }

        public void insertContact(Data c) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            String query = "Select * from contacts";
            Cursor cursor = db.rawQuery(query, null);
            int count = cursor.getCount();

            values.put(COLUMN_ID, count);
            values.put(COLUMN_USERNAME, c.getUsername());
            values.put(COLUMN_EMAIL, c.getEmail());
            values.put(COLUMN_PASSWORD, c.getPassword());

            db.insert(TABLE_NAME, null, values);
            db.close();
        }

        public String searchPass(String uname) {
            db = this.getReadableDatabase();
            String query = "select uname, pass from " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            String a, b;
            b = "not found";
            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getString(0);


                    if (a.equals(uname)) {
                        b = cursor.getString(1);
                        break;
                    }
                }
                while (cursor.moveToNext());
            }

            return b;

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
            db.execSQL(query);
            this.onCreate(db);

        }
    }
}