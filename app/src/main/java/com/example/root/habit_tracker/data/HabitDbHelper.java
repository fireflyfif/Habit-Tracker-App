package com.example.root.habit_tracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.root.habit_tracker.data.HabitContract;
import com.example.root.habit_tracker.data.HabitContract.HabitEntry;

/**
 * Created by root on 6/25/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    /**
     * Database version. If you change the database schema, you must increment the database version
     */
    public static final int DATABASE_VERSION = 2;

    /**
     * Name of the database file
     */
    public static final String DATABASE_NAME = "habit_tracker.db";

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry.COLUMN_HABIT_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + HabitEntry.COLUMN_HABIT_ACTIVITY + " TEXT, "
                + HabitEntry.COLUMN_HABIT_NOTES + " TEXT);";

        Log.v("HabitDbHelper", "SQL statement for creating the table " + SQL_CREATE_HABIT_TABLE);

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABIT_TABLE);

        // HabitEntry.COLUMN_HABIT_TIME + " INTEGER NOT NULL DEFAULT (strftime('%s', 'now')), "
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
