package com.emroxriprap.klok.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by administrator on 7/30/15.
 */
public class KlokDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "KlokData.db";

    public KlokDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ENTRIES_TABLE = "CREATE TABLE " + KlokContract.KlokEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                KlokContract.KlokEntry._ID + " INTEGER PRIMARY KEY," +

                // the ID of the location entry associated with this weather data
                KlokContract.KlokEntry.COLUMN_JOB_NAME + " INTEGER NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_ADDRESS_ONE + " TEXT NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_ADDRESS_TWO + " INTEGER NOT NULL," +

                KlokContract.KlokEntry.COLUMN_CITY + " REAL NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_STATE + " REAL NOT NULL, " +

                KlokContract.KlokEntry.COLUMN_HOURS + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_ENTRIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + KlokContract.KlokEntry.TABLE_NAME);

    }
}
