package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by diego on 3/26/2018.
 */

public class personInformation extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //example of button pressed
        // Button pushed = (Button) findViewById(R.id.Button); //change R.id. (the button you pressed id)
        // pushed.setOnClickListener(this);
    }

    //checks to see if anything was pressed
    public void onClick(View v)
    {
        switch(v.getId())
        {
            //case R.id.Button:
                // if you want to open a new slide
                // then run finished() and go to MainActivity, the method onActivityResult.
            //    finished();
            //    break;
        }
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

}
