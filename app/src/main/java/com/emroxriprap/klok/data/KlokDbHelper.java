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
                KlokContract.KlokEntry._ID + " INTEGER PRIMARY KEY," +
                KlokContract.KlokEntry.COLUMN_JOB_NAME + " TEXT NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                KlokContract.KlokEntry.COLUMN_DATE_STRING + " TEXT NOT NULL, " +
//                KlokContract.KlokEntry.COLUMN_ADDRESS_ONE + " TEXT NOT NULL, " +
//                KlokContract.KlokEntry.COLUMN_ADDRESS_TWO + " TEXT NOT NULL," +
//
//                KlokContract.KlokEntry.COLUMN_CITY + " TEXT NOT NULL, " +
//                KlokContract.KlokEntry.COLUMN_STATE + " TEXT NOT NULL, " +

                KlokContract.KlokEntry.COLUMN_HOURS + " REAL NOT NULL);";
        final String SQL_CREATE_JOBS_TABLE = "CREATE TABLE " + KlokContract.JobEntry.TABLE_NAME + " (" +
                KlokContract.JobEntry._ID + " INTEGER PRIMARY KEY," +
                KlokContract.JobEntry.COLUMN_JOB_NAME + " TEXT NOT NULL, " +
                KlokContract.JobEntry.COLUMN_ADDRESS_ONE + " TEXT NOT NULL, " +
                KlokContract.JobEntry.COLUMN_ADDRESS_TWO + " TEXT NOT NULL," +

                KlokContract.JobEntry.COLUMN_CITY + " TEXT NOT NULL, " +
                KlokContract.JobEntry.COLUMN_STATE + " TEXT NOT NULL);";


        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
        db.execSQL(SQL_CREATE_JOBS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + KlokContract.KlokEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + KlokContract.JobEntry.TABLE_NAME);
    }
}
