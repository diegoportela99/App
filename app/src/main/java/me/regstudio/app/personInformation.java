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

//class used for retrieving more information about the user.

public class personInformation extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //checks to see if anything was pressed
    public void onClick(View v)
    {
        switch(v.getId())
        {

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
