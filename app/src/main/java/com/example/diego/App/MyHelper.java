package com.example.diego.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class MyHelper  extends SQLiteOpenHelper {
    private Context con;
    public MyHelper(Context context) {
        super(context,"MyDatabase",null,1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="create table RunTimes(xValues DOUBLE, yValues DOUBLE);";
        db.execSQL(createTable);
        Toast.makeText(con,"Table Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(double valX, double valY)

    {SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("xValues",valX);
        contentValues.put("yValues",valY);

        db.insert("RunTimes", null,contentValues);
        Toast.makeText(con,"Data Inserted", Toast.LENGTH_LONG).show();

    }
}
