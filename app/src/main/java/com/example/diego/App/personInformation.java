package com.example.diego.App;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.random.R;

/**
 * Created by diego on 3/26/2018.
 */

//class used for retrieving more information about the user.

public class personInformation extends Activity implements View.OnClickListener{

    MainActivity mainObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);

        Button back = (Button) findViewById(R.id.backButton);
        Button change = (Button) findViewById(R.id.ChangeB);

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mainObj.loggedIn) {
                    finished(0);
                } else {
                    log();
                }

            }
        });

        back.setOnClickListener(this);

        TextView name = (TextView) findViewById(R.id.nameText);
        TextView height = (TextView) findViewById(R.id.HeightText);
        TextView weight = (TextView) findViewById(R.id.WeightText);
        TextView age = (TextView) findViewById(R.id.ageText);
        TextView email = (TextView) findViewById(R.id.EmailText);

        name.setText("");
        age.setText("");
        weight.setText("");
        height.setText("");
        email.setText("");

        if (mainObj.loggedIn) {
            if (mainObj.name != null) {
                name.setText(mainObj.name);
            }
            if (mainObj.email != null) {
                email.setText(mainObj.email);
            }
            if (mainObj.age != 0) {
                age.setText("" + mainObj.age);
            }
            if (mainObj.weight != 0) {
                weight.setText("" + mainObj.weight + " kg");
            }
            if (mainObj.height != 0) {
                height.setText("" + mainObj.height + " cm");
            }
        }
    }

    //checks to see if anything was pressed
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case (R.id.backButton):
                finished();
                break;
        }
    }

    private void log() {
        Toast.makeText(this,  "Please log in first!", Toast.LENGTH_SHORT).show();
    }


    //finished activity in new page class
    private void finished()
    {
        int result = 3; //number is unique to all pages

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    private void finished(int y)
    {
        int result = 3; //number is unique to all pages

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }
}
