package com.example.diego.App;


import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.diego.random.R;

import static java.lang.Math.abs;


//Zich class

public class FunctionActivity extends AppCompatActivity implements SensorEventListener {

    private boolean isStart = false; //detect whether the function is started ot not.
    private TextView timeText; //Timing.
    private TextView stepText; //Count Step.
    private TextView speedText; //Real-time Speed.
    private  TextView caloriesText;
    Button buttonStart; // Start the functions.
    Button buttonClear; // Stop the functions.
    private long startTime = 0; //Record the very beginning start time.
    private Handler mHandler = new Handler(); //Manage runnable.
    private SetTimeRunnable setTimeRunnable = new SetTimeRunnable(); //Update time.
    private SetStepRunnable setStepRunnable = new SetStepRunnable(); //Update step.
    private SetSpeedRunnable setSpeedRunnable = new SetSpeedRunnable(); //Update speed.
    private int stepNum = 0; //Real-time data, step count.
    private int stepNumHistory = 0; //Record the number of steps when clicking start
    private int speedNumber = 0; //It will change every delayMillis(3.142 seconds)
    private double thisMeter = 0; //The meter during the period of delayMillis(3.142 seconds)
    private SensorManager sensorManager; //SensorManager lets you access the device's sensors. Get an instance of this class by...(in the method of getStepDetector()).

    private MainActivity mainObj;

    class SetTimeRunnable implements Runnable {
        long time;
        @Override
        public void run() {
            if (isStart) {
                time = System.currentTimeMillis() - startTime;
                mainObj.timeFinished = (int) time;
                mainObj.stepCount = stepNum - stepNumHistory;
                mainObj.stepHist = stepNumHistory;
                setTime(time);
                mHandler.postDelayed(setTimeRunnable, 21);
            }
        }
    }

    class SetStepRunnable implements Runnable {
        @Override
        public void run() {
            if (isStart) {
                mHandler.postDelayed(setStepRunnable, 50);
                int a = stepNum - stepNumHistory;
                if (a < 0) {
                    a = 0;
                }
                stepText.setText(a + "");
                double calories1 = a*Double.parseDouble("60")*0.000357781754; //Double.parseDouble("60") is the weight.
                // if the data you get is String, u can just use the value of weight getten from user. if the value is exactly double, u can just use a*(double)weight*0.000357781754
                // a is the step count.
                int calories2 = (int)(calories1*100); //I want to use format to show the calories data, but just now I don't know why I can't use it, so I use this fool method.
                double calories = (double)(calories2)/(double)(100);
                caloriesText.setText(calories+"");
            }
        }
    }

    class SetSpeedRunnable implements Runnable {
        // First of all, I wanted to use  mobile phone sensor to get the distance of movement of every second, but the mobile phone hardware(sensor) does not provide this function.
        // Secondly, I wanted to use the latitude and longitude coordinate difference to get the actual distance, but for walking or running, the error will be very large sometimes, because the acquired latitude and longitude coordinates are ok for a car when it is moving, but are not very accurate for a person.
        // Thirdly, I wanted to calculate the speed through the acceleration sensor, but I can not get the initial speed of v0, because v=v0+at.
        // So I took this very very very simple method:
        @Override
        public void run() {
            if (isStart) {
                mHandler.postDelayed(setSpeedRunnable, 3142);//3.1415s
                int thisNum = stepNum - speedNumber;
                speedNumber = stepNum;
                if (thisNum < 0|| thisNum >= 100) {
                    thisMeter = 0;
                }
                if (thisNum == 0){
                    thisMeter = 0;
                }
                if (thisNum < 7 && thisNum > 0){
                    thisMeter = thisNum*(0.65 + 0.15*Math.random());
                }
                if (thisNum > 7 && thisNum < 100){
                    thisMeter = thisNum*(0.95 + 0.2*Math.random());
                }
                double t = 3.142;
                int a = (int)((thisMeter/t)*10);
                double b = (double)a/(double)10;
                speedText.setText(b+"");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        findId();
        setInitView();
        //Initialize current state based on historical state
        isStart = SPUtil.getPrefBoolean(this, "isStart", false);
        startTime = SPUtil.getPrefLong(this, "startTime", 0);
        //Step count
        stepNumHistory = SPUtil.getPrefInt(this, "stepNumHistory", 0);

        if (isStart == true) {
            buttonState("1");
            mHandler.postDelayed(setTimeRunnable, 21);
            mHandler.postDelayed(setStepRunnable, 50);
            mHandler.postDelayed(setSpeedRunnable, 3142);
            speedNumber = stepNum;

        } else {
            buttonState("2");
        }
        getStepDetector(); //Turn on listening, listening the number of steps, each time I need to use this method when I come back to this interface, otherwise the data can not be obtained
    }

    public void functionStart(View view) {
        buttonState("1");
        isStart = true;
        startTime = System.currentTimeMillis();
        SPUtil.setPrefInt(FunctionActivity.this, "stepNumHistory", stepNum); //Context represents the current activity environment, all resources used
        stepNumHistory = stepNum;
        mHandler.postDelayed(setTimeRunnable,21);
        mHandler.postDelayed(setStepRunnable,50);
        mHandler.postDelayed(setSpeedRunnable, 3142);
    }

    public void functionClear(View view) {
        mHandler.removeCallbacksAndMessages(null); //Remove the Handler listener. If it is not removed, the background is always running and resources are wasted. Also there may be some bugs, and the data may be misplaced. It is mainly a waste of performance.
        buttonState("2");
        isStart = false;
        startTime = 0;
        setInitView();

        //Clear the data, but actually I just set the variable to the initial default value.
        SPUtil.setPrefBoolean(FunctionActivity.this, "isStart", isStart);
        SPUtil.setPrefLong(FunctionActivity.this, "startTime", startTime);
        SPUtil.setPrefInt(FunctionActivity.this, "stepNumHistory", 0);

        finished();
    }

    public void functionNext(View view) {
        nextScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        //Store the data.
        SPUtil.setPrefBoolean(FunctionActivity.this, "isStart", isStart);
        SPUtil.setPrefLong(FunctionActivity.this, "startTime", startTime);
        //Store the step
        if (isStart) {
            SPUtil.setPrefInt(FunctionActivity.this, "stepNumHistory", stepNumHistory);
        }


    }

    private void findId() {
        buttonStart = (Button) findViewById(R.id.buttonStart); //find those id.
        buttonClear = (Button) findViewById(R.id.buttonClear);
        timeText = (TextView) findViewById(R.id.time);
        stepText = (TextView) findViewById(R.id.step);
        speedText = (TextView) findViewById(R.id.speed);
        caloriesText = findViewById(R.id.calories);
    }

    private void setInitView() {
        timeText.setText("00:00:00");
        stepText.setText("0");
        speedText.setText("0.0");
        caloriesText.setText("0");
    }

    private void setTime(long time) {
        int ms = (int) (time % 1000 / 10);
        int sec = (int) (time / 1000 % 60);
        int min = (int) (time / 1000 / 60);
        timeText.setText(min + ":" + sec + ":" + ms);
    }

    private void buttonState(String type) { //Set whether the button can be clicked or not
        if (type.equals("1")) {
            buttonStart.setEnabled(false);
            buttonClear.setEnabled(true);
        } else {
            buttonStart.setEnabled(true);
            buttonClear.setEnabled(false);
        }
    }

    private void getStepDetector() {
        if (sensorManager != null) {  //The first time it is definitely null. Turn on the sensor's listening , but my code of this part was unregistered, so it is possible to create some bugs from the second time, so I pointed it to null here.
            sensorManager = null;
        }
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE); // Get Instances of Sensor Manager
        //Sensor about pedometer can be used after android4.4
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) { //Determine whether the current system version supports sensors or not
            addCountStepListener(); //Add listening
        }
    }

    private void addCountStepListener() {
        android.hardware.Sensor countSensor = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_STEP_COUNTER); //Get sensor type and adding the number of steps.
        if (countSensor != null) {
            sensorManager.registerListener(FunctionActivity.this, countSensor, SensorManager.SENSOR_DELAY_GAME); //Register listening and listen to current sensor changes
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //The total number of steps increases gradually.
        int tempStep = (int) event.values[0];
        stepNum = tempStep;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onBackPressed() {
        finished();
    }

    private void nextScreen()
    {
        int result = 5;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }

    private void finished()
    {
        //setFinishedTime();
        //setSteps();
        int result = 5;
        mainObj.numOfRuns++;

        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",result);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}

