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

    EditText user;
    EditText pass;

    private String username, password;

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

                //setup
                finished(1);
                break;

            case R.id.button:
                //login
                pass = (EditText)findViewById(R.id.PasswordL);
                user = (EditText)findViewById(R.id.UsernameL);

                checkSet(user.getText().toString(), pass.getText().toString());

                finished(2);
                break;
        }
    }

    //login method (accessing the database)
    protected void log() {


        Toast.makeText(this, username + ", you have logged on!",
                Toast.LENGTH_SHORT).show();
    }

    protected void checkSet(String username, String password) {

        this.username = username;
        this.password = password;

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