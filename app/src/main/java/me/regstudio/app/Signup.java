package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Diego on 22/03/2018.
 */

public class Signup extends Activity implements View.OnClickListener {

    private String password, username, email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        password = "";
        username = "";
        email = "";

        Button pressed = (Button) findViewById(R.id.signupBut);
        pressed.setOnClickListener(this);
    }

    //start of button click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if sign up button was pressed
            case R.id.signupBut:

                //init all input fields
                EditText passField = (EditText) findViewById(R.id.PasswordS);
                EditText passField2 = (EditText) findViewById(R.id.PasswordS2);
                EditText username = (EditText) findViewById(R.id.Username);
                EditText email = (EditText) findViewById(R.id.Email);

                //check to see if passwords match, is greater than 7 characters and no space
                if (passField.getText().toString().equals(passField2.getText().toString()) &&
                        passField.getText().toString().length() >= 7 &&
                        !passField.getText().toString().contains(" ")) {


                    this.password = passField.getText().toString(); //set password
                    this.email = email.getText().toString(); //set email

                    //check if username valid
                    if (checkUser(username.getText().toString(), this.password, this.email)) {
                        finished();
                    } else {
                        break;
                    }

                } else {

                    //password was invalid
                    Toast.makeText(this, "Sign up failed! Please make sure your password has at least 7 characters and match.",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }


    //check user is compatible with database, returns true if it is and program ends
    private boolean checkUser(String username, String password, String email) {
        if (username.contains(" ")) {
            Toast.makeText(this, "Username cannot have a space!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 3) {
            Toast.makeText(this, "Username cannot have less than 3 characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (specialCase(username)) {
            Toast.makeText(this, "Username cannot have any special characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        //if all tests passed then set username
        this.username = username;

        //if created in the database
        if (setCheck()) {
            Toast.makeText(this, "Sign up worked!", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            Toast.makeText(this, "Sign up failed! Couldn't reach database", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    //returns true if created successfully in database
    protected boolean setCheck() {
            Data data = new Data();
            return data.userCreate(username, password, email);
    }


    //check if username contains any special symbols
    protected boolean specialCase(String username)
    {
        //double checking username contains information.
        if (username == null || username.trim().isEmpty()) {
            return true;
        }

        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(username);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b == true) {
            //special case found
            return true;
        }
        else{
            //special case not found
            return false;
        }

    }

    //finished activity
    private void finished()
    {
        int result = 2;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
