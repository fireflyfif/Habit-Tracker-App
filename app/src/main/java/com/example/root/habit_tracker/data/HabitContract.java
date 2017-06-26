package com.example.root.habit_tracker.data;

import android.provider.BaseColumns;

/**
 * Created by root on 6/25/17.
 */

public class HabitContract {

    private HabitContract() {
    }

    public static abstract class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_HABIT_TIME = "time";
        public static final String COLUMN_HABIT_ACTIVITY = "activity";
        public static final String COLUMN_HABIT_NOTES = "notes";

        /**
         * Activities constants
         */
        public static final int ACTIVITY_HABIT_READING = 0;
        public static final int ACTIVITY_HABIT_CODING = 1;
        public static final int ACTIVITY_HABIT_TRAINING = 2;
        public static final int ACTIVITY_HABIT_TAKING_MEDICATIONS = 3;
        public static final int ACTIVITY_HABIT_YOGA = 4;
        public static final int ACTIVITY_HABIT_MEDITATION = 5;
        public static final int ACTIVITY_HABIT_DINNER = 6;
        public static final int ACTIVITY_HABIT_NO_ACTIVITY = 7;
    }
}
