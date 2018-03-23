package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by diego on 3/21/2018.
 */

public class login extends Activity implements View.OnClickListener{

    private String username, password;
    EditText user;
    EditText pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button pressed = (Button) findViewById(R.id.button2);
        Button pressed1 = (Button) findViewById(R.id.button);

        pressed.setOnClickListener(this);
        pressed1.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button2:

                //setup button pressed
                finished(1);
                break;

            case R.id.button:
                //login button pressed
                user = (EditText)findViewById(R.id.UsernameL);
                pass = (EditText)findViewById(R.id.PasswordL);

                this.username = user.getText().toString();
                this.password = pass.getText().toString();

                //check the username and password combination
                if (checkSet())
                {
                    finished(2);
                } else{
                    Toast.makeText(this,  "Login in failed! Couldn't reach database",
                            Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //login method (accessing the database)
    protected void log() {

        Toast.makeText(this,  "Welcome back, " + username + "!",
                Toast.LENGTH_SHORT).show();
    }

    protected boolean checkSet() {
        //check if this is a valid user/pass combination
        Data data = new Data();
        return data.checkUser(username, password);
    }


    public void finished(int x)
    {
        if (x == 1) {
            Intent returnIntent = getIntent();
            returnIntent.putExtra("result",x);
            setResult(RESULT_OK,returnIntent);
            finish();
        }
        else
        {
            log();
            Intent returnIntent = getIntent();
            returnIntent.putExtra("result",x);
            setResult(RESULT_CANCELED,returnIntent);
            finish();
        }
    }

}