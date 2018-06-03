package com.example.diego.App;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.diego.random.R;

/**
 * Created by Diego on 27/05/2018.
 */

public class changes extends Activity implements View.OnClickListener {

    EditText name, height, weight, age, email;
    private MainActivity mainObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changes);

        name = (EditText) findViewById(R.id.Name_change);
        height = (EditText) findViewById(R.id.Height_change);
        weight = (EditText) findViewById(R.id.Weight_change);
        age = (EditText) findViewById(R.id.Age_change);
        email = (EditText) findViewById(R.id.Email_change);

        Button b = (Button) findViewById(R.id.finish_changes);

        b.setOnClickListener(this);

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
            weight.setText("" + mainObj.weight);
        }
        if (mainObj.height != 0) {
            height.setText("" + mainObj.height);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_changes:

                //set the values..
                mainObj.name = this.name.getText().toString();
                mainObj.email = this.email.getText().toString();
                mainObj.height = Integer.parseInt(this.height.getText().toString());
                mainObj.age = Integer.parseInt(this.age.getText().toString());
                mainObj.weight = Integer.parseInt(this.weight.getText().toString());

                finished();
                break;
        }
    }

    protected void finished() {
        int x = 8;
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",x);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
