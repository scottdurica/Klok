package com.emroxriprap.klok.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by administrator on 7/30/15.
 */
public class KlokProvider extends ContentProvider{

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private KlokDbHelper mOpenHelper;

    public static final int ALL_ENTRIES = 100;
    public static final int SINGLE_ENTRY = 101;
    public static final int ALL_JOBS = 102;
    public static final int SINGLE_JOB = 103;
//    public static final int WEATHER_WITH_LOCATION_AND_DATE = 102;
//    public static final int LOCATION = 300;

    @Override
    public boolean onCreate() {
        mOpenHelper = new KlokDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case ALL_ENTRIES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KlokContract.KlokEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "location"
            case SINGLE_ENTRY: {
                retCursor = getEntry(uri, projection, sortOrder,SINGLE_ENTRY);
                break;
            }
            case ALL_JOBS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KlokContract.JobEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SINGLE_JOB: {
                retCursor = getEntry(uri, projection,sortOrder,SINGLE_JOB);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getEntry(Uri uri, String[] projection, String sortOrder,int requester) {

        switch (requester){
            case SINGLE_ENTRY: {
                String id = KlokContract.KlokEntry.getEntryIdFromUri(uri);

                return mOpenHelper.getReadableDatabase().query(
                        KlokContract.KlokEntry.TABLE_NAME,
                        projection,
                        "WHERE id = ?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
            }
            case SINGLE_JOB: {
                String id = KlokContract.JobEntry.getEntryIdFromUri(uri);

                return mOpenHelper.getReadableDatabase().query(
                        KlokContract.JobEntry.TABLE_NAME,
                        projection,
                        "WHERE id = ?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder);
            }
            default:
                throw new UnsupportedOperationException("Couldn't retreive data from: " + uri);
        }


    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case ALL_ENTRIES: {
//                normalizeDate(values);
                long _id = db.insert(KlokContract.KlokEntry.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = KlokContract.KlokEntry.buildEntriesUri(_id);
                    Toast.makeText(getContext(), "Inserted into Entries DB", Toast.LENGTH_SHORT).show();
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case ALL_JOBS: {
                long _id = db.insert(KlokContract.JobEntry.TABLE_NAME, null, values);
                if (_id > 0){
                    returnUri = KlokContract.JobEntry.buildEntriesUri(_id);
                    Toast.makeText(getContext(), "Inserted into Jobs DB", Toast.LENGTH_SHORT).show();

                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }
//    private void normalizeDate(ContentValues values) {
//        // normalize the date value
//        if (values.containsKey(KlokContract.KlokEntry.COLUMN_DATE)) {
//            long dateValue = values.getAsLong(KlokContract.KlokEntry.COLUMN_DATE);
//            values.put(KlokContract.KlokEntry.COLUMN_DATE, KlokContract.normalizeDate(dateValue));
//        }
//    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match){
            case ALL_ENTRIES:
                return KlokContract.KlokEntry.CONTENT_TYPE;
            case SINGLE_ENTRY:
                return KlokContract.KlokEntry.CONTENT_ITEM_TYPE;
            case ALL_JOBS:
                return KlokContract.JobEntry.CONTENT_TYPE;
            case SINGLE_JOB:
                return KlokContract.JobEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    public static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher matcher = new UriMatcher((UriMatcher.NO_MATCH));
        final String authority = KlokContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority,KlokContract.PATH_ENTRIES,ALL_ENTRIES);
        matcher.addURI(authority,KlokContract.PATH_ENTRIES + "/#", SINGLE_ENTRY);
        matcher.addURI(authority,KlokContract.PATH_JOB,ALL_JOBS);
        matcher.addURI(authority,KlokContract.PATH_JOB + "/#", SINGLE_JOB);
//        matcher.addURI(authority,WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);
//        matcher.addURI(authority,WeatherContract.PATH_LOCATION,LOCATION);


        // 3) Return the new matcher!
        return matcher;
    }
}
