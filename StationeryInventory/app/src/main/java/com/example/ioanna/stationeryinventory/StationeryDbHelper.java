package com.example.ioanna.stationeryinventory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.ioanna.stationeryinventory.StationeryContract.SQL_CREATE_STATIONERY_ENTRIES;
import static com.example.ioanna.stationeryinventory.StationeryContract.SQL_DELETE_STATIONERY_ENTRIES;

/**
 * Created by Ioanna on 24/07/2017.
 */

public class StationeryDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Stationery.db";

    public StationeryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //Create Stationery table
        db.execSQL(SQL_CREATE_STATIONERY_ENTRIES);
        Log.i("onCreate", "Table has been created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_STATIONERY_ENTRIES);
        Log.i("onUpgrade", "Table has been deleted");
        onCreate(db);
        Log.i("onCreate", "Table has been created");
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
