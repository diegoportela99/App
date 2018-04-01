package me.regstudio.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import static java.lang.Thread.sleep;

/**
 * Created by Diego on 31/03/2018.
 */

public class run extends Activity implements View.OnClickListener{

    private Chronometer timer;
    private int elapsedMillis;
    private MainActivity mainObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run);

        Button stop = (Button) findViewById(R.id.stopRun);
        stop.setOnClickListener(this);
        timer = (Chronometer) findViewById(R.id.chronometer2);

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stopRun:
                finished();
                break;
        }
    }

    private void setFinishedTime()
    {
        elapsedMillis = (int) (SystemClock.elapsedRealtime() - timer.getBase()); //Divide by 1000 if you want time in seconds
        mainObj.timeFinished = elapsedMillis;
    }



    //finished activity
    private void finished()
    {
        setFinishedTime();
        int result = 4;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
