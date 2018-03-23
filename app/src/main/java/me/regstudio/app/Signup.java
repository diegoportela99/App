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
            case R.id.signupBut:
                EditText passField = (EditText)findViewById(R.id.PasswordS);
                EditText passField2 = (EditText)findViewById(R.id.PasswordS2);

                //check to see if passwords match and is greater than 7 characters
                if (passField.getText().toString().equals(passField2.getText().toString()) &&
                        passField.getText().toString().length() >= 7) {

                    Toast.makeText(this, "Sign up worked!", Toast.LENGTH_SHORT).show();
                    password = passField.getText().toString(); //set password
                    finished();
                    break;
                }else {

                    Toast.makeText(this, "Sign up failed! Please make sure you have at least 7 characters.",
                            Toast.LENGTH_SHORT).show();
                    break;
                }


        }
    }

    private void finished()
    {
        int result = 02;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
