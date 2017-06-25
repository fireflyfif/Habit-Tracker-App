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
        public static final String ACTIVITY_HABIT_READING = "reading";
        public static final String ACTIVITY_HABIT_TRAINING = "training";
        public static final String ACTIVITY_HABIT_CODING = "coding";
        public static final String ACTIVITY_HABIT_YOGA = "yoga";
        public static final String ACTIVITY_HABIT_TAKING_MEDICATIONS = "taking medications";
        public static final String ACTIVITY_HABIT_MEDITATION = "meditation";
        public static final String ACTIVITY_HABIT_DINNER = "making dinner";

    }
}
