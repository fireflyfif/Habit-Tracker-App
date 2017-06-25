package com.example.root.habit_tracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.habit_tracker.data.HabitContract.HabitEntry;
import com.example.root.habit_tracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    /**
     * Database helper that will provide and stored in the app.
     */
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // To access the database, instantiate the subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the habits database.
     */
    private void displayDatabaseInfo() {
        // To access the database, instantiate the subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // will be used after this query.
        String[] projection = {
                HabitEntry.COLUMN_HABIT_TIME,
                HabitEntry.COLUMN_HABIT_ACTIVITY,
                HabitEntry.COLUMN_HABIT_NOTES
        };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayDatabase = (TextView) findViewById(R.id.database_text_view);

        try {
            // Create a header in the Text View that looks like this:
            //
            // Habits table contains <number of rows in Cursor> habits.
            // time - activity - notes
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayDatabase.setText("The Habits table contains " + cursor.getCount() +
                    " habit entries.\n\n");
            displayDatabase.append(HabitEntry.COLUMN_HABIT_TIME + " - " +
                            HabitEntry.COLUMN_HABIT_ACTIVITY + " - " +
                            HabitEntry.COLUMN_HABIT_NOTES + "\n");

            // Figure out the index of each column
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);
            int activityColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_ACTIVITY);
            int notesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NOTES);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or int value of the word
                // at the current row the cursor is on.
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentActivity = cursor.getInt(timeColumnIndex);
                String currentNotes = cursor.getString(notesColumnIndex);
                // Display the values from each column of the current row in the cursor in
                // the TextView
                displayDatabase.append(("\n" + currentTime + " - " +
                currentActivity + " - " +
                currentNotes));
            }
        } finally {
            // Always close the cursor when done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    /**
     * Insert and save new habit into the database
     */
    private void insertHabit() {
        // Get the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues obejct where column names are the keys,
        // and populate the values with some dummy data
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_TIME, "");
        values.put(HabitEntry.COLUMN_HABIT_ACTIVITY, 5);
        values.put(HabitEntry.COLUMN_HABIT_NOTES, "Today I was lazy.");
    }
}
