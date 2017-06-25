package com.example.root.habit_tracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.root.habit_tracker.data.HabitContract;
import com.example.root.habit_tracker.data.HabitContract.HabitEntry;

/**
 * Created by root on 6/25/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    /**
     * Database version. If you change the database schema, you must increment the database version
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Name of the database file
     */
    public static final String DATABASE_NAME = "habit.db";

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry.COLUMN_HABIT_TIME + " INTEGER NOT NULL (strftime('%s', 'now')), "
                + HabitEntry.COLUMN_HABIT_ACTIVITY + " INTEGER, "
                + HabitEntry.COLUMN_HABIT_NOTES + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
