package com.example.diego.App;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Diego on 24/03/2018.
 */

public class Data extends Activity{

    MainActivity obj;
    //Constructor
    public Data(){

    }

    //create user and store the username, password and email in the database
    //returns true if it was successful
    protected boolean userCreate(String username, String password, String email)
    {
        if (email.length() < 1){
            obj.username = username;
            obj.password = password;

        } else{
            obj.username = username;
            obj.password = password;
            obj.email = email;
        }

        //obj.setStorage();
        return true;
    }

    //checks to see if user is in database, returns true if user exists
    public boolean checkUser(String username, String password)
    {
        try {
            if (obj.username.equals(username) && obj.password.equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
