package com.example.diego.App;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.diego.random.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class Graph extends AppCompatActivity{

    Button button;
    EditText xInput, yInput;
    BarChart barChart;
    MyHelper myHelper;
    ArrayList<BarEntry> entries;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> Runs;

    static double xInt, yInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphing);

        button = (Button) findViewById(R.id.button);
        yInput = (EditText) findViewById(R.id.inputTextY);
        xInput = (EditText) findViewById(R.id.inputTextX);
        barChart = (BarChart) findViewById(R.id.bargraph);


        myHelper = new MyHelper(this);
        sqLiteDatabase = myHelper.getWritableDatabase();

        exqButton();


        barChart.setTouchEnabled(true);
        barChart.setDrawGridBackground(true);// If enabled, the background rectangle behind the chart drawing-area will be drawn.
        barChart.setDrawBorders(true);// Enables / disables drawing the chart borders (lines surrounding the chart).
        barChart.setDragEnabled(true);// Enables/disables dragging (panning) for the chart.
        barChart.setScaleEnabled(true ); //Enables/disables scaling for the chart on both (boolean enabled): Enables/disables scaling on the y-axis.
        barChart.setPinchZoom(true);// If set to true, pinch-zooming is enabled. If disabled, x- and y-axis can be zoomed separately.
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.invalidate();
        //barChart.clear();
        // barChart.setDescription(Description desc); -- Sets the color of the chart border lines.

        if (xInt != 0 && yInt != 0) {
            setValues(xInt, yInt);
            xInt = 0;
            yInt = 0;
            if (yInt > 1) {
                Toast.makeText(this,"Data Inserted", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void exqButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double xVal = Double.parseDouble(String.valueOf(xInput.getText()));
                double yVal = Double.parseDouble(String.valueOf(yInput.getText()));
                setValues(xVal, yVal);
            }
        });
    }

    protected void setValues(double x, double y) {
        myHelper.insertData(x, y);

        entries = new ArrayList<BarEntry>(Arrays.asList(getData()));

        BarDataSet barDataSet = new BarDataSet(entries, "Runs");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
    }


    private BarEntry[] getData() {
        // Read data from database
        String[] columns = {"xValues", "yValues"};
        Cursor cursor = sqLiteDatabase.query("RunTimes", columns, null,null,null,null,null);
        BarEntry[] be = new BarEntry[cursor.getCount()];
        for(int i = 0; i<cursor.getCount();i++){

            cursor.moveToNext();
            be[i] = new BarEntry(cursor.getInt(0),cursor.getInt(1));
        }
        return be;
    }



}
