package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by diego on 3/21/2018.
 */

public class login extends Activity implements View.OnClickListener{
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

                //set up login here, remember to call finnished after completed.
                finished(1);
                break;

            case R.id.button:
                finished(2);
                break;
        }
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
            // move this logged on text to an appropriate place as it will cause a bug later on
            Toast.makeText(this, "You have logged on!", Toast.LENGTH_SHORT).show();
            Intent returnIntent = getIntent();
            returnIntent.putExtra("result",x);
            setResult(RESULT_CANCELED,returnIntent);
            finish();
        }
    }

}