package me.regstudio.app;

/**
 * Created by Diego on 24/03/2018.
 */

public class Data {
    private String usernameIn, passwordIn, emailIn;

    //Constructors
    public Data(String username, String password, String email) {
        this.usernameIn = username;
        this.passwordIn = password;
        this.emailIn = email;
    }
    public Data(String username, String password){
        this.usernameIn = username;
        this.passwordIn = password;
    }
    public Data(){
        usernameIn = "";
        passwordIn = "";
    }

    

    //create user and store the username, password and email in the database
    //returns true if it was successful
    protected boolean userCreate()
    {
        return false;
    }

    //checks to see if user is in database, returns true if user exists
    protected boolean checkUser(String username, String password)
    {
        return false;
    }

}
