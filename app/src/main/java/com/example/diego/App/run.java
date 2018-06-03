package com.example.diego.App;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.diego.random.R;

import static java.lang.Thread.sleep;

/**
 * Created by Diego on 31/03/2018.
 */

public class run extends Activity implements View.OnClickListener{

    private Chronometer timer;
    private int elapsedMillis;
    private MainActivity mainObj;
    private FunctionActivity funcAct;
    private Handler mHandler = new Handler();
    private setDistance setDistance_ = new setDistance();
    private TextView distance;

    class setDistance implements Runnable {
        @Override
        public void run() {
            mainObj.metersTravelled = (int) (0.70104 * mainObj.stepCount);

            if (mainObj.metersTravelled < 1000) {
                if (mainObj.metersTravelled <100) {
                    distance.setText(mainObj.metersTravelled/1000 + ".0" + mainObj.metersTravelled + " km");
                } else {
                    distance.setText(mainObj.metersTravelled / 1000 + "." + mainObj.metersTravelled + " km");
                }
            } else {
                distance.setText(mainObj.metersTravelled / 1000 + "." + mainObj.metersTravelled % 1000 + " km");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run);

        Button stop = (Button) findViewById(R.id.stopRun);
        distance = (TextView) findViewById(R.id.distanceText);

        timer = (Chronometer) findViewById(R.id.chronometer2);
        timer.setBase(SystemClock.elapsedRealtime() - mainObj.timeFinished);

        mHandler.postDelayed(setDistance_, 21);

        if (mainObj.timeFinished > 0) {
            timer.start();
        }

        stop.setOnClickListener(this);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stopRun:
                nextScreen();
                break;
        }
    }

    private void setFinishedTime()
    {
        elapsedMillis = (int) (SystemClock.elapsedRealtime() - timer.getBase()); //Divide by 1000 if you want time in seconds
        mainObj.timeFinished = elapsedMillis;
    }

    //stops from going back
    @Override
    public void onBackPressed() { nextScreen(); }


    private void nextScreen()
    {
        setFinishedTime();
        int result = 4;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }
}