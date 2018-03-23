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

public class Signup extends Activity implements View.OnClickListener{

    protected String password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Button pressed = (Button) findViewById(R.id.signupBut);
        pressed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            //if sign up button was pressed
            case R.id.signupBut:

                //init all input fields
                EditText passField = (EditText)findViewById(R.id.PasswordS);
                EditText passField2 = (EditText)findViewById(R.id.PasswordS2);
                EditText username = (EditText)findViewById(R.id.Username);
                EditText email = (EditText)findViewById(R.id.Email);

                //check to see if passwords match, is greater than 7 characters and no space
                if (passField.getText().toString().equals(passField2.getText().toString()) &&
                        passField.getText().toString().length() >= 7 &&
                        !passField.getText().toString().contains(" ")) {


                    password = passField.getText().toString(); //set password

                    //check if user information is valid in database
                    if (checkUser(username.getText().toString(), password, email.getText().toString()) == true)
                    {
                        finished();
                    }else
                    {
                        break;
                    }

                }else {

                        //password was invalid
                        Toast.makeText(this, "Sign up failed! Please make sure your password has at least 7 characters and do not match.",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
        }


    //check user is compatible with database, returns true if it is and program ends
    protected boolean checkUser(String username, String password, String email)
    {
        if (username.contains(" ")) {
            Toast.makeText(this, "Username cannot have a space!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 3)
        {
            Toast.makeText(this, "Username cannot have less than 3 characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (specialCase(username) == true)
        {
            Toast.makeText(this, "Username cannot have any special characters!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }


        Toast.makeText(this, "Sign up worked!", Toast.LENGTH_SHORT).show();
        return true;
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
        int result = 02;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
