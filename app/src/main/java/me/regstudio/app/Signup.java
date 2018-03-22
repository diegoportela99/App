package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Diego on 22/03/2018.
 */

public class Signup extends Activity implements View.OnClickListener{
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

                //check to see if signup worked etc
                Toast.makeText(this, "Sign up worked!", Toast.LENGTH_SHORT).show();
                finished();
                break;
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
