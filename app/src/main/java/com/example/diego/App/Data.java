package com.example.diego.App;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Diego on 24/03/2018.
 */

public class Data extends Activity{

    //Constructor
    public Data(){

    }

    //create user and store the username, password and email in the database
    //returns true if it was successful
    protected boolean userCreate(String username, String password, String email)
    {
        if (email.length() < 1){
            // ~don't add the email to the database, because it doesn't exist
        } else{
            // ~add email to the database, it does exist
        }

        return true;
    }

    //checks to see if user is in database, returns true if user exists
    public boolean checkUser(String username, String password)
    {

        return true;
    }

}
