package com.example.root.habit_tracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v4.text.TextUtilsCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.example.root.habit_tracker.data.HabitContract.HabitEntry;
import com.example.root.habit_tracker.data.HabitDbHelper;

import java.util.Date;
import java.util.Locale;

import static com.example.root.habit_tracker.data.HabitContract.HabitEntry.*;

public class HabitActivity extends AppCompatActivity {

    /**
     * Database helper that will provide and stored in the app.
     */
    private HabitDbHelper mDbHelper;

    /**
     * EditText field to enter the user's notes
     */
    private EditText mAddNotes;

    /**
     * Spinner to choose the activity
     */
    private Spinner mActivitySpinner;

    /**
     * Activity choices. The possible values are:
     * 0 for reading,
     * 1 for training
     * 2 for coding
     * 3 for yoga
     * 4 for taking medications
     * 5 for meditation
     * 6 for making dinner
     */
    private int mActivity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Find relevant views that are needed to read user input
        mAddNotes = (EditText) findViewById(R.id.add_notes);
        mActivitySpinner = (Spinner) findViewById(R.id.spinner_activity);

        // Insert Dummy Data into the database when addDummyData Button is clicked
        Button addDummyData = (Button) findViewById(R.id.add_database);
        if (addDummyData != null) {
            addDummyData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertDummyHabit();
                    displayDatabaseInfo();
                }
            });
        }

        // Insert user's Data into the database when addUserHabit Button is clicked
        Button addUserHabit = (Button) findViewById(R.id.add_activity_button);
        if (addUserHabit != null) {
            addUserHabit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertHabit();
                    displayDatabaseInfo();
                }
            });
        }

        // To access the database, instantiate the subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter activitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_activity_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view 1 item per line
        activitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mActivitySpinner.setAdapter(activitySpinnerAdapter);

        // Set the integer mActivity to the constant values
        mActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.activity_no_activity))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_NO_ACTIVITY; // No Activity
                    } else if (selection.equals(getString(R.string.activity_reading))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_READING; // Reading
                    } else if (selection.equals(getString(R.string.activity_coding))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_CODING; // Coding
                    } else if (selection.equals(getString(R.string.activity_training))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_TRAINING; // Training
                    } else if (selection.equals(getString(R.string.activity_medications))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_TAKING_MEDICATIONS; // Taking Medications
                    } else if (selection.equals(getString(R.string.activity_yoga))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_YOGA; // Yoga
                    } else if (selection.equals(getString(R.string.activity_meditation))) {
                        mActivity = HabitEntry.ACTIVITY_HABIT_MEDITATION; // Meditation
                    } else {
                        mActivity = HabitEntry.ACTIVITY_HABIT_DINNER; // Making Dinner
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mActivity = 0; // No Activity
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the habits database.
     */
    private void displayDatabaseInfo() {
        // To access the database, instantiate the subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // will be used after this query.
        String[] projection = {
                COLUMN_HABIT_TIME,
                COLUMN_HABIT_ACTIVITY,
                COLUMN_HABIT_NOTES
        };

        Cursor cursor = db.query(
                TABLE_NAME,
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
            displayDatabase.append(COLUMN_HABIT_TIME + " - " +
                    COLUMN_HABIT_ACTIVITY + " - " +
                    COLUMN_HABIT_NOTES + "\n");
            displayDatabase.append("\nThe Activities are as follow:\n" +
                    "0 - No Activity; " + "1 - Reading; " + "2 - Coding; " + "3 - Training; " +
                    "4 - Taking Medications; " + "5 - Yoga; " + "6 - Meditation; " +
                    "7 - Making Dinner;" + "\n");

            // Figure out the index of each column
            int timeColumnIndex = cursor.getColumnIndex(COLUMN_HABIT_TIME);
            int activityColumnIndex = cursor.getColumnIndex(COLUMN_HABIT_ACTIVITY);
            int notesColumnIndex = cursor.getColumnIndex(COLUMN_HABIT_NOTES);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or int value of the word
                // at the current row the cursor is on.
                String currentTime = cursor.getString(timeColumnIndex);
                int currentActivity = cursor.getInt(activityColumnIndex);
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
     * Helper method to format the date and time
     *
     * @return datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        Log.v("HabitActivity", "New row ID " + dateFormat);
        return dateFormat.format(date);
    }

    /**
     * Insert and save new habit into the database
     */
    private void insertDummyHabit() {
        // Create the database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Get the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and populate the values with some dummy data
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_TIME, getDateTime());
        values.put(COLUMN_HABIT_ACTIVITY, 4);
        values.put(COLUMN_HABIT_NOTES, "Today I was lazy.");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.v("HabitActivity", "New row ID " + newRowId);
    }

    /**
     * Insert and save new habit into the database
     */
    private void insertHabit() {
        String notesString = mAddNotes.getText().toString().trim();

        // Create the database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Get the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and populate the values with the data from the user input
        ContentValues valuesOfUser = new ContentValues();
        valuesOfUser.put(COLUMN_HABIT_TIME, getDateTime());
        valuesOfUser.put(COLUMN_HABIT_ACTIVITY, mActivity);
        valuesOfUser.put(COLUMN_HABIT_NOTES, notesString);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, valuesOfUser);

        Log.v("HabitActivity", "New row ID " + newRowId);
    }
}
