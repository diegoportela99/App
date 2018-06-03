package com.example.diego.App;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Debug;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.random.R;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Vector;

import static android.provider.Telephony.Mms.Part.FILENAME;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Graph g;

    //private Toolbar mToolbar;

    protected static boolean hideLogin, hideRun, loggedIn;

    //these values must be added to the database rather than saved onto this class.
    public static final String TEXTFILE = "mydir_app.txt";
    protected static String username, password, email, name;
    protected static int stepHist, age, numOfRuns, stepCount, timeFinished, metersTravelled, weight, height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Fitness App");

        readFileOnInternalStorage(); //gets database information

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button r = (Button) findViewById(R.id.runButton);
        Button l = (Button) findViewById(R.id.BLogin);
        Button n = (Button) findViewById(R.id.addGraph);

        NavigationView nv = (NavigationView) findViewById(R.id.nv1);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                switch (menuItem.getItemId()) {
                    case(R.id.nav_account):
                        personInfo();

                        break;
                    case (R.id.nav_logout):
                        logout();
                        hideRun("yes");
                        hideLogin("no");

                        loggedIn = false;
                        setScoreToZero();
                        break;

                    case (R.id.nav_settings):
                        setStorage();
                        break;

                    case (R.id.nav_status):
                        graphing();
                        break;

                    case (R.id.nav_map):
                        map();
                        break;

                }
                return true;
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functionActivity();
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGraph();
            }

        });

        if (username != null && loggedIn == true) {
            hideRun("no");
            hideLogin("yes");
            updateScores();
        } else if (loggedIn == false){
            hideRun("yes");
            setScoreToZero();
        } else {
            hideRun("yes");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                signup();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //go back to main page (clean up is done in the page's class)
                if (hideLogin == true)
                {
                    hideLogin("yes");
                    hideRun("no");
                }
                makeSound();
            }
        }

        if (requestCode == 2)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Login();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        //personInformation.java result (if button was pressed)
        if (requestCode == 3)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                //finished() called

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Changes();
            }
        }

        if (requestCode == 4)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                //finished() called (stop button)
                updateScores();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                functionActivity();
                //Write your code if there's no result
            }
        }

        if (requestCode == 5)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(this,  "Good job!", Toast.LENGTH_SHORT).show();

                updateScores();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                run();

            }
        }

        if (requestCode == 6)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == 7)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == 8)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }//onActivityResult

    //hide the login button after logged on.
    protected void hideLogin(String x)
    {
        View b = findViewById(R.id.BLogin);

        switch (x)
        {
            case "yes":
                b.setVisibility(View.GONE);
                hideLogin = true;
                break;

            case "no":
                b.setVisibility(View.VISIBLE);
                hideLogin = false;
                break;
        }

    }

    private void logout() {
        Toast.makeText(this,  "Logged out!", Toast.LENGTH_SHORT).show();
    }

    protected void hideRun(String x)
    {
        View b = findViewById(R.id.runButton);

        switch (x)
        {
            case "yes":
                b.setVisibility(View.GONE);
                hideRun = true;
                break;

            case "no":
                b.setVisibility(View.VISIBLE);
                hideRun = false;
                break;
        }

    }

    protected void makeSound()
    {
        if (loggedIn) {
            updateScores();
        } else {
            setScoreToZero();
        }

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
    }

    protected void personInfo()
    {
        makeSound();
        Intent personInfo = new Intent(MainActivity.this, personInformation.class);
        startActivityForResult(personInfo, 3);
    }

    protected void run()
    {
        makeSound();
        Intent r = new Intent(MainActivity.this, run.class);
        startActivityForResult(r, 4);
    }

    protected void functionActivity()
    {
        makeSound();
        Intent f = new Intent(MainActivity.this, FunctionActivity.class);
        startActivityForResult(f, 5);
    }

    protected void Login()
    {
        makeSound();
        Intent i = new Intent(MainActivity.this, login.class);
        startActivityForResult(i, 1);
    }

    protected void map()
    {
        makeSound();
        Intent Map = new Intent(MainActivity.this, Map.class);
        startActivityForResult(Map, 6);
    }

    protected void signup()
    {
        makeSound();
        Intent signup = new Intent(MainActivity.this, Signup.class);
        startActivityForResult(signup, 2);

    }

    protected void graphing()
    {
        makeSound();
        g.xInt = 0.001;
        g.yInt = 0.001;
        Intent signup = new Intent(MainActivity.this, Graph.class);
        startActivityForResult(signup, 7);
    }

    protected void Changes() {
        makeSound();
        Intent signup = new Intent(MainActivity.this, changes.class);
        startActivityForResult(signup, 8);
    }

    protected void updateScores() {
        TextView score = (TextView) findViewById(R.id.TotalScore);
        TextView lastStep = (TextView) findViewById(R.id.StepCount);
        TextView lastRun = (TextView) findViewById(R.id.RunTime);

        lastStep.setText("" + stepCount);
        lastRun.setText(""+ timeFinished/1000);
        score.setText("" + stepHist);

        setStorage();
    }

    protected void addGraph() {
        makeSound();

        g.xInt = numOfRuns;
        g.yInt = timeFinished/1000;
        Intent signup = new Intent(MainActivity.this, Graph.class);
        startActivityForResult(signup, 7);
    }

    protected void setScoreToZero() {
        TextView score = (TextView) findViewById(R.id.TotalScore);
        TextView lastStep = (TextView) findViewById(R.id.StepCount);
        TextView lastRun = (TextView) findViewById(R.id.RunTime);

        lastStep.setText("0");
        lastRun.setText("0");
        score.setText("0");

        setStorage();
    }

    protected void setStorage() {
        writeFileOnInternalStorage(timeFinished + "\n" + stepCount + "\n" + metersTravelled
                + "\n" + username + "\n" + password + "\n" + email + "\n" + stepHist + "\n"
                + numOfRuns + "\n" + loggedIn + "\n" + name + "\n" + height + "\n" + weight + "\n" + age);

        readFileOnInternalStorage();
    }

    public void writeFileOnInternalStorage(String sBody) {
        try {
            FileOutputStream fos =  openFileOutput(TEXTFILE, Context.MODE_PRIVATE);
            fos.write(sBody.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFileOnInternalStorage() {
        try {
            FileInputStream fis = openFileInput(TEXTFILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));

            String line;
            Vector<String> info = new Vector();

            while ((line = reader.readLine()) != null) {
                info.addElement(line);
            }

            info.toArray();

            timeFinished = Integer.parseInt(info.elementAt(0));
            stepCount = Integer.parseInt(info.elementAt(1));
            metersTravelled = Integer.parseInt(info.elementAt(2));
            username = (info.elementAt(3));
            password = (info.elementAt(4));
            email = (info.elementAt(5));
            stepHist = Integer.parseInt(info.elementAt(6));
            numOfRuns = Integer.parseInt(info.elementAt(7));
            loggedIn = Boolean.parseBoolean(info.elementAt(8));
            name = (info.elementAt(9));
            height = Integer.parseInt(info.elementAt(10));
            weight = Integer.parseInt(info.elementAt(11));
            age = Integer.parseInt(info.elementAt(12));

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
